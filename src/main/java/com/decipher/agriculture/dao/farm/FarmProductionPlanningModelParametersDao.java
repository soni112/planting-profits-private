package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.FarmProductionPlanningModelParameters;

public interface FarmProductionPlanningModelParametersDao
{
	int save(FarmProductionPlanningModelParameters farmProduction);
	boolean update(FarmProductionPlanningModelParameters farmProduction);
	boolean deleteById(int id);
	boolean saveList(Set<FarmProductionPlanningModelParameters> farmProductionList);
	FarmProductionPlanningModelParameters getFarmProductionById(int id);
}
