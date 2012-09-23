package com.brainmaster.mobitronik.action;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.StoreData;

@Component
@Scope("session")
public class StoreForm {

	private StoreData store = new StoreData(UUID.randomUUID());

	public StoreData getStore() {
		return store;
	}

	public void setStore(StoreData store) {
		this.store = store;
	}

	public void createNewStore() {
		store = new StoreData(UUID.randomUUID());
	}
}
