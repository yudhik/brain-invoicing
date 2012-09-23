package com.brainmaster.mobitronik.action;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.IncomeReportData;

@Component
@Scope("session")
public class ReportingForm {

	private Date selectedStartDate;
	private Date selectedEndDate;
	private IncomeReportData selectedInvoice;
	private String uuidParam;
	private String documentTypeParam;

	public ReportingForm() {
	}

	public Date getSelectedStartDate() {
		if (selectedStartDate == null)
			selectedStartDate = new Date();
		return selectedStartDate;
	}

	public void setSelectedStartDate(Date selectedStartDate) {
		this.selectedStartDate = selectedStartDate;
	}

	public Date getSelectedEndDate() {
		return selectedEndDate;
	}

	public void setSelectedEndDate(Date selectedEndDate) {
		this.selectedEndDate = selectedEndDate;
	}

	public IncomeReportData getSelectedInvoice() {
		return selectedInvoice;
	}

	public void setSelectedInvoice(IncomeReportData selectedInvoice) {
		this.selectedInvoice = selectedInvoice;
	}

	public String getUuidParam() {
		return uuidParam;
	}

	public void setUuidParam(String uuidParam) {
		this.uuidParam = uuidParam;
	}

	public String getDocumentTypeParam() {
		return documentTypeParam;
	}

	public void setDocumentTypeParam(String documentTypeParam) {
		this.documentTypeParam = documentTypeParam;
	}

}
