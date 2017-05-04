package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.CropYieldFieldVariancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CropYieldFieldVariancesDao;
import com.decipher.agriculture.data.farm.CropYieldFieldVariances;
import com.decipher.view.form.farmDetails.CropYieldFieldVariancesView;

@Repository
@Transactional
public class CropYieldFieldVariancesServiceImpl implements
        CropYieldFieldVariancesService
{
	@Autowired
	private CropYieldFieldVariancesDao CropYieldFieldVariancesDao;

	@Override
	public int saveCropYieldFieldVariances(
			CropYieldFieldVariances cropYieldFieldVariances)
	{
		return CropYieldFieldVariancesDao
				.saveCropYieldFieldVariances(cropYieldFieldVariances);
	}

	@Override
	public boolean updateCropYieldFieldVariances(
			CropYieldFieldVariances cropYieldFieldVariances)
	{
		return CropYieldFieldVariancesDao
				.updateCropYieldFieldVariances(cropYieldFieldVariances);
	}

	@Override
	public boolean deleteCropYieldFieldVariancesById(int id)
	{
		return CropYieldFieldVariancesDao.deleteCropYieldFieldVariancesById(id);
	}

	@Override
	public CropYieldFieldVariancesView getCropYieldFieldVariancesById(int id)
	{
		return new CropYieldFieldVariancesView(
				CropYieldFieldVariancesDao.getCropYieldFieldVariancesById(id));
	}

	@Override
	public boolean saveCropYieldFieldVariancesList(
			Set<CropYieldFieldVariances> cropYieldFieldVariancesList)
	{
		return CropYieldFieldVariancesDao
				.saveCropYieldFieldVariancesList(cropYieldFieldVariancesList);
	}

}
