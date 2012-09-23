package com.brainmaster.mobitronik.action;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.brainmaster.mobitronik.dto.PackagingValueData;

public class PackageConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return new PackagingValueData(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((PackagingValueData) value).getPackageId();
	}

}
