package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.SummaryCropInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.SummaryCropInfoDao;
import com.decipher.agriculture.data.farm.SummaryCropInfo;
import com.decipher.view.form.farmDetails.SummaryCropInfoView;

@Repository
@Transactional
public class SummaryCropInfoServiceImpl implements SummaryCropInfoService
{
	@Autowired
	private SummaryCropInfoDao summaryCropInfoDao;

	@Override
	public int save(SummaryCropInfo summaryCropInfo)
	{
		return summaryCropInfoDao.save(summaryCropInfo);
	}

	@Override
	public boolean update(SummaryCropInfo summaryCropInfo)
	{
		return summaryCropInfoDao.update(summaryCropInfo);
	}

	@Override
	public boolean deleteById(int id)
	{
		return summaryCropInfoDao.deleteById(id);
	}

	@Override
	public boolean saveList(Set<SummaryCropInfo> summaryCropInfoList)
	{
		return summaryCropInfoDao.saveList(summaryCropInfoList);
	}

	@Override
	public SummaryCropInfoView getSummaryCropInfoById(int id)
	{
		return new SummaryCropInfoView(
				summaryCropInfoDao.getSummaryCropInfoById(id));
	}

}
