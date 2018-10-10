package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponentUsage;

import java.io.Serializable;

public class VariableCropProductionCostsComponentUsageView implements Serializable {

    private Integer id;
    private Double usagePerUoMLand;
    private String Other;

    public VariableCropProductionCostsComponentUsageView() {

    }

    public VariableCropProductionCostsComponentUsageView(
            VariableCropProductionCostsComponentUsage variableCropProductionCostsComponentUsage) {
        this.id = variableCropProductionCostsComponentUsage.getId();
        this.usagePerUoMLand = variableCropProductionCostsComponentUsage
                .getUsagePerUoMLand();
        Other = variableCropProductionCostsComponentUsage.getOther();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getUsagePerUoMLand() {
        return usagePerUoMLand;
    }

    public void setUsagePerUoMLand(Double usagePerUoMLand) {
        this.usagePerUoMLand = usagePerUoMLand;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String other) {
        Other = other;
    }
}
