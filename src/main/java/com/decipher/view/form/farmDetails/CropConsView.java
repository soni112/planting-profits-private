package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.CropCons;

import java.io.Serializable;

public class CropConsView  implements Serializable {

    private Integer id;
    private String specialCropConsiderations;

    public CropConsView() {

    }

    public CropConsView(CropCons cropCons) {
        this.id = cropCons.getId();
        this.specialCropConsiderations = cropCons.getSpecialCropConsiderations();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecialCropConsiderations() {
        return specialCropConsiderations;
    }

    public void setSpecialCropConsiderations(String specialCropConsiderations) {
        this.specialCropConsiderations = specialCropConsiderations;
    }
}
