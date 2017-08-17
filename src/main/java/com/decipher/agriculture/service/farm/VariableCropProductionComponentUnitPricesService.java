package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.VariableCropProductionComponentUnitPrices;
import com.decipher.view.form.farmDetails.VariableCropProductionComponentUnitPricesView;

public interface VariableCropProductionComponentUnitPricesService {
    int save(VariableCropProductionComponentUnitPrices unitPrices);

    boolean update(VariableCropProductionComponentUnitPrices unitPrices);

    boolean deleteById(int id);

    boolean saveList(Set<VariableCropProductionComponentUnitPrices> unitPricesList);

    VariableCropProductionComponentUnitPricesView getComponentUnitPricesById(int id);
}
