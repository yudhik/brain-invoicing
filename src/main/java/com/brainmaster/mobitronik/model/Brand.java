package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.brainmaster.util.DatabaseColumnConstant;

@Entity
@Table(name = "brand")
public class Brand implements Serializable {

	private static final long serialVersionUID = -1603771716730111235L;

	@Id
	@Type(type = "uuid")
	@Column(length = DatabaseColumnConstant.SIZE_UUID)
	private UUID uuid;

	@Column(name = "brand_name", unique = true)
	private String brandName;

	@OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<Product>();

	public Brand() {
	}

	public Brand(UUID uuid, String brandName) {
		this.uuid = uuid;
		this.brandName = brandName;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Product> getProducts() {
		return products;
	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}

}
