package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.brainmaster.util.DatabaseColumnConstant;

@Entity
@Table(name = "store")
public class Store implements Serializable {

	private static final long serialVersionUID = 5947838295063055068L;

	@Id
	@Type(type = "uuid")
	@Column(length = DatabaseColumnConstant.SIZE_UUID)
	private UUID uuid;

	@Column(name = "store_name", unique = true)
	private String storeName;

	@Column
	private Byte[] address;

	@Column(name = "phone_number")
	private String phoneNumber;

	@ManyToOne(targetEntity = Store.class, optional = true)
	private Store parentStore;

	@OneToMany(mappedBy = "parentStore", cascade = CascadeType.ALL)
	private List<Store> childStores = new ArrayList<Store>();

	@OneToMany(mappedBy = "id.store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserStore> users = new ArrayList<UserStore>();

	@OneToMany(mappedBy = "id.store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProductStore> products = new ArrayList<ProductStore>();

	public Store() {
	}

	public Store(UUID uuid) {
		this.uuid = uuid;
	}

	public Store(UUID uuid, Byte[] address, String phoneNumber) {
		this.uuid = uuid;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public Store(UUID uuid, String storeName, Byte[] address, String phoneNumber) {
		this.uuid = uuid;
		this.storeName = storeName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Byte[] getAddress() {
		return address;
	}

	public void setAddress(Byte[] address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Store getParentStore() {
		return parentStore;
	}

	public void setParentStore(Store parentStore) {
		this.parentStore = parentStore;
	}

	public List<Store> getChildStores() {
		return childStores;
	}

	public void setChildStores(List<Store> childStores) {
		this.childStores = childStores;
	}

	public List<UserStore> getUsers() {
		return users;
	}

	public void setUsers(List<UserStore> users) {
		this.users = users;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setProducts(List<ProductStore> products) {
		this.products = products;
	}

	public List<ProductStore> getProducts() {
		return products;
	}

	@Override
	public String toString() {
		return this.getClass().getName();
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
		Store other = (Store) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
