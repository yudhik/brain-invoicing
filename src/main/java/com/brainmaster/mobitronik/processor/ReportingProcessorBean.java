package com.brainmaster.mobitronik.processor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DocumentSource;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.InvoiceTransactionService;
import com.brainmaster.mobitronik.bean.service.ProductService;
import com.brainmaster.mobitronik.bean.service.ProductStoreService;
import com.brainmaster.mobitronik.bean.service.RetailDocumentService;
import com.brainmaster.mobitronik.bean.service.StoreService;
import com.brainmaster.mobitronik.dto.IncomeReportData;
import com.brainmaster.mobitronik.dto.InvoiceDetailData;
import com.brainmaster.mobitronik.dto.StockReportData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.CompositeProductStore;
import com.brainmaster.mobitronik.model.DocumentType;
import com.brainmaster.mobitronik.model.InvoiceTransaction;
import com.brainmaster.mobitronik.model.InvoiceTransactionDetail;
import com.brainmaster.mobitronik.model.ProductStore;
import com.brainmaster.mobitronik.model.ProductStorePriceAccumulation;
import com.brainmaster.mobitronik.model.ProductStoreTransaction;
import com.brainmaster.mobitronik.model.RetailDocument;
import com.brainmaster.mobitronik.model.Store;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Component
@Scope("prototype")
public class ReportingProcessorBean implements ReportingProcessor {

	private static final Logger log = LoggerFactory.getLogger(ReportingProcessorBean.class);

	@Autowired
	private InvoiceTransactionService invoiceTransactionService;

	@Autowired
	private ProductStoreService productStoreService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private RetailDocumentService retailDocumentService;

	private TransformerFactory factory = TransformerFactory.newInstance(
			"net.sf.saxon.TransformerFactoryImpl", null);

	@Override
	public List<IncomeReportData> getSellerIncomeReports(StoreData sellerStore, Date start, Date end) {
		Store store = storeService.find(UUIDHelper.stringToUUID(sellerStore.getUuid()));
		List<InvoiceTransaction> invoiceList = invoiceTransactionService.getInvoiceForSellerWithinDate(store, start, end);
		List<IncomeReportData> incomeReportDatas = new ArrayList<IncomeReportData>();
		for (InvoiceTransaction invoiceTransaction : invoiceList) {
			invoiceTransaction = invoiceTransactionService.populateInvoiceDetail(invoiceTransaction);
			BigDecimal profitGain = BigDecimal.ZERO;
			List<InvoiceDetailData> invoiceDetailDatas = new ArrayList<InvoiceDetailData>();
			for (InvoiceTransactionDetail detail : invoiceTransaction.getTransactions()) {
				CompositeProductStore productStoreId = new CompositeProductStore(productService.find(detail.getProduct()), store);
				ProductStorePriceAccumulation productStorePriceAccumulation;
				Integer quantity = detail.getSerialNumber().equals("-") ? detail.getQuantity() : detail.getSerialNumber().contains(",") ? detail
						.getSerialNumber().split(",").length : 1;
				BigDecimal lineItemTotal = new BigDecimal(quantity).multiply(detail.getPrice());
				try {
					productStorePriceAccumulation = productStoreService.findLatestProductStorePriceAccumulation(productStoreService
							.findStoreQuantity(productStoreId));
					BigDecimal lineItemBaseTotal = BigDecimal.ZERO; 
					if(productStorePriceAccumulation != null)
						lineItemBaseTotal = new BigDecimal(quantity).multiply(productStorePriceAccumulation.getAveragePrice());
					log.debug("line item total : {}, line item base total : {}", new String[] { lineItemTotal.toPlainString(),
							lineItemBaseTotal.toPlainString() });
					log.debug("qty detail : {}, price detail : {}, average price : {}", new String[] { quantity.toString(),
							detail.getPrice().toPlainString(), productStorePriceAccumulation.getAveragePrice().toPlainString() });
					profitGain = profitGain.add(lineItemTotal.subtract(lineItemBaseTotal));
				} catch (NoResultException e) {
					log.error("can not find productStore price accumulation for : " + productStoreId.toString());
					profitGain = profitGain.add(lineItemTotal);
				}
				invoiceDetailDatas.add(InvoiceDetailData.convertInvoiceTransaction(detail));
			}
			IncomeReportData incomeReportData = new IncomeReportData(invoiceTransaction.getUuid(), invoiceTransaction.getInvoiceNumber(),
					invoiceTransaction.getInvoiceDate(), invoiceTransaction.getGrandTotal(), sellerStore, StoreData.convertStore(invoiceTransaction
							.getBuyer()), invoiceDetailDatas, profitGain);
			incomeReportDatas.add(incomeReportData);
		}
		return incomeReportDatas;
	}

	@Override
	public String getTransformationResult(String uuidParam, String documentTypeParam) {
		if (uuidParam != null && documentTypeParam != null && DocumentType.INVOICE.toString().equalsIgnoreCase(documentTypeParam)) {
			try {
				InputStream templateIn = Thread.currentThread().getContextClassLoader().getResourceAsStream("/stylesheets/invoice.xslt");
				RetailDocument document = null;
				try {
					document = retailDocumentService.find(UUIDHelper.stringToUUID(uuidParam), true);
				} catch (Exception e) {
					log.error("something is not right here", e);
				}
				Source template = new StreamSource(templateIn);
				Source source = document.getDocumentContent().getDataAsSource();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				Result result = new StreamResult(out);
				factory.newTransformer(template).transform(source, result);
				return new String(out.toByteArray());
			} catch (Exception e) {
				log.error("cannot generate output", e);
			}
		}
		return null;
	}

