package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.FieldPlanningParameters;
import com.decipher.view.form.farmDetails.FieldPlanningParametersView;

public interface FieldPlanningParametersService {
    int save(FieldPlanningParameters fieldPlanningParameters);

    boolean update(FieldPlanningParameters fieldPlanningParameters);

    boolean deleteById(int id);

    boolean saveList(Set<FieldPlanningParameters> fieldPlanningParametersList);

    FieldPlanningParametersView getFieldPlanningById(int id);
}
