package com.brainmaster.mobitronik.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.RoleService;
import com.brainmaster.mobitronik.dto.RoleData;
import com.brainmaster.mobitronik.model.Role;

@Component
@Scope("prototype")
public class RoleProcessorBean implements RoleProcessor {

	@Autowired
	private RoleService roleService;

	@Override
	public List<RoleData> getRoles() {
		List<RoleData> roles = new ArrayList<RoleData>();
		for (Role roleEntity : roleService.findAll()) {
			roles.add(RoleData.convertRole(roleEntity));
		}
		return roles;
	}

	@Override
	public void saveRole(RoleData roleData) {
		roleService.addRole(roleData.getRoleEntity());
	}

}
