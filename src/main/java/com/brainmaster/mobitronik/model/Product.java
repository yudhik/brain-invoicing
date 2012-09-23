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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.brainmaster.util.DatabaseColumnConstant;

@Entity
@NamedQueries( { @NamedQuery(name = "product-by-code", query = "from Product a where a.productName like :productName") })
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = -2520437947468554143L;

	@Id
	@Type(type = "uuid")
	@Column(length = DatabaseColumnConstant.SIZE_UUID)
	private UUID uuid;

	@Column(name = "product_code", unique = true)
	private String productCode;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "barcode_number")
	private String barcodeNumber;

	@JoinColumn(name = "package_code")
	@ManyToOne(targetEntity = PackagingValue.class)
	private PackagingValue packageCode;

	@ManyToOne(targetEntity = Brand.class)
	private Brand brand;

	@ManyToOne(targetEntity = Category.class)
	private Category category;

	@OneToMany(mappedBy = "id.product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProductStore> stores = new ArrayList<ProductStore>();

	public Product() {
	}

	public Product(UUID uuid, String productCode, String productName,
			String barcodeNumber, Brand brand, Category category,
			PackagingValue packageCode) {
		this.uuid = uuid;
		this.productCode = productCode;
		this.productName = productName;
		this.barcodeNumber = barcodeNumber;
		this.brand = brand;
		this.category = category;
		this.packageCode = packageCode;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setPackageCode(PackagingValue packageCode) {
		this.packageCode = packageCode;
	}

	public PackagingValue getPackageCode() {
		return packageCode;
	}

	public void setStores(List<ProductStore> stores) {
		this.stores = stores;
	}

	public List<ProductStore> getStores() {
		return stores;
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
		Product other = (Product) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
