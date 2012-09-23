package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//@Entity
//@NamedQueries({
//	@NamedQuery(name = "latest-product-store-quantity-transaction", 
//			query = "from ProductStoreQuantityTransaction a where a.productStoreId = :productStoreId " +
//					"and a.dateCreated = (select MAX(b.dateCreated) from ProductStoreQuantityTransaction b where b.productStoreId = :productStoreId)")
//})
public class ProductStoreQuantityTransaction implements Serializable {

	private static final long serialVersionUID = -9049577502752270674L;

	//	@Id
	//	@Type(type = "uuid")
	//	@Column(length = DatabaseColumnConstant.SIZE_UUID)
	private UUID uuid;

	//	@ManyToOne
	//	@JoinColumns({ 
	//		@JoinColumn(name = "store_uuid"),
	//		@JoinColumn(name = "product_uuid") 
	//	})
	private ProductStoreQuantity productStoreId;

	//	@Column(name = "document_uuid", length = DatabaseColumnConstant.SIZE_UUID)
	//	@Type(type = "uuid")
	private UUID documentId;

	//	@ManyToOne
	//	@JoinColumn(name = "package_code")
	private PackagingValue packagingValue;

	//	@Column(name = "remains_quantity")
	private Integer remainsQuantity;

	private Integer quantity;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;

	public ProductStoreQuantityTransaction() {
	}

	public ProductStoreQuantityTransaction(UUID uuid,
			ProductStoreQuantity productStoreId, UUID documentId,
			PackagingValue packagingValue, Integer remainsQuantity,
			Integer quantity, Date dateCreated) {
		this.uuid = uuid;
		this.productStoreId = productStoreId;
		this.documentId = documentId;
		this.packagingValue = packagingValue;
		this.remainsQuantity = remainsQuantity;
		this.quantity = quantity;
		this.dateCreated = dateCreated;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getDocumentId() {
		return documentId;
	}

	public void setDocumentId(UUID documentId) {
		this.documentId = documentId;
	}

	public PackagingValue getPackagingValue() {
		return packagingValue;
	}

	public void setPackagingValue(PackagingValue packagingValue) {
		this.packagingValue = packagingValue;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setProductStoreId(ProductStoreQuantity productStoreId) {
		this.productStoreId = productStoreId;
	}

	public ProductStoreQuantity getProductStoreId() {
		return productStoreId;
	}

	public void setRemainsQuantity(Integer remainsQuantity) {
		this.remainsQuantity = remainsQuantity;
	}

	public Integer getRemainsQuantity() {
		return remainsQuantity;
	}

}
