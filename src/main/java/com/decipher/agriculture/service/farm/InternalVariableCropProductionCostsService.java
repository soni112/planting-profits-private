package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.InternalVariableCropProductionCosts;
import com.decipher.view.form.farmDetails.InternalVariableCropProductionCostsView;

public interface InternalVariableCropProductionCostsService
{
	int save(InternalVariableCropProductionCosts productionCosts);

	boolean update(InternalVariableCropProductionCosts productionCosts);

	boolean deleteById(int id);

	boolean saveList(
			Set<InternalVariableCropProductionCosts> productionCostsList);

	InternalVariableCropProductionCostsView getCropProductionCostById(int id);
}
