package com.brainmaster.mobitronik.processor;

import java.util.List;

import com.brainmaster.mobitronik.dto.CategoryData;

public interface CategoryProcessor {

	public List<CategoryData> getCategories();

	public void saveCategory(CategoryData categoryData);

	public void updateCategory(CategoryData categoryData);

    public void removeCategory(CategoryData categoryData);
}
