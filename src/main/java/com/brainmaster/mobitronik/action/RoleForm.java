package com.brainmaster.mobitronik.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.RoleData;

@Component
@Scope("session")
public class RoleForm {

	private RoleData role = new RoleData();

	public RoleData getRole() {
		return role;
	}

	public void setRole(RoleData role) {
		this.role = role;
	}

	public void createNewRole() {
		role = new RoleData();
	}
}
