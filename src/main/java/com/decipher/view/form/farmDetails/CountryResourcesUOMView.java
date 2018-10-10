package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.CountryResourcesUOM;

import java.io.Serializable;

public class CountryResourcesUOMView  implements Serializable {
    private Integer id;
    private String uoMResource;

    public CountryResourcesUOMView() {

    }

    public CountryResourcesUOMView(CountryResourcesUOM countryResourcesUOM) {
        this.id = countryResourcesUOM.getId();
        this.uoMResource = countryResourcesUOM.getUoMResource();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUoMResource() {
        return uoMResource;
    }

    public void setUoMResource(String uoMResource) {
        this.uoMResource = uoMResource;
    }
}
