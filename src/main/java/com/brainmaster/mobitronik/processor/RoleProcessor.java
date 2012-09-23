package com.brainmaster.mobitronik.processor;

import java.util.List;

import com.brainmaster.mobitronik.dto.RoleData;

public interface RoleProcessor {

	public List<RoleData> getRoles();

	public void saveRole(RoleData roleData);
}
