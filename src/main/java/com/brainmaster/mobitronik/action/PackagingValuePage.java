package com.brainmaster.mobitronik.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.PackagingValueData;
import com.brainmaster.mobitronik.primefaces.model.PackagingValueDataModel;
import com.brainmaster.mobitronik.processor.PackagingValueProcessor;

@Component
@Scope("request")
public class PackagingValuePage {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(PackagingValuePage.class);

	@Autowired
	private PackagingValueProcessor packagingValueProcessor;

	@Autowired
	private PackagingValueForm packagingValueForm;

	private PackagingValueData selectedPackagingValue;

	public List<PackagingValueData> getPackDatas() {
		return packagingValueProcessor.getPackDatas();
	}

	public void setSelectedPackagingValue(PackagingValueData selectedPackagingValue) {
		this.selectedPackagingValue = selectedPackagingValue;
	}

	public PackagingValueData getSelectedPackagingValue() {
		return selectedPackagingValue;
	}

	public boolean isRenderPackagingValues() {
		if (getPackDatas().size() > 0)
			return true;
		return false;
	}

	public void savePackagingValue() {
		packagingValueProcessor.savePackagingValue(packagingValueForm.getPackagingValue());
		packagingValueForm.createNewPackagingValue();
	}

	public PackagingValueDataModel getPackagingValueDataModel() {
		return new PackagingValueDataModel(getPackDatas());
	}

	public PackagingValueForm getPackagingValueForm() {
		return packagingValueForm;
	}

	public void setPackagingValueForm(PackagingValueForm packagingValueForm) {
		this.packagingValueForm = packagingValueForm;
	}

}
