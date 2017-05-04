package com.decipher.agriculture.dao.farm;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.data.farm.CropType;

public interface CropTypeDao {
	CropType getCropByName(String name);

	boolean isCropExitsWithName(String name);

	int saveCrop(CropType cropType);

	boolean updateCrop(CropType cropType);

	boolean deleteCropById(int id);

	CropType getCropById(int id);

	boolean saveCropTypeList(Set<CropType> cropTypeList);

	List<CropType> getAllCropTypeByArcesId(int arcesId);

	List<CropType> getAllCropByFarm(int farmId);

	List<CropType> getAllCropByFarmIds(List<Integer> farmId);

	List<CropType> getAllCropByGroupIds(Integer[] groupId);

	boolean deleteCropByFarmId(int farmId);
}
