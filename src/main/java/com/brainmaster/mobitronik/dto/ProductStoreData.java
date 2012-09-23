package com.brainmaster.mobitronik.dto;

import java.io.Serializable;

import com.brainmaster.mobitronik.model.CompositeProductStore;
import com.brainmaster.mobitronik.model.ProductStore;

public class ProductStoreData implements Serializable {

	private static final long serialVersionUID = -3582742929443681829L;

	private ProductData product;
	private StoreData store;
	private Integer quantity;

	public ProductStoreData() {
	}

	public ProductStoreData(ProductData product, StoreData store, Integer quantity) {
		this.product = product;
		this.store = store;
		this.quantity = quantity;
	}

	public ProductData getProduct() {
		return product;
	}

	public void setProduct(ProductData product) {
		this.product = product;
	}

	public StoreData getStore() {
		return store;
	}

	public void setStore(StoreData store) {
		this.store = store;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public static ProductStoreData convert(ProductStore productStore) {
		return new ProductStoreData(ProductData.convertProduct(productStore.getProduct()),
				StoreData.convertStore(productStore.getStore()), productStore.getQuantity());
	}

	public ProductStore getProductStoreEntity() {
		return new ProductStore(new CompositeProductStore(product.getProductEntity(), store.getStoreEntity()), quantity);
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
		ProductStoreData other = (ProductStoreData) obj;
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
		return "ProductStoreData [product=" + product + ", store=" + store + ", quantity=" + quantity + "]";
	}

}
