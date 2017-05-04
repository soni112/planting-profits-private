package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.VariableCropProductionComponentUnitPricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.VariableCropProductionComponentUnitPricesDao;
import com.decipher.agriculture.data.farm.VariableCropProductionComponentUnitPrices;
import com.decipher.view.form.farmDetails.VariableCropProductionComponentUnitPricesView;

@Repository
@Transactional
public class VariableCropProductionComponentUnitPricesServiceImpl implements
        VariableCropProductionComponentUnitPricesService
{
	@Autowired
	private VariableCropProductionComponentUnitPricesDao unitPricesDao;

	@Override
	public int save(VariableCropProductionComponentUnitPrices unitPrices)
	{
		return unitPricesDao.save(unitPrices);
	}

	@Override
	public boolean update(VariableCropProductionComponentUnitPrices unitPrices)
	{
		return unitPricesDao.update(unitPrices);
	}

	@Override
	public boolean deleteById(int id)
	{
		return unitPricesDao.deleteById(id);
	}

	@Override
	public boolean saveList(
			Set<VariableCropProductionComponentUnitPrices> unitPricesList)
	{
		return unitPricesDao.saveList(unitPricesList);
	}

	@Override
	public VariableCropProductionComponentUnitPricesView getComponentUnitPricesById(
			int id)
	{
		return new VariableCropProductionComponentUnitPricesView(
				unitPricesDao.getComponentUnitPricesById(id));
	}

}
