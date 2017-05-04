package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.ExternalVariableCropProductionCosts;

public interface ExternalVariableCropProductionCostsDao
{
	int save(ExternalVariableCropProductionCosts productionCosts);

	boolean update(ExternalVariableCropProductionCosts productionCosts);

	boolean delete(int id);

	ExternalVariableCropProductionCosts getProductionCostsById(int id);

	boolean saveList(
            Set<ExternalVariableCropProductionCosts> productionCostsList);
}
