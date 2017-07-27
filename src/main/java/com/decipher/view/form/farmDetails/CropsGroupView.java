package com.decipher.view.form.farmDetails;

import java.util.Set;

import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.util.AgricultureStandardUtils;

public class CropsGroupView {
    private Integer id;
    private String cropsGroupName;
    private String minimumAcres;
    private String minimumAcresPercentage;
    private String maximumAcres;
    private String maximumAcresPercentage;
    private Set<CropType> cropSet;

    public CropsGroupView(CropsGroup cropsGroup) {
        this.id = cropsGroup.getId();
        this.cropsGroupName = cropsGroup.getCropsGroupName();
        this.minimumAcres = cropsGroup.getMinimumAcres();
        this.minimumAcres = cropsGroup.getMinimumAcresPercentageStr() != null ? cropsGroup.getMinimumAcresPercentageStr() : "0";
        this.maximumAcres = cropsGroup.getMaximumAcres();
        this.maximumAcres = cropsGroup.getMaximumAcresPercentageStr() != null ? cropsGroup.getMaximumAcresPercentageStr() : "0";
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

    public String getMinimumAcresWithoutComma(){
        if (minimumAcres == null || minimumAcres.equalsIgnoreCase("0")) {
            return "";
        } else {
            return AgricultureStandardUtils.removeAllCommas(minimumAcres);
        }
    }

    public void setMinimumAcres(String minimumAcres) {
        this.minimumAcres = minimumAcres;
    }

    public String getMaximumAcres() {
        return maximumAcres;
    }

    public String getMaximumAcresWithoutComma() {
        if (maximumAcres == null || maximumAcres.toString().equalsIgnoreCase("0")) {
            return "";
        } else {
            // return maximumAcres;
            return AgricultureStandardUtils.removeAllCommas(maximumAcres);
        }
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

    public String getMinimumAcresPercentage() {
        return minimumAcresPercentage;
    }

    public void setMinimumAcresPercentage(String minimumAcresPercentage) {
        this.minimumAcresPercentage = minimumAcresPercentage;
    }

    public String getMaximumAcresPercentage() {
        return maximumAcresPercentage;
    }

    public void setMaximumAcresPercentage(String maximumAcresPercentage) {
        this.maximumAcresPercentage = maximumAcresPercentage;
    }
}
