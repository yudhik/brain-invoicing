package com.brainmaster.mobitronik.action;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.CategoryData;

@Component
@Scope("session")
public class CategoryForm {

	private CategoryData category = new CategoryData(UUID.randomUUID());
	private CategoryData selectedCategory;
	private CategoryData selectedParentCategory;

	public CategoryData getCategory() {
		return category;
	}

	public void setCategory(CategoryData category) {
		this.category = category;
	}

	public void createNewCategory() {
		category = new CategoryData(UUID.randomUUID());
	}

	public CategoryData getSelectedParentCategory() {
		return selectedParentCategory;
	}

	public void setSelectedParentCategory(CategoryData selectedParentCategory) {
		this.selectedParentCategory = selectedParentCategory;
		if (selectedParentCategory != null)
			category.setParentCategory(selectedParentCategory);
	}

	public CategoryData getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(CategoryData selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

}
