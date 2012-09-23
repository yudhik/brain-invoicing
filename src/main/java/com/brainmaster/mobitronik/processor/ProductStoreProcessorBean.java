package com.brainmaster.mobitronik.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.ProductStoreService;
import com.brainmaster.mobitronik.bean.service.StoreService;
import com.brainmaster.mobitronik.dto.ProductStoreData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.ProductStore;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Component
@Scope("prototype")
public class ProductStoreProcessorBean implements ProductStoreProcessor {

	@Autowired
	private ProductStoreService productStoreService;

	@Autowired
	private StoreService storeService;

	@Override
	public List<ProductStoreData> getProductStores() {
		List<ProductStoreData> productStoreDatas = new ArrayList<ProductStoreData>();
		for (ProductStore productStore : productStoreService.findAllStoreQuantity())
			productStoreDatas.add(ProductStoreData.convert(productStore));
		return productStoreDatas;
	}

	@Override
	public void saveProductStore(ProductStoreData productStoreData) {
		storeService.addProduct(UUIDHelper.stringToUUID(productStoreData.getStore().getUuid()), productStoreData.getProduct().getUuid());
	}

	@Override
	public List<ProductStoreData> getProductStoresByActiveStore(StoreData selectedStore) {
		List<ProductStoreData> productStores = new ArrayList<ProductStoreData>();
		for (ProductStore productStore : storeService.findProductForStore(selectedStore.getStoreEntity())) {
			productStores.add(ProductStoreData.convert(productStore));
		}
		return productStores;
	}

	@Override
	public void updateProductQuantityAtStore(ProductStoreData productStoreData) {
		storeService.updateQuantityOfProductAtStore(UUIDHelper.stringToUUID(productStoreData.getStore().getUuid()), productStoreData.getProduct()
				.getUuid(), productStoreData.getQuantity());
	}

}
