package com.decipher.view.form.farmDetails;

import java.io.Serializable;
import java.math.BigDecimal;

import com.decipher.agriculture.data.farm.ExternalCropPriceInfo;

public class ExternalCropPriceInfoView  implements Serializable {
    private Integer id;
    private BigDecimal intExpCropPrice;
    private BigDecimal intMinCropPrice;
    private BigDecimal intMaxCropPrice;

    public ExternalCropPriceInfoView() {

    }

    public ExternalCropPriceInfoView(ExternalCropPriceInfo cropPriceInfo) {
        this.id = cropPriceInfo.getId();
        this.intExpCropPrice = cropPriceInfo.getIntExpCropPrice();
        this.intMinCropPrice = cropPriceInfo.getIntMinCropPrice();
        this.intMaxCropPrice = cropPriceInfo.getIntMaxCropPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getIntExpCropPrice() {
        return intExpCropPrice;
    }

    public void setIntExpCropPrice(BigDecimal intExpCropPrice) {
        this.intExpCropPrice = intExpCropPrice;
    }

    public BigDecimal getIntMinCropPrice() {
        return intMinCropPrice;
    }

    public void setIntMinCropPrice(BigDecimal intMinCropPrice) {
        this.intMinCropPrice = intMinCropPrice;
    }

    public BigDecimal getIntMaxCropPrice() {
        return intMaxCropPrice;
    }

    public void setIntMaxCropPrice(BigDecimal intMaxCropPrice) {
        this.intMaxCropPrice = intMaxCropPrice;
    }
}
