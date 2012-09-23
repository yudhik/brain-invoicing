package com.brainmaster.mobitronik.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.brainmaster.mobitronik.model.User;

public class UserData implements Serializable {

	private static final long serialVersionUID = -8096142949112778855L;

	private String emailAddress;
	private String password;
	private String confirmPassword;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private List<StoreData> stores;

	private List<String> storeIds;
	private List<String> roleIds;

	public UserData() {
	}

	public UserData(String emailAddress, String password, String firstName, String lastName, String phoneNumber) {
		this.emailAddress = emailAddress;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public User getUserEntity() {
		User user = new User(this.emailAddress, ArrayUtils.toObject(this.password.getBytes()), this.firstName);
		user.setPhoneNumber(this.phoneNumber);
		user.setLastName(this.lastName);
		return user;
	}

	public static UserData convertUser(User user) {
		return new UserData(user.getEmailAddress(),
				new String(ArrayUtils.toPrimitive(user.getPassword())), user.getFirstName(),
				user.getLastName(), user.getPhoneNumber());
	}

	public void setStores(List<StoreData> stores) {
		this.stores = stores;
	}

	public void setStoreIds(List<String> storeIds) {
		this.storeIds = storeIds;
	}

	public List<String> getStoreIds() {
		return storeIds;
	}

	public List<StoreData> getStores() {
		return stores;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserData other = (UserData) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		return true;
	}

}
