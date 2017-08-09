package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.CropResourceUsageFieldVariances;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.util.AgricultureStandardUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CropResourceUsageFieldVariancesView implements Cloneable {
    private Integer id;
    private String cropFieldResourceUse;
    private String cropResourceAmount;
    private Integer cropId;
    private String cropName;

    @JsonIgnore
    private CropResourceUsageFieldVariances cropResourceUsageFieldVariances;

    CropResourceUsageFieldVariancesView() {

    }

    public CropResourceUsageFieldVariancesView(CropResourceUsageFieldVariances cropResourceUsageFieldVariances) {
        this.id = cropResourceUsageFieldVariances.getId();
        this.cropFieldResourceUse = cropResourceUsageFieldVariances.getCropFieldResourceUse();
        this.cropResourceAmount = cropResourceUsageFieldVariances.getCropResourceAmount();
        // PlantingProfitLogger.info("cropResourceAmount : "+cropResourceAmount);
        CropType type = cropResourceUsageFieldVariances.getCropType();
        if (type != null) {
            this.cropName = type.getCropName();
            this.cropId = type.getId();
        }

        this.cropResourceUsageFieldVariances = cropResourceUsageFieldVariances;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCropFieldResourceUse() {
        return cropFieldResourceUse;
    }

    public void setCropFieldResourceUse(String cropFieldResourceUse) {
        this.cropFieldResourceUse = cropFieldResourceUse;
    }

    public String getCropResourceAmount() {
        if (cropResourceAmount == null || cropResourceAmount.equalsIgnoreCase("0")) {
            return "";
        } else {
            return AgricultureStandardUtils.commaSeparaterForPrice(cropResourceAmount);
        }
    }

    public void setCropResourceAmount(String cropResourceAmount) {
        this.cropResourceAmount = cropResourceAmount;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public CropResourceUsageFieldVariances getCropResourceUsageFieldVariances() {
        return cropResourceUsageFieldVariances;
    }

    public void setCropResourceUsageFieldVariances(CropResourceUsageFieldVariances cropResourceUsageFieldVariances) {
        this.cropResourceUsageFieldVariances = cropResourceUsageFieldVariances;
    }

    @Override
    public CropResourceUsageFieldVariancesView clone() throws CloneNotSupportedException {
        return (CropResourceUsageFieldVariancesView) super.clone();
    }
}