package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@AssociationOverrides({
		@AssociationOverride(name = "productStore.id.product", joinColumns = @JoinColumn(name = "product_id")),
		@AssociationOverride(name = "productStore.id.store", joinColumns = @JoinColumn(name = "store_id"))})
@NamedQueries({@NamedQuery(name = "latest-product-store-quantity-transaction", query = "from ProductStoreTransaction a where a.productStore = :productStore "
		+ "and a.transactionDate = (select MAX(b.transactionDate) from ProductStoreTransaction b where b.productStore = :productStore)")})
@Table(name = "productstoretransaction")
public class ProductStoreTransaction implements Serializable {

	private static final long serialVersionUID = 6241771394758018088L;

	@Id
	@Type(type = "uuid")
	private UUID uuid;

	@ManyToOne
	private ProductStore productStore;

	@Column(name = "transaction_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;

	private Long outstandingQuantity;

	private Long quantity;

	@Type(type = "uuid")
	@Column(name = "document_reference", nullable = false)
	private UUID documentReference;// this reference should refer to the invoice
	// uuid

	public ProductStoreTransaction() {
	}

	public ProductStoreTransaction(UUID uuid, Date transactionDate,
			Long quantity, UUID documentReference) {
		this.uuid = uuid;
		this.transactionDate = transactionDate;
		this.quantity = quantity;
		this.documentReference = documentReference;
	}

	public ProductStoreTransaction(UUID uuid, Date transactionDate,
			Long outstandingQuantity, Long quantity, UUID documentReference) {
		this.uuid = uuid;
		this.transactionDate = transactionDate;
		this.outstandingQuantity = outstandingQuantity;
		this.quantity = quantity;
		this.documentReference = documentReference;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public ProductStore getProductStore() {
		return productStore;
	}

	public void setProductStore(ProductStore productStore) {
		this.productStore = productStore;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public UUID getDocumentReference() {
		return documentReference;
	}

	public void setDocumentReference(UUID documentReference) {
		this.documentReference = documentReference;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getOutstandingQuantity() {
		return outstandingQuantity;
	}

	public void setOutstandingQuantity(Long outstandingQuantity) {
		this.outstandingQuantity = outstandingQuantity;
	}

	@Override
	public String toString() {
		return "ProductStoreTransaction [uuid=" + uuid + ", productStore="
				+ productStore + ", transactionDate=" + transactionDate
				+ ", outstandingQuantity=" + outstandingQuantity
				+ ", quantity=" + quantity + ", documentReference="
				+ documentReference + "]";
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
		ProductStoreTransaction other = (ProductStoreTransaction) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
