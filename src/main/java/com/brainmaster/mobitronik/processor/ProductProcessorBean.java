package com.brainmaster.mobitronik.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.ProductService;
import com.brainmaster.mobitronik.bean.service.StoreService;
import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.Product;
import com.brainmaster.mobitronik.model.ProductStore;
import com.brainmaster.mobitronik.model.Store;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Component
@Scope("prototype")
public class ProductProcessorBean implements ProductProcessor {

	@Autowired
	private ProductService productService;

	@Autowired
	private StoreService storeService;

	@Override
	public List<ProductData> getProducts() {
		List<ProductData> products = new ArrayList<ProductData>();
		for (Product brand : productService.findAll())
			products.add(ProductData.convertProduct(brand));
		return products;
	}

	@Override
	public List<ProductData> getProductForStore(StoreData storeData) {
		List<ProductData> productDatas = new ArrayList<ProductData>();
		Store OfficeStore = storeService.find(UUIDHelper.stringToUUID(storeData.getUuid()));
		List<ProductStore> productStores = storeService.findProductForStore(OfficeStore);
		for (ProductStore productStore : productStores) {
			productDatas.add(ProductData.convertProduct(productStore.getProduct()));
		}
		return productDatas;
	}

	@Override
	public void saveProduct(ProductData productData) {
		productService.addProduct(productData.getProductEntity());
	}

}
