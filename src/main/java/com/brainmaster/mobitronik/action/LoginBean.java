package com.brainmaster.mobitronik.action;

import java.io.Serializable;

import com.brainmaster.mobitronik.dto.UserData;

public class LoginBean implements Serializable {

	private static final long serialVersionUID = -4138087670339926168L;

	private UserData userData;
	private boolean authenticate;

	public LoginBean() {
		authenticate = false;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public boolean isAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(boolean authenticate) {
		this.authenticate = authenticate;
	}

}
