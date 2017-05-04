package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.InternalVariableCropProductionCostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.InternalVariableCropProductionCostsDao;
import com.decipher.agriculture.data.farm.InternalVariableCropProductionCosts;
import com.decipher.view.form.farmDetails.InternalVariableCropProductionCostsView;

@Repository
@Transactional
public class InternalVariableCropProductionCostsServiceImpl implements
        InternalVariableCropProductionCostsService
{
	@Autowired
	private InternalVariableCropProductionCostsDao internalDao;

	@Override
	public int save(InternalVariableCropProductionCosts productionCosts)
	{
		return internalDao.save(productionCosts);
	}

	@Override
	public boolean update(InternalVariableCropProductionCosts productionCosts)
	{
		return internalDao.update(productionCosts);
	}

	@Override
	public boolean deleteById(int id)
	{
		return internalDao.deleteById(id);
	}

	@Override
	public boolean saveList(
			Set<InternalVariableCropProductionCosts> productionCostsList)
	{
		return internalDao.saveList(productionCostsList);
	}

	@Override
	public InternalVariableCropProductionCostsView getCropProductionCostById(
			int id)
	{
		return new InternalVariableCropProductionCostsView(
				internalDao.getCropProductionCostById(id));
	}

}
