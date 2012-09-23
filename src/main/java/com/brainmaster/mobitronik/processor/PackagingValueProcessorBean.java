package com.brainmaster.mobitronik.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.bean.service.PackagingValueService;
import com.brainmaster.mobitronik.dto.PackagingValueData;
import com.brainmaster.mobitronik.model.PackagingValue;

@Component
@Scope("prototype")
public class PackagingValueProcessorBean implements PackagingValueProcessor {

	@Autowired
	private PackagingValueService packagingValueService;

	@Override
	public List<PackagingValueData> getPackDatas() {
		List<PackagingValueData> packDatas = new ArrayList<PackagingValueData>();
		for (PackagingValue value : packagingValueService.findAll())
			packDatas.add(PackagingValueData.convertPackagingValue(value));
		return packDatas;
	}

	@Override
	public void savePackagingValue(PackagingValueData valueData) {
		packagingValueService.addPackagingValue(valueData.getPackagingValueEntity());
	}

}
