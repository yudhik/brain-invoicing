package com.brainmaster.mobitronik.dto;

import java.io.Serializable;

import com.brainmaster.mobitronik.model.PackagingValue;

public class PackagingValueData implements Serializable {

	private static final long serialVersionUID = -2774071929534126855L;

	private String packageId;
	private Integer contentQuantity;

	public PackagingValueData() {
	}

	public PackagingValueData(String packageId) {
		this.packageId = packageId;
	}

	public PackagingValueData(String packageId, Integer contentQuantity) {
		this.packageId = packageId;
		this.contentQuantity = contentQuantity;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public Integer getContentQuantity() {
		return contentQuantity;
	}

	public void setContentQuantity(Integer contentQuantity) {
		this.contentQuantity = contentQuantity;
	}

	public PackagingValue getPackagingValueEntity() {
		return new PackagingValue(packageId, contentQuantity);
	}

	public static PackagingValueData convertPackagingValue(PackagingValue packagingValue) {
		return new PackagingValueData(packagingValue.getPackageId(), packagingValue.getContentQuantity());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((packageId == null) ? 0 : packageId.hashCode());
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
		PackagingValueData other = (PackagingValueData) obj;
		if (packageId == null) {
			if (other.packageId != null)
				return false;
		} else if (!packageId.equals(other.packageId))
			return false;
		return true;
	}

}
