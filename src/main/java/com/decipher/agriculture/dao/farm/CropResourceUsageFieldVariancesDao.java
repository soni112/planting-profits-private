package com.decipher.agriculture.dao.farm;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.data.farm.CropResourceUsageFieldVariances;

public interface CropResourceUsageFieldVariancesDao {
    int saveCropResourceUsageFieldVariances(CropResourceUsageFieldVariances cropResourceUsageFieldVariances);

    boolean updateCropResourceUsageFieldVariances(CropResourceUsageFieldVariances cropResourceUsageFieldVariances);

    boolean deleteCropResourceUsageFieldVariancesById(int id);

    CropResourceUsageFieldVariances getCropResourceUsageFieldVariancesById(int id);

    boolean saveCropResourceUsageFieldVariancesList(Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariancesList);

    List<CropResourceUsageFieldVariances> getAllResourceByCrop(int cropId);

    List<CropResourceUsageFieldVariances> getAllResourceByCropIds(Integer[] cropId);
}
