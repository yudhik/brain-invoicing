package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brainmaster.mobitronik.dto.StoreData;

public class StoreDataModel extends ListDataModel<StoreData> implements SelectableDataModel<StoreData> {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(StoreDataModel.class);

	public StoreDataModel() {
	}

	public StoreDataModel(List<StoreData> stores) {
		super(stores);
	}

	@Override
	public Object getRowKey(StoreData object) {
		return object.getUuid();
	}

	@Override
	public StoreData getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<StoreData> list = (List<StoreData>) getWrappedData();
		for (StoreData store : list) {
			if (store.getUuid().equals(rowKey))
				return store;
		}
		return null;
	}

}
