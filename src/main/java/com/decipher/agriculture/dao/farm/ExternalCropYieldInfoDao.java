package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.ExternalCropYieldInfo;

public interface ExternalCropYieldInfoDao {
    int saveExternalCropPriceInfo(ExternalCropYieldInfo externalCropYieldInfo);

    boolean updateExternalCropYieldInfo(
            ExternalCropYieldInfo externalCropYieldInfo);

    boolean deleteExternalCropYieldInfoById(int id);

    ExternalCropYieldInfo getExternalCropYieldInfoById(int id);

    boolean saveExternalCropYieldInfoList(
            Set<ExternalCropYieldInfo> externalCropYieldInfoList);
}
