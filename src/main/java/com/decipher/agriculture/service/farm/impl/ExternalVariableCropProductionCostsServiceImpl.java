package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.ExternalVariableCropProductionCostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.ExternalVariableCropProductionCostsDao;
import com.decipher.agriculture.data.farm.ExternalVariableCropProductionCosts;
import com.decipher.view.form.farmDetails.ExternalVariableCropProductionCostsView;

@Repository
@Transactional
public class ExternalVariableCropProductionCostsServiceImpl implements
        ExternalVariableCropProductionCostsService
{
	@Autowired
	private ExternalVariableCropProductionCostsDao productionCostsDao;

	@Override
	public int save(ExternalVariableCropProductionCosts productionCosts)
	{
		return productionCostsDao.save(productionCosts);
	}

	@Override
	public boolean update(ExternalVariableCropProductionCosts productionCosts)
	{
		return productionCostsDao.update(productionCosts);
	}

	@Override
	public boolean delete(int id)
	{
		return productionCostsDao.delete(id);
	}

	@Override
	public ExternalVariableCropProductionCostsView getProductionCostsById(
			int id)
	{
		return new ExternalVariableCropProductionCostsView(
				productionCostsDao.getProductionCostsById(id));
	}

	@Override
	public boolean saveList(
			Set<ExternalVariableCropProductionCosts> productionCostsList)
	{
		return productionCostsDao.saveList(productionCostsList);
	}

}
