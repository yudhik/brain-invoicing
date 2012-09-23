package com.brainmaster.mobitronik.dto;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;

import com.brainmaster.mobitronik.model.Store;
import com.brainmaster.util.helper.uuid.UUIDHelper;

public class StoreData implements Serializable {

	private static final long serialVersionUID = 8635433626490875417L;

	private String uuid;
	private String storeName;
	private String address;
	private String phoneNumber;
	private StoreData parentStore;

	public StoreData() {
	}

	public StoreData(UUID uuid) {
		this.uuid = UUIDHelper.uuidToString(uuid);
	}

	public StoreData(String uuid, String storeName, String address,
			String phoneNumber) {
		this.uuid = uuid;
		this.storeName = storeName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public StoreData getParentStore() {
		return parentStore;
	}

	public void setParentStore(StoreData parentStore) {
		this.parentStore = parentStore;
	}

	public Store getStoreEntity() {
		Store store = new Store(UUIDHelper.stringToUUID(uuid), storeName, ArrayUtils.toObject(address.getBytes()), phoneNumber);
		if (parentStore != null)
			store.setParentStore(new Store(UUIDHelper.stringToUUID(parentStore.getUuid()), parentStore.getStoreName(),
					ArrayUtils.toObject(parentStore.getAddress().getBytes()), parentStore.getPhoneNumber()));
		return store;
	}

	public static StoreData convertStore(Store store) {
		StoreData storeData = new StoreData(UUIDHelper.uuidToString(store.getUuid()), store.getStoreName(),
				store.getAddress() == null ? null : new String(ArrayUtils.toPrimitive(store.getAddress())), store.getPhoneNumber());
		if (store.getParentStore() != null)
			storeData.setParentStore(new StoreData(UUIDHelper.uuidToString(store.getParentStore().getUuid()),
					store.getParentStore().getStoreName(),
					store.getParentStore().getAddress() == null ? null : ArrayUtils.toString(store.getParentStore().getAddress()),
					store.getParentStore().getPhoneNumber()));
		return storeData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		StoreData other = (StoreData) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StoreData [uuid=" + uuid + ", storeName=" + storeName + ", address=" + address + ", phoneNumber=" + phoneNumber + ", parentStore=" +
				parentStore + "]";
	}

}
