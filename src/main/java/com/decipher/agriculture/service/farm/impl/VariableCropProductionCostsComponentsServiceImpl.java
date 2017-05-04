package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.VariableCropProductionCostsComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.VariableCropProductionCostsComponentsDao;
import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponents;
import com.decipher.view.form.farmDetails.VariableCropProductionCostsComponentsView;

@Repository
@Transactional
public class VariableCropProductionCostsComponentsServiceImpl implements
        VariableCropProductionCostsComponentsService
{
	@Autowired
	private VariableCropProductionCostsComponentsDao componentsDao;

	@Override
	public int save(VariableCropProductionCostsComponents costsComponents)
	{
		return componentsDao.save(costsComponents);
	}

	@Override
	public boolean update(VariableCropProductionCostsComponents costsComponents)
	{
		return componentsDao.update(costsComponents);
	}

	@Override
	public boolean deleteById(int id)
	{
		return componentsDao.deleteById(id);
	}

	@Override
	public boolean saveList(
			Set<VariableCropProductionCostsComponents> costsComponentsList)
	{
		return componentsDao.saveList(costsComponentsList);
	}

	@Override
	public VariableCropProductionCostsComponentsView getCostsComponentsById(
			int id)
	{
		return new VariableCropProductionCostsComponentsView(
				componentsDao.getCostsComponentsById(id));
	}

}
