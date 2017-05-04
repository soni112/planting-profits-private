package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.ExternalVariableCropProductionCostComponentPricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.ExternalVariableCropProductionCostComponentPricesDao;
import com.decipher.agriculture.data.farm.ExternalVariableCropProductionCostComponentPrices;
import com.decipher.view.form.farmDetails.ExternalVariableCropProductionCostComponentPricesView;

@Repository
@Transactional
public class ExternalVariableCropProductionCostComponentPricesServiceImpl
		implements ExternalVariableCropProductionCostComponentPricesService
{
	@Autowired
	private ExternalVariableCropProductionCostComponentPricesDao componentPricesDao;

	@Override
	public int save(
			ExternalVariableCropProductionCostComponentPrices componentPrices)
	{
		return componentPricesDao.save(componentPrices);
	}

	@Override
	public boolean update(
			ExternalVariableCropProductionCostComponentPrices componentPrices)
	{
		return componentPricesDao.update(componentPrices);
	}

	@Override
	public boolean delete(int id)
	{
		return componentPricesDao.delete(id);
	}

	@Override
	public ExternalVariableCropProductionCostComponentPricesView getProductionCostComponentPricesById(
			int id)
	{
		return new ExternalVariableCropProductionCostComponentPricesView(
				componentPricesDao.getProductionCostComponentPricesById(id));
	}

	@Override
	public boolean saveList(
			Set<ExternalVariableCropProductionCostComponentPrices> componentPricesList)
	{
		return componentPricesDao.saveList(componentPricesList);
	}

}
