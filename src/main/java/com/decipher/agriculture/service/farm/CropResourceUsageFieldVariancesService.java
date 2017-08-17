package com.decipher.agriculture.service.farm;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.data.farm.CropResourceUsageFieldVariances;
import com.decipher.view.form.farmDetails.CropResourceUsageFieldVariancesView;

public interface CropResourceUsageFieldVariancesService {
    int saveCropResourceUsageFieldVariances(
            CropResourceUsageFieldVariances cropResourceUsageFieldVariances);

    boolean updateCropResourceUsageFieldVariances(
            CropResourceUsageFieldVariances cropResourceUsageFieldVariances);

    boolean deleteCropResourceUsageFieldVariancesById(int id);

    CropResourceUsageFieldVariancesView getCropResourceUsageFieldVariancesById(
            int id);

    boolean saveCropResourceUsageFieldVariancesList(
            Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariancesList);

    List<CropResourceUsageFieldVariancesView> getAllResourceByCrop(
            int cropId);

    List<CropResourceUsageFieldVariancesView> getAllResourceByCropIds(
            Integer[] cropId);
}
