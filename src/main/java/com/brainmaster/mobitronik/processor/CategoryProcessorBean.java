package com.brainmaster.mobitronik.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.CategoryService;
import com.brainmaster.mobitronik.dto.CategoryData;
import com.brainmaster.mobitronik.model.Category;
import com.brainmaster.util.helper.uuid.UUIDHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Scope("prototype")
public class CategoryProcessorBean implements CategoryProcessor {
    
	private static final Logger log = LoggerFactory.getLogger(CategoryProcessorBean.class);

	@Autowired
	private CategoryService categoryService;

	@Override
	public List<CategoryData> getCategories() {
		List<CategoryData> categories = new ArrayList<CategoryData>();
		for (Category category : categoryService.findAll())
			categories.add(CategoryData.convertCategory(category));
		return categories;
	}

	@Override
	public void saveCategory(CategoryData categoryData) {
		if (categoryData.getParentCategory() == null)
			categoryService.addCategory(categoryData.getCategoryEntity());
		else {
			Category parent = categoryService.find(UUIDHelper.stringToUUID(categoryData.getParentCategory().getUuid()));
			Category child = new Category(UUIDHelper.stringToUUID(categoryData.getUuid()), categoryData.getCategoryName(), parent);
			categoryService.addCategory(child);
			categoryService.addParentCategory(child.getUuid(), UUIDHelper.stringToUUID(categoryData.getParentCategory().getUuid()));
		}
	}

	public void updateCategory(CategoryData categoryData) {
		Category category = categoryService.find(UUIDHelper.stringToUUID(categoryData.getUuid()));
		if(categoryData.getParentCategory() != null) {
		    log.info("found parent category");
		    category.setParentCategory(categoryService.find(UUIDHelper.stringToUUID(categoryData.getParentCategory().getUuid())));
		}
		categoryService.update(category);
	}

    public void removeCategory(CategoryData categoryData) {
	Category category = categoryService.find(UUIDHelper.stringToUUID(categoryData.getUuid()));
	categoryService.removeCategory(category);
    }

}
