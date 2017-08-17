package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponentUsage;
import com.decipher.view.form.farmDetails.VariableCropProductionCostsComponentUsageView;

public interface VariableCropProductionCostsComponentUsageService {
    int save(VariableCropProductionCostsComponentUsage componentUsage);

    boolean update(VariableCropProductionCostsComponentUsage componentUsage);

    boolean deleteById(int id);

    boolean saveList(Set<VariableCropProductionCostsComponentUsage> componentUsageList);

    VariableCropProductionCostsComponentUsageView getcomponentUsageById(int id);
}
