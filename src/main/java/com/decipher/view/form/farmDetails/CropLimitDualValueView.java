package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farmOutput.CropLimitDualValue;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by raja on 12/22/15.
 * @update - Abhishek
 * @date - 04-01-2016
 */
public class CropLimitDualValueView  implements Serializable {
    private Double minimumAcreageValue;
    private Double minimumDualValue;
    private Double maximumAcreageValue;
    private Double maximumDualValue;
    private String cropName;

    public CropLimitDualValueView(CropLimitDualValue cropLimitDualValue){
        DecimalFormat formatter = new DecimalFormat("#.00");
        maximumDualValue = Double.parseDouble(formatter.format(cropLimitDualValue.getMaximumDualValue() == null ? 0 : cropLimitDualValue.getMaximumDualValue()));
        maximumAcreageValue = Double.parseDouble(formatter.format(cropLimitDualValue.getMaximumPrimalValue() == null ? 0 : cropLimitDualValue.getMaximumPrimalValue()));
        minimumDualValue = Double.parseDouble(formatter.format(cropLimitDualValue.getMinimumDualValue() == null ? 0 : cropLimitDualValue.getMinimumDualValue()));
        minimumAcreageValue = Double.parseDouble(formatter.format(cropLimitDualValue.getMinimumPrimalValue() == null ? 0 : cropLimitDualValue.getMinimumPrimalValue()));
        cropName = cropLimitDualValue.getCropType().getCropName();

    }

    public Double getMinimumAcreageValue() {
        return minimumAcreageValue;
    }

    public void setMinimumAcreageValue(Double minimumAcreageValue) {
        this.minimumAcreageValue = minimumAcreageValue;
    }

    public Double getMinimumDualValue() {
        return minimumDualValue;
    }

    public void setMinimumDualValue(Double minimumDualValue) {
        this.minimumDualValue = minimumDualValue;
    }

    public Double getMaximumAcreageValue() {
        return maximumAcreageValue;
    }

    public void setMaximumAcreageValue(Double maximumAcreageValue) {
        this.maximumAcreageValue = maximumAcreageValue;
    }

    public Double getMaximumDualValue() {
        return maximumDualValue;
    }

    public void setMaximumDualValue(Double maximumDualValue) {
        this.maximumDualValue = maximumDualValue;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }
}
