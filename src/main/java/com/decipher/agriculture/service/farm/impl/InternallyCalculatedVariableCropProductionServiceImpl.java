package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.InternallyCalculatedVariableCropProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.InternallyCalculatedVariableCropProductionDao;
import com.decipher.agriculture.data.farm.InternallyCalculatedVariableCropProduction;
import com.decipher.view.form.farmDetails.InternallyCalculatedVariableCropProductionView;

@Repository
@Transactional
public class InternallyCalculatedVariableCropProductionServiceImpl implements
        InternallyCalculatedVariableCropProductionService
{

	@Autowired
	private InternallyCalculatedVariableCropProductionDao internalCropDao;

	@Override
	public int save(InternallyCalculatedVariableCropProduction cropProduction)
	{
		return internalCropDao.save(cropProduction);
	}

	@Override
	public boolean update(
			InternallyCalculatedVariableCropProduction cropProduction)
	{
		return internalCropDao.update(cropProduction);
	}

	@Override
	public boolean deleteById(int id)
	{
		return internalCropDao.deleteById(id);
	}

	@Override
	public boolean saveList(
			Set<InternallyCalculatedVariableCropProduction> cropProductionList)
	{
		return internalCropDao.saveList(cropProductionList);
	}

	@Override
	public InternallyCalculatedVariableCropProductionView getCropProductionById(
			int id)
	{
		return new InternallyCalculatedVariableCropProductionView(
				internalCropDao.getCropProductionById(id));
	}

}
