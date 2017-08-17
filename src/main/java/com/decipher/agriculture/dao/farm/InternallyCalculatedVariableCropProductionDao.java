package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.InternallyCalculatedVariableCropProduction;

public interface InternallyCalculatedVariableCropProductionDao {
    int save(InternallyCalculatedVariableCropProduction cropProduction);

    boolean update(InternallyCalculatedVariableCropProduction cropProduction);

    boolean deleteById(int id);

    boolean saveList(Set<InternallyCalculatedVariableCropProduction> cropProductionList);

    InternallyCalculatedVariableCropProduction getCropProductionById(int id);
}
