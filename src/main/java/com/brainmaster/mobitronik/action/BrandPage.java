package com.brainmaster.mobitronik.action;

import java.util.List;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.BrandData;
import com.brainmaster.mobitronik.primefaces.model.BrandDataModel;
import com.brainmaster.mobitronik.processor.BrandProcessor;

@Component
@Scope("request")
public class BrandPage {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(BrandPage.class);

	@Autowired
	private BrandProcessor brandProcessor;

	private BrandData selectedBrand;

	@Autowired
	private BrandForm brandForm;

	public List<BrandData> getBrands() {
		return brandProcessor.getBrands();
	}

	public boolean isRenderBrands() {
		if (getBrands().size() > 0)
			return true;
		return false;
	}

	public BrandDataModel getBrandDataModel() {
		return new BrandDataModel(getBrands());
	}

	public void saveBrand() {
		brandProcessor.saveBrand(getBrandForm().getBrand());
		brandForm.createNewBrand();
	}

	public void updateBrand() {
		brandProcessor.updateBrand(getBrandForm().getSelectedBrand());
	}

	public void removeBrand() {
		brandProcessor.removeBrand(getBrandForm().getSelectedBrand());
	}

	public BrandForm getBrandForm() {
		return brandForm;
	}

	public void setBrandForm(BrandForm brandForm) {
		this.brandForm = brandForm;
	}

	public BrandData getSelectedBrand() {
		return selectedBrand;
	}

	public void setSelectedBrand(BrandData selectedBrand) {
		this.selectedBrand = selectedBrand;
	}

	public void updateSelection(SelectEvent event) {
		getBrandForm().setSelectedBrand((BrandData) event.getObject());
	}
}
