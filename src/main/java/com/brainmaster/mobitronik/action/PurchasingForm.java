package com.brainmaster.mobitronik.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.SequenceNumberGeneratorService;
import com.brainmaster.mobitronik.dto.InvoiceData;
import com.brainmaster.mobitronik.dto.InvoiceDetailData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.DocumentType;

@Component
@Scope("session")
public class PurchasingForm {
	
	@Autowired
	private SequenceNumberGeneratorService sequenceNumberGeneratorService;

	private InvoiceData purchaseData = new InvoiceData(UUID.randomUUID(), DocumentType.PURCHASE_INVOICE);
	private StoreData buyerStore = new StoreData();
	private InvoiceDetailData detail = new InvoiceDetailData();
	private List<InvoiceDetailData> details = new ArrayList<InvoiceDetailData>();
	private InvoiceData selectedPurchase;
	private Date selectedDate;
	private boolean useAdvancedSearch;
	private String uuidParam;
	private String documentTypeParam;
	private String searchBy;
	private String searchTerms;

	public InvoiceData getPurchaseData() {
		purchaseData.setSequenceNumber(sequenceNumberGeneratorService.getNextSequence(DocumentType.PURCHASE_INVOICE));
		return purchaseData;
	}

	public void setPurchaseData(InvoiceData purchaseData) {
		this.purchaseData = purchaseData;
	}

	public StoreData getBuyerStore() {
		return buyerStore;
	}

	public void setBuyerStore(StoreData buyerStore) {
		this.buyerStore = buyerStore;
	}

	public InvoiceData getSelectedPurchase() {
		return selectedPurchase;
	}

	public void setSelectedPurchase(InvoiceData selectedPurchase) {
		this.selectedPurchase = selectedPurchase;
	}

	public List<InvoiceDetailData> getDetails() {
		return details;
	}

	public void setDetails(List<InvoiceDetailData> details) {
		this.details = details;
	}

	public InvoiceDetailData getDetail() {
		return detail;
	}

	public void setDetail(InvoiceDetailData detail) {
		this.detail = detail;
	}

	public void createNewPurchase() {
		purchaseData = new InvoiceData(UUID.randomUUID(), DocumentType.PURCHASE_INVOICE);
		details = new ArrayList<InvoiceDetailData>();
	}

	public void addDetail() {
		purchaseData.getInvoiceDetails().add(detail);
		detail = new InvoiceDetailData();
	}

	public boolean isUseAdvancedSearch() {
		return useAdvancedSearch;
	}

	public void setUseAdvancedSearch(boolean useAdvancedSearch) {
		this.useAdvancedSearch = useAdvancedSearch;
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getUuidParam() {
		return uuidParam;
	}

	public void setUuidParam(String uuidParam) {
		this.uuidParam = uuidParam;
	}

	public String getDocumentTypeParam() {
		return documentTypeParam;
	}

	public void setDocumentTypeParam(String documentTypeParam) {
		this.documentTypeParam = documentTypeParam;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}

	public boolean isTextTerms() {
		if (searchBy != null && (searchBy.equalsIgnoreCase("code") || searchBy.equalsIgnoreCase("name")))
			return true;
		return false;
	}

	public boolean isBrandTerms() {
		if (searchBy != null && searchBy.equalsIgnoreCase("brand"))
			return true;
		return false;
	}

	public boolean isCategoryTerms() {
		if (searchBy != null && searchBy.equalsIgnoreCase("category"))
			return true;
		return false;
	}
}