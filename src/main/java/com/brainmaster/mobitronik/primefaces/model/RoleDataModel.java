package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.brainmaster.mobitronik.dto.RoleData;

public class RoleDataModel extends ListDataModel<RoleData> implements SelectableDataModel<RoleData> {

	public RoleDataModel() {
	}

	public RoleDataModel(List<RoleData> list) {
		super(list);
	}

	@Override
	public Object getRowKey(RoleData roleData) {
		return roleData.getRole();
	}

	@Override
	public RoleData getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<RoleData> rolesData = (List<RoleData>) getWrappedData();

		for (RoleData roleData : rolesData) {
			if (roleData.getRole().equals(rowKey))
				return roleData;
		}
		return null;
	}

}
