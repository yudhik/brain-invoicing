package com.brainmaster.mobitronik.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.BrandData;
import com.brainmaster.mobitronik.dto.CategoryData;
import com.brainmaster.mobitronik.dto.PackagingValueData;
import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.primefaces.model.ProductDataModel;
import com.brainmaster.mobitronik.processor.BrandProcessor;
import com.brainmaster.mobitronik.processor.CategoryProcessor;
import com.brainmaster.mobitronik.processor.PackagingValueProcessor;
import com.brainmaster.mobitronik.processor.ProductProcessor;

@Component
@Scope("request")
public class ProductPage {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ProductPage.class);

	@Autowired
	private ProductProcessor productProcessor;

	@Autowired
	private BrandProcessor brandProcessor;

	@Autowired
	private PackagingValueProcessor packagingValueProcessor;

	@Autowired
	private CategoryProcessor categoryProcessor;

	@Autowired
	private ProductForm productForm;

	private ProductData selectedProduct;

	public List<ProductData> getProducts() {
		return productProcessor.getProducts();
	}

	public void setSelectedProduct(ProductData selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public ProductData getSelectedProduct() {
		return selectedProduct;
	}

	public void saveProduct() {
		productProcessor.saveProduct(getProductForm().getProduct());
		productForm.createNewProduct();
	}

	public ProductDataModel getProductDataModel() {
		return new ProductDataModel(getProducts());
	}

	public List<BrandData> getBrands() {
		return brandProcessor.getBrands();
	}

	public List<CategoryData> getCategories() {
		return categoryProcessor.getCategories();
	}

	public List<PackagingValueData> getPackageCodes() {
		return packagingValueProcessor.getPackDatas();
	}

	public ProductForm getProductForm() {
		return productForm;
	}

	public void setProductForm(ProductForm productForm) {
		this.productForm = productForm;
	}
}
