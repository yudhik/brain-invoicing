package com.brainmaster.mobitronik.action;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.BrandData;

@Component
@Scope("session")
public class BrandForm {

	private BrandData brand = new BrandData(UUID.randomUUID());
	private BrandData selectedBrand;

	public BrandData getBrand() {
		return brand;
	}

	public void setBrand(BrandData brand) {
		this.brand = brand;
	}

	public void createNewBrand() {
		brand = new BrandData(UUID.randomUUID());
	}

	public BrandData getSelectedBrand() {
		return selectedBrand;
	}

	public void setSelectedBrand(BrandData selectedBrand) {
		this.selectedBrand = selectedBrand;
	}

}
