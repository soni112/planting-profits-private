package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.CountryResourcesUOM;

public interface CountryResourcesUOMDao
{
	int saveCountryResourcesUOM(CountryResourcesUOM countryResourcesUOM);
	boolean updateCountryResourcesUOM(CountryResourcesUOM countryResourcesUOM);
	boolean deleteCountryResourcesUOMById(int id);
	CountryResourcesUOM getCountryResourcesUOMById(int id);
	boolean saveCountryResourcesUOMList(Set<CountryResourcesUOM> countryResourcesUOMList);
}
