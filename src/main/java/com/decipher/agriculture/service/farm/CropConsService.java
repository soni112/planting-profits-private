package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.CropCons;
import com.decipher.view.form.farmDetails.CropConsView;

public interface CropConsService
{
	int saveCropCons(CropCons cropCons);
	boolean updateCropCons(CropCons cropCons);
	boolean deleteCropConsById(int id);
	boolean saveCropConsList(Set<CropCons> cropConsList);
	CropConsView getCropConsById(int id);
}
