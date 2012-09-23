package com.brainmaster.mobitronik.primefaces.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brainmaster.mobitronik.dto.UserData;

public class UserDataModel extends ListDataModel<UserData> implements SelectableDataModel<UserData> {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UserDataModel.class);

	public UserDataModel() {
	}

	public UserDataModel(List<UserData> userDatas) {
		super(userDatas);
	}

	@Override
	public Object getRowKey(UserData userData) {
		return userData.getEmailAddress();
	}

	@Override
	public UserData getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<UserData> usersData = (List<UserData>) getWrappedData();

		for (UserData userData : usersData) {
			if (userData.getEmailAddress().equals(rowKey))
				return userData;
		}
		return null;
	}

}
