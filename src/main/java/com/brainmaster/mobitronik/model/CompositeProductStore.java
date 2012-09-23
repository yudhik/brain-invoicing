package com.brainmaster.mobitronik.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.brainmaster.util.helper.uuid.UUIDHelper;

@Embeddable
public class CompositeProductStore implements Serializable {

	private static final long serialVersionUID = 5178347163876248031L;

	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(targetEntity = Store.class)
	@JoinColumn(name = "store_id")
	private Store store;

	public CompositeProductStore() {
	}

	public CompositeProductStore(Product product, Store store) {
		this.product = product;
		this.store = store;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
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
		CompositeProductStore other = (CompositeProductStore) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "store[id :" + UUIDHelper.uuidToString(store.getUuid()) + ", name: " + store.getStoreName() + " ], product[id :" +
				UUIDHelper.uuidToString(product.getUuid()) + ", name : " + product.getProductName() + "]";
	}

}
