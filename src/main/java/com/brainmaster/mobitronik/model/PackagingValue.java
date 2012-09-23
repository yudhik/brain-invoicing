package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "packagingvalue")
public class PackagingValue implements Serializable {

	private static final long serialVersionUID = 1297099907148015361L;

	@Id
	@Column(name = "package_id", length = 5)
	private String packageId;

	@Column(name = "content_quantity")
	private Integer contentQuantity;

	@OneToMany(mappedBy = "packageCode", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<Product>();

	public PackagingValue() {
	}

	public PackagingValue(String packageId, Integer contentQuantity) {
		this.packageId = packageId.toUpperCase();
		this.contentQuantity = contentQuantity;
	}

	public String getPackageId() {
		return packageId.toUpperCase();
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId.toUpperCase();
	}

	public Integer getContentQuantity() {
		return contentQuantity;
	}

	public void setContentQuantity(Integer contentQuantity) {
		this.contentQuantity = contentQuantity;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
