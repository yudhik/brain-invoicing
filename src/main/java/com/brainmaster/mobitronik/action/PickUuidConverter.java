package com.brainmaster.mobitronik.action;

import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brainmaster.util.helper.uuid.UUIDHelper;

public class PickUuidConverter implements Converter {
	private static final Logger log = LoggerFactory.getLogger(PickUuidConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		log.debug("pick uuid converter value : " + value);
		return UUIDHelper.stringToUUID(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return UUIDHelper.uuidToString((UUID)value);
	}
}
