package com.decipher.agriculture.service.farm.impl;

import java.util.ArrayList;
import java.util.List;

import com.decipher.agriculture.service.farm.CropGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CropGroupDao;
import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.view.form.farmDetails.CropsGroupView;

@Repository
@Transactional
public class CropGroupServiceImpl implements CropGroupService
{
	@Autowired
	private CropGroupDao cropGroupDao;

	@Override
	public int saveCropGroup(CropsGroup cropsGroup)
	{
		return cropGroupDao.saveCropGroup(cropsGroup);
	}

	@Override
	public List<CropsGroupView> getAllCropGroupByFarm(int farmId)
	{
		List<CropsGroupView> cropsGroupView=new ArrayList<CropsGroupView>();
		List<CropsGroup> cropsGroupList=cropGroupDao.getAllCropGroupByFarm(farmId);
		
		if(cropsGroupList !=null)
		{
			for(CropsGroup groupList:cropsGroupList)
				cropsGroupView.add(new CropsGroupView(groupList));
		}
		return cropsGroupView;
	}

}
