package com.decipher.agriculture.service.farm;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.data.farm.CropResourceUsage;
import com.decipher.view.form.farmDetails.CropResourceUsageView;

public interface CropResourceUsageService {
    int saveCropResourceUsage(CropResourceUsage cropResourceUsage);

    boolean updateCropResourceUsage(CropResourceUsage cropResourceUsage);

    boolean deleteCropResourceUsageById(int id);

    CropResourceUsageView getCropResourceUsageById(int id);

    boolean saveCropResourceUsageList(Set<CropResourceUsage> cropResourceUsageList);

    List<CropResourceUsageView> getAllCropResourceUsageByFarmId(int farmId);

    String getAllCropResourceUsageByFarmIds(List<Integer> farmIds);
}
