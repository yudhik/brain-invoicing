package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//@Entity
//@NamedQueries({
//	@NamedQuery(name = "store-quantity-for-store", query="from ProductStoreQuantity a where a.id.storeId = :storeId")
//})
public class ProductStoreQuantity implements Serializable {

	private static final long serialVersionUID = -2463971217191292127L;

	//	@EmbeddedId
	private CompositeProductStore id;

	private Integer quantity;

	//	@Temporal(TemporalType.TIMESTAMP)
	//	@Column(name = "last_update")
	private Date lastUpdate;

	//	@OneToMany(mappedBy = "productStoreId")
	private List<ProductStoreQuantityTransaction> transactions;

	public ProductStoreQuantity() {
	}

	public ProductStoreQuantity(CompositeProductStore id, Integer quantity,
			Date lastUpdate) {
		this.id = id;
		this.quantity = quantity;
		this.lastUpdate = lastUpdate;
	}

	public CompositeProductStore getId() {
		return id;
	}

	public void setId(CompositeProductStore id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setTransactions(
			List<ProductStoreQuantityTransaction> transactions) {
		this.transactions = transactions;
	}

	public List<ProductStoreQuantityTransaction> getTransactions() {
		return transactions;
	}

}
