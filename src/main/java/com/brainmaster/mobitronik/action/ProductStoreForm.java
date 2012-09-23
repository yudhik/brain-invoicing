package com.brainmaster.mobitronik.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.ProductStoreData;
import com.brainmaster.mobitronik.dto.StoreData;

@Component
@Scope("session")
public class ProductStoreForm {

	private ProductStoreData productStore = new ProductStoreData();
	private StoreData selectedStore;
	private StoreData detailStokSelected = new StoreData();
	private ProductStoreData selectedProductStore;

	public ProductStoreData getProductStore() {
		return productStore;
	}

	public void setProductStore(ProductStoreData productStore) {
		this.productStore = productStore;
	}

	public void createNewProductStore() {
		productStore = new ProductStoreData();
	}

	public StoreData getSelectedStore() {
		return selectedStore;
	}

	public void setSelectedStore(StoreData selectedStore) {
		this.selectedStore = selectedStore;
	}

	public StoreData getDetailStokSelected() {
		return detailStokSelected;
	}

	public void setDetailStokSelected(StoreData detailStokSelected) {
		this.detailStokSelected = detailStokSelected;
	}

	public ProductStoreData getSelectedProductStore() {
		return selectedProductStore;
	}

	public void setSelectedProductStore(ProductStoreData selectedProductStore) {
		this.selectedProductStore = selectedProductStore;
	}

}
