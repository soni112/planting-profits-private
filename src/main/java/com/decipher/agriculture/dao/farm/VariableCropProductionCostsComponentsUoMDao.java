package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponentsUoM;

public interface VariableCropProductionCostsComponentsUoMDao
{
	int save(VariableCropProductionCostsComponentsUoM costsComponents);

	boolean update(VariableCropProductionCostsComponentsUoM costsComponents);

	boolean deleteById(int id);

	boolean saveList(Set<VariableCropProductionCostsComponentsUoM> costsComponentsList);

	VariableCropProductionCostsComponentsUoM getCostsComponentsUoMById(int id);
}
