package com.brainmaster.mobitronik.processor;

import java.util.List;

import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.dto.StoreData;

public interface ProductProcessor {

	public List<ProductData> getProducts();

	public List<ProductData> getProductForStore(StoreData storeData);

	public void saveProduct(ProductData productData);
}
