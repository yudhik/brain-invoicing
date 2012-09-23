package com.brainmaster.mobitronik.action;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.web.jsf.FacesContextUtils;

import com.brainmaster.mobitronik.bean.service.RoleService;
import com.brainmaster.mobitronik.dto.RoleData;

public class PickRoleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.length() == 0)
			return new RoleData();
		RoleService roleService = (RoleService) FacesContextUtils.getWebApplicationContext(context).getBean("roleService");
		return RoleData.convertRole(roleService.find(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((RoleData) value).getRole();
	}

}
