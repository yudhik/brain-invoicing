package com.brainmaster.mobitronik.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.brainmaster.mobitronik.model.DocumentType;
import com.brainmaster.mobitronik.model.InvoiceTransaction;
import com.brainmaster.mobitronik.model.InvoiceTransactionDetail;
import com.brainmaster.mobitronik.model.Store;

public class IncomeReportData extends InvoiceData {

	private static final long serialVersionUID = -8971780883235910419L;

	private BigDecimal invoiceProfit;

	public IncomeReportData() {
	}

//	public IncomeReportData(UUID uuid, String invoiceNumber, BigDecimal invoiceProfit) {
//		super(uuid, invoiceNumber);
//		this.invoiceProfit = invoiceProfit;
//	}

	public IncomeReportData(UUID uuid, String invoiceNumber, Date invoiceDate,
			BigDecimal grandTotal, StoreData seller, StoreData buyer,
			List<InvoiceDetailData> invoiceDetails, BigDecimal invoiceProfit) {
		super(uuid, invoiceNumber, invoiceDate, grandTotal, seller, buyer, invoiceDetails);
		this.invoiceProfit = invoiceProfit;
	}

	public static IncomeReportData convertIncomeReport(InvoiceTransaction invoice, Store seller, Store buyer, BigDecimal invoiceProfit) {
		List<InvoiceDetailData> transactionDatas = new ArrayList<InvoiceDetailData>();
		for (InvoiceTransactionDetail detail : invoice.getTransactions())
			transactionDatas.add(InvoiceDetailData.convertInvoiceTransaction(detail));
		IncomeReportData incomeReportData = new IncomeReportData(invoice.getUuid(), invoice.getInvoiceNumber(), invoice.getInvoiceDate(),
				invoice.getGrandTotal(), StoreData.convertStore(seller), StoreData.convertStore(buyer), transactionDatas, invoiceProfit);
		if (invoice.getDocumentType() == null)
			incomeReportData.setDocumentType(DocumentType.INVOICE);
		else
			incomeReportData.setDocumentType(invoice.getDocumentType());
		incomeReportData.setInvoiceProfit(invoiceProfit);
		return incomeReportData;
	}

	public static IncomeReportData convertIncomeReportWithOutDetails(InvoiceTransaction invoice, Store seller, Store buyer, BigDecimal invoiceProfit) {
		IncomeReportData incomeReportData = new IncomeReportData(invoice.getUuid(), invoice.getInvoiceNumber(), invoice.getInvoiceDate(),
				invoice.getGrandTotal(), StoreData.convertStore(seller), StoreData.convertStore(buyer),
				new ArrayList<InvoiceDetailData>(), invoiceProfit);
		if (invoice.getDocumentType() == null)
			incomeReportData.setDocumentType(DocumentType.INVOICE);
		else
			incomeReportData.setDocumentType(invoice.getDocumentType());
		incomeReportData.setInvoiceProfit(invoiceProfit);
		return incomeReportData;
	}

	public BigDecimal getInvoiceProfit() {
		return invoiceProfit;
	}

	public void setInvoiceProfit(BigDecimal invoiceProfit) {
		this.invoiceProfit = invoiceProfit;
	}

}
