package com.brainmaster.mobitronik.processor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.brainmaster.mobitronik.action.PurchaseAdvancedSearchForm;
import com.brainmaster.mobitronik.dto.InvoiceData;
import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.DocumentType;

public interface PurchasingProcessor {

	public List<InvoiceData> getPurchasingList(Date selectedDate, StoreData storeData);

	public List<InvoiceData> getPurchasingList(Date selectedDate, StoreData storeData, PurchaseAdvancedSearchForm advanceSearchForm);

	public UUID savePurchasing(InvoiceData invoiceData) throws Exception;

	public UUID savePurchasing(StoreData storeData, InvoiceData invoiceData) throws Exception;

	public List<InvoiceData> getPurchasingFromBuyerStoreForDate(StoreData storeData, Date selectedDate);

	public List<InvoiceData> getPurchasingFromBuyerStore(StoreData storeData);

	public List<InvoiceData> getPurchasingFromBuyerStoreWithAdvanceSearchCriteria(StoreData storeData, ProductData productData, DocumentType documentType);

	public List<InvoiceData> getPurchasingFromSellerStoreWithAdvanceSearchCriteria(StoreData storeData, ProductData productData, DocumentType documentType);

	public List<InvoiceData> getPurchasingSummaryWithReference(StoreData storeData, String invoiceNumber);

	public String getTransformationResult(String uuidParam, String documentTypeParam);
}
