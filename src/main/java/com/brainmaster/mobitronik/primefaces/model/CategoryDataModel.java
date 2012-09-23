package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brainmaster.mobitronik.dto.CategoryData;

public class CategoryDataModel extends ListDataModel<CategoryData> implements SelectableDataModel<CategoryData> {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CategoryDataModel.class);

	public CategoryDataModel() {
	}

	public CategoryDataModel(List<CategoryData> categories) {
		super(categories);
	}

	@Override
	public Object getRowKey(CategoryData object) {
		return object.getUuid();
	}

	@Override
	public CategoryData getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<CategoryData> list = (List<CategoryData>) getWrappedData();

		for (CategoryData categoryData : list) {
			if (categoryData != null)
				return categoryData;
		}

		return null;
	}

}
