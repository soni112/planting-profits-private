package com.decipher.agriculture.service.farm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.decipher.agriculture.service.farm.CropTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CropTypeDao;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.view.form.farmDetails.CropListForGroupView;
import com.decipher.view.form.farmDetails.CropTypeView;

@Repository
@Transactional
public class CropTypeServiceImpl implements CropTypeService {

	@Autowired
	private CropTypeDao cropTypeDao;

	@Override
	public CropType getCropByName(String name) {
		return cropTypeDao.getCropByName(name);

	}

	@Override
	public boolean isCropExitsWithName(String name) {
		return cropTypeDao.isCropExitsWithName(name);
	}

	@Override
	public int saveCrop(CropType cropType) {
		return cropTypeDao.saveCrop(cropType);
	}

	@Override
	public boolean updateCrop(CropType cropType) {
		return cropTypeDao.updateCrop(cropType);
	}

	@Override
	public boolean deleteCropById(int id) {
		return cropTypeDao.deleteCropById(id);
	}

	@Override
	public CropTypeView getCropById(int id) {
		return new CropTypeView(cropTypeDao.getCropById(id));
	}

	@Override
	public boolean saveCropTypeList(Set<CropType> cropTypeList) {
		return cropTypeDao.saveCropTypeList(cropTypeList);
	}

	@Override
	public boolean deleteCropByFarmId(int farmId) {
		return cropTypeDao.deleteCropByFarmId(farmId);
	}

	@Override
	public List<CropTypeView> getAllCropTypeByArcesId(int arcesId) {
		List<CropType> cropTypes = cropTypeDao.getAllCropTypeByArcesId(arcesId);
		List<CropTypeView> cropTypeViews = new ArrayList<CropTypeView>();
		if (cropTypes != null) {
			for (CropType cropType : cropTypes) {
				cropTypeViews.add(new CropTypeView(cropType));
			}
		}
		return cropTypeViews;
	}

	@Override
	public List<CropTypeView> getAllCropByFarm(int farmId) {

		List<CropTypeView> cropTypeViews = new ArrayList<CropTypeView>();
		List<CropType> cropTypes = cropTypeDao.getAllCropByFarm(farmId);

		if (cropTypes != null) {
			for (CropType cropType : cropTypes)
				cropTypeViews.add(new CropTypeView(cropType));
		}
		return cropTypeViews;

	}

	@Override
	public List<CropType> getAllCropByFarmId(int farmId) {
		return cropTypeDao.getAllCropByFarm(farmId);
	}

	@Override
	public String getAllCropIdsByFarmIds(List<Integer> farmId) {
		String cropIds = "";
		List<CropType> cropResourceList = cropTypeDao.getAllCropByFarmIds(farmId);
		if (cropResourceList != null) {
			for (CropType cropList : cropResourceList)
				cropIds += cropList.getId() + ",";
		}
		return cropIds.substring(0, cropIds.length() - 1);
	}

	@Override
	public List<CropListForGroupView> getAllCropByGroupIds(Integer[] groupId) {
		List<CropListForGroupView> cropsGroupView = new ArrayList<CropListForGroupView>();
		List<CropType> cropTypeList = cropTypeDao.getAllCropByGroupIds(groupId);
		if (cropTypeList != null) {
			for (CropType detailsList : cropTypeList)
				cropsGroupView.add(new CropListForGroupView(detailsList));
		}
		return cropsGroupView;
	}

}
