package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.VariableCropProductionCostsComponentsUoMService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.VariableCropProductionCostsComponentsUoMDao;
import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponentsUoM;
import com.decipher.view.form.farmDetails.VariableCropProductionCostsComponentsUoMView;

@Repository
@Transactional
public class VariableCropProductionCostsComponentsUoMServiceImpl implements
        VariableCropProductionCostsComponentsUoMService
{
	private VariableCropProductionCostsComponentsUoMDao costsComponentsUoMDao;

	@Override
	public int save(VariableCropProductionCostsComponentsUoM costsComponents)
	{
		return costsComponentsUoMDao.save(costsComponents);
	}

	@Override
	public boolean update(
			VariableCropProductionCostsComponentsUoM costsComponents)
	{
		return costsComponentsUoMDao.update(costsComponents);
	}

	@Override
	public boolean deleteById(int id)
	{
		return costsComponentsUoMDao.deleteById(id);
	}

	@Override
	public boolean saveList(
			Set<VariableCropProductionCostsComponentsUoM> costsComponentsList)
	{
		return costsComponentsUoMDao.saveList(costsComponentsList);
	}

	@Override
	public VariableCropProductionCostsComponentsUoMView getCostsComponentsUoMById(
			int id)
	{
		return new VariableCropProductionCostsComponentsUoMView(
				costsComponentsUoMDao.getCostsComponentsUoMById(id));
	}

}
