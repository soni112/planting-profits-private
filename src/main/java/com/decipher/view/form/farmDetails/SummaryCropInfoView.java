package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.SummaryCropInfo;

import java.io.Serializable;

public class SummaryCropInfoView implements Serializable {
    private Integer id;
    private String uomCrop;
    private String fieldCrop;
    private String vegOrFruitCrop;
    private String hiRiskCrop;
    private String conservation_Crop;
    private String Irrigated;

    public SummaryCropInfoView() {

    }

    public SummaryCropInfoView(SummaryCropInfo summaryCropInfo) {
        this.id = summaryCropInfo.getId();
        this.uomCrop = summaryCropInfo.getUomCrop();
        this.fieldCrop = summaryCropInfo.getFieldCrop();
        this.vegOrFruitCrop = summaryCropInfo.getVegOrFruitCrop();
        this.hiRiskCrop = summaryCropInfo.getHiRiskCrop();
        this.conservation_Crop = summaryCropInfo.getConservation_Crop();
        this.Irrigated = summaryCropInfo.getIrrigated();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUomCrop() {
        return uomCrop;
    }

    public void setUomCrop(String uomCrop) {
        this.uomCrop = uomCrop;
    }

    public String getFieldCrop() {
        return fieldCrop;
    }

    public void setFieldCrop(String fieldCrop) {
        this.fieldCrop = fieldCrop;
    }

    public String getVegOrFruitCrop() {
        return vegOrFruitCrop;
    }

    public void setVegOrFruitCrop(String vegOrFruitCrop) {
        this.vegOrFruitCrop = vegOrFruitCrop;
    }

    public String getHiRiskCrop() {
        return hiRiskCrop;
    }

    public void setHiRiskCrop(String hiRiskCrop) {
        this.hiRiskCrop = hiRiskCrop;
    }

    public String getConservation_Crop() {
        return conservation_Crop;
    }

    public void setConservation_Crop(String conservation_Crop) {
        this.conservation_Crop = conservation_Crop;
    }

    public String getIrrigated() {
        return Irrigated;
    }

    public void setIrrigated(String irrigated) {
        Irrigated = irrigated;
    }
}
