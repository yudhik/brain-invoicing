package com.brainmaster.mobitronik.action;

import java.util.List;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.CategoryData;
import com.brainmaster.mobitronik.primefaces.model.CategoryDataModel;
import com.brainmaster.mobitronik.processor.CategoryProcessor;

@Component
@Scope("request")
public class CategoryPage {

	private static final Logger log = LoggerFactory.getLogger(CategoryPage.class);
	@Autowired
	private CategoryProcessor categoryProcessor;
	@Autowired
	private CategoryForm categoryForm;
	private CategoryData selectedCategory;

	public CategoryForm getCategoryForm() {
		return categoryForm;
	}

	public void setCategoryForm(CategoryForm categoryForm) {
		this.categoryForm = categoryForm;
	}

	public void setSelectedCategory(CategoryData selectedCategory) {
		log.info("selected category : " + selectedCategory.getCategoryName());
		this.selectedCategory = selectedCategory;
	}

	public CategoryData getSelectedCategory() {
		return selectedCategory;
	}

	public List<CategoryData> getCategories() {

		return categoryProcessor.getCategories();
	}

	public boolean isRenderCategories() {
		if (getCategories().size() > 0) {
			return true;
		}
		return false;
	}

	public CategoryDataModel getCategoryDataModel() {
		return new CategoryDataModel(getCategories());
	}

	public void saveCategory() {
		categoryProcessor.saveCategory(getCategoryForm().getCategory());
		categoryForm.createNewCategory();
	}

	public void updateCategory() {
		categoryProcessor.updateCategory(getCategoryForm().getSelectedCategory());
	}
	
	public void removeCategory() {
	    categoryProcessor.removeCategory(getCategoryForm().getSelectedCategory());
	}
	
	public void updateSelection(SelectEvent event) {
		getCategoryForm().setSelectedCategory((CategoryData) event.getObject());
	}
}
