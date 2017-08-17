package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.FieldPlanningParameters;

public interface FieldPlanningParametersDao {
    int save(FieldPlanningParameters fieldPlanningParameters);

    boolean update(FieldPlanningParameters fieldPlanningParameters);

    boolean deleteById(int id);

    boolean saveList(Set<FieldPlanningParameters> fieldPlanningParametersList);

    FieldPlanningParameters getFieldPlanningById(int id);
}
