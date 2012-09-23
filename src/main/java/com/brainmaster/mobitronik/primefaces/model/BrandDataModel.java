package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.brainmaster.mobitronik.dto.BrandData;

public class BrandDataModel extends ListDataModel<BrandData> implements SelectableDataModel<BrandData> {

	public BrandDataModel() {
	}

	public BrandDataModel(List<BrandData> list) {
		super(list);
	}

	@Override
	public Object getRowKey(BrandData object) {
		return object.getUuid();
	}

	@Override
	public BrandData getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<BrandData> list = (List<BrandData>) getWrappedData();

		for (BrandData brandData : list) {
			if (brandData != null)
				return brandData;
		}
		return null;
	}

}
