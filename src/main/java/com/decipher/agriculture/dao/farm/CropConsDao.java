package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.CropCons;

public interface CropConsDao
{
	int saveCropCons(CropCons cropCons);
	boolean updateCropCons(CropCons cropCons);
	boolean deleteCropConsById(int id);
	CropCons getCropConsById(int id);
	boolean saveCropConsList(Set<CropCons> cropConsList);
}
