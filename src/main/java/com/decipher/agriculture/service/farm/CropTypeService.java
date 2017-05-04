package com.decipher.agriculture.service.farm;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.data.farm.CropType;
import com.decipher.view.form.farmDetails.CropListForGroupView;
import com.decipher.view.form.farmDetails.CropTypeView;

public interface CropTypeService {
    CropType getCropByName(String name);

    boolean isCropExitsWithName(String name);

    int saveCrop(CropType cropType);

    boolean updateCrop(CropType cropType);

    boolean deleteCropById(int id);

    CropTypeView getCropById(int id);

    boolean saveCropTypeList(Set<CropType> cropTypeList);

    List<CropTypeView> getAllCropTypeByArcesId(int arcesId);

    List<CropTypeView> getAllCropByFarm(int farmId);

    boolean deleteCropByFarmId(int farmId);

    List<CropType> getAllCropByFarmId(int farmId);

    List<CropListForGroupView> getAllCropByGroupIds(Integer[] groupId);

    String getAllCropIdsByFarmIds(List<Integer> farmId);
}
