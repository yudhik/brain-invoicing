package com.brainmaster.mobitronik.dto;

import java.io.Serializable;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brainmaster.mobitronik.model.Category;
import com.brainmaster.util.helper.uuid.UUIDHelper;

public class CategoryData implements Serializable {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CategoryData.class);

	private static final long serialVersionUID = -5582167776657959040L;

	private String uuid;
	private String categoryName;
	private CategoryData parentCategory;

	public CategoryData() {
	}

	public CategoryData(UUID uuid) {
		this.uuid = UUIDHelper.uuidToString(uuid);
	}

	public CategoryData(String uuid, String categoryName) {
		this.uuid = uuid;
		this.categoryName = categoryName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public CategoryData getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(CategoryData parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Category getCategoryEntity() {
		return new Category(UUIDHelper.stringToUUID(uuid), categoryName);//, parentCategory.getCategoryEntity());
	}

	public static CategoryData convertCategory(Category category) {
		CategoryData data = new CategoryData(UUIDHelper.uuidToString(category.getUuid()), category.getCategoryName());
		if (category.getParentCategory() != null)
			data.setParentCategory(new CategoryData(UUIDHelper.uuidToString(category.getParentCategory().getUuid()), 
				category.getParentCategory().getCategoryName()));
		return data;
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
		CategoryData other = (CategoryData) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}
