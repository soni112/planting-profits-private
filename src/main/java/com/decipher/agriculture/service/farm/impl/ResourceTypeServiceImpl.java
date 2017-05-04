package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.ResourceTypeDao;
import com.decipher.agriculture.data.farm.ResourceType;
import com.decipher.view.form.farmDetails.ResourceTypeView;

@Repository
@Transactional
public class ResourceTypeServiceImpl implements ResourceTypeService
{
	@Autowired
	private ResourceTypeDao resourceTypeDao;

	@Override
	public int save(ResourceType resourceType)
	{
		return resourceTypeDao.save(resourceType);
	}

	@Override
	public boolean update(ResourceType resourceType)
	{
		return resourceTypeDao.update(resourceType);
	}

	@Override
	public boolean deleteById(int id)
	{
		return resourceTypeDao.deleteById(id);
	}

	@Override
	public boolean saveList(Set<ResourceType> resourceTypeList)
	{
		return resourceTypeDao.saveList(resourceTypeList);
	}

	@Override
	public ResourceTypeView getCropProductionCostById(int id)
	{
		return new ResourceTypeView(
				resourceTypeDao.getCropProductionCostById(id));
	}

}
