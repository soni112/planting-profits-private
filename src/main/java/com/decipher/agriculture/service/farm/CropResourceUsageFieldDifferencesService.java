package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.CropResourceUsageFieldDifferences;

public interface CropResourceUsageFieldDifferencesService
{
	int saveCropResourceUsageFieldDifferences(Set<CropResourceUsageFieldDifferences> obj);
}
