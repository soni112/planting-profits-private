package com.decipher.agriculture.dao.farm;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.data.farm.CropResourceUsage;

public interface CropResourceUsageDao {
    int saveCropResourceUsage(CropResourceUsage cropResourceUsage);

    boolean updateCropResourceUsage(CropResourceUsage cropResourceUsage);

    boolean deleteCropResourceUsageById(int id);

    CropResourceUsage getCropResourceUsageById(int id);

    boolean saveCropResourceUsageList(Set<CropResourceUsage> cropResourceUsageList);

    List<CropResourceUsage> getAllCropResourceUsageByFarmId(int farmId);

    List<CropResourceUsage> getAllCropResourceUsageByFarmIds(List<Integer> farmId);
}
