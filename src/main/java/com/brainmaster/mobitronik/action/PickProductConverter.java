package com.brainmaster.mobitronik.action;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.brainmaster.mobitronik.bean.service.ProductService;
import com.brainmaster.mobitronik.dto.BrandData;
import com.brainmaster.mobitronik.dto.CategoryData;
import com.brainmaster.mobitronik.dto.PackagingValueData;
import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.model.Product;
import com.brainmaster.util.helper.uuid.UUIDHelper;

public class PickProductConverter implements Converter {
	private static final Logger log = LoggerFactory.getLogger(PickProductConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		log.debug("pick product converter value : " + value);
		if (value == null || value.length() == 0)
			return new ProductData();
		ProductService productService = (ProductService) FacesContextUtils.getWebApplicationContext(context).getBean("productService");
		Product product = productService.find(UUIDHelper.stringToUUID(value));
		log.debug("product data : " +
				new ProductData(product.getUuid(), product.getProductCode(), product.getProductName(), product.getBarcodeNumber(),
						PackagingValueData.convertPackagingValue(product.getPackageCode()), BrandData.convertBrand(product.getBrand()),
						CategoryData.convertCategory(product.getCategory())).toString());
		return new ProductData(product.getUuid(), product.getProductCode(), product.getProductName(), product.getBarcodeNumber(),
				PackagingValueData.convertPackagingValue(product.getPackageCode()), BrandData.convertBrand(product.getBrand()),
				CategoryData.convertCategory(product.getCategory()));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return UUIDHelper.uuidToString(((ProductData) value).getUuid());
	}

}
