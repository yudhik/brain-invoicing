package com.brainmaster.mobitronik.action;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.dto.StoreData;

@Component
@Scope("session")
public class PurchaseAdvancedSearchForm implements Serializable {

	private static final long serialVersionUID = -2196381975437903484L;

	private String selectedStore;

	private StoreData selectedSearchStore;

	private String invoiceNumber;

	private ProductData product;

	private Date selectionDate;

	public ProductData getProduct() {
		return product;
	}

	public void setProduct(ProductData product) {
		this.product = product;
	}

	public Date getSelectionDate() {
		return selectionDate;
	}

	public void setSelectionDate(Date selectionDate) {
		this.selectionDate = selectionDate;
	}

	public String getSelectedStore() {
		return selectedStore;
	}

	public void setSelectedStore(String selectedStore) {
		this.selectedStore = selectedStore;
	}

	public StoreData getSelectedSearchStore() {
		return selectedSearchStore;
	}

	public void setSelectedSearchStore(StoreData selectedSearchStore) {
		this.selectedSearchStore = selectedSearchStore;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

}
