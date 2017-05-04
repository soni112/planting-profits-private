package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.CropUnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CropUnitOfMeasureDao;
import com.decipher.agriculture.data.farm.CropUnitOfMeasure;
import com.decipher.view.form.farmDetails.CropUnitOfMeasureView;

@Repository
@Transactional
public class CropUnitOfMeasureServiceImpl implements CropUnitOfMeasureService
{
	@Autowired
	private CropUnitOfMeasureDao CropUnitOfMeasureDao;

	@Override
	public int saveCropUnitOfMeasure(CropUnitOfMeasure cropUnitOfMeasure)
	{
		return CropUnitOfMeasureDao.saveCropUnitOfMeasure(cropUnitOfMeasure);
	}

	@Override
	public boolean updateCropUnitOfMeasure(CropUnitOfMeasure cropUnitOfMeasure)
	{
		return CropUnitOfMeasureDao.updateCropUnitOfMeasure(cropUnitOfMeasure);
	}

	@Override
	public boolean deleteCropUnitOfMeasureById(int id)
	{
		return CropUnitOfMeasureDao.deleteCropUnitOfMeasureById(id);
	}

	@Override
	public CropUnitOfMeasureView getCropUnitOfMeasureById(int id)
	{
		return new CropUnitOfMeasureView(
				CropUnitOfMeasureDao.getCropUnitOfMeasureById(id));
	}

	@Override
	public boolean saveCropUnitOfMeasureList(
			Set<CropUnitOfMeasure> cropUnitOfMeasureList)
	{
		return CropUnitOfMeasureDao
				.saveCropUnitOfMeasureList(cropUnitOfMeasureList);
	}

}
