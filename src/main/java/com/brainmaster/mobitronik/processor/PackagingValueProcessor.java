package com.brainmaster.mobitronik.processor;

import java.util.List;

import com.brainmaster.mobitronik.dto.PackagingValueData;

public interface PackagingValueProcessor {

	public List<PackagingValueData> getPackDatas();

	public void savePackagingValue(PackagingValueData valueData);
}
