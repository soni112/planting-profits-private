package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.ExternalCropYieldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.ExternalCropYieldInfoDao;
import com.decipher.agriculture.data.farm.ExternalCropYieldInfo;
import com.decipher.view.form.farmDetails.ExternalCropYieldInfoView;

@Repository
@Transactional
public class ExternalCropYieldInfoServiceImpl implements
        ExternalCropYieldInfoService
{

	@Autowired
	private ExternalCropYieldInfoDao externalCropYieldInfoDao;

	@Override
	public int saveExternalCropPriceInfo(
			ExternalCropYieldInfo externalCropYieldInfo)
	{
		return externalCropYieldInfoDao
				.saveExternalCropPriceInfo(externalCropYieldInfo);
	}

	@Override
	public boolean updateExternalCropYieldInfo(
			ExternalCropYieldInfo externalCropYieldInfo)
	{
		return externalCropYieldInfoDao
				.updateExternalCropYieldInfo(externalCropYieldInfo);
	}

	@Override
	public boolean deleteExternalCropYieldInfoById(int id)
	{
		return externalCropYieldInfoDao.deleteExternalCropYieldInfoById(id);
	}

	@Override
	public ExternalCropYieldInfoView getExternalCropYieldInfoById(int id)
	{
		return new ExternalCropYieldInfoView(
				externalCropYieldInfoDao.getExternalCropYieldInfoById(id));
	}

	@Override
	public boolean saveExternalCropYieldInfoList(
			Set<ExternalCropYieldInfo> externalCropYieldInfoList)
	{
		return externalCropYieldInfoDao
				.saveExternalCropYieldInfoList(externalCropYieldInfoList);
	}

}
