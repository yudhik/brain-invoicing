package com.brainmaster.mobitronik.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.dto.ProductStoreData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.primefaces.model.ProductStoreDataModel;
import com.brainmaster.mobitronik.processor.ProductProcessor;
import com.brainmaster.mobitronik.processor.ProductStoreProcessor;

@Component
@Scope("request")
public class ProductStorePage {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ProductStorePage.class);

	@Autowired
	private ProductStoreProcessor productStoreProcessor;

	@Autowired
	private ProductProcessor productProcessor;

	@Autowired
	private ProductStoreForm productStoreForm;

	@Autowired
	private ActiveUser activeUser;

	public ProductStoreDataModel getProductStoreDataModel() {
		return new ProductStoreDataModel(getProductStores());
	}

	public List<ProductStoreData> getProductStores() {
		if (activeUser.isSuperUser())
			return productStoreProcessor.getProductStores();
		return productStoreProcessor.getProductStoresByActiveStore(activeUser.getSelectedStore());
	}

	public List<ProductData> getProducts() {
		return productProcessor.getProducts();
	}

	public List<StoreData> getStores() {
		return activeUser.getManagedStores();
	}

	public void saveProductStore() {
		productStoreProcessor.saveProductStore(getProductStoreForm().getProductStore());
		productStoreForm.createNewProductStore();
	}

	public void updateProductStore() {
		productStoreProcessor.updateProductQuantityAtStore(productStoreForm.getSelectedProductStore());
	}

	public ProductStoreForm getProductStoreForm() {
		return productStoreForm;
	}

	public void setProductStoreForm(ProductStoreForm productStoreForm) {
		this.productStoreForm = productStoreForm;
	}

}
