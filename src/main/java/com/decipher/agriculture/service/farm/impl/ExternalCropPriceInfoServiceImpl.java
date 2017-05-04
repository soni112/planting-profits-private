package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.ExternalCropPriceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.ExternalCropPriceInfoDao;
import com.decipher.agriculture.data.farm.ExternalCropPriceInfo;
import com.decipher.view.form.farmDetails.ExternalCropPriceInfoView;

@Repository
@Transactional
public class ExternalCropPriceInfoServiceImpl implements
        ExternalCropPriceInfoService
{

	@Autowired
	private ExternalCropPriceInfoDao externalCropPriceInfoDao;

	@Override
	public int saveExternalCropPriceInfo(
			ExternalCropPriceInfo externalCropPriceInfo)
	{
		return externalCropPriceInfoDao
				.saveExternalCropPriceInfo(externalCropPriceInfo);
	}

	@Override
	public boolean updateExternalCropPriceInfo(
			ExternalCropPriceInfo externalCropPriceInfo)
	{
		return externalCropPriceInfoDao
				.updateExternalCropPriceInfo(externalCropPriceInfo);
	}

	@Override
	public boolean deleteExternalCropPriceInfoById(int id)
	{
		return externalCropPriceInfoDao.deleteExternalCropPriceInfoById(id);
	}

	@Override
	public ExternalCropPriceInfoView getExternalCropPriceInfoById(int id)
	{
		return new ExternalCropPriceInfoView(
				externalCropPriceInfoDao.getExternalCropPriceInfoById(id));
	}

	@Override
	public boolean saveExternalCropPriceInfoList(
			Set<ExternalCropPriceInfo> externalCropPriceInfoList)
	{
		return externalCropPriceInfoDao
				.saveExternalCropPriceInfoList(externalCropPriceInfoList);
	}

}
