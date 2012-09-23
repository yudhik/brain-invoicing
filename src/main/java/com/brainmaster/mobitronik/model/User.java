package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.brainmaster.util.types.UUIDType;

@Entity
@TypeDefs({@TypeDef(name = "uuid", typeClass = UUIDType.class)})
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 6862888645550391162L;

	@Id
	@Column(name = "email_address")
	private String emailAddress;

	@Column(nullable = false)
	private Byte[] password;

	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@Column(name = "last_name", length = 50)
	private String lastName;

	@Column(name = "phone_number", length = 25)
	private String phoneNumber;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id.user", cascade = CascadeType.ALL)
	private List<UserRole> roles = new ArrayList<UserRole>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id.user", cascade = CascadeType.ALL)
	private List<UserStore> stores = new ArrayList<UserStore>();

	public User() {
	}

	public User(String emailAddress, Byte[] password, String firstName) {
		this.emailAddress = emailAddress;
		this.password = password;
		this.firstName = firstName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Byte[] getPassword() {
		return password;
	}

	public void setPassword(Byte[] password) {
		this.password = password;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setStores(List<UserStore> stores) {
		this.stores = stores;
	}

	public List<UserStore> getStores() {
		return stores;
	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}
}
