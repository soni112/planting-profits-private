package com.decipher.agriculture.service.farm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.decipher.agriculture.service.farm.CropResourceUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CropResourceUsageDao;
import com.decipher.agriculture.data.farm.CropResourceUsage;
import com.decipher.view.form.farmDetails.CropResourceUsageView;

@Repository
@Transactional
public class CropResourceUsageServiceImpl implements CropResourceUsageService {

	@Autowired
	private CropResourceUsageDao cropResourceUsageDao;

	@Override
	public int saveCropResourceUsage(CropResourceUsage cropResourceUsage) {
		return cropResourceUsageDao.saveCropResourceUsage(cropResourceUsage);
	}

	@Override
	public boolean updateCropResourceUsage(CropResourceUsage cropResourceUsage) {
		return cropResourceUsageDao.updateCropResourceUsage(cropResourceUsage);
	}

	@Override
	public boolean deleteCropResourceUsageById(int id) {
		return cropResourceUsageDao.deleteCropResourceUsageById(id);
	}

	@Override
	public CropResourceUsageView getCropResourceUsageById(int id) {
		return new CropResourceUsageView(
				cropResourceUsageDao.getCropResourceUsageById(id));
	}

	@Override
	public boolean saveCropResourceUsageList(
			Set<CropResourceUsage> cropResourceUsageList) {
		return cropResourceUsageDao
				.saveCropResourceUsageList(cropResourceUsageList);
	}

	@Override
	public List<CropResourceUsageView> getAllCropResourceUsageByFarmId(
			int farmId) {
		List<CropResourceUsageView> cropResourceUsageView = new ArrayList<CropResourceUsageView>();
		List<CropResourceUsage> cropResourceList = cropResourceUsageDao.getAllCropResourceUsageByFarmId(farmId);
		if (cropResourceList != null) {
			for (CropResourceUsage resourceList : cropResourceList)
				if(!resourceList.getCropResourceUseAmount().isEmpty()
						&& !resourceList.getCropResourceUseAmount().equalsIgnoreCase("0")) {
					cropResourceUsageView.add(new CropResourceUsageView(resourceList));
				}
		}
		return cropResourceUsageView;
	}

	@Override
	public String getAllCropResourceUsageByFarmIds(List<Integer> farmId) {
		String cropResourceUsage = "";
		List<CropResourceUsage> cropResourceList = cropResourceUsageDao
				.getAllCropResourceUsageByFarmIds(farmId);
		if (cropResourceList != null) {
			for (CropResourceUsage resourceList : cropResourceList)
				cropResourceUsage += resourceList.getId() + ",";
		}
		return cropResourceUsage.substring(0, cropResourceUsage.length() - 1);
	}

}
