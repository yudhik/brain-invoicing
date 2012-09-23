package com.brainmaster.mobitronik.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.BrandService;
import com.brainmaster.mobitronik.dto.BrandData;
import com.brainmaster.mobitronik.model.Brand;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Component
@Scope("prototype")
public class BrandProcessorBean implements BrandProcessor {

	@Autowired
	private BrandService brandService;

	@Override
	public List<BrandData> getBrands() {
		List<BrandData> brands = new ArrayList<BrandData>();
		for (Brand brand : brandService.findAll())
			brands.add(BrandData.convertBrand(brand));
		return brands;
	}

	@Override
	public void saveBrand(BrandData brandData) {
		brandService.addBrand(brandData.getBrandEntity());
	}

	@Override
	public void removeBrand(BrandData brandData) {
		brandService.removeBrand(brandService.find(UUIDHelper.stringToUUID(brandData.getUuid())));
	}

	@Override
	public void updateBrand(BrandData brandData) {
		Brand brand = brandService.find(UUIDHelper.stringToUUID(brandData.getUuid()));
		brand.setBrandName(brandData.getBrandName());
		brandService.updateBrand(brand);
	}
}
