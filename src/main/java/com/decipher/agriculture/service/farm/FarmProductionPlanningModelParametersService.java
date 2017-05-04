package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.FarmProductionPlanningModelParameters;
import com.decipher.view.form.farmDetails.FarmProductionPlanningModelParametersView;

public interface FarmProductionPlanningModelParametersService
{
	int save(FarmProductionPlanningModelParameters farmProduction);
	boolean update(FarmProductionPlanningModelParameters farmProduction);
	boolean deleteById(int id);
	boolean saveList(Set<FarmProductionPlanningModelParameters> farmProductionList);
	FarmProductionPlanningModelParametersView getFarmProductionById(int id);
}
