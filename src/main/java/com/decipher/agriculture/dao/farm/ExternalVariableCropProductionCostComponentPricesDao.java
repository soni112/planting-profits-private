package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.ExternalVariableCropProductionCostComponentPrices;

public interface ExternalVariableCropProductionCostComponentPricesDao
{
	int save(ExternalVariableCropProductionCostComponentPrices componentPrices);

	boolean update(
            ExternalVariableCropProductionCostComponentPrices componentPrices);

	boolean delete(int id);

	ExternalVariableCropProductionCostComponentPrices getProductionCostComponentPricesById(
            int id);

	boolean saveList(
            Set<ExternalVariableCropProductionCostComponentPrices> componentPricesList);
}
