package com.decipher.agriculture.dao.farm;

import java.util.List;

import com.decipher.agriculture.data.farm.CropFieldChocies;

public interface CropFieldChociesDao
{
	int saveCropFieldChocies(CropFieldChocies cropFieldChocies);
	boolean updateCropFieldChocies(CropFieldChocies cropFieldChocies);
	boolean deleteCropFieldChociesById(int id);
	CropFieldChocies getCropFieldChociesById(int id);
	List<CropFieldChocies> getAllCropFiledsCrop(int cropId);
	List<CropFieldChocies> getAllCropFiledsCropIds(Integer[] cropId);
}
