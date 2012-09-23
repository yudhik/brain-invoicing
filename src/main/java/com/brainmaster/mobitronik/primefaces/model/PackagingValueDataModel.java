package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brainmaster.mobitronik.dto.PackagingValueData;

public class PackagingValueDataModel extends ListDataModel<PackagingValueData> implements SelectableDataModel<PackagingValueData> {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(PackagingValueDataModel.class);

	public PackagingValueDataModel() {
	}

	public PackagingValueDataModel(List<PackagingValueData> list) {
		super(list);
	}

	@Override
	public Object getRowKey(PackagingValueData object) {
		return object.getPackageId();
	}

	@Override
	public PackagingValueData getRowData(String rowKey) {

		@SuppressWarnings("unchecked")
		List<PackagingValueData> list = (List<PackagingValueData>) getWrappedData();

		for (PackagingValueData data : list)
			if (data != null)
				return data;

		return null;
	}

}
