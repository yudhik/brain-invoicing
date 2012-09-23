package com.brainmaster.mobitronik.action;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.ProductData;

@Component
@Scope("session")
public class ProductForm {

	private ProductData product = new ProductData(UUID.randomUUID());

	public ProductData getProduct() {
		return product;
	}

	public void setProduct(ProductData product) {
		this.product = product;
	}

	public void createNewProduct() {
		product = new ProductData(UUID.randomUUID());
	}

}
