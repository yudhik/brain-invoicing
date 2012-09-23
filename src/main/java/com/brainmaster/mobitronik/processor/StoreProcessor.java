package com.brainmaster.mobitronik.processor;

import java.util.List;

import com.brainmaster.mobitronik.dto.StoreData;

public interface StoreProcessor {

	public List<StoreData> getStores();

	public void saveStore(StoreData storeData);
}
