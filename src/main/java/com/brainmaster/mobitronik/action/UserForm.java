package com.brainmaster.mobitronik.action;

import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.RoleData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.dto.UserData;

@Component
@Scope("session")
public class UserForm {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UserForm.class);

	private UserData user = new UserData();
	private UserData selectedUser;
	
	private DualListModel<StoreData> selectedUserStoresModel;
	private DualListModel<RoleData> selectedUserRolesModel;

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public void createNewUser() {
		user = new UserData();
	}
	
	public void setSelectedUser(UserData selectedUser) {
		this.selectedUser = selectedUser;
	}

	public UserData getSelectedUser() {
		return selectedUser;
	}

	public DualListModel<StoreData> getSelectedUserStoresModel() {
		return selectedUserStoresModel;
	}

	public void setSelectedUserStoresModel(DualListModel<StoreData> selectedUserStoresModel) {
		this.selectedUserStoresModel = selectedUserStoresModel;
	}

	public DualListModel<RoleData> getSelectedUserRolesModel() {
		return selectedUserRolesModel;
	}

	public void setSelectedUserRolesModel(DualListModel<RoleData> selectedUserRolesModel) {
		this.selectedUserRolesModel = selectedUserRolesModel;
	}
	
	
}
