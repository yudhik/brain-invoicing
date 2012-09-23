package com.brainmaster.mobitronik.processor;

import java.util.List;

import com.brainmaster.mobitronik.dto.ProductStoreData;
import com.brainmaster.mobitronik.dto.StoreData;

public interface ProductStoreProcessor {

	public List<ProductStoreData> getProductStores();

	public void saveProductStore(ProductStoreData productStoreData);

	public void updateProductQuantityAtStore(ProductStoreData productStoreData);

	public List<ProductStoreData> getProductStoresByActiveStore(
			StoreData selectedStore);
}
