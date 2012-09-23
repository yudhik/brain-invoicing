package com.brainmaster.mobitronik.model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@AssociationOverrides({
		@AssociationOverride(name = "id.user", joinColumns = @JoinColumn(name = "user_id")),
		@AssociationOverride(name = "id.store", joinColumns = @JoinColumn(name = "store_id"))})
@Table(name = "userstore")
public class UserStore implements Serializable {

	private static final long serialVersionUID = -7693552695854826988L;

	@EmbeddedId
	private CompositeUserStore id;

	public UserStore() {
	}

	public UserStore(CompositeUserStore id) {
		this.id = id;
	}

	public CompositeUserStore getId() {
		return id;
	}

	public void setId(CompositeUserStore id) {
		this.id = id;
	}

	@Transient
	public Store getStore() {
		return getId().getStore();
	}

	public void setStore(Store store) {
		getId().setStore(store);
	}

	@Transient
	public User getUser() {
		return getId().getUser();
	}

	public void setUser(User user) {
		getId().setUser(user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		UserStore other = (UserStore) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
