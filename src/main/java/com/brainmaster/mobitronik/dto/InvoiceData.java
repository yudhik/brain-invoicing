package com.brainmaster.mobitronik.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.brainmaster.mobitronik.helper.ReferenceNumberGenerator;
import com.brainmaster.mobitronik.model.DocumentType;
import com.brainmaster.mobitronik.model.InvoiceTransaction;
import com.brainmaster.mobitronik.model.InvoiceTransactionDetail;
import com.brainmaster.mobitronik.model.Store;
import com.brainmaster.util.helper.uuid.UUIDHelper;

public class InvoiceData implements Serializable {

	private static final long serialVersionUID = 4128131346834131428L;

	private UUID uuid;

	private String invoiceNumber;

	private Date invoiceDate = new Date();

	private BigDecimal grandTotal;

	private StoreData seller;

	private StoreData buyer;

	private DocumentType documentType;

	private List<InvoiceDetailData> invoiceDetails = new ArrayList<InvoiceDetailData>();

	public InvoiceData() {
	}

	public InvoiceData(UUID uuid, DocumentType documentType) {
		this.documentType = documentType;
		this.uuid = uuid;
	}

	public InvoiceData(UUID uuid, String invoiceNumber, Date invoiceDate,
			BigDecimal grandTotal, StoreData seller, StoreData buyer,
			List<InvoiceDetailData> invoiceDetails) {
		this.uuid = uuid;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.grandTotal = grandTotal;
		this.seller = seller;
		this.buyer = buyer;
		this.invoiceDetails = invoiceDetails;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getId() {
		return UUIDHelper.uuidToString(uuid);
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getGrandTotal() {
		BigDecimal total = BigDecimal.ZERO;
		if (invoiceDetails.size() > 0) {
			for (InvoiceDetailData detail : invoiceDetails) {
				total = total.add(detail.getTotal());
			}
		}
		return total;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public StoreData getSeller() {
		return seller;
	}

	public void setSeller(StoreData seller) {
		this.seller = seller;
	}

	public StoreData getBuyer() {
		return buyer;
	}

	public void setBuyer(StoreData buyer) {
		this.buyer = buyer;
	}

	public List<InvoiceDetailData> getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(List<InvoiceDetailData> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public static InvoiceData convertInvoice(InvoiceTransaction invoice, Store seller, Store buyer) {
		List<InvoiceDetailData> transactionDatas = new ArrayList<InvoiceDetailData>();
		for (InvoiceTransactionDetail detail : invoice.getTransactions())
			transactionDatas.add(InvoiceDetailData.convertInvoiceTransaction(detail));
		InvoiceData invoiceData = new InvoiceData(invoice.getUuid(), invoice.getInvoiceNumber(), invoice.getInvoiceDate(), invoice.getGrandTotal(),
				StoreData.convertStore(seller), StoreData.convertStore(buyer), transactionDatas);
		if (invoice.getDocumentType() == null)
			invoiceData.setDocumentType(DocumentType.INVOICE);
		else
			invoiceData.setDocumentType(invoice.getDocumentType());
		return invoiceData;
	}

	public static InvoiceData convertInvoiceWithOutDetails(
			InvoiceTransaction invoice, Store seller, Store buyer) {
		InvoiceData invoiceData = new InvoiceData(invoice.getUuid(), invoice.getInvoiceNumber(), invoice.getInvoiceDate(), invoice.getGrandTotal(),
				StoreData.convertStore(seller), StoreData.convertStore(buyer), new ArrayList<InvoiceDetailData>());
		if (invoice.getDocumentType() == null)
			invoiceData.setDocumentType(DocumentType.INVOICE);
		else
			invoiceData.setDocumentType(invoice.getDocumentType());
		return invoiceData;
	}

	public InvoiceTransaction getInvoiceEntity() {
		InvoiceTransaction transaction = new InvoiceTransaction(uuid, invoiceDate, getGrandTotal(), seller.getStoreEntity(), buyer.getStoreEntity());
		transaction.setDocumentType(documentType);
		transaction.setInvoiceNumber(invoiceNumber);
		for (InvoiceDetailData detail : invoiceDetails) {
			transaction.getTransactions().add(detail.getInvoiceTransactionEntity());
		}
		return transaction;
	}

	public String getGrandTotalPlain() {
		if (getGrandTotal() != null)
			return getGrandTotal().toPlainString();
		return BigDecimal.ZERO.toPlainString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceData other = (InvoiceData) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceData [uuid=" + uuid + ", invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate + ", grandTotal=" + grandTotal +
				", seller=" + seller + ", buyer=" + buyer + ", invoiceDetails=" + invoiceDetails + "]";
	}

	public String getPlainInvoiceDate() {
		if (invoiceDate == null)
			return "";
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
		return formater.format(invoiceDate);
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public void setSequenceNumber(int nextSequence) {
		this.invoiceNumber = ReferenceNumberGenerator.generate(documentType,nextSequence);
		
	}
}
