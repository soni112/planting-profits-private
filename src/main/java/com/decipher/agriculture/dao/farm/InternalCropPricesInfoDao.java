package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.InternalCropPricesInfo;

public interface InternalCropPricesInfoDao
{
	int save(InternalCropPricesInfo cropPricesInfo);

	boolean update(InternalCropPricesInfo cropPricesInfo);

	boolean deleteById(int id);

	boolean saveList(Set<InternalCropPricesInfo> cropPricesInfoList);

	InternalCropPricesInfo getInternalCropPricesInfoById(int id);
}
