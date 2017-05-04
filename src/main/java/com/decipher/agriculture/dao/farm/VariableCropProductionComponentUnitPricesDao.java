package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.VariableCropProductionComponentUnitPrices;

public interface VariableCropProductionComponentUnitPricesDao
{
	int save(VariableCropProductionComponentUnitPrices unitPrices);

	boolean update(VariableCropProductionComponentUnitPrices unitPrices);

	boolean deleteById(int id);

	boolean saveList(Set<VariableCropProductionComponentUnitPrices> unitPricesList);

	VariableCropProductionComponentUnitPrices getComponentUnitPricesById(int id);
}
