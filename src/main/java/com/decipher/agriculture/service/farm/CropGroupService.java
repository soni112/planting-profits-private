package com.decipher.agriculture.service.farm;

import java.util.List;

import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.view.form.farmDetails.CropsGroupView;

public interface CropGroupService
{
	int saveCropGroup(CropsGroup cropsGroup);
	List<CropsGroupView> getAllCropGroupByFarm(int farmId);
	//boolean updateCropYieldFieldVariances(CropYieldFieldVariances cropYieldFieldVariances);
	//boolean deleteCropYieldFieldVariancesById(int id);
	//CropYieldFieldVariancesView getCropYieldFieldVariancesById(int id);
	//boolean saveCropYieldFieldVariancesList(Set<CropYieldFieldVariances> cropYieldFieldVariancesList);
}
