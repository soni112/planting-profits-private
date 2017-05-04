package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponents;
import com.decipher.view.form.farmDetails.VariableCropProductionCostsComponentsView;

public interface VariableCropProductionCostsComponentsService
{
	int save(VariableCropProductionCostsComponents costsComponents);

	boolean update(VariableCropProductionCostsComponents costsComponents);

	boolean deleteById(int id);

	boolean saveList(Set<VariableCropProductionCostsComponents> costsComponentsList);

	VariableCropProductionCostsComponentsView getCostsComponentsById(int id);
}
