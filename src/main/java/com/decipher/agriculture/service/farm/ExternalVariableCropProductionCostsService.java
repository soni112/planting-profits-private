package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.ExternalVariableCropProductionCosts;
import com.decipher.view.form.farmDetails.ExternalVariableCropProductionCostsView;

public interface ExternalVariableCropProductionCostsService
{
	int save(ExternalVariableCropProductionCosts productionCosts);

	boolean update(ExternalVariableCropProductionCosts productionCosts);

	boolean delete(int id);

	ExternalVariableCropProductionCostsView getProductionCostsById(int id);

	boolean saveList(
            Set<ExternalVariableCropProductionCosts> productionCostsList);
}
