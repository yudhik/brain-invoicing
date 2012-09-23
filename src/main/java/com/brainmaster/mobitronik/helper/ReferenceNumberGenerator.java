package com.brainmaster.mobitronik.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.brainmaster.mobitronik.model.DocumentType;

public class ReferenceNumberGenerator {

	private static final String INVOICE_CODE = "INV";
	private static final String PURCHASE_CODE = "PUR";
	private static final String SEPARATOR_CODE = "-";

	public static String generate(DocumentType documentType) {
		switch (documentType) {
		case INVOICE:
			return getNextInvoiceNumber(INVOICE_CODE);
		case PURCHASE_INVOICE:
			return getNextInvoiceNumber(PURCHASE_CODE);
		default:
			break;
		}
		return null;
	}

	private static String getNextInvoiceNumber(String prefix) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("ddMMyyhhmmssSS");
		StringBuilder invoiceNumber = new StringBuilder();
		invoiceNumber.append(prefix).append(SEPARATOR_CODE).append(dateFormater.format(new Date()));
		return invoiceNumber.toString();
	}
	
	public static String generate(DocumentType documentType, int sequenceNumber) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("ddMMyyyy");
		StringBuilder invoiceNumber = new StringBuilder();
		invoiceNumber.append(documentType.toString())
				.append(dateFormater.format(new Date()))
				.append(SEPARATOR_CODE).append(StringUtils.leftPad(""+sequenceNumber, 4, "0"));
		return invoiceNumber.toString();
	}

}
