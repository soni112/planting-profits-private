package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.InternallyCalculatedVariableCropProductionCostComponents;

public interface InternallyCalculatedVariableCropProductionCostComponentsDao
{
	int save(InternallyCalculatedVariableCropProductionCostComponents costComponents);

	boolean update(InternallyCalculatedVariableCropProductionCostComponents costComponents);

	boolean deleteById(int id);

	boolean saveList(Set<InternallyCalculatedVariableCropProductionCostComponents> costComponentsList);

	InternallyCalculatedVariableCropProductionCostComponents getCostComponentsById(int id);
}
