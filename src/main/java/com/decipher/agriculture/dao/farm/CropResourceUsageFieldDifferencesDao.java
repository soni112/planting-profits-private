package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.CropResourceUsageFieldDifferences;

public interface CropResourceUsageFieldDifferencesDao {
    int saveCropResourceUsageFieldDifferences(Set<CropResourceUsageFieldDifferences> obj);
}
