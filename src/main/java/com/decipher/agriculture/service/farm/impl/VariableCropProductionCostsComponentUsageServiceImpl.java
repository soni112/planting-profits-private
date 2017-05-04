package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.VariableCropProductionCostsComponentUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.VariableCropProductionCostsComponentUsageDao;
import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponentUsage;
import com.decipher.view.form.farmDetails.VariableCropProductionCostsComponentUsageView;

@Repository
@Transactional
public class VariableCropProductionCostsComponentUsageServiceImpl implements
        VariableCropProductionCostsComponentUsageService
{
	@Autowired
	private VariableCropProductionCostsComponentUsageDao componentUsageDao;

	@Override
	public int save(VariableCropProductionCostsComponentUsage componentUsage)
	{
		return componentUsageDao.save(componentUsage);
	}

	@Override
	public boolean update(
			VariableCropProductionCostsComponentUsage componentUsage)
	{
		return componentUsageDao.update(componentUsage);
	}

	@Override
	public boolean deleteById(int id)
	{
		return componentUsageDao.deleteById(id);
	}

	@Override
	public boolean saveList(
			Set<VariableCropProductionCostsComponentUsage> componentUsageList)
	{
		return componentUsageDao.saveList(componentUsageList);
	}

	@Override
	public VariableCropProductionCostsComponentUsageView getcomponentUsageById(
			int id)
	{
		return new VariableCropProductionCostsComponentUsageView(
				componentUsageDao.getcomponentUsageById(id));
	}

}
