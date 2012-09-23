package com.brainmaster.mobitronik.dto;

import java.io.Serializable;
import java.util.UUID;

import com.brainmaster.mobitronik.model.Product;

public class ProductData implements Serializable {

	private static final long serialVersionUID = 1320904849740573843L;

	private UUID uuid;
	private String productCode;
	private String productName;
	private String barcodeNumber;
	private PackagingValueData packageCode;
	private BrandData brand;
	private CategoryData category;

	public ProductData() {
	}

	public ProductData(UUID uuid) {
		this.uuid = uuid;
	}

	public ProductData(UUID uuid, String productCode, String productName,
			String barcodeNumber, PackagingValueData packageCode,
			BrandData brand, CategoryData category) {
		this.uuid = uuid;
		this.productCode = productCode;
		this.productName = productName;
		this.barcodeNumber = barcodeNumber;
		this.packageCode = packageCode;
		this.brand = brand;
		this.category = category;
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

	public PackagingValueData getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(PackagingValueData packageCode) {
		this.packageCode = packageCode;
	}

	public BrandData getBrand() {
		return brand;
	}

	public void setBrand(BrandData brand) {
		this.brand = brand;
	}

	public CategoryData getCategory() {
		return category;
	}

	public void setCategory(CategoryData category) {
		this.category = category;
	}

	public Product getProductEntity() {
		Product product = new Product(uuid, productCode, productName, barcodeNumber, brand.getBrandEntity(),
				category.getCategoryEntity(), packageCode.getPackagingValueEntity());
		return product;
	}

	public static ProductData convertProduct(Product product) {
		return new ProductData(product.getUuid(), product.getProductCode(),
				product.getProductName(), product.getBarcodeNumber(),
				PackagingValueData.convertPackagingValue(product.getPackageCode()), BrandData.convertBrand(product.getBrand()),
				CategoryData.convertCategory(product.getCategory()));
	}

	@Override
	public String toString() {
		return "ProductData [uuid=" + uuid + ", productCode=" + productCode + ", productName=" + productName + ", barcodeNumber=" + barcodeNumber +
				", packageCode=" + packageCode + ", brand=" + brand + ", category=" + category + "]";
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
		ProductData other = (ProductData) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
