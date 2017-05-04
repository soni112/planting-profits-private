package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.InternalCropPricesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.InternalCropPricesInfoDao;
import com.decipher.agriculture.data.farm.InternalCropPricesInfo;
import com.decipher.view.form.farmDetails.InternalCropPricesInfoView;

@Repository
@Transactional
public class InternalCropPricesInfoServiceImpl implements
        InternalCropPricesInfoService
{
	@Autowired
	private InternalCropPricesInfoDao internalCropDao;

	@Override
	public int save(InternalCropPricesInfo cropPricesInfo)
	{
		return internalCropDao.save(cropPricesInfo);
	}

	@Override
	public boolean update(InternalCropPricesInfo cropPricesInfo)
	{
		return internalCropDao.update(cropPricesInfo);
	}

	@Override
	public boolean deleteById(int id)
	{
		return internalCropDao.deleteById(id);
	}

	@Override
	public boolean saveList(Set<InternalCropPricesInfo> cropPricesInfoList)
	{
		return internalCropDao.saveList(cropPricesInfoList);
	}

	@Override
	public InternalCropPricesInfoView getInternalCropPricesInfoById(int id)
	{
		return new InternalCropPricesInfoView(
				internalCropDao.getInternalCropPricesInfoById(id));
	}

}
