package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.InternalVariableCropProductionCosts;

public interface InternalVariableCropProductionCostsDao
{
	int save(InternalVariableCropProductionCosts productionCosts);

	boolean update(InternalVariableCropProductionCosts productionCosts);

	boolean deleteById(int id);

	boolean saveList(
			Set<InternalVariableCropProductionCosts> productionCostsList);

	InternalVariableCropProductionCosts getCropProductionCostById(int id);
}
