package com.decipher.view.form.farmDetails;

import java.util.Set;

import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.CropsGroup;

public class CropsGroupView {
    private Integer id;
    private String cropsGroupName;
    private String minimumAcres;
    private String maximumAcres;
    private Set<CropType> cropSet;

    public CropsGroupView(CropsGroup cropsGroup) {
        this.id = cropsGroup.getId();
        this.cropsGroupName = cropsGroup.getCropsGroupName();
        this.minimumAcres = cropsGroup.getMinimumAcres();
        this.maximumAcres = cropsGroup.getMaximumAcres();
        this.cropSet = cropsGroup.getCropTypes();
//		PlantingProfitLogger.info("cropsGroupName : "+cropsGroupName);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCropsGroupName() {
        return cropsGroupName;
    }

    public void setCropsGroupName(String cropsGroupName) {
        this.cropsGroupName = cropsGroupName;
    }

    public String getMinimumAcres() {
        return minimumAcres;
    }

    public void setMinimumAcres(String minimumAcres) {
        this.minimumAcres = minimumAcres;
    }

    public String getMaximumAcres() {
        return maximumAcres;
    }

    public void setMaximumAcres(String maximumAcres) {
        this.maximumAcres = maximumAcres;
    }

    public Set<CropType> getCropSet() {
        return cropSet;
    }

    public void setCropSet(Set<CropType> cropSet) {
        this.cropSet = cropSet;
    }
}
