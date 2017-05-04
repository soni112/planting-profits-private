package com.decipher.agriculture.dao.farm;

import java.util.List;

import com.decipher.agriculture.data.farm.CropsGroup;

public interface CropGroupDao
{
	 int saveCropGroup(CropsGroup cropGroup);
	 List<CropsGroup> getAllCropGroupByFarm(int farmId);
	//boolean updateCropYieldFieldVariances(CropYieldFieldVariances cropYieldFieldVariances);
	//boolean deleteCropYieldFieldVariancesById(int id);
	//CropYieldFieldVariances getCropYieldFieldVariancesById(int id);
	//boolean saveCropYieldFieldVariancesList(Set<CropYieldFieldVariances> cropYieldFieldVariancesList);
}
