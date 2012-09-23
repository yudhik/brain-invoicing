package com.brainmaster.mobitronik.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.ProductStoreService;
import com.brainmaster.mobitronik.dto.ProductStoreData;
import com.brainmaster.mobitronik.model.ProductStore;
import com.brainmaster.mobitronik.primefaces.model.ProductStoreDataModel;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Component
@Scope("session")
public class ProductStoreOpnamePage {

	private static final Logger log = LoggerFactory.getLogger(ProductStorePage.class);

	@Autowired
	private ProductStoreService productStoreService;

	private ProductStoreData productStore = new ProductStoreData();
	private ProductStoreData selectedProductStore;
	private List<ProductStoreData> productStoreDatas;

	private Long start;
	private static final Long RELOAD_EVERY = 30000L;

	private void checkTimeout() {
		if (start == null) {
			start = new Date().getTime();
			//			log.info("start at : "+ start.toString());
		}
		//		log.info("new Date().getTime() - start >= RELOAD_EVERY : "+ (new Date().getTime() - start >= RELOAD_EVERY));
		if (new Date().getTime() - start >= RELOAD_EVERY) {
			//			log.info("reload data");
			start = null;
			reloadData();
		}
	}

	private void reloadData() {
		getProductStores();
	}

	public ProductStoreDataModel getProductStoreDataModel() {
		checkTimeout();
		return new ProductStoreDataModel(getProductStores());
	}

	public List<ProductStoreData> getProductStores() {
		if (productStoreDatas == null || start == null) {
			productStoreDatas = new ArrayList<ProductStoreData>();
			for (ProductStore productStore : productStoreService.findAllStoreQuantity())
				productStoreDatas.add(ProductStoreData.convert(productStore));
		}
		return productStoreDatas;
	}

	private void invalidate() {
		productStoreDatas = null;
	}

	public ProductStoreData getProductStore() {
		return productStore;
	}

	public void setProductStore(ProductStoreData productStore) {
		this.productStore = productStore;
	}

	public void saveProductStore() {
		log.error(productStore.toString());
		productStoreService.updateQuantity(UUIDHelper.stringToUUID(selectedProductStore.getStore().getUuid()), selectedProductStore.getProduct()
				.getUuid(), selectedProductStore.getQuantity());
		invalidate();
		productStore = new ProductStoreData();
	}

	public ProductStoreData getSelectedProductStore() {
		return selectedProductStore;
	}

	public void setSelectedProductStore(ProductStoreData selectedProductStore) {
		log.info("selected Product = " + selectedProductStore.toString());
		this.selectedProductStore = selectedProductStore;
	}
}
