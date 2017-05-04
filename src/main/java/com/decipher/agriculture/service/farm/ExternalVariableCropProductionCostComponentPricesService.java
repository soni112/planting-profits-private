package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.ExternalVariableCropProductionCostComponentPrices;
import com.decipher.view.form.farmDetails.ExternalVariableCropProductionCostComponentPricesView;

public interface ExternalVariableCropProductionCostComponentPricesService
{
	int save(ExternalVariableCropProductionCostComponentPrices componentPrices);

	boolean update(
			ExternalVariableCropProductionCostComponentPrices componentPrices);

	boolean delete(int id);

	ExternalVariableCropProductionCostComponentPricesView getProductionCostComponentPricesById(
			int id);

	boolean saveList(
			Set<ExternalVariableCropProductionCostComponentPrices> componentPricesList);
}
