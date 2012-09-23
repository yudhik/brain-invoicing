package com.brainmaster.mobitronik.processor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.brainmaster.mobitronik.action.InvoiceAdvancedSearchForm;
import com.brainmaster.mobitronik.dto.InvoiceData;
import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.DocumentType;

public interface InvoiceProcessor {

	public List<InvoiceData> getInvoiceList(Date selectedDate, StoreData storeData);

	public List<InvoiceData> getInvoiceList(Date selectedDate, StoreData storeData, InvoiceAdvancedSearchForm advanceSearchForm, DocumentType documentType);

	public UUID saveInvoice(InvoiceData invoiceData) throws Exception;

	public UUID saveInvoice(StoreData storeData, InvoiceData invoiceData) throws Exception;
	
	public List<InvoiceData> getInvoicesFromSellerStoreForDate(StoreData storeData, Date selectedDate);

	public List<InvoiceData> getInvoicesFromSellerStore(StoreData storeData);

	public List<InvoiceData> getInvoicesFromSellerStoreWithAdvanceSearchCriteria(StoreData storeData, ProductData productData, DocumentType documentType);

	public List<InvoiceData> getInvoicesFromBuyerStoreWithAdvanceSearchCriteria(StoreData storeData, ProductData productData, DocumentType documentType);

	public List<InvoiceData> getInvoiceSummaryWithReference(StoreData storeData, String invoiceNumber);

	public String getTransformationResult(String uuidParam, String documentTypeParam);
}
