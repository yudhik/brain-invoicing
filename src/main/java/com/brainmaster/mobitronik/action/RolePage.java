package com.brainmaster.mobitronik.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.RoleData;
import com.brainmaster.mobitronik.primefaces.model.RoleDataModel;
import com.brainmaster.mobitronik.processor.RoleProcessor;

@Component
@Scope("request")
public class RolePage {

	private static final Logger log = LoggerFactory.getLogger(RolePage.class);

	@Autowired
	private RoleProcessor roleProcessor;

	@Autowired
	private RoleForm roleForm;

	private RoleData selectedRole;

	public boolean isRenderRoles() {
		if (getRoles().size() > 0)
			return true;
		return false;
	}

	public void saveRole() {
		roleProcessor.saveRole(getRoleForm().getRole());
	}

	public List<RoleData> getRoles() {
		return roleProcessor.getRoles();
	}

	public void setSelectedRole(RoleData selectedRole) {
		if (selectedRole != null) {
			log.debug("role " + selectedRole.getRole() + " is selected");
			this.selectedRole = selectedRole;
		}
	}

	public RoleData getSelectedRole() {
		return selectedRole;
	}

	public RoleDataModel getRoleDataModel() {
		return new RoleDataModel(getRoles());
	}

	public RoleForm getRoleForm() {
		return roleForm;
	}

	public void setRoleForm(RoleForm roleForm) {
		this.roleForm = roleForm;
	}

}
