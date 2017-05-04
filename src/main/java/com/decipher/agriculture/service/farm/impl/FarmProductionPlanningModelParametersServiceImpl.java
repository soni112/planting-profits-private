package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.FarmProductionPlanningModelParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.decipher.agriculture.dao.farm.FarmProductionPlanningModelParametersDao;
import com.decipher.agriculture.data.farm.FarmProductionPlanningModelParameters;
import com.decipher.view.form.farmDetails.FarmProductionPlanningModelParametersView;

@Repository
@Transactional
public class FarmProductionPlanningModelParametersServiceImpl implements
        FarmProductionPlanningModelParametersService
{
	@Autowired
	private FarmProductionPlanningModelParametersDao farmProductionDao;

	@Override
	public int save(FarmProductionPlanningModelParameters farmProduction)
	{
		return farmProductionDao.save(farmProduction);
	}

	@Override
	public boolean update(FarmProductionPlanningModelParameters farmProduction)
	{
		return farmProductionDao.update(farmProduction);
	}

	@Override
	public boolean deleteById(int id)
	{
		return farmProductionDao.deleteById(id);
	}

	@Override
	public boolean saveList(
			Set<FarmProductionPlanningModelParameters> farmProductionList)
	{
		return farmProductionDao.saveList(farmProductionList);
	}

	@Override
	public FarmProductionPlanningModelParametersView getFarmProductionById(
			int id)
	{
		return new FarmProductionPlanningModelParametersView(
				farmProductionDao.getFarmProductionById(id));
	}

}
