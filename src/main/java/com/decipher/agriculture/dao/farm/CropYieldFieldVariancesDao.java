package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.CropYieldFieldVariances;

public interface CropYieldFieldVariancesDao {
    int saveCropYieldFieldVariances(CropYieldFieldVariances cropYieldFieldVariances);

    boolean updateCropYieldFieldVariances(CropYieldFieldVariances cropYieldFieldVariances);

    boolean deleteCropYieldFieldVariancesById(int id);

    CropYieldFieldVariances getCropYieldFieldVariancesById(int id);

    boolean saveCropYieldFieldVariancesList(Set<CropYieldFieldVariances> cropYieldFieldVariancesList);
}
