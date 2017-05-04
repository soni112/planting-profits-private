package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.CountryResourcesUOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CountryResourcesUOMDao;
import com.decipher.agriculture.data.farm.CountryResourcesUOM;
import com.decipher.view.form.farmDetails.CountryResourcesUOMView;

@Repository
@Transactional
public class CountryResourcesUOMServiceImpl implements CountryResourcesUOMService
{
	
	@Autowired
	private CountryResourcesUOMDao countryResourcesUOMServiceDao;

	@Override
	public int saveCountryResourcesUOM(CountryResourcesUOM countryResourcesUOM)
	{
		return countryResourcesUOMServiceDao.saveCountryResourcesUOM(countryResourcesUOM);
	}

	@Override
	public boolean updateCountryResourcesUOM(
			CountryResourcesUOM countryResourcesUOM)
	{
		return countryResourcesUOMServiceDao.updateCountryResourcesUOM(countryResourcesUOM);
	}

	@Override
	public boolean deleteCountryResourcesUOMById(int id)
	{
		return countryResourcesUOMServiceDao.deleteCountryResourcesUOMById(id);
	}

	@Override
	public CountryResourcesUOMView getCountryResourcesUOMById(int id)
	{
		return new CountryResourcesUOMView(countryResourcesUOMServiceDao.getCountryResourcesUOMById(id));
	}

	@Override
	public boolean saveCountryResourcesUOMList(
			Set<CountryResourcesUOM> countryResourcesUOMList)
	{
		return countryResourcesUOMServiceDao.saveCountryResourcesUOMList(countryResourcesUOMList);
	}

}
