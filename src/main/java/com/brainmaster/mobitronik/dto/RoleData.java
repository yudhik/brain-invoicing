package com.brainmaster.mobitronik.dto;

import java.io.Serializable;

import com.brainmaster.mobitronik.model.Role;

public class RoleData implements Serializable {

	private static final long serialVersionUID = -2782867201546882298L;

	private String role;
	private String description;

	public RoleData() {
	}

	public RoleData(String role, String description) {
		this.role = role;
		this.description = description;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Role getRoleEntity() {
		return new Role(this.role, this.description);
	}

	public static RoleData convertRole(Role roleEntity) {
		return new RoleData(roleEntity.getRole(), roleEntity.getDescription());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		RoleData other = (RoleData) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

}
