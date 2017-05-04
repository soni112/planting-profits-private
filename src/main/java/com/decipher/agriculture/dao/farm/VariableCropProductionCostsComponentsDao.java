package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponents;

public interface VariableCropProductionCostsComponentsDao
{
	int save(VariableCropProductionCostsComponents costsComponents);

	boolean update(VariableCropProductionCostsComponents costsComponents);

	boolean deleteById(int id);

	boolean saveList(Set<VariableCropProductionCostsComponents> costsComponentsList);

	VariableCropProductionCostsComponents getCostsComponentsById(int id);
}
