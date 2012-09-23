package com.brainmaster.mobitronik.dto;

import java.io.Serializable;
import java.util.UUID;

import com.brainmaster.mobitronik.model.Brand;
import com.brainmaster.util.helper.uuid.UUIDHelper;

public class BrandData implements Serializable {

	private static final long serialVersionUID = 111015351793142203L;

	private String uuid;
	private String brandName;

	public BrandData() {
	}

	public BrandData(UUID uuid) {
		this.uuid = UUIDHelper.uuidToString(uuid);
	}

	public BrandData(String uuid, String brandName) {
		this.uuid = uuid;
		this.brandName = brandName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Brand getBrandEntity() {
		return new Brand(UUIDHelper.objectToUUID(uuid), brandName);
	}

	public static BrandData convertBrand(Brand brand) {
		return new BrandData(UUIDHelper.uuidToString(brand.getUuid()), brand.getBrandName());
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
		BrandData other = (BrandData) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BrandData [uuid=" + uuid + ", brandName=" + brandName + "]";
	}

}
