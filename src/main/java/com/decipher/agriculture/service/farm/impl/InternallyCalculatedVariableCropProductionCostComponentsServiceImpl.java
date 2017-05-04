package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.InternallyCalculatedVariableCropProductionCostComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.InternallyCalculatedVariableCropProductionCostComponentsDao;
import com.decipher.agriculture.data.farm.InternallyCalculatedVariableCropProductionCostComponents;
import com.decipher.view.form.farmDetails.InternallyCalculatedVariableCropProductionCostComponentsView;

@Repository
@Transactional
public class InternallyCalculatedVariableCropProductionCostComponentsServiceImpl
		implements
        InternallyCalculatedVariableCropProductionCostComponentsService
{
	@Autowired
	private InternallyCalculatedVariableCropProductionCostComponentsDao costComponentsDao;

	@Override
	public int save(
			InternallyCalculatedVariableCropProductionCostComponents costComponents)
	{
		return costComponentsDao.save(costComponents);
	}

	@Override
	public boolean update(
			InternallyCalculatedVariableCropProductionCostComponents costComponents)
	{
		return costComponentsDao.update(costComponents);
	}

	@Override
	public boolean deleteById(int id)
	{
		return costComponentsDao.deleteById(id);
	}

	@Override
	public boolean saveList(
			Set<InternallyCalculatedVariableCropProductionCostComponents> costComponentsList)
	{
		return costComponentsDao.saveList(costComponentsList);
	}

	@Override
	public InternallyCalculatedVariableCropProductionCostComponentsView getCostComponentsById(
			int id)
	{
		return new InternallyCalculatedVariableCropProductionCostComponentsView(
				costComponentsDao.getCostComponentsById(id));
	}

}
