package com.brainmaster.mobitronik.action;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brainmaster.mobitronik.dto.BrandData;
import com.brainmaster.util.helper.uuid.UUIDHelper;

public class BrandConverter implements Converter {

	private static final Logger log = LoggerFactory.getLogger(BrandConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		BrandData data = new BrandData(UUIDHelper.stringToUUID(value));
		log.debug("get as object : " + data);
		return data;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		log.debug("get as string : " + ((BrandData) value).getUuid());
		return ((BrandData) value).getUuid();
	}

}
