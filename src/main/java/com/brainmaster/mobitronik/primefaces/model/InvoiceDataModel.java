package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.brainmaster.mobitronik.dto.InvoiceData;

public class InvoiceDataModel extends ListDataModel<InvoiceData> implements SelectableDataModel<InvoiceData> {

	public InvoiceDataModel() {
	}

	public InvoiceDataModel(List<InvoiceData> invoices) {
		super(invoices);
	}

	@Override
	public Object getRowKey(InvoiceData object) {
		return object.getUuid();
	}

	@Override
	public InvoiceData getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<InvoiceData> datas = (List<InvoiceData>) getWrappedData();
		for (InvoiceData data : datas)
			if (data.getUuid() != null)
				return data;
		return null;
	}

}
