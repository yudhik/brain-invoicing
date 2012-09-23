package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.brainmaster.util.DatabaseColumnConstant;

@Entity
@NamedQueries({
		@NamedQuery(name = "invoice-with-reference", query = "from InvoiceTransaction a where a.invoiceNumber like :reference and a.documentType = 4"),
		@NamedQuery(name = "invoice-with-buyer-store", query = "from InvoiceTransaction a where a.buyer = :buyer and a.documentType = 4"),
		@NamedQuery(name = "invoice-with-seller-store", query = "from InvoiceTransaction a where a.seller = :seller and a.documentType = 3"),
		@NamedQuery(name = "invoice-with-seller-store-and-date", query = "from InvoiceTransaction a where a.seller = :seller and a.invoiceDate = :date"),
		@NamedQuery(name = "invoice-with-seller-store-and-date-range", query = "from InvoiceTransaction a where a.seller = :seller and a.invoiceDate between :start and :end and a.documentType = 3"),
		@NamedQuery(name = "invoice-with-seller-store-and-product", query = "from InvoiceTransaction a left join fetch a.transactions b where a.seller = :seller and b.product = :productUuid and a.documentType = :documentType"),
		@NamedQuery(name = "invoice-with-buyer-store-and-product", query = "from InvoiceTransaction a left join fetch a.transactions b where a.buyer = :buyer and b.product = :productUuid and a.documentType = :documentType"),
		@NamedQuery(name = "invoice-with-buyer-store-and-date", query = "from InvoiceTransaction a where a.buyer = :buyer and a.invoiceDate = :date and a.documentType = 4"),
		@NamedQuery(name = "count-document-type-from-date", query="from InvoiceTransaction a where a.documentType = :documentType and a.invoiceDate = :invoiceDate")

})
@Table(name = "invoicetransaction")
public class InvoiceTransaction implements Serializable {

	private static final long serialVersionUID = -5633728676496815146L;

	@Id
	@Type(type = "uuid")
	@Column(length = DatabaseColumnConstant.SIZE_UUID)
	private UUID uuid;

	@Column(name = "invoice_number", length = DatabaseColumnConstant.SIZE_DEFAULT)
	private String invoiceNumber;

	@Temporal(TemporalType.DATE)
	@Column(name = "invoice_date")
	private Date invoiceDate;

	@Column(name = "grand_total")
	private BigDecimal grandTotal;

	//	@Type(type = "uuid")
	@ManyToOne(targetEntity = Store.class)
	private Store seller;

	@ManyToOne(targetEntity = Store.class)
	//	@Type(type = "uuid")
	private Store buyer;

	@Column(name = "document_type")
	private DocumentType documentType;

	@OneToMany(mappedBy = "invoiceTransaction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<InvoiceTransactionDetail> transactions = new ArrayList<InvoiceTransactionDetail>();

	public InvoiceTransaction() {
	}

	public InvoiceTransaction(UUID uuid, Date invoiceDate,
			BigDecimal grandTotal, Store seller, Store buyer) {
		this.uuid = uuid;
		this.invoiceDate = invoiceDate;
		this.grandTotal = grandTotal;
		this.seller = seller;
		this.buyer = buyer;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Store getSeller() {
		return seller;
	}

	public void setSeller(Store seller) {
		this.seller = seller;
	}

	public Store getBuyer() {
		return buyer;
	}

	public void setBuyer(Store buyer) {
		this.buyer = buyer;
	}

	public List<InvoiceTransactionDetail> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<InvoiceTransactionDetail> transactions) {
		this.transactions = transactions;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
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
		InvoiceTransaction other = (InvoiceTransaction) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceTransaction [uuid=" + uuid + ", invoiceNumber="
				+ invoiceNumber + ", invoiceDate=" + invoiceDate
				+ ", grandTotal=" + grandTotal + ", seller=" + seller
				+ ", buyer=" + buyer + "]";
	}

}
