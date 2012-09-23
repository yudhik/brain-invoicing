package com.brainmaster.mobitronik.processor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.action.PurchaseAdvancedSearchForm;
import com.brainmaster.mobitronik.bean.service.InvoiceTransactionService;
import com.brainmaster.mobitronik.bean.service.ProductService;
import com.brainmaster.mobitronik.bean.service.RetailDocumentService;
import com.brainmaster.mobitronik.bean.service.StoreService;
import com.brainmaster.mobitronik.dto.InvoiceData;
import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.DocumentType;
import com.brainmaster.mobitronik.model.InvoiceTransaction;
import com.brainmaster.mobitronik.model.Product;
import com.brainmaster.mobitronik.model.RetailDocument;
import com.brainmaster.mobitronik.model.Store;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Component
@Scope("prototype")
public class PurchasingProcessorBean implements PurchasingProcessor {

	private static final Logger log = LoggerFactory.getLogger(PurchasingProcessorBean.class);

	@Autowired
	private InvoiceTransactionService invoiceTransactionService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private RetailDocumentService retailDocumentService;

	private TransformerFactory factory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", null);

	@Override
	public List<InvoiceData> getPurchasingList(Date selectedDate, StoreData storeData) {
		if (selectedDate == null)
			return getPurchasingFromBuyerStore(storeData);
		return getPurchasingFromBuyerStoreForDate(storeData, selectedDate);
	}

	@Override
	public List<InvoiceData> getPurchasingList(Date selectedDate, StoreData storeData, PurchaseAdvancedSearchForm advancedSearchForm) {
		if (!StringUtils.isEmpty(advancedSearchForm.getSelectedStore()) && advancedSearchForm.getSelectedStore() != null &&
				advancedSearchForm.getProduct() != null) {
			if ("seller".equals(advancedSearchForm.getSelectedStore())) {
				return getPurchasingFromSellerStoreWithAdvanceSearchCriteria(new StoreData(UUIDHelper.stringToUUID(advancedSearchForm
						.getSelectedStore())), advancedSearchForm.getProduct(), DocumentType.PURCHASE_INVOICE);
			}
			if ("buyer".equals(advancedSearchForm.getSelectedStore())) {
				return getPurchasingFromBuyerStoreWithAdvanceSearchCriteria(new StoreData(UUIDHelper.stringToUUID(advancedSearchForm
						.getSelectedStore())), advancedSearchForm.getProduct(), DocumentType.PURCHASE_INVOICE);
			}
		} else if (!StringUtils.isEmpty(advancedSearchForm.getInvoiceNumber())) {
			return getPurchasingSummaryWithReference(storeData, advancedSearchForm.getInvoiceNumber());
		}
		return null;
	}

	@Override
	public UUID savePurchasing(InvoiceData invoiceData) throws Exception {
		UUID uuid = null;
		try {
			uuid = invoiceTransactionService.addInvoiceTransaction(invoiceData.getInvoiceEntity());
		} catch (Exception e) {
			log.error("can not save purchasing", e);
		}
		return uuid;
	}

	@Override
	public UUID savePurchasing(StoreData storeData, InvoiceData invoiceData) throws Exception {
		invoiceData.setBuyer(storeData);
		return savePurchasing(invoiceData);
	}

	@Override
	public List<InvoiceData> getPurchasingFromBuyerStoreForDate(StoreData storeData, Date selectedDate) {
		List<InvoiceData> invoiceList = new ArrayList<InvoiceData>();
		Store store = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		List<InvoiceTransaction> invoices = invoiceTransactionService.getInvoicesFromBuyerStoreForDate(store, selectedDate);
		for (InvoiceTransaction invoice : invoices) {
			Store seller = storeService.find(invoice.getBuyer().getUuid());
			invoiceList.add(InvoiceData.convertInvoiceWithOutDetails(invoice,
					seller, store));
		}
		return invoiceList;
	}

	@Override
	public List<InvoiceData> getPurchasingFromBuyerStore(StoreData storeData) {
		List<InvoiceData> invoiceList = new ArrayList<InvoiceData>();
		Store store = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		List<InvoiceTransaction> invoices = invoiceTransactionService.getInvoicesFromBuyerStore(store);
		for (InvoiceTransaction invoice : invoices) {
			Store seller = storeService.find(invoice.getBuyer().getUuid());
			invoiceList.add(InvoiceData.convertInvoiceWithOutDetails(invoice, seller, store));
		}
		return invoiceList;
	}

	@Override
	public List<InvoiceData> getPurchasingFromBuyerStoreWithAdvanceSearchCriteria(StoreData storeData, ProductData productData, DocumentType documentType) {
		List<InvoiceData> invoiceList = new ArrayList<InvoiceData>();
		Store store = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		Product product = productService.find(productData.getUuid());
		List<InvoiceTransaction> invoices = invoiceTransactionService.getInvoicesFromBuyerStoreWithAdvanceSearchCriteria(store, product, documentType);
		for (InvoiceTransaction invoice : invoices) {
			Store seller = storeService.find(invoice.getBuyer().getUuid());
			invoiceList.add(InvoiceData.convertInvoiceWithOutDetails(invoice, seller, store));
		}
		return invoiceList;
	}

	@Override
	public List<InvoiceData> getPurchasingFromSellerStoreWithAdvanceSearchCriteria(StoreData storeData, ProductData productData, DocumentType documentType) {
		List<InvoiceData> invoiceList = new ArrayList<InvoiceData>();
		Store store = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		Product product = productService.find(productData.getUuid());
		List<InvoiceTransaction> invoices = invoiceTransactionService.getInvoicesFromSellerStoreWithAdvanceSearchCriteria(store, product, documentType);
		for (InvoiceTransaction invoice : invoices) {
			Store seller = storeService.find(invoice.getBuyer().getUuid());
			invoiceList.add(InvoiceData.convertInvoiceWithOutDetails(invoice, seller, store));
		}
		return invoiceList;
	}

	@Override
	public List<InvoiceData> getPurchasingSummaryWithReference(StoreData storeData, String invoiceNumber) {
		List<InvoiceData> invoiceList = new ArrayList<InvoiceData>();
		Store store = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		List<InvoiceTransaction> invoices = invoiceTransactionService.getInvoiceWithReference(invoiceNumber, false);
		for (InvoiceTransaction invoice : invoices) {
			Store seller = storeService.find(invoice.getBuyer().getUuid());
			invoiceList.add(InvoiceData.convertInvoiceWithOutDetails(invoice, seller, store));
		}
		return invoiceList;
	}

	@Override
	public String getTransformationResult(String uuidParam,
			String documentTypeParam) {
		if (uuidParam != null && documentTypeParam != null && DocumentType.PURCHASE_INVOICE.toString().equalsIgnoreCase(documentTypeParam)) {
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

}
