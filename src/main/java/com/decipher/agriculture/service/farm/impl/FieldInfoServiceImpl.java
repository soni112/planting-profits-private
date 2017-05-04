package com.decipher.agriculture.service.farm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.decipher.agriculture.service.farm.FieldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.FieldInfoDao;
import com.decipher.agriculture.data.farm.FieldInfo;
import com.decipher.view.form.farmDetails.FieldInfoView;

@Repository
@Transactional
public class FieldInfoServiceImpl implements FieldInfoService
{

	@Autowired
	private FieldInfoDao fieldInfoDAO;

	@Override
	public int saveFieldInfo(FieldInfo fieldInfo)
	{
		return fieldInfoDAO.saveFieldInfo(fieldInfo);
	}

	@Override
	public boolean updateFieldInfo(FieldInfo fieldInfo)
	{
		return fieldInfoDAO.updateFieldInfo(fieldInfo);
	}

	@Override
	public boolean deleteFieldInfoById(int id)
	{
		return fieldInfoDAO.deleteFieldInfoById(id);
	}

	@Override
	public boolean saveList(Set<FieldInfo> fieldInfoList)
	{
		return fieldInfoDAO.saveList(fieldInfoList);
	}

	@Override
	public List<FieldInfoView> getAllFieldsByFarmId(int farmId)
	{
		List<FieldInfoView> fieldInfoViewList=new ArrayList<FieldInfoView>();
		List<FieldInfo> detailsFieldsList=fieldInfoDAO.getAllFieldsByFarmId(farmId);
		if(detailsFieldsList !=null)
		{
			for(FieldInfo detailsList:detailsFieldsList)
				fieldInfoViewList.add(new FieldInfoView(detailsList));
		}
		return fieldInfoViewList;
	}

	@Override
	public List<FieldInfo> getAllFieldsByFarm(int farmId)
	{
		List<FieldInfo> fieldInfoList=fieldInfoDAO.getAllFieldsByFarmId(farmId);
		return fieldInfoList;
	}
	
	

	//@Override
	//public FieldInfoView getFieldInfoById(int id)
	//{
	// new FieldInfoView(fieldInfoDAO.getFieldInfoById(id));
	//}

}
