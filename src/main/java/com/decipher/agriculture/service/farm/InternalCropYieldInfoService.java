package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.InternalCropYieldInfo;
import com.decipher.view.form.farmDetails.InternalCropYieldInfoView;

public interface InternalCropYieldInfoService
{
	int save(InternalCropYieldInfo cropYieldInfo);

	boolean update(InternalCropYieldInfo cropYieldInfo);

	boolean deleteById(int id);

	boolean saveList(Set<InternalCropYieldInfo> cropYieldInfoList);

	InternalCropYieldInfoView getInternalCropYieldInfoById(int id);
}
