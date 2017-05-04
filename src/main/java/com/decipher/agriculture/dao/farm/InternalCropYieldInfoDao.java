package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.InternalCropYieldInfo;

public interface InternalCropYieldInfoDao
{
	int save(InternalCropYieldInfo cropYieldInfo);

	boolean update(InternalCropYieldInfo cropYieldInfo);

	boolean deleteById(int id);

	boolean saveList(Set<InternalCropYieldInfo> cropYieldInfoList);

	InternalCropYieldInfo getInternalCropYieldInfoById(int id);
}
