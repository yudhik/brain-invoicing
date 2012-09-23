package com.brainmaster.mobitronik.action;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.IncomeReportData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.DocumentType;
import com.brainmaster.mobitronik.primefaces.model.IncomeReportDataModel;
import com.brainmaster.mobitronik.processor.ReportingProcessor;
import com.brainmaster.mobitronik.processor.RetailDocumentProcessor;

@Component
@Scope("request")
public class ReportingPage {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ReportingPage.class);

	@Autowired
	private ReportingProcessor reportingProcessor;

	@Autowired
	private RetailDocumentProcessor retailDocumentProcessor;

	@Autowired
	private ReportingForm reportingForm;

	@Autowired
	private ActiveUser activeUser;

	private StoreData sellerStore;

	public List<IncomeReportData> getIncomeReportDatas() {
		return reportingProcessor.getSellerIncomeReports(getSellerStore(), getReportingForm().getSelectedStartDate(), getReportingForm()
				.getSelectedEndDate());
	}

	public IncomeReportDataModel getIncomeReportDataModel() {
		return new IncomeReportDataModel(getIncomeReportDatas());
	}

	public StoreData getSellerStore() {
		if (sellerStore == null)
			sellerStore = activeUser.getSelectedStore();
		return sellerStore;
	}

	public String getRowCount() {
		return "" + getIncomeReportDatas().size();
	}

	public String getTotalRevenue() {
		BigDecimal totalRevenue = BigDecimal.ZERO;
		for (IncomeReportData incomeReportData : getIncomeReportDatas())
			totalRevenue = totalRevenue.add(incomeReportData.getInvoiceProfit());
		return totalRevenue.setScale(2).toPlainString();
	}

	public String getPopulateInvoiceResult() {
		if (getReportingForm().getSelectedInvoice() != null) {
			getReportingForm().setUuidParam(retailDocumentProcessor.getDocumentId(getReportingForm().getSelectedInvoice().getInvoiceNumber()));
			getReportingForm().setDocumentTypeParam(DocumentType.INVOICE.toString());
			return "show_invoiceDetail";
		}
		return "";
	}

	public ReportingForm getReportingForm() {
		return reportingForm;
	}

	public void setReportingForm(ReportingForm reportingForm) {
		this.reportingForm = reportingForm;
	}

	public String getTransformationResult() {
		return reportingProcessor.getTransformationResult(reportingForm.getUuidParam(), reportingForm.getDocumentTypeParam());
	}
	
	public String printIncomeReport() {
		return "show_incomeReportPlain";
	}
	
	public String getReportTransformationResult() {
		return reportingProcessor.getReportTransformationResult(getSellerStore().getStoreName(), getReportingForm().getSelectedStartDate(), getReportingForm()
				.getSelectedEndDate(), getIncomeReportDatas(), new BigDecimal(getTotalRevenue()));
	}
}
