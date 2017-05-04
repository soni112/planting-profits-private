package com.decipher.agriculture.service.farm.impl;

import java.util.ArrayList;
import java.util.List;

import com.decipher.agriculture.service.farm.CropFieldChociesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CropFieldChociesDao;
import com.decipher.agriculture.data.farm.CropFieldChocies;
//import com.decipher.view.form.farmDetails.CropFieldChociesView;
import com.decipher.view.form.farmDetails.CropFieldChociesView;

@Repository
@Transactional
public class CropFieldChociesServiceImpl implements CropFieldChociesService
{

	@Autowired
	private CropFieldChociesDao cropFieldChociesDao;

	@Override
	public int saveCropFieldChocies(CropFieldChocies cropFieldChocies)
	{
		return cropFieldChociesDao.saveCropFieldChocies(cropFieldChocies);
	}

	@Override
	public boolean updateCropFieldChocies(CropFieldChocies cropFieldChocies)
	{
		return cropFieldChociesDao.updateCropFieldChocies(cropFieldChocies);
	}

	@Override
	public boolean deleteCropFieldChociesById(int id)
	{
		return cropFieldChociesDao.deleteCropFieldChociesById(id);
	}

	@Override
	public List<CropFieldChociesView> getAllCropFiledsCrop(int cropId)
	{
		List<CropFieldChociesView> cropFieldChociesView=new ArrayList<CropFieldChociesView>();
		List<CropFieldChocies> cropFieldChociesList=cropFieldChociesDao.getAllCropFiledsCrop(cropId);
		if(cropFieldChociesList !=null)
		{
			for(CropFieldChocies detailsList:cropFieldChociesList)
				cropFieldChociesView.add(new CropFieldChociesView(detailsList));
		}
		return cropFieldChociesView;
	}

	@Override
	public List<CropFieldChociesView> getAllCropFiledsCropIds(Integer[] cropId)
	{
		List<CropFieldChociesView> cropFieldChociesView=new ArrayList<CropFieldChociesView>();
		List<CropFieldChocies> cropFieldChociesList= cropFieldChociesDao.getAllCropFiledsCropIds(cropId);
		if(cropFieldChociesList !=null)
		{
			for(CropFieldChocies detailsList:cropFieldChociesList)
				cropFieldChociesView.add(new CropFieldChociesView(detailsList));
		}
		return cropFieldChociesView;
	}

	//@Override
	//public CropFieldChociesView getCropFieldChociesById(int id)
//	{
		//return new CropFieldChociesView(
		//		cropFieldChociesDao.getCropFieldChociesById(id));
	//}

}
