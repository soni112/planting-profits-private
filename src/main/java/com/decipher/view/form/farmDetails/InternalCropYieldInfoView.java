package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.InternalCropYieldInfo;

import java.io.Serializable;


public class InternalCropYieldInfoView implements Serializable {

    private Integer id;
    private String intExpCropYield;
    private String intMinCropYield;
    private String intMaxCropYield;

    public InternalCropYieldInfoView() {

    }

    public InternalCropYieldInfoView(InternalCropYieldInfo internalCropYieldInfo) {
        this.id = internalCropYieldInfo.getId();
        this.intExpCropYield = internalCropYieldInfo.getIntExpCropYield();
        this.intMinCropYield = internalCropYieldInfo.getIntMinCropYield();
        this.intMaxCropYield = internalCropYieldInfo.getIntMaxCropYield();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntExpCropYield() {
        return intExpCropYield;
    }

    public void setIntExpCropYield(String intExpCropYield) {
        this.intExpCropYield = intExpCropYield;
    }

    public String getIntMinCropYield() {
        return intMinCropYield;
    }

    public void setIntMinCropYield(String intMinCropYield) {
        this.intMinCropYield = intMinCropYield;
    }

    public String getIntMaxCropYield() {
        return intMaxCropYield;
    }

    public void setIntMaxCropYield(String intMaxCropYield) {
        this.intMaxCropYield = intMaxCropYield;
    }
}
