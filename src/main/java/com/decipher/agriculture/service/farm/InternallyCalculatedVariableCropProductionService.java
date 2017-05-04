package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.InternallyCalculatedVariableCropProduction;
import com.decipher.view.form.farmDetails.InternallyCalculatedVariableCropProductionView;

public interface InternallyCalculatedVariableCropProductionService
{
	int save(InternallyCalculatedVariableCropProduction cropProduction);

	boolean update(InternallyCalculatedVariableCropProduction cropProduction);

	boolean deleteById(int id);

	boolean saveList(Set<InternallyCalculatedVariableCropProduction> cropProductionList);

	InternallyCalculatedVariableCropProductionView getCropProductionById(int id);
}
