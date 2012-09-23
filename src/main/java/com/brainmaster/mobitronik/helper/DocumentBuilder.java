package com.brainmaster.mobitronik.helper;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import com.brainmaster.mobitronik.model.InvoiceTransaction;
import com.brainmaster.mobitronik.model.InvoiceTransactionDetail;
import com.brainmaster.mobitronik.model.Store;
import com.brainmaster.util.formatter.XMLDateFormatter;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Deprecated
public class DocumentBuilder {

	public static String buildInvoiceDocument(InvoiceTransaction invoice, Store buyer, Store seller) {
		Element invoiceDocument = new DefaultElement("invoiceDocument");
		Document doc = DocumentHelper.createDocument(invoiceDocument);
		Element root = doc.getRootElement();
		root.addAttribute("createdDate", XMLDateFormatter.getXMLFormat(new Date()));

		Element buyerType = new DefaultElement("buyer");
		buyerType.addAttribute("id", UUIDHelper.uuidToString(invoice.getBuyer().getUuid()));
		buyerType.add(createNameElement(buyer.getStoreName()));
		buyerType.add(createStreetAddressOne(new String(ArrayUtils.toString(buyer.getAddress()))));
		buyerType.add(createPhoneNumber(buyer.getPhoneNumber()));

		Element sellerType = new DefaultElement("seller");
		sellerType.addAttribute("id", UUIDHelper.uuidToString(invoice.getSeller().getUuid()));
		sellerType.add(createNameElement(seller.getStoreName()));
		sellerType.add(createStreetAddressOne(new String(ArrayUtils.toString(seller.getAddress()))));
		sellerType.add(createPhoneNumber(seller.getPhoneNumber()));

		Element invoiceSummary = new DefaultElement("invoiceSummary");
		invoiceSummary.addElement("invoiceCurrencyISO").addText("IDR");
		invoiceSummary.addElement("invoiceNumber").addText(invoice.getInvoiceNumber());
		invoiceSummary.addElement("invoiceTotal").addText(invoice.getGrandTotal().toPlainString());
		invoiceSummary.addElement("remark");

		root.add(buyerType);
		root.add(sellerType);
		root.add(invoiceSummary);

		for (InvoiceTransactionDetail detail : invoice.getTransactions()) {
			Element tradeItem = new DefaultElement("tradeITem");
			int quantity = detail.getSerialNumber().contains(",") ? detail.getSerialNumber().split(",").length : 1;
			tradeItem.addAttribute("number", "" + invoice.getTransactions().indexOf(detail));
			tradeItem.addElement("gtin").addText(detail.getGtin() == null ? "00000000000" : detail.getGtin());

			Element additionalTradeInformation = tradeItem.addElement("additionalTradeInformation");

			additionalTradeInformation.add(createTradeItemAdditionalInformation("serial_number", detail.getSerialNumber()));
			additionalTradeInformation.add(createTradeItemAdditionalInformation("product_name", ""));
			additionalTradeInformation.add(createTradeItemAdditionalInformation("price", detail.getSerialNumber()));
			additionalTradeInformation.add(createTradeItemAdditionalInformation("quantity", detail.getSerialNumber()));

			tradeItem.addElement("total").addText(detail.getPrice().multiply(new BigDecimal(quantity)).toPlainString());

			root.add(tradeItem);
		}
		return root.asXML();
	}

	private static Element createPhoneNumber(String string) {
		return new DefaultElement("phoneNumber").addText(string);
	}

	@SuppressWarnings("unused")
	private static Element createStreetAddressTwo(String string) {
		return new DefaultElement("streetAddressTwo").addText(string);
	}

	private static Element createStreetAddressOne(String string) {
		return new DefaultElement("streetAddressOne").addText(string);
	}

	private static Element createNameElement(String text) {
		return new DefaultElement("name").addText(text);
	}

	private static Element createTradeItemElementInformationType(String text) {
		return new DefaultElement("tradeItemInformationType").addText(text);
	}

	private static Element createTradeItemElementInformationValue(String text) {
		return new DefaultElement("tradeItemInformationValue").addText(text);
	}

	private static Element createTradeItemAdditionalInformation(String type, String value) {
		Element tradeItemInformation = new DefaultElement("tradeItemInformation");
		tradeItemInformation.add(createTradeItemElementInformationType(type));
		tradeItemInformation.add(createTradeItemElementInformationValue(value));
		return tradeItemInformation;
	}

}
