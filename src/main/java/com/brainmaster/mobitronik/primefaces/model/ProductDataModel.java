package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brainmaster.mobitronik.dto.ProductData;

public class ProductDataModel extends ListDataModel<ProductData> implements SelectableDataModel<ProductData> {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ProductDataModel.class);

	public ProductDataModel() {
	}

	public ProductDataModel(List<ProductData> productsData) {
		super(productsData);
	}

	@Override
	public Object getRowKey(ProductData object) {
		return object.getUuid();
	}

	@Override
	public ProductData getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<ProductData> list = (List<ProductData>) getWrappedData();

		for (ProductData productData : list)
			if (productData.getUuid() != null)
				return productData;

		return null;
	}

}
