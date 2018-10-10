package com.decipher.view.form.farmDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.InternalVariableCropProductionCosts;
import com.decipher.agriculture.data.farm.OptionalCropProductionCostsDetails;
import com.decipher.util.AgricultureStandard;
import com.decipher.util.AgricultureStandardUtils;

public class OptionalCropProductionCostsDetailsView implements Serializable {
    private Integer id;
    private String costComponentsName;
    private BigDecimal unitsPerAcre;
    private BigDecimal unitPrices;
    private BigDecimal unitTotal;
    private Integer cropId;
    private String cropName;
    private String cropNameWithSpace;
    private BigDecimal calculateTotal;
    private static final DecimalFormat DECIMALFORMATTER = AgricultureStandard.decimalFormat;

    public OptionalCropProductionCostsDetailsView(OptionalCropProductionCostsDetails productionCostsDetails) {
        this.id = productionCostsDetails.getId();
        this.costComponentsName = productionCostsDetails.getCostComponentsName();
        this.unitsPerAcre = productionCostsDetails.getUnitsPerAcre();
        this.unitPrices = productionCostsDetails.getUnitPrices();
        this.unitTotal = productionCostsDetails.getUnitTotal();
        CropType type = productionCostsDetails.getCropType();
        InternalVariableCropProductionCosts productionCosts = type.getCostsCropProductionCosts();
//		PlantingProfitLogger.info("OptionalCropProductionCostsDetailsView : ");
        if (type != null) {
            this.cropId = type.getId();
            this.cropName = type.getCropName();
            this.cropNameWithSpace = type.getCropName();
//			PlantingProfitLogger.info("cropNameWithSpace : "+cropNameWithSpace);
            if (productionCosts != null) {
                this.calculateTotal = productionCosts.getCalculatedVariableProductionCost();
            }


        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getUnitsPerAcre() {
        return unitsPerAcre;
    }

    public void setUnitsPerAcre(BigDecimal unitsPerAcre) {
        this.unitsPerAcre = unitsPerAcre;
    }

    public String getUnitsPerAcreStr() {
        if (unitsPerAcre == null
                || unitsPerAcre.toString()
                .equalsIgnoreCase("0.0000")) {
            return "";
        } else {
            Float unitsPerAcreFloat = Float.parseFloat(unitsPerAcre.toString());
            String s1 = DECIMALFORMATTER.format(unitsPerAcreFloat);
            return AgricultureStandardUtils.commaSeparaterForPrice(s1);
        }
    }


    public BigDecimal getUnitPrices() {
        return unitPrices;
    }

    public String getUnitPricesStr() {
        if (unitPrices == null
                || unitPrices.toString()
                .equalsIgnoreCase("0.0000")) {
            return "";
        } else {
            Float unitPricesFloat = Float.parseFloat(unitPrices.toString());
            String s1 = DECIMALFORMATTER.format(unitPricesFloat);
            return "$" + AgricultureStandardUtils.commaSeparaterForPrice(s1);
        }
    }

    public void setUnitPrices(BigDecimal unitPrices) {
        this.unitPrices = unitPrices;
    }

    public BigDecimal getUnitTotal() {
        return unitTotal;
    }

    public String getUnitTotalStr() {
        if (unitTotal == null
                || unitTotal.toString().equalsIgnoreCase("0.0000")) {
            return "";
        } else {
            Float unitTotalFloat = Float.parseFloat(unitTotal.toString());
            String s1 = DECIMALFORMATTER.format(unitTotalFloat);
            return "$" + AgricultureStandardUtils.commaSeparaterForPrice(s1);
        }
    }

    public void setUnitTotal(BigDecimal unitTotal) {
        this.unitTotal = unitTotal;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getCropName() {
        return cropName.replaceAll("\\s+", "");
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCostComponentsName() {
        return costComponentsName;
    }

    public void setCostComponentsName(String costComponentsName) {
        this.costComponentsName = costComponentsName;
    }

    public BigDecimal getCalculateTotal() {
        return calculateTotal;
    }

    public String getCalculateTotalStr() {
        if (calculateTotal == null
                || calculateTotal.toString()
                .equalsIgnoreCase("0.0000")) {
            return "";
        } else {
            Float calculateTotalFloat = Float.parseFloat(calculateTotal.toString());
            String s1 = DECIMALFORMATTER.format(calculateTotalFloat);
            return "$" + AgricultureStandardUtils.commaSeparaterForPrice(s1);
        }
    }

    public void setCalculateTotal(BigDecimal calculateTotal) {
        this.calculateTotal = calculateTotal;
    }

    public String getCropNameWithSpace() {
        return cropNameWithSpace;
    }

    public void setCropNameWithSpace(String cropNameWithSpace) {
        this.cropNameWithSpace = cropNameWithSpace;
    }
}
