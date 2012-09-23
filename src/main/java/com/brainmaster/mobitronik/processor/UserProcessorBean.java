package com.brainmaster.mobitronik.processor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.RoleService;
import com.brainmaster.mobitronik.bean.service.StoreService;
import com.brainmaster.mobitronik.bean.service.UserService;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.dto.UserData;
import com.brainmaster.mobitronik.model.CompositeUserRole;
import com.brainmaster.mobitronik.model.CompositeUserStore;
import com.brainmaster.mobitronik.model.User;
import com.brainmaster.mobitronik.model.UserRole;
import com.brainmaster.mobitronik.model.UserStore;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Component
@Scope("prototype")
public class UserProcessorBean implements UserProcessor {
	
	private static final Logger log = LoggerFactory.getLogger(UserProcessorBean.class);

	@Autowired
	private UserService userService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private RoleService roleService;

	@Override
	public List<UserData> getUsers() {
		List<UserData> users = new ArrayList<UserData>();
		for (User user : userService.findAll())
			users.add(UserData.convertUser(user));
		return users;
	}

	@Override
	public void saveUser(UserData user) {
		User entity = user.getUserEntity();
		for (String id : user.getStoreIds()) {
			CompositeUserStore userStoreId = new CompositeUserStore(entity, storeService.find(UUIDHelper.stringToUUID(id)));
			UserStore userStore = new UserStore(userStoreId);
			entity.getStores().add(userStore);
		}
		for (String id : user.getRoleIds()) {
			CompositeUserRole userRoleId = new CompositeUserRole(entity, roleService.find(id));
			UserRole userRole = new UserRole(userRoleId);
			entity.getRoles().add(userRole);
		}
		userService.addUser(entity);
	}

	@Override
	public List<StoreData> findUserStore(String username) {
		List<StoreData> stores = new ArrayList<StoreData>();
		User authenticateUser = userService.find(username);
		if (authenticateUser == null)
			return stores;
		for (UserStore store : authenticateUser.getStores()) {
			stores.add(StoreData.convertStore(store.getStore()));
		}
		return stores;
	}

	@Override
	public void updateUser(UserData user) {
		log.debug("update user with email : {}", user.getEmailAddress());
		userService.updateUser(user);
	}

	@Override
	public void removeUser(UserData user) {
		userService.removeUser(user);
	}

}
