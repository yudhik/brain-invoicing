package com.brainmaster.mobitronik.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.StoreService;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.Store;

@Component
@Scope("prototype")
public class StoreProcessorBean implements StoreProcessor {

	@Autowired
	private StoreService storeService;

	@Override
	public List<StoreData> getStores() {
		List<StoreData> stores = new ArrayList<StoreData>();
		for (Store store : storeService.findAll()) {
			stores.add(StoreData.convertStore(storeService.find(store.getUuid())));
		}
		return stores;
	}

	@Override
	public void saveStore(StoreData storeData) {
		storeService.addStore(storeData.getStoreEntity());
	}

}
