package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.CropUnitOfMeasure;

public interface CropUnitOfMeasureDao
{
	int saveCropUnitOfMeasure(CropUnitOfMeasure cropUnitOfMeasure);
	boolean updateCropUnitOfMeasure(CropUnitOfMeasure cropUnitOfMeasure);
	boolean deleteCropUnitOfMeasureById(int id);
	CropUnitOfMeasure getCropUnitOfMeasureById(int id);
	boolean saveCropUnitOfMeasureList(Set<CropUnitOfMeasure> cropUnitOfMeasureList);
	
}
