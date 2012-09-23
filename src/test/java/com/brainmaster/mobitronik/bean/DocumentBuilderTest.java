package com.brainmaster.mobitronik.bean;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.junit.Test;

import com.brainmaster.util.formatter.XMLDateFormatter;

public class DocumentBuilderTest {

	@Test
	public void testCreatingDocument() {
		Element invoiceDocument = new DefaultElement("invoiceDocument");
		Document doc = DocumentHelper.createDocument(invoiceDocument);
		Element root = doc.getRootElement();
		root.addAttribute("createdDate", XMLDateFormatter
				.getXMLFormat(new Date()));

		Element buyerType = new DefaultElement("buyer");
		buyerType.addAttribute("id", "00192");
		buyerType.add(createNameElement("this is buyer name"));
		buyerType.add(createStreetAddressOne("address1"));
		buyerType.add(createStreetAddressTwo("address2"));
		buyerType.add(createPhoneNumber("Jln. kemanggisan II no 3"));

		Element sellerType = new DefaultElement("seller");
		sellerType.addAttribute("id", "0011");
		sellerType.add(createNameElement("this is seller name"));
		sellerType.add(createStreetAddressOne("address1"));
		sellerType.add(createStreetAddressTwo("address2"));
		sellerType.add(createPhoneNumber("Jln. kemanggisan II no 3"));

		Element invoiceSummary = new DefaultElement("invoiceSummary");
		invoiceSummary.addElement("invoiceTotal").addText("120000");
		invoiceSummary.addElement("remark");

		root.add(buyerType);
		root.add(sellerType);
		root.add(invoiceSummary);

		for (int i = 0; i < 3; i++) {
			Element tradeItem = new DefaultElement("tradeITem");
			tradeItem.addAttribute("number", "" + i);
			tradeItem.addElement("gtin").addText("00000000000" + i);
			Element additionalTradeInformation = tradeItem
					.addElement("additionalTradeInformation");
			additionalTradeInformation
					.add(createTradeItemAdditionalInformation("serial_number",
							"00000" + i));
			additionalTradeInformation
					.add(createTradeItemAdditionalInformation("price", ""
							+ (i + 1) * 1000));
			additionalTradeInformation
					.add(createTradeItemAdditionalInformation("quantity", ""
							+ (i + 1) * 10));
			root.add(tradeItem);
		}

		System.out.println(root.asXML());

	}

	private Element createPhoneNumber(String string) {
		return new DefaultElement("phoneNumber").addText(string);
	}

	private Element createStreetAddressTwo(String string) {
		return new DefaultElement("streetAddressTwo").addText(string);
	}

	private Element createStreetAddressOne(String string) {
		return new DefaultElement("streetAddressOne").addText(string);
	}

	private Element createNameElement(String text) {
		return new DefaultElement("name").addText(text);
	}

	private Element createTradeItemElementInformationType(String text) {
		return new DefaultElement("tradeItemInformationType").addText(text);
	}

	private Element createTradeItemElementInformationValue(String text) {
		return new DefaultElement("tradeItemInformationValue").addText(text);
	}

	private Element createTradeItemAdditionalInformation(String type,
			String value) {
		Element tradeItemInformation = new DefaultElement(
				"tradeItemInformation");
		tradeItemInformation.add(createTradeItemElementInformationType(type));
		tradeItemInformation.add(createTradeItemElementInformationValue(value));
		return tradeItemInformation;
	}

}
