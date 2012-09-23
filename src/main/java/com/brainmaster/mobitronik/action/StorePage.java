package com.brainmaster.mobitronik.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.primefaces.model.StoreDataModel;
import com.brainmaster.mobitronik.processor.StoreProcessor;

@Component
@Scope("request")
public class StorePage {

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(StorePage.class);

	@Autowired
	private StoreProcessor storeProcessor;

	@Autowired
	private StoreForm storeForm;

	private StoreData selectedStore;

	public List<StoreData> getStores() {
		return storeProcessor.getStores();
	}

	public void setSelectedStore(StoreData selectedStore) {
		this.selectedStore = selectedStore;
	}

	public StoreData getSelectedStore() {
		return selectedStore;
	}

	public void saveStore() {
		storeProcessor.saveStore(getStoreForm().getStore());
		storeForm.createNewStore();
	}

	public StoreDataModel getStoreDataModel() {
		return new StoreDataModel(getStores());
	}

	public StoreForm getStoreForm() {
		return storeForm;
	}

	public void setStoreForm(StoreForm storeForm) {
		this.storeForm = storeForm;
	}

}
