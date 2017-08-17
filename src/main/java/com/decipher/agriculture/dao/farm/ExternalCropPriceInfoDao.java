package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.ExternalCropPriceInfo;

public interface ExternalCropPriceInfoDao {
    int saveExternalCropPriceInfo(ExternalCropPriceInfo externalCropPriceInfo);

    boolean updateExternalCropPriceInfo(
            ExternalCropPriceInfo externalCropPriceInfo);

    boolean deleteExternalCropPriceInfoById(int id);

    ExternalCropPriceInfo getExternalCropPriceInfoById(int id);

    boolean saveExternalCropPriceInfoList(
            Set<ExternalCropPriceInfo> externalCropPriceInfoList);
}
