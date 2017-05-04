package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.CropYieldFieldVariances;
import com.decipher.view.form.farmDetails.CropYieldFieldVariancesView;

public interface CropYieldFieldVariancesService
{
	int saveCropYieldFieldVariances(CropYieldFieldVariances cropYieldFieldVariances);
	boolean updateCropYieldFieldVariances(CropYieldFieldVariances cropYieldFieldVariances);
	boolean deleteCropYieldFieldVariancesById(int id);
	CropYieldFieldVariancesView getCropYieldFieldVariancesById(int id);
	boolean saveCropYieldFieldVariancesList(Set<CropYieldFieldVariances> cropYieldFieldVariancesList);
}
