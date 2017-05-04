package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.ExternalCropPriceInfo;
import com.decipher.view.form.farmDetails.ExternalCropPriceInfoView;

public interface ExternalCropPriceInfoService
{
	int saveExternalCropPriceInfo(ExternalCropPriceInfo externalCropPriceInfo);

	boolean updateExternalCropPriceInfo(
            ExternalCropPriceInfo externalCropPriceInfo);

	boolean deleteExternalCropPriceInfoById(int id);

	ExternalCropPriceInfoView getExternalCropPriceInfoById(int id);

	boolean saveExternalCropPriceInfoList(
            Set<ExternalCropPriceInfo> externalCropPriceInfoList);
}
