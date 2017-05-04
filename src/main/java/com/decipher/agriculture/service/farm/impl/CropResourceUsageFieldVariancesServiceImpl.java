package com.decipher.agriculture.service.farm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.decipher.agriculture.service.farm.CropResourceUsageFieldVariancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CropResourceUsageFieldVariancesDao;
import com.decipher.agriculture.data.farm.CropResourceUsageFieldVariances;
import com.decipher.view.form.farmDetails.CropResourceUsageFieldVariancesView;

@Repository
@Transactional
public class CropResourceUsageFieldVariancesServiceImpl implements
        CropResourceUsageFieldVariancesService {

	@Autowired
	private CropResourceUsageFieldVariancesDao cropResourceUsageFieldVariancesDao;

	@Override
	public int saveCropResourceUsageFieldVariances(
			CropResourceUsageFieldVariances cropResourceUsageFieldVariances) {
		return cropResourceUsageFieldVariancesDao
				.saveCropResourceUsageFieldVariances(cropResourceUsageFieldVariances);
	}

	@Override
	public boolean updateCropResourceUsageFieldVariances(
			CropResourceUsageFieldVariances cropResourceUsageFieldVariances) {
		return cropResourceUsageFieldVariancesDao
				.updateCropResourceUsageFieldVariances(cropResourceUsageFieldVariances);
	}

	@Override
	public boolean deleteCropResourceUsageFieldVariancesById(int id) {
		return cropResourceUsageFieldVariancesDao
				.deleteCropResourceUsageFieldVariancesById(id);
	}

	@Override
	public CropResourceUsageFieldVariancesView getCropResourceUsageFieldVariancesById(
			int id) {
		return new CropResourceUsageFieldVariancesView(
				cropResourceUsageFieldVariancesDao
						.getCropResourceUsageFieldVariancesById(id));
	}

	@Override
	public boolean saveCropResourceUsageFieldVariancesList(
			Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariancesList) {
		return cropResourceUsageFieldVariancesDao
				.saveCropResourceUsageFieldVariancesList(cropResourceUsageFieldVariancesList);
	}

	@Override
	public List<CropResourceUsageFieldVariancesView> getAllResourceByCrop(int cropId) {
		List<CropResourceUsageFieldVariancesView> cropResourceView = new ArrayList<CropResourceUsageFieldVariancesView>();
		List<CropResourceUsageFieldVariances> cropResourcesList = cropResourceUsageFieldVariancesDao.getAllResourceByCrop(cropId);
		if (cropResourcesList != null) {
			for (CropResourceUsageFieldVariances detailsList : cropResourcesList)
				cropResourceView.add(new CropResourceUsageFieldVariancesView(detailsList));
		}
		return cropResourceView;
	}

	@Override
	public List<CropResourceUsageFieldVariancesView> getAllResourceByCropIds(
			Integer[] cropId) {
		List<CropResourceUsageFieldVariancesView> cropResourceView = new ArrayList<CropResourceUsageFieldVariancesView>();
		List<CropResourceUsageFieldVariances> cropResourcesList = cropResourceUsageFieldVariancesDao.getAllResourceByCropIds(cropId);
		if (cropResourcesList != null) {
			for (CropResourceUsageFieldVariances detailsList : cropResourcesList)
				cropResourceView.add(new CropResourceUsageFieldVariancesView(detailsList));
		}
		return cropResourceView;
	}
}