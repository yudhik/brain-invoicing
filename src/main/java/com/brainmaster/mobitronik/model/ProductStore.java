package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@AssociationOverrides( {
		@AssociationOverride(name = "id.product", joinColumns = @JoinColumn(name = "product_id")),
		@AssociationOverride(name = "id.store", joinColumns = @JoinColumn(name = "store_id")) })
@Table(name = "productstore")
public class ProductStore implements Serializable {

	private static final long serialVersionUID = -2463971217191292127L;

	@EmbeddedId
	private CompositeProductStore id;

	private Integer quantity;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_calculation_time")
	private Date lastCalculationTime;

	@OneToMany(mappedBy = "productStore", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProductStoreTransaction> transactions = new ArrayList<ProductStoreTransaction>();

	@OneToMany(mappedBy = "productStore", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProductStorePriceAccumulation> accumulations = new ArrayList<ProductStorePriceAccumulation>();

	public ProductStore() {
	}

	public ProductStore(CompositeProductStore id, Integer quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	public CompositeProductStore getId() {
		return id;
	}

	public void setId(CompositeProductStore id) {
		this.id = id;
	}

	@Transient
	public Product getProduct() {
		return getId().getProduct();
	}

	public void setProduct(Product product) {
		getId().setProduct(product);
	}

	@Transient
	public Store getStore() {
		return getId().getStore();
	}

	public void setStore(Store store) {
		getId().setStore(store);
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public List<ProductStoreTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<ProductStoreTransaction> transactions) {
		this.transactions = transactions;
	}

	public List<ProductStorePriceAccumulation> getAccumulations() {
		return accumulations;
	}

	public void setAccumulations(
			List<ProductStorePriceAccumulation> accumulations) {
		this.accumulations = accumulations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProductStore other = (ProductStore) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductStore [id=" + id + ", quantity=" + quantity + "]";
	}

	public Date getLastCalculationTime() {
		return lastCalculationTime;
	}

	public void setLastCalculationTime(Date lastCalculationTime) {
		this.lastCalculationTime = lastCalculationTime;
	}

}
