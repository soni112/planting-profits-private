package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponentUsage;

public interface VariableCropProductionCostsComponentUsageDao
{
	int save(VariableCropProductionCostsComponentUsage componentUsage);

	boolean update(VariableCropProductionCostsComponentUsage componentUsage);

	boolean deleteById(int id);

	boolean saveList(Set<VariableCropProductionCostsComponentUsage> componentUsageList);

	VariableCropProductionCostsComponentUsage getcomponentUsageById(int id);
}
