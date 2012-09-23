package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.brainmaster.mobitronik.dto.IncomeReportData;

public class IncomeReportDataModel extends ListDataModel<IncomeReportData> implements SelectableDataModel<IncomeReportData> {

	public IncomeReportDataModel() {
	}

	public IncomeReportDataModel(List<IncomeReportData> incomeReports) {
		super(incomeReports);
	}

	@Override
	public Object getRowKey(IncomeReportData object) {
		return object.getUuid();
	}

	@Override
	public IncomeReportData getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<IncomeReportData> incomeReportDatas = (List<IncomeReportData>) getWrappedData();
		for (IncomeReportData incomeReportData : incomeReportDatas)
			if (incomeReportData.getUuid() != null)
				return incomeReportData;
		return null;
	}

}
