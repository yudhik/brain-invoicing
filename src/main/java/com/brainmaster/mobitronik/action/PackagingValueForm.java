package com.brainmaster.mobitronik.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.PackagingValueData;

@Component
@Scope("session")
public class PackagingValueForm {

	private PackagingValueData packagingValue = new PackagingValueData();

	public PackagingValueData getPackagingValue() {
		return packagingValue;
	}

	public void setPackagingValue(PackagingValueData packagingValue) {
		this.packagingValue = packagingValue;
	}

	public void createNewPackagingValue() {
		packagingValue = new PackagingValueData();
	}

}
