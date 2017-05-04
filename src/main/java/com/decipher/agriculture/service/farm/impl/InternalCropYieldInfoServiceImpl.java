package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.InternalCropYieldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.InternalCropYieldInfoDao;
import com.decipher.agriculture.data.farm.InternalCropYieldInfo;
import com.decipher.view.form.farmDetails.InternalCropYieldInfoView;

@Repository
@Transactional
public class InternalCropYieldInfoServiceImpl implements
        InternalCropYieldInfoService
{


	@Autowired
	private InternalCropYieldInfoDao internalCropDao;

	@Override
	public int save(InternalCropYieldInfo cropYieldInfo)
	{
		return internalCropDao.save(cropYieldInfo);
	}

	@Override
	public boolean update(InternalCropYieldInfo cropYieldInfo)
	{
		return internalCropDao.update(cropYieldInfo);
	}

	@Override
	public boolean deleteById(int id)
	{
		return internalCropDao.deleteById(id);
	}

	@Override
	public boolean saveList(Set<InternalCropYieldInfo> cropYieldInfoList)
	{
		return internalCropDao.saveList(cropYieldInfoList);
	}

	@Override
	public InternalCropYieldInfoView getInternalCropYieldInfoById(int id)
	{
		return new InternalCropYieldInfoView(
				internalCropDao.getInternalCropYieldInfoById(id));
	}

}
