package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.CropResourceUsageFieldDifferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CropResourceUsageFieldDifferencesDao;
import com.decipher.agriculture.data.farm.CropResourceUsageFieldDifferences;
@Transactional
@Repository
public class CropResourceUsageFieldDifferencesServiceImpl implements CropResourceUsageFieldDifferencesService
{
	@Autowired
	CropResourceUsageFieldDifferencesDao cropResourceUsageFieldDifferencesDao;
	@Override
	public int saveCropResourceUsageFieldDifferences(Set<CropResourceUsageFieldDifferences> obj)
	{
		return cropResourceUsageFieldDifferencesDao.saveCropResourceUsageFieldDifferences(obj);
	}

}
