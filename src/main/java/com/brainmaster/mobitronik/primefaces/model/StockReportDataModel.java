package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.brainmaster.mobitronik.dto.StockReportData;

public class StockReportDataModel extends ListDataModel<StockReportData> implements SelectableDataModel<StockReportData> {

	public StockReportDataModel() {
	}

	public StockReportDataModel(List<StockReportData> stockReportDatas) {
		super(stockReportDatas);
	}

	@Override
	public Object getRowKey(StockReportData object) {
		return object.getProduct().getUuid();
	}

	@Override
	public StockReportData getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<StockReportData> stockReportDatas = (List<StockReportData>) getWrappedData();
		for (StockReportData stockReportData : stockReportDatas)
			if (stockReportData.getProduct() != null)
				return stockReportData;
		return null;
	}

}
