package com.brainmaster.mobitronik.processor;

import java.util.List;

import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.dto.UserData;

public interface UserProcessor {

	public List<UserData> getUsers();

	public void saveUser(UserData user);

	public List<StoreData> findUserStore(String username);
	
	public void updateUser(UserData user);
	
	public void removeUser(UserData user);
}
