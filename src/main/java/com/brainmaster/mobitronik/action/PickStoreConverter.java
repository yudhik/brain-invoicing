package com.brainmaster.mobitronik.action;

import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.brainmaster.mobitronik.bean.service.StoreService;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.Store;
import com.brainmaster.util.helper.uuid.UUIDHelper;

public class PickStoreConverter implements Converter {

	private static final Logger log = LoggerFactory.getLogger(PickStoreConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		log.debug("pick store converter value : " + value);
		if (value == null || value.length() == 0 || value.length() != 32)
			return new StoreData();
		StoreService storeService = (StoreService) FacesContextUtils.getWebApplicationContext(context).getBean("storeService");
		Store store = storeService.find(UUIDHelper.stringToUUID(value));
		return new StoreData(UUIDHelper.uuidToString(store.getUuid()), store.getStoreName(),
				new String(ArrayUtils.toPrimitive(store.getAddress())), store.getPhoneNumber());
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof UUID)
			return UUIDHelper.uuidToString(UUIDHelper.objectToUUID(value));
		return ((StoreData) value).getUuid();
	}

}
