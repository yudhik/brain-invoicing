package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.brainmaster.util.DatabaseColumnConstant;

@Entity
@Table(name = "category")
public class Category implements Serializable {

	private static final long serialVersionUID = -4114451169235211979L;

	@Id
	@Type(type = "uuid")
	@Column(length = DatabaseColumnConstant.SIZE_UUID)
	private UUID uuid;

	@Column(name = "category_name", unique = true)
	private String categoryName;

	@ManyToOne(targetEntity = Category.class, optional = true)
	private Category parentCategory;

	@OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
	private List<Category> childCategories = new ArrayList<Category>();

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<Product>();

	public Category() {
	}

	public Category(UUID uuid, String categoryName) {
		this.uuid = uuid;
		this.categoryName = categoryName;
	}

	public Category(UUID uuid, String categoryName, Category parentCategory) {
		this.uuid = uuid;
		this.categoryName = categoryName;
		this.parentCategory = parentCategory;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Category> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(List<Category> childCategories) {
		this.childCategories = childCategories;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Product> getProducts() {
		return products;
	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}

}