	public List<StockReportData> getSellerProductReports(StoreData sellerStore, Date start, Date end) {
		List<ProductStore> productStores = storeService.findProductForStore(storeService.find(UUIDHelper.stringToUUID(sellerStore.getUuid())));
		List<StockReportData> stockReportDatas = new ArrayList<StockReportData>();
		for (ProductStore productStore : productStores) {
			ProductStorePriceAccumulation storePriceAccumulation = null;
			ProductStoreTransaction productStoreQuantityTransaction = null;
			BigDecimal averagePrice = BigDecimal.ZERO;
			Integer latestQuantity = productStore.getQuantity();
			try {
				storePriceAccumulation = productStoreService.findLatestProductStorePriceAccumulation(productStore);
			} catch (NoResultException e) {
				log.warn("no result found for store price accumulation, using default value : " + averagePrice.toPlainString());
			}
			try {
				productStoreQuantityTransaction = productStoreService.findLatestProductStoreTransaction(productStore);
			} catch (NoResultException e) {
				log.warn("no result found for store price accumulation, using default value : " + productStore.getQuantity());
			}
			if (storePriceAccumulation != null)
				averagePrice = storePriceAccumulation.getAveragePrice();
			if (productStoreQuantityTransaction != null)
				latestQuantity = Long.valueOf(latestQuantity - productStoreQuantityTransaction.getOutstandingQuantity()).intValue();
			stockReportDatas.add(new StockReportData(productStore, averagePrice, latestQuantity));
		}
		return stockReportDatas;
	}

	@Override
	public String getReportTransformationResult(String sellerName, Date startDate, Date endDate, List<IncomeReportData> details, BigDecimal totalProfit) {
		if (details != null && details.size() > 0) {
			try {
				InputStream templateIn = Thread.currentThread().getContextClassLoader().getResourceAsStream("/stylesheets/reporting-profit.xslt");
				Source template = new StreamSource(templateIn);
				Source source = populateDocumentReport(sellerName, startDate, endDate, details, totalProfit);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				Result result = new StreamResult(out);
				factory.newTransformer(template).transform(source, result);
				return new String(out.toByteArray());
			} catch (Exception e) {
				log.error("cannot generate output", e);
			}
		}
		return null;
	}

	private Source populateDocumentReport(String sellerName, Date startDate, Date endDate, List<IncomeReportData> details, BigDecimal totalProfit) {
		Element rootElement = new DefaultElement("incomeReportDocument");
		Document document = DocumentHelper.createDocument(rootElement);
		
		Element companyInformation = new DefaultElement("companyInformation");
		Element period = new DefaultElement("period");
		Element summary = new DefaultElement("summary");
		Element reportItems = new DefaultElement("reportItems");
		
		//company information section
		Element companyName = companyInformation.addElement("companyName");
		companyName.setText(sellerName);
		Element companyAddress = companyInformation.addElement("companyAddress");
		Element address1 = companyAddress.addElement("address1");
		address1.setText("n/a");
		Element address2 = companyAddress.addElement("address2");
		address2.setText("n/a");
		Element phoneNumber = companyAddress.addElement("phoneNumber");
		phoneNumber.setText("n/a");
		
		
		//period section
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Element start = period.addElement("start");
		Element end = period.addElement("end");
		start.setText(formatter.format(startDate));
		end.setText(formatter.format(endDate));
		
		//summary section
		Element totalInvoice = summary.addElement("totalInvoice");
		Element profitSummary = summary.addElement("totalProfit");
		BigDecimal grandTotal = BigDecimal.ZERO;
		profitSummary.setText(totalProfit.setScale(2).toPlainString());
		
		//detail section
		for(IncomeReportData incomeReportData : details) {
			Element reportItem = reportItems.addElement("reportItem");
			reportItem.add(new DefaultAttribute("number", ""+details.indexOf(incomeReportData)+1));
			Element invoiceNumber = reportItem.addElement("invoiceNumber");
			Element invoiceDate = reportItem.addElement("invoiceDate");
			Element buyerName = reportItem.addElement("buyerName");
			Element total = reportItem.addElement("invoiceTotal");
			Element invoiceProfit = reportItem.addElement("invoiceProfit");
			
			invoiceNumber.setText(incomeReportData.getInvoiceNumber());
			invoiceDate.setText(formatter.format(incomeReportData.getInvoiceDate()));
			buyerName.setText(incomeReportData.getBuyer().getStoreName());
			total.setText(incomeReportData.getGrandTotal().setScale(2).toPlainString());
			grandTotal = grandTotal.add(incomeReportData.getGrandTotal().setScale(2));
			invoiceProfit.setText(incomeReportData.getInvoiceProfit().setScale(2).toPlainString());
		}
		
		totalInvoice.setText(grandTotal.setScale(2).toPlainString());
		
		rootElement.add(companyInformation);
		rootElement.add(period);
		rootElement.add(summary);
		rootElement.add(reportItems);
//		log.info("document xml : {}", document.asXML());
		return new DocumentSource(document);
	}
}
