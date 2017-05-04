package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.InternalCropPricesInfo;
import com.decipher.view.form.farmDetails.InternalCropPricesInfoView;

public interface InternalCropPricesInfoService
{
	int save(InternalCropPricesInfo cropPricesInfo);

	boolean update(InternalCropPricesInfo cropPricesInfo);

	boolean deleteById(int id);

	boolean saveList(Set<InternalCropPricesInfo> cropPricesInfoList);

	InternalCropPricesInfoView getInternalCropPricesInfoById(int id);
}
