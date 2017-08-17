package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.ResourceType;
import com.decipher.view.form.farmDetails.ResourceTypeView;

public interface ResourceTypeService {
    int save(ResourceType resourceType);

    boolean update(ResourceType resourceType);

    boolean deleteById(int id);

    boolean saveList(Set<ResourceType> resourceTypeList);

    ResourceTypeView getCropProductionCostById(int id);
}
