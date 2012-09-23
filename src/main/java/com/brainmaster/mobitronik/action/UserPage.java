package com.brainmaster.mobitronik.action;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.RoleData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.dto.UserData;
import com.brainmaster.mobitronik.primefaces.model.UserDataModel;
import com.brainmaster.mobitronik.processor.RoleProcessor;
import com.brainmaster.mobitronik.processor.StoreProcessor;
import com.brainmaster.mobitronik.processor.UserProcessor;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Component
@Scope("request")
public class UserPage {

	private static final Logger log = LoggerFactory.getLogger(UserPage.class);

	@Autowired
	private UserProcessor userProcessor;

	@Autowired
	private StoreProcessor storeProcessor;

	@Autowired
	private RoleProcessor roleProcessor;

	@Autowired
	private UserForm userForm;

	private DualListModel<StoreData> storesModel;
	private DualListModel<RoleData> rolesModel;

	public void saveUser() {
		userProcessor.saveUser(getUserForm().getUser());
	}

	public List<UserData> getUsers() {
		return userProcessor.getUsers();
	}

	public boolean isRenderUsers() {
		if (getUsers().size() > 0)
			return true;
		return false;
	}

	public UserDataModel getUserDataModel() {
		return new UserDataModel(getUsers());
	}

	public UserForm getUserForm() {
		return userForm;
	}

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public void setStoresModel(DualListModel<StoreData> storesModel) {
		this.storesModel = storesModel;
		log.debug("target size : " + storesModel.getTarget().size());
		List<String> ids = new ArrayList<String>();
		for (StoreData data : storesModel.getTarget())
			ids.add(data.getUuid());
		if (ids.size() > 0)
			getUserForm().getUser().setStoreIds(ids);
	}

	public DualListModel<StoreData> getStoresModel() {
		if (storesModel == null) {
			List<StoreData> source = new ArrayList<StoreData>();
			List<StoreData> target = new ArrayList<StoreData>();
			for (StoreData store : storeProcessor.getStores()) {
				source.add(store);
			}
			storesModel = new DualListModel<StoreData>(source, target);
		}
		return storesModel;
	}

	public void setRolesModel(DualListModel<RoleData> rolesModel) {
		this.rolesModel = rolesModel;
		List<String> ids = new ArrayList<String>();
		for (RoleData data : rolesModel.getTarget())
			ids.add(data.getRole());
		if (ids.size() > 0)
			getUserForm().getUser().setRoleIds(ids);
	}

	public DualListModel<RoleData> getRolesModel() {
		if (rolesModel == null) {
			List<RoleData> source = new ArrayList<RoleData>();
			List<RoleData> target = new ArrayList<RoleData>();
			for (RoleData role : roleProcessor.getRoles()) {
				source.add(role);
			}
			rolesModel = new DualListModel<RoleData>(source, target);
		}
		return rolesModel;
	}
	
	public void setSelectedUserStores(DualListModel<StoreData> storesModel) {
		getUserForm().setSelectedUserStoresModel(storesModel);
		List<String> ids = new ArrayList<String>();
		for (StoreData data : getUserForm().getSelectedUserStoresModel().getTarget())
			ids.add(data.getUuid());
		if (ids.size() > 0)
			getUserForm().getSelectedUser().setStoreIds(ids);
	}
	
	public DualListModel<StoreData> getSelectedUserStores() {
		if (getUserForm().getSelectedUserStoresModel() == null) {
			List<StoreData> source = new ArrayList<StoreData>();
			List<StoreData> target = new ArrayList<StoreData>();
			for (StoreData store : storeProcessor.getStores()) {
				boolean setToSource = true;
				if(getUserForm().getSelectedUser() != null) {
					for(String storeId : getUserForm().getSelectedUser().getStoreIds()) {
						if(store.getStoreEntity().getUuid().equals(UUIDHelper.stringToUUID(storeId))) {
							target.add(store);
							setToSource = false;
						}
					}
				}
				if(setToSource)
					source.add(store);
			}
			getUserForm().setSelectedUserStoresModel(new DualListModel<StoreData>(source, target));
		}
		return getUserForm().getSelectedUserStoresModel();
	}
	
	public void setSelectedUserRoles(DualListModel<RoleData> rolesModel) {
		getUserForm().setSelectedUserRolesModel(rolesModel);
		List<String> ids = new ArrayList<String>();
		for (RoleData data : getUserForm().getSelectedUserRolesModel().getTarget())
			ids.add(data.getRole());
		if (ids.size() > 0)
			getUserForm().getSelectedUser().setRoleIds(ids);
	}
	
	public DualListModel<RoleData> getSelectedUserRoles(){
		if (getUserForm().getSelectedUserRolesModel() == null) {
			List<RoleData> source = new ArrayList<RoleData>();
			List<RoleData> target = new ArrayList<RoleData>();
			for (RoleData role : roleProcessor.getRoles()) {
				boolean setToSource = true;
				if(getUserForm().getSelectedUser() != null) {
					for(String roleId : getUserForm().getSelectedUser().getRoleIds()) {
						if(role.getRole().equals(roleId)) {
							target.add(role);
							setToSource = false;
						}
					}
				}
				if(setToSource)
					source.add(role);
			}
			getUserForm().setSelectedUserRolesModel(new DualListModel<RoleData>(source, target));
		}
		return getUserForm().getSelectedUserRolesModel();
	}
	
	public void updateSelection(SelectEvent event) {
		getUserForm().setSelectedUser((UserData) event.getObject());
	}
	
	public void updateSelectedUser() {
		userProcessor.updateUser(getUserForm().getSelectedUser());
	}
	
	public void removeSelectedUser() {
		userProcessor.removeUser(getUserForm().getSelectedUser());
	}
}
