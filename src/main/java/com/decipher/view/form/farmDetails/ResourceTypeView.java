package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.ResourceType;

import java.io.Serializable;

public class ResourceTypeView implements Serializable {
    private Integer id;
    private String country;
    private String resourceType;

    public ResourceTypeView() {

    }

    public ResourceTypeView(ResourceType resourceType) {
        this.id = resourceType.getId();
        this.country = resourceType.getCountry();
        this.resourceType = resourceType.getResourceType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
