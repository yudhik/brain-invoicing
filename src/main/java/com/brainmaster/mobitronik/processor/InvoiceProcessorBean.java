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

import com.brainmaster.mobitronik.action.InvoiceAdvancedSearchForm;
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
public class InvoiceProcessorBean implements InvoiceProcessor {

	private static final Logger log = LoggerFactory.getLogger(InvoiceProcessorBean.class);

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
	public UUID saveInvoice(InvoiceData invoiceData) throws Exception {
		return invoiceTransactionService.addInvoiceTransaction(invoiceData.getInvoiceEntity());
	}

	@Override
	public UUID saveInvoice(StoreData storeData, InvoiceData invoiceData) throws Exception {
		invoiceData.setSeller(storeData);
		return saveInvoice(invoiceData);
	}

	@Override
	public List<InvoiceData> getInvoicesFromSellerStoreForDate(StoreData storeData, Date selectedDate) {
		List<InvoiceData> invoiceList = new ArrayList<InvoiceData>();
		Store store = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		List<InvoiceTransaction> invoices = invoiceTransactionService.getInvoicesFromSellerStoreForDate(store, selectedDate);
		for (InvoiceTransaction invoice : invoices) {
			Store buyer = storeService.find(invoice.getBuyer().getUuid());
//			invoiceList.add(InvoiceData.convertInvoiceWithOutDetails(invoice, store, buyer));
			invoiceList.add(InvoiceData.convertInvoice(invoice, store, buyer));
			
		}
		return invoiceList;
	}

	@Override
	public List<InvoiceData> getInvoicesFromSellerStore(StoreData storeData) {
		List<InvoiceData> invoiceList = new ArrayList<InvoiceData>();
		Store store = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		List<InvoiceTransaction> invoices = invoiceTransactionService.getInvoicesFromSellerStore(store);
		for (InvoiceTransaction invoice : invoices) {
			Store buyer = storeService.find(invoice.getBuyer().getUuid());
			invoiceList.add(InvoiceData.convertInvoice(invoice,store, buyer));
		}
		return invoiceList;
	}

	@Override
	public List<InvoiceData> getInvoicesFromSellerStoreWithAdvanceSearchCriteria(StoreData storeData, ProductData productData, DocumentType documentType) {
		List<InvoiceData> invoiceList = new ArrayList<InvoiceData>();
		Store store = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		Product product = productService.find(productData.getUuid());
		List<InvoiceTransaction> invoices = invoiceTransactionService.getInvoicesFromSellerStoreWithAdvanceSearchCriteria(store, product, documentType);
		for (InvoiceTransaction invoice : invoices) {
			Store buyer = storeService.find(invoice.getBuyer().getUuid());
			invoiceList.add(InvoiceData.convertInvoice(invoice, store, buyer));
		}
		return invoiceList;
	}

	@Override
	public List<InvoiceData> getInvoicesFromBuyerStoreWithAdvanceSearchCriteria(StoreData storeData, ProductData productData, DocumentType documentType) {
		List<InvoiceData> invoiceList = new ArrayList<InvoiceData>();
		Store store = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		Product product = productService.find(productData.getUuid());
		List<InvoiceTransaction> invoices = invoiceTransactionService.getInvoicesFromBuyerStoreWithAdvanceSearchCriteria(store, product, documentType);
		for (InvoiceTransaction invoice : invoices) {
			Store buyer = storeService.find(invoice.getBuyer().getUuid());
			invoiceList.add(InvoiceData.convertInvoice(invoice, store, buyer));
		}
		return invoiceList;
	}

	@Override
	public List<InvoiceData> getInvoiceSummaryWithReference(StoreData storeData, String invoiceNumber) {
		List<InvoiceData> invoiceList = new ArrayList<InvoiceData>();
		Store store = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		List<InvoiceTransaction> invoices = invoiceTransactionService.getInvoiceWithReference(invoiceNumber, false);
		for (InvoiceTransaction invoice : invoices) {
			Store buyer = storeService.find(invoice.getBuyer().getUuid());
			invoiceList.add(InvoiceData.convertInvoiceWithOutDetails(invoice, store, buyer));
		}
		return invoiceList;
	}

	@Override
	public List<InvoiceData> getInvoiceList(Date selectedDate, StoreData storeData) {
		if (selectedDate == null)
			return getInvoicesFromSellerStore(storeData);
		return getInvoicesFromSellerStoreForDate(storeData, selectedDate);
	}

	@Override
	public List<InvoiceData> getInvoiceList(Date selectedDate, StoreData storeData, InvoiceAdvancedSearchForm advancedSearchForm, DocumentType documentType) {
		if (!StringUtils.isEmpty(advancedSearchForm.getSelectedStore()) && advancedSearchForm.getSelectedStore() != null &&
				advancedSearchForm.getProduct() != null) {
			if ("seller".equals(advancedSearchForm.getSelectedStore())) {
				return getInvoicesFromSellerStoreWithAdvanceSearchCriteria(new StoreData(UUIDHelper.stringToUUID(advancedSearchForm
						.getSelectedSearchStore().getUuid())), advancedSearchForm.getProduct(), documentType);
			}
			if ("buyer".equals(advancedSearchForm.getSelectedStore())) {
				return getInvoicesFromBuyerStoreWithAdvanceSearchCriteria(new StoreData(UUIDHelper
						.stringToUUID(advancedSearchForm.getSelectedSearchStore().getUuid())), advancedSearchForm.getProduct(), documentType);
			}
		} else if (!StringUtils.isEmpty(advancedSearchForm.getInvoiceNumber())) {
			return getInvoiceSummaryWithReference(storeData, advancedSearchForm.getInvoiceNumber());
		}
		return null;
	}

	@Override
	public String getTransformationResult(String uuidParam, String documentTypeParam) {
		if (uuidParam != null && documentTypeParam != null
				&& DocumentType.INVOICE.toString().equalsIgnoreCase(documentTypeParam)) {
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
