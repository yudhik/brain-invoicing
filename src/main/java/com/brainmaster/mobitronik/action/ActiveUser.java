package com.brainmaster.mobitronik.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.processor.StoreProcessor;
import com.brainmaster.mobitronik.processor.UserProcessor;

@Component
@Scope("session")
public class ActiveUser {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ActiveUser.class);

	@Autowired
	private UserProcessor userProcessor;

	@Autowired
	private StoreProcessor storeProcessor;

	private StoreData selectedStore;

	private SecurityContext getSecurityContext() {
		return SecurityContextHolder.getContext();
	}

	public String getUsername() {
		return ((User) getSecurityContext().getAuthentication().getPrincipal()).getUsername();
	}

	public List<String> getAuthorities() {
		List<String> roles = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		List<SimpleGrantedAuthority> grantedAuthorities = (List<SimpleGrantedAuthority>) getSecurityContext().getAuthentication().getAuthorities();
		for (SimpleGrantedAuthority grantedAuthority : grantedAuthorities) {
			String authority = grantedAuthority.getAuthority();
			roles.add(authority.substring(authority.indexOf("_") + 1).toLowerCase());
		}
		return roles;
	}

	public StoreData getSelectedStore() {
		if (selectedStore == null && getUsername() != null) {
			List<StoreData> stores = getManagedStores();
			if (stores.size() > 0)
				selectedStore = stores.get(0);
		}
		return selectedStore;
	}

	public void setSelectedStore(StoreData selectedStore) {
		this.selectedStore = selectedStore;
	}

	public boolean isSuperUser() {
		return getAuthorities().contains("super-user");
	}

	public boolean isAdministrator() {
		return getAuthorities().contains("administrator");
	}

	public List<StoreData> getManagedStores() {
		if (isSuperUser())
			return storeProcessor.getStores();
		return userProcessor.findUserStore(getUsername());
	}
}
