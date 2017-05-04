package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.CropUnitOfMeasure;
import com.decipher.view.form.farmDetails.CropUnitOfMeasureView;

public interface CropUnitOfMeasureService
{
	int saveCropUnitOfMeasure(CropUnitOfMeasure cropUnitOfMeasure);
	boolean updateCropUnitOfMeasure(CropUnitOfMeasure cropUnitOfMeasure);
	boolean deleteCropUnitOfMeasureById(int id);
	CropUnitOfMeasureView getCropUnitOfMeasureById(int id);
	boolean saveCropUnitOfMeasureList(Set<CropUnitOfMeasure> cropUnitOfMeasureList);
}
