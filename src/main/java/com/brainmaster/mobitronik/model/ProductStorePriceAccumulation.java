package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@NamedQueries({@NamedQuery(name = "latest-product-store-average-price", query = "from ProductStorePriceAccumulation a where a.productStore = :productStore "
		+ "and a.transactionDate = (select MAX(b.transactionDate) from ProductStorePriceAccumulation b where b.productStore = :productStore)")})
@Table(name = "productstorepriceaccumulation")
public class ProductStorePriceAccumulation implements Serializable {

	private static final long serialVersionUID = 2742647731462827387L;

	@Id
	@Type(type = "uuid")
	private UUID uuid;

	@ManyToOne
	private ProductStore productStore;

	@Type(type = "uuid")
	@Column(name = "document_reference")
	private UUID documentReference;

	@Column(name = "average_price")
	private BigDecimal averagePrice;

	@Column(name = "updated_price")
	private BigDecimal updatedPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transaction_date")
	private Date transactionDate;

	public ProductStorePriceAccumulation() {
	}

	public ProductStorePriceAccumulation(UUID uuid, ProductStore productStore,
			UUID documentReference, BigDecimal averagePrice,
			BigDecimal updatedPrice, Date transactionDate) {
		this.uuid = uuid;
		this.productStore = productStore;
		this.documentReference = documentReference;
		this.averagePrice = averagePrice;
		this.updatedPrice = updatedPrice;
		this.transactionDate = transactionDate;
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

	public UUID getDocumentReference() {
		return documentReference;
	}

	public void setDocumentReference(UUID documentReference) {
		this.documentReference = documentReference;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getUpdatedPrice() {
		return updatedPrice;
	}

	public void setUpdatedPrice(BigDecimal updatedPrice) {
		this.updatedPrice = updatedPrice;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "ProductStorePriceAccumulation [uuid=" + uuid
				+ ", productStore=" + productStore + ", documentReference="
				+ documentReference + ", averagePrice=" + averagePrice
				+ ", updatedPrice=" + updatedPrice + ", transactionDate="
				+ transactionDate + "]";
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
		ProductStorePriceAccumulation other = (ProductStorePriceAccumulation) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
