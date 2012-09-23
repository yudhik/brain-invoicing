package com.brainmaster.mobitronik.processor;

import java.util.List;

import com.brainmaster.mobitronik.dto.BrandData;

public interface BrandProcessor {

	public List<BrandData> getBrands();

	public void removeBrand(BrandData brandData);

	public void saveBrand(BrandData brandData);

	public void updateBrand(BrandData brandData);
}
