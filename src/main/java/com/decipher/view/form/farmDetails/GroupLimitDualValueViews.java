package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farmOutput.GroupLimitDualValue;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by abhishek on 4/1/16.
 */
public class GroupLimitDualValueViews implements Serializable {

    private Double minimumAcreageValue;
    private Double minimumDualValue;
    private Double maximumAcreageValue;
    private Double maximumDualValue;
    private String groupName;

    public GroupLimitDualValueViews(GroupLimitDualValue groupLimitDualValue){
        DecimalFormat formatter = new DecimalFormat("#.00");
        groupName = groupLimitDualValue.getCropsGroup().getCropsGroupName();
        maximumDualValue = Double.parseDouble(formatter.format(groupLimitDualValue.getMaximumDualValue() == null ? 0 : groupLimitDualValue.getMaximumDualValue()));
        maximumAcreageValue = Double.parseDouble(formatter.format(groupLimitDualValue.getMaximumPrimalValue() == null ? 0 : groupLimitDualValue.getMaximumPrimalValue()));
        minimumDualValue = Double.parseDouble(formatter.format(groupLimitDualValue.getMinimumDualValue() == null ? 0 : groupLimitDualValue.getMinimumDualValue()));
        minimumAcreageValue = Double.parseDouble(formatter.format(groupLimitDualValue.getMinimumPrimalValue() == null ? 0 : groupLimitDualValue.getMinimumDualValue()));
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
