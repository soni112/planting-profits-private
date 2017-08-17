package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.ResourceType;

public interface ResourceTypeDao {
    int save(ResourceType resourceType);

    boolean update(ResourceType resourceType);

    boolean deleteById(int id);

    boolean saveList(Set<ResourceType> resourceTypeList);

    ResourceType getCropProductionCostById(int id);
}
