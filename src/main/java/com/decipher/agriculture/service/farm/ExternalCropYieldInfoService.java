package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.ExternalCropYieldInfo;
import com.decipher.view.form.farmDetails.ExternalCropYieldInfoView;

public interface ExternalCropYieldInfoService
{
	int saveExternalCropPriceInfo(ExternalCropYieldInfo externalCropYieldInfo);

	boolean updateExternalCropYieldInfo(
			ExternalCropYieldInfo externalCropYieldInfo);

	boolean deleteExternalCropYieldInfoById(int id);

	ExternalCropYieldInfoView getExternalCropYieldInfoById(int id);

	boolean saveExternalCropYieldInfoList(
			Set<ExternalCropYieldInfo> externalCropYieldInfoList);
}
