package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponentsUoM;
import com.decipher.view.form.farmDetails.VariableCropProductionCostsComponentsUoMView;

public interface VariableCropProductionCostsComponentsUoMService
{
	int save(VariableCropProductionCostsComponentsUoM costsComponents);

	boolean update(VariableCropProductionCostsComponentsUoM costsComponents);

	boolean deleteById(int id);

	boolean saveList(Set<VariableCropProductionCostsComponentsUoM> costsComponentsList);

	VariableCropProductionCostsComponentsUoMView getCostsComponentsUoMById(int id);
}
