package com.decipher.agriculture.service.farm;

import java.util.List;

import com.decipher.agriculture.data.farm.CropFieldChocies;
import com.decipher.view.form.farmDetails.CropFieldChociesView;
//import com.decipher.view.form.farmDetails.CropFieldChociesView;

public interface CropFieldChociesService
{
	int saveCropFieldChocies(CropFieldChocies cropFieldChocies);
	boolean updateCropFieldChocies(CropFieldChocies cropFieldChocies);
	boolean deleteCropFieldChociesById(int id);
	//CropFieldChociesView getCropFieldChociesById(int id);
	List<CropFieldChociesView> getAllCropFiledsCrop(int cropId);
	List<CropFieldChociesView> getAllCropFiledsCropIds(Integer[] cropId);
}
