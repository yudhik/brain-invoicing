package com.brainmaster.mobitronik.action;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brainmaster.mobitronik.dto.CategoryData;
import com.brainmaster.util.helper.uuid.UUIDHelper;

public class CategoryConverter implements Converter {

	private static final Logger log = LoggerFactory.getLogger(CategoryConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		log.debug("value at get as object : " + value + ", value length : " + value.length());
		if (value == null || value.length() < 1)
			return null;
		CategoryData data = new CategoryData(UUIDHelper.stringToUUID(value));
		log.debug("get as object : " + data.getUuid());
		return data;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		CategoryData data = (CategoryData) value;
		log.debug("get as string : " + data.getUuid());
		return data.getUuid() != null ? data.getUuid() : null;
	}

}
