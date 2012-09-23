package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.brainmaster.mobitronik.dto.ProductStoreData;
import com.brainmaster.util.helper.uuid.UUIDHelper;

public class ProductStoreDataModel extends ListDataModel<ProductStoreData> implements SelectableDataModel<ProductStoreData> {

	public ProductStoreDataModel(List<ProductStoreData> productStoreDatas) {
		super(productStoreDatas);
	}

	@Override
	public Object getRowKey(ProductStoreData object) {
		return UUIDHelper.uuidToString(object.getProduct().getUuid()).concat("_").concat(object.getStore().getUuid());
	}

	@Override
	public ProductStoreData getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<ProductStoreData> productStoreDatas = (List<ProductStoreData>) getWrappedData();

		for (ProductStoreData productStoreData : productStoreDatas) {
			if (productStoreData != null)
				return new ProductStoreData();
		}

		return null;
	}

}
