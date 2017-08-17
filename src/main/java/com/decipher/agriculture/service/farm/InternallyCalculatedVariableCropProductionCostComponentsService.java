package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.InternallyCalculatedVariableCropProductionCostComponents;
import com.decipher.view.form.farmDetails.InternallyCalculatedVariableCropProductionCostComponentsView;

public interface InternallyCalculatedVariableCropProductionCostComponentsService {
    int save(
            InternallyCalculatedVariableCropProductionCostComponents costComponents);

    boolean update(
            InternallyCalculatedVariableCropProductionCostComponents costComponents);

    boolean deleteById(int id);

    boolean saveList(
            Set<InternallyCalculatedVariableCropProductionCostComponents> costComponentsList);

    InternallyCalculatedVariableCropProductionCostComponentsView getCostComponentsById(
            int id);
}
