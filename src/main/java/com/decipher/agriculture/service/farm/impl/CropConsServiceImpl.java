package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.CropConsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CropConsDao;
import com.decipher.agriculture.data.farm.CropCons;
import com.decipher.view.form.farmDetails.CropConsView;

@Repository
@Transactional
public class CropConsServiceImpl implements CropConsService
{
	
	@Autowired
	private CropConsDao cropConsDao;

	@Override
	public int saveCropCons(CropCons cropCons)
	{
		return cropConsDao.saveCropCons(cropCons);
	}

	@Override
	public boolean updateCropCons(CropCons cropCons)
	{
		return cropConsDao.updateCropCons(cropCons);
	}

	@Override
	public boolean deleteCropConsById(int id)
	{
		return cropConsDao.deleteCropConsById(id);
	}

	@Override
	public boolean saveCropConsList(Set<CropCons> cropConsList)
	{
		return cropConsDao.saveCropConsList(cropConsList);
	}

	@Override
	public CropConsView getCropConsById(int id)
	{
		return new CropConsView(cropConsDao.getCropConsById(id));
	}

}
