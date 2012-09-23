package com.brainmaster.mobitronik.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.StockReportData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.primefaces.model.StockReportDataModel;
import com.brainmaster.mobitronik.processor.ReportingProcessor;
import com.brainmaster.mobitronik.processor.RetailDocumentProcessor;

@Component
@Scope("request")
public class StockReportingPage {

	@Autowired
	private ReportingProcessor reportingProcessor;

	@Autowired
	private RetailDocumentProcessor retailDocumentProcessor;

	@Autowired
	private ActiveUser activeUser;

	@Autowired
	private StockReportingForm stockReportingForm;

	private StoreData sellerStore;

	public StoreData getSellerStore() {
		if (sellerStore == null)
			sellerStore = activeUser.getSelectedStore();
		return sellerStore;
	}

	public List<StockReportData> getStockReportDatas() {
		return reportingProcessor.getSellerProductReports(getSellerStore(), getStockReportingForm().getSelectedStartDate(),
				getStockReportingForm().getSelectedEndDate());
	}

	public String getRowCount() {
		return "" + getStockReportDatas().size();
	}

	public StockReportDataModel getStockReportDataModel() {
		return new StockReportDataModel(getStockReportDatas());
	}

	public StockReportingForm getStockReportingForm() {
		return stockReportingForm;
	}

	public void setStockReportingForm(StockReportingForm stockReportingForm) {
		this.stockReportingForm = stockReportingForm;
	}

}
