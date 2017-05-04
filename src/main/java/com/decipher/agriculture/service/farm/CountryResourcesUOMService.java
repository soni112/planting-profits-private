package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.CountryResourcesUOM;
import com.decipher.view.form.farmDetails.CountryResourcesUOMView;

public interface CountryResourcesUOMService
{
	int saveCountryResourcesUOM(CountryResourcesUOM countryResourcesUOM);
	boolean updateCountryResourcesUOM(CountryResourcesUOM countryResourcesUOM);
	boolean deleteCountryResourcesUOMById(int id);
	CountryResourcesUOMView getCountryResourcesUOMById(int id);
	boolean saveCountryResourcesUOMList(Set<CountryResourcesUOM> countryResourcesUOMList);
}
