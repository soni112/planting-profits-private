package com.decipher.view.form.farmDetails;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Set;

import com.decipher.agriculture.data.farm.AdditionalCropIncome;
import com.decipher.agriculture.data.farm.CropForwardSales;
import com.decipher.agriculture.data.farm.CropLimit;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.CropUnitOfMeasure;
import com.decipher.agriculture.data.farm.CropYieldFieldVariances;
import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.agriculture.data.farm.FieldInfo;
import com.decipher.agriculture.data.farm.InternalCropPricesInfo;
import com.decipher.agriculture.data.farm.InternalCropYieldInfo;
import com.decipher.agriculture.data.farm.InternalVariableCropProductionCosts;
import com.decipher.agriculture.data.farm.OptionalCropPlantingDates;
import com.decipher.agriculture.data.farm.SummaryCropInfo;
import com.decipher.util.AgricultureStandard;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.PlantingProfitLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class CropTypeView implements Cloneable {
    public static final DateFormat formatter = AgricultureStandard.FORMATTER;

    private Integer id;
    private String cropTypeName;
    private String cropName;
    private Boolean selected;
    private String cropUOM;

    private String intExpCropYield;
    private String intMinCropYield;
    private String intMaxCropYield;

    private BigDecimal intExpCropPrice;
    private BigDecimal intMinCropPrice;
    private BigDecimal intMaxCropPrice;
    private BigDecimal summaryVariableProductionCost;
    private BigDecimal calculatedVariableProductionCost;

    private String hiRiskCrop;
    private String conservation_Crop;
    private String irrigated;

    private Date preferredPlantingDate;
    private Date earlyPlantingDate;
    private Date latePlantingDate;
    private Integer lengthofSeason;
    private String cropNameForId;

    private String minimumAcres;
    private String maximumAcres;

    private String forwardPrice;
    private String forwardQuantity;
    private Boolean proposedchecked;
    private String firmchecked;
    private Double forwardAcres;
    private String contactIdentifier;
    private Double forwardUpperLimit;

    private Integer cropYieldFieldId;
    private Double expCropYieldField;
    private Double minCropYieldField;
    private Double maxCropYieldField;
    private Double varProductionCost;
    private Integer fieldIdForVariances;
    private String fieldNameForVariances;
    private Integer cropIdForVariences;
    private Set<CropsGroup> groupSet;
    private boolean montyCarloStatus;
    /**
     * @changed - Abhishek
     * @date - 09-02-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private CropType cropType;

    private Double governmentPayments;
    private Double coProductsPrice;
    private Double additionalVariableProductionCost;
    private Double additionalIncome;

    private static final DecimalFormat DECIMALFORMATTER = AgricultureStandard.decimalFormat;

    public CropTypeView() {

    }

    public CropTypeView(CropType cropType) {

        this.id = cropType.getId();
        this.cropType = cropType;
        this.cropTypeName = cropType.getCropTypeName();
        this.cropName = cropType.getCropName();
        this.selected = cropType.getSelected();
        this.cropNameForId = cropType.getCropName();
        this.groupSet = cropType.getCropsGroup();

        this.montyCarloStatus = cropType.getFarmInfo().getMontyCarloStatus();

        // PlantingProfitLogger.info("intExpCropYieldbefore"+intExpCropYield);
        if (this.selected) {
            CropUnitOfMeasure cropUom = cropType.getCropUnitOfMeasure();
            if (cropUom != null) {
                this.cropUOM = cropUom.getUnitOfMeasure();
            }
            InternalCropYieldInfo cropYieldInfo = cropType.getCropYieldInfo();
            if (cropYieldInfo != null) {
                this.intExpCropYield = cropYieldInfo.getIntExpCropYield();
                this.intMinCropYield = cropYieldInfo.getIntMinCropYield();
                this.intMaxCropYield = cropYieldInfo.getIntMaxCropYield();
            }
            // PlantingProfitLogger.info("intExpCropYield"+intExpCropYield);
            InternalCropPricesInfo cropPricesInfo = cropType.getCropPricesInfo();
            if (cropPricesInfo != null) {
                this.intExpCropPrice = cropPricesInfo.getIntExpCropPrice();
                this.intMinCropPrice = cropPricesInfo.getIntMinCropPrice();
                this.intMaxCropPrice = cropPricesInfo.getIntMaxCropPrice();
            }
            InternalVariableCropProductionCosts costsCropProductionCosts = cropType.getCostsCropProductionCosts();
            if (costsCropProductionCosts != null) {
                this.calculatedVariableProductionCost = costsCropProductionCosts.getCalculatedVariableProductionCost();
                this.summaryVariableProductionCost = costsCropProductionCosts.getSummaryVariableProductionCost();
            }

            SummaryCropInfo cropInfo = cropType.getCropInfo();
            if (cropInfo != null) {
                this.hiRiskCrop = cropInfo.getHiRiskCrop();
                this.conservation_Crop = cropInfo.getConservation_Crop();
                this.irrigated = cropInfo.getIrrigated();
            }

            OptionalCropPlantingDates cropPlantingDates = cropType.getCropPlantingDates();
            if (cropPlantingDates != null) {
                this.preferredPlantingDate = cropPlantingDates.getPreferredPlantingDate();
                this.earlyPlantingDate = cropPlantingDates.getEarlyPlantingDate();
                this.latePlantingDate = cropPlantingDates.getLatePlantingDate();
                this.lengthofSeason = cropPlantingDates.getLengthofSeason();
            }

            CropLimit cropLimit = cropType.getCropLimit();
            if (cropLimit != null) {
                this.minimumAcres = cropLimit.getMinimumAcres();
                this.maximumAcres = cropLimit.getMaximumAcres();
            }
            CropForwardSales cropForwardSales = cropType.getCropForwardSales();
            if (cropForwardSales != null) {
                this.forwardPrice = cropForwardSales.getPrice();
                this.forwardQuantity = cropForwardSales.getQuantity();
                this.forwardAcres = cropForwardSales.getAcres();
                this.proposedchecked = cropForwardSales.getProposedchecked();
                this.firmchecked = cropForwardSales.getFirmchecked();
                this.contactIdentifier = cropForwardSales.getContactIdentifier();
                this.forwardUpperLimit = cropForwardSales.getUpperLimit();
            }

            CropYieldFieldVariances yieldFieldVariances = cropType.getCropYieldFieldVariances();
            if (yieldFieldVariances != null && yieldFieldVariances.getExpCropYieldField() != 0.0
                    && yieldFieldVariances.getMinCropYieldField() != 0.0 && yieldFieldVariances.getMaxCropYieldField() != 0.0
                    && yieldFieldVariances.getVarProductionCost() != 0.0) {
                this.cropYieldFieldId = yieldFieldVariances.getId();
                this.expCropYieldField = yieldFieldVariances.getExpCropYieldField();
                this.minCropYieldField = yieldFieldVariances.getMinCropYieldField();
                this.maxCropYieldField = yieldFieldVariances.getMaxCropYieldField();
                this.varProductionCost = yieldFieldVariances.getVarProductionCost();
                CropType type = yieldFieldVariances.getCropType();

                if (type != null) {
                    this.cropIdForVariences = type.getId();
                }

                FieldInfo fieldInfo = yieldFieldVariances.getFieldInfo();
                if (fieldInfo != null) {
                    this.fieldIdForVariances = fieldInfo.getId();
                    this.fieldNameForVariances = fieldInfo.getFieldName();
                }
            }
        }

        AdditionalCropIncome additionalCropIncome = cropType.getAdditionalCropIncome();
        if (additionalCropIncome != null) {
            this.governmentPayments = additionalCropIncome.getGovernmentPayments();
            this.coProductsPrice = additionalCropIncome.getCoProductsPrice();
            this.additionalVariableProductionCost = additionalCropIncome.getAdditionalVariableProductionCost();
            this.additionalIncome = additionalCropIncome.getAdditionalIncome();
        }


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCropTypeName() {
        return cropTypeName;
    }

    public void setCropTypeName(String cropTypeName) {
        this.cropTypeName = cropTypeName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getCropUOM() {
        return cropUOM;
    }

    public void setCropUOM(String cropUOM) {
        this.cropUOM = cropUOM;
    }

    public String getIntExpCropYield() {
        if (intExpCropYield != null) {
            return AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(intExpCropYield);
        } else {
            return "0";
        }

    }

    public String getIntExpCropYieldStr() {
        if (intExpCropYield != null) {
            return AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(intExpCropYield);
        } else {
            return "";
        }

    }

    public String getIntExpCropYieldWithDecimal() {
        if (intExpCropYield != null) {
            return AgricultureStandardUtils.priceWithOneDecimal(intExpCropYield);
        } else {
            return "";
        }

    }

    public void setIntExpCropYield(String intExpCropYield) {
        this.intExpCropYield = intExpCropYield;
    }

    public String getIntMinCropYield() {
        /*if (intMinCropYield.trim().equals("")) {
            return "";
        } else {
            return AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(intMinCropYield);
        }*/
        if (intMinCropYield != null && !intMinCropYield.equalsIgnoreCase("0")) {
            return AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(intMinCropYield);
        } else {
            return "0";
        }
    }

    public String getIntMinCropYieldStr() {
        if (intMinCropYield != null && !intMinCropYield.equalsIgnoreCase("0")) {
            return AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(intMinCropYield);
        } else {
            return "";
        }
    }

    public void setIntMinCropYield(String intMinCropYield) {
        this.intMinCropYield = intMinCropYield;
    }

    public String getIntMaxCropYield() {
        /*if (intMaxCropYield.trim().equals("")) {
            return "";
        } else {
            return AgricultureStandardUtils
                    .commaSeparaterForPriceWithOneDecimal(intMaxCropYield);
        }*/
        if (intMaxCropYield != null && !intMaxCropYield.equalsIgnoreCase("0")) {
            return AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(intMaxCropYield);
        } else {
            return "0";
        }

    }

    public String getIntMaxCropYieldStr() {
        if (intMaxCropYield != null && !intMaxCropYield.equalsIgnoreCase("0")) {
            return AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(intMaxCropYield);
        } else {
            return "";
        }

    }

    public void setIntMaxCropYield(String intMaxCropYield) {
        this.intMaxCropYield = intMaxCropYield;
    }

    public BigDecimal getIntExpCropPrice() {
        return intExpCropPrice;
    }

    public String getIntExpCropPriceFloat() {
        if (intExpCropPrice == null || intExpCropPrice.toString().equalsIgnoreCase("0.0000")) {
            return "";
        } else {
            Float intExpCropPriceFloat = Float.parseFloat(intExpCropPrice.toString());
            String s1 = DECIMALFORMATTER.format(intExpCropPriceFloat);
            return "$" + AgricultureStandardUtils.commaSeparaterForPrice(s1);
        }
    }

    public String getIntExpCropPriceString() {
        if (intExpCropPrice != null && intExpCropPrice.doubleValue() != 0.0) {
            return "$" + AgricultureStandardUtils.commaSeparatedForPriceWithThreeDecimal(intExpCropPrice.toString());
        } else {
            return "$0.00";
        }

    }

    public void setIntExpCropPrice(BigDecimal intExpCropPrice) {
        this.intExpCropPrice = intExpCropPrice;
    }

    public BigDecimal getIntMinCropPrice() {
        return intMinCropPrice;
    }

    public String getIntMinCropPriceString() {
        if (intMinCropPrice != null && intMinCropPrice.doubleValue() != 0.0) {
            return AgricultureStandardUtils.commaSeparatedForPriceWithThreeDecimal(intMinCropPrice.toString());
        } else {
            return "";
        }

    }

    public String getIntMinCropPriceFloat() {
        if (intMinCropPrice == null || intMinCropPrice.toString().equalsIgnoreCase("0.0000")) {
            return "";
        } else {
            Float intMinCropPriceFloat = Float.parseFloat(intMinCropPrice.toString());
            String s1 = DECIMALFORMATTER.format(intMinCropPriceFloat);
            return "$" + AgricultureStandardUtils.commaSeparaterForPrice(s1);
        }
    }

    public void setIntMinCropPrice(BigDecimal intMinCropPrice) {
        this.intMinCropPrice = intMinCropPrice;
    }

    public BigDecimal getIntMaxCropPrice() {
        return intMaxCropPrice;
    }

    public String getIntMaxCropPriceString() {
        if (intMaxCropPrice != null && intMaxCropPrice.doubleValue() != 0.0) {
            return AgricultureStandardUtils.commaSeparatedForPriceWithThreeDecimal(intMaxCropPrice.toString());
        } else {
            return "";
        }

    }

    public String getIntMaxCropPriceFloat() {
        if (intMaxCropPrice == null || intMaxCropPrice.toString().equalsIgnoreCase("0.0000")) {
            return "";
        } else {
            Float intMaxCropPriceFloat = Float.parseFloat(intMaxCropPrice
                    .toString());
            String s1 = DECIMALFORMATTER.format(intMaxCropPriceFloat);
            return "$" + AgricultureStandardUtils.commaSeparaterForPrice(s1);
        }

    }

    public BigDecimal getSummaryVariableProductionCost() {
        return summaryVariableProductionCost;
    }

    public String getSummaryVariableProductionCostFloat() {
        if (summaryVariableProductionCost == null
                || summaryVariableProductionCost.toString().equalsIgnoreCase(
                "0.0000")) {
            return "";
        } else {
            return String.valueOf(summaryVariableProductionCost.floatValue());
        }
    }

    public void setSummaryVariableProductionCost(BigDecimal summaryVariableProductionCost) {
        this.summaryVariableProductionCost = summaryVariableProductionCost;
    }

    public BigDecimal getCalculatedVariableProductionCost() {
        return calculatedVariableProductionCost;
    }

    public String getCalculatedVariableProductionCostFloat() {
        if (calculatedVariableProductionCost == null
                || calculatedVariableProductionCost.toString()
                .equalsIgnoreCase("0.0000")) {
            return "";
        } else {
            Float calculatedVariableProductionCostFloat = Float
                    .parseFloat(calculatedVariableProductionCost.toString());
            String s1 = DECIMALFORMATTER
                    .format(calculatedVariableProductionCostFloat);
            return "$" + AgricultureStandardUtils.commaSeparaterForPrice(s1);
        }
    }

    public String getCalculatedProfitString() {
        Float variableProductionCostFloat = Float.parseFloat(calculatedVariableProductionCost.toString());
        Float yield = Float.parseFloat(intExpCropYield.toString());
        Float price = Float.parseFloat(intExpCropPrice.toString());
        String s1 = DECIMALFORMATTER.format((yield * price) - variableProductionCostFloat);
        return "$" + AgricultureStandardUtils.commaSeparaterForPrice(s1);
    }

    public String getCalculatedProfitFloat() {
        /**
         * @changed - Abhishek
         * @date - 06-02-2016
         * @desc -for getting random number for monty carlo simulation
         */
        /*Float variableProductionCostFloat = Float.parseFloat(calculatedVariableProductionCost.toString());
        Float yield = Float.parseFloat(intExpCropYield.toString());
        Float price = Float.parseFloat(intExpCropPrice.toString());
        String s1 = DECIMALFORMATTER.format((yield * price) - variableProductionCostFloat);*/
        String profit = "0.0";
        if (montyCarloStatus) {
            Double montyCarloAnalysisProfit = getMontyCarloAnalysisProfit();
            profit = DECIMALFORMATTER.format(montyCarloAnalysisProfit);
        } else {
            Float variableProductionCostFloat = Float.parseFloat(calculatedVariableProductionCost.toString());
            Float yield = Float.parseFloat(intExpCropYield);
            Float price = Float.parseFloat(intExpCropPrice.toString());
            Float profitFloat = (yield * price) - variableProductionCostFloat;
            profit = profitFloat.toString();
        }
        return "$" + AgricultureStandardUtils.commaSeparaterForPrice(profit);
    }

    public Float getCalculatedProfitFloatPrimitiveType() {
        /**
         * @changed - Abhishek
         * @date - 06-02-2016
         * @desc -for getting random number for monty carlo simulation
         */
        /*Float variableProductionCostFloat = Float.parseFloat(calculatedVariableProductionCost.toString());
        Float yield = Float.parseFloat(intExpCropYield.toString());
        Float price = Float.parseFloat(intExpCropPrice.toString());
        PlantingProfitLogger.info("AgricultureStandardUtils.doubleWithOneDecimal((double)(yield * price) - variableProductionCostFloat) = " + AgricultureStandardUtils.doubleWithOneDecimal((double)(yield * price) - variableProductionCostFloat));*/
        Double profit = 0.0;
        if (montyCarloStatus) {
            profit = getMontyCarloAnalysisProfit();

        } else {
            double variableProductionCostFloat = Double.parseDouble(calculatedVariableProductionCost.toString());
            double yield = Double.parseDouble(intExpCropYield);
            double price = Double.parseDouble(intExpCropPrice.toString());
            profit = (yield * price) - variableProductionCostFloat;

        }
        return Float.parseFloat(AgricultureStandardUtils.doubleWithOneDecimal(profit).toString());
    }

    /**
     * @added - Abhishek
     * @date - 06-02-2016
     * @desc - monty carlo analysis for crop profit
     */
    public Double getMontyCarloAnalysisProfit() {

        Double minYield = Double.parseDouble(this.intMinCropYield == null ? "0" : this.intMinCropYield);
        Double maxYield = Double.parseDouble(this.intMaxCropYield == null ? "0" : this.intMaxCropYield);

        Double minPrice = Double.parseDouble(this.intMinCropPrice == null ? "0" : this.intMinCropPrice.toString());
        Double maxPrice = Double.parseDouble(this.intMaxCropPrice == null ? "0" : this.intMaxCropPrice.toString());
        Double varCostProduction = Double.parseDouble(this.calculatedVariableProductionCost == null ? "0" : this.calculatedVariableProductionCost.toString());

        Double profitForCrop = 0.0;

        if ((minYield != 0 && maxYield != 0) && (minPrice != 0 && maxPrice != 0)) {
            for (int i = 0; i < 10000; i++) {
                profitForCrop += getRandomNumberDouble(minYield, maxYield) * getRandomNumberDouble(minPrice, maxPrice) - varCostProduction;
            }
            profitForCrop = profitForCrop / 10000;
        } else {
            profitForCrop = Double.parseDouble(this.intExpCropYield == null ? "0" : this.intExpCropYield)
                    * Double.parseDouble(this.intExpCropPrice == null ? "0" : this.intExpCropPrice.toString())
                    - Double.parseDouble(this.calculatedVariableProductionCost == null ? "0" : this.calculatedVariableProductionCost.toString());
        }

        if (this.additionalIncome != null && this.additionalIncome != 0) {
            profitForCrop += this.additionalIncome;
        }

        PlantingProfitLogger.info("Monty Carlo Simulated Profit : " + profitForCrop);

        return profitForCrop;
    }

    /**
     * @added - Abhishek
     * @date - 06-02-2016
     * @desc -for getting random number for monty carlo simulation
     */
    private Double getRandomNumberDouble(Double minimum, Double maximum) {
        /*PlantingProfitLogger.info("Random Number : " + randomNumber);*/

        return Math.floor(Math.random() * (maximum - minimum + 1)) + minimum;
    }


    public String getCalculatedVariableProductionCostWithoutCommaAndDollar() {
        if (calculatedVariableProductionCost == null
                || calculatedVariableProductionCost.toString()
                .equalsIgnoreCase("0.0000")) {
            return "";
        } else {
            Float calculatedVariableProductionCostFloat = Float
                    .parseFloat(calculatedVariableProductionCost.toString());
            return DECIMALFORMATTER
                    .format(calculatedVariableProductionCostFloat);
        }
    }

    public void setCalculatedVariableProductionCost(BigDecimal calculatedVariableProductionCost) {
        this.calculatedVariableProductionCost = calculatedVariableProductionCost;
    }

    public void setIntMaxCropPrice(BigDecimal intMaxCropPrice) {
        this.intMaxCropPrice = intMaxCropPrice;
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
        return irrigated;
    }

    public void setIrrigated(String irrigated) {
        this.irrigated = irrigated;
    }

    public Date getPreferredPlantingDate() {
        return preferredPlantingDate;
    }

    public String getPreferredPlantingDateStr() {
        if (preferredPlantingDate == null
                || preferredPlantingDate.toString().equalsIgnoreCase(
                "0002-11-30 00:00:00.0")) {
            return "";
        } else {
            return formatter.format(preferredPlantingDate);
        }
    }

    public void setPreferredPlantingDate(Date preferredPlantingDate) {
        this.preferredPlantingDate = preferredPlantingDate;
    }

    public Date getEarlyPlantingDate() {
        return earlyPlantingDate;
    }

    public String getEarlyPlantingDateStr() {
        if (earlyPlantingDate == null
                || earlyPlantingDate.toString().equalsIgnoreCase(
                "0002-11-30 00:00:00.0")) {
            return "";
        } else {
            return formatter.format(earlyPlantingDate);
        }
    }

    public void setEarlyPlantingDate(Date earlyPlantingDate) {
        this.earlyPlantingDate = earlyPlantingDate;
    }

    public Date getLatePlantingDate() {
        return latePlantingDate;
    }

    public String getLatePlantingDateStr() {
        if (latePlantingDate == null
                || latePlantingDate.toString().equalsIgnoreCase(
                "0002-11-30 00:00:00.0")) {
            return "";
        } else {
            return formatter.format(latePlantingDate);
        }

    }

    public void setLatePlantingDate(Date latePlantingDate) {
        this.latePlantingDate = latePlantingDate;
    }

    public String getLengthofSeasonStr() {
        if (lengthofSeason == null
                || lengthofSeason.toString().equalsIgnoreCase("0")) {
            return "";
        } else {
            return lengthofSeason.toString();
        }
    }

    public Integer getLengthofSeason() {
        return lengthofSeason;
    }

    public void setLengthofSeason(Integer lengthofSeason) {
        this.lengthofSeason = lengthofSeason;
    }

    public String getCropNameForId() {
        return cropNameForId.replaceAll("\\s+", "");
    }

    public void setCropNameForId(String cropNameForId) {
        this.cropNameForId = cropNameForId;
    }

    public String getMinimumAcres() {
        if (minimumAcres == null
                || minimumAcres.toString().equalsIgnoreCase("0")) {
            return "";
        } else {
            // return minimumAcres;
            return AgricultureStandardUtils
                    .commaSeparaterForField(minimumAcres);
        }
    }

    public String getMinimumAcresWithoutComma() {
        if (minimumAcres == null
                || minimumAcres.toString().equalsIgnoreCase("0")) {
            return "";
        } else {
            return AgricultureStandardUtils.removeAllCommas(minimumAcres);
        }
    }

    public void setMinimumAcres(String minimumAcres) {
        this.minimumAcres = minimumAcres;
    }

    public String getMaximumAcres() {
        if (maximumAcres == null
                || maximumAcres.toString().equalsIgnoreCase("0")) {
            return "";
        } else {
            // return maximumAcres;
            return AgricultureStandardUtils.commaSeparaterForField(maximumAcres);
        }
    }

    public String getMaximumAcresWithoutComma() {
        if (maximumAcres == null
                || maximumAcres.toString().equalsIgnoreCase("0")) {
            return "";
        } else {
            // return maximumAcres;
            return maximumAcres;
        }
    }

    public void setMaximumAcres(String maximumAcres) {
        this.maximumAcres = maximumAcres;
    }

    public String getForwardPrice() {
        if (forwardPrice == null || forwardPrice.equalsIgnoreCase("0")) {
            return "";
        } else {
            /**
             * @changed - Abhishek
             * @date - 07-12-2015
             */
            Float priceFloat = Float.parseFloat(forwardPrice.replaceAll("\\$", ""));
            String s1 = DECIMALFORMATTER.format(priceFloat);
            return "$" + AgricultureStandardUtils.commaSeparaterForPrice(s1);

        }
    }

    public String getPriceStr() {
        if (forwardPrice == null || forwardPrice.equalsIgnoreCase("0")) {
            return "0";
        } else {
            return AgricultureStandardUtils.commaSeparaterForPrice(forwardPrice);
        }
    }

    public String getPriceSimple() {
        return forwardPrice;
    }

    public void setForwardPrice(String forwardPrice) {
        this.forwardPrice = forwardPrice;
    }

    public String getForwardQuantity() {
        if (forwardQuantity == null || forwardQuantity.toString().equalsIgnoreCase("0")) {
            return "";
        } else {

            /**
             * @changed - Abhishek
             * @date - 01-12-2015
             */

            return forwardQuantity;
//			return AgricultureStandardUtils.commaSeparaterForField(forwardQuantity);
        }
    }

    public String getQuantityStr() {
        if (forwardQuantity == null || forwardQuantity.equalsIgnoreCase("0")) {
            return "";
        } else {
            return AgricultureStandardUtils.commaSeparaterForField(forwardQuantity);
        }

    }

    public void setForwardQuantity(String forwardQuantity) {
        this.forwardQuantity = forwardQuantity;
    }

    public String getFirmchecked() {
        return firmchecked;
    }

    public void setFirmchecked(String firmchecked) {
        this.firmchecked = firmchecked;
    }

    public String getContactIdentifier() {
        if (contactIdentifier == null || contactIdentifier.equalsIgnoreCase("0")) {
            return "";
        } else {
            return contactIdentifier;
        }
    }

    public void setContactIdentifier(String contactIdentifier) {
        this.contactIdentifier = contactIdentifier;
    }

    public Double getForwardUpperLimit() {
        return forwardUpperLimit;
    }

    public String getUpperLimitStr() {
        if (forwardUpperLimit == null || forwardUpperLimit.toString().equalsIgnoreCase("0")
                || forwardUpperLimit.toString().equalsIgnoreCase("0.0")) {
            return "";
        } else {
            return forwardUpperLimit.toString() + "%";
        }
    }

    public void setForwardUpperLimit(Double forwardUpperLimit) {
        this.forwardUpperLimit = forwardUpperLimit;
    }

    public Integer getCropYieldFieldId() {
        return cropYieldFieldId;
    }

    public void setCropYieldFieldId(Integer cropYieldFieldId) {
        this.cropYieldFieldId = cropYieldFieldId;
    }

    public Double getExpCropYieldField() {
        return expCropYieldField;
    }

    public String getExpCropYieldFieldStr() {
        if (expCropYieldField == null || expCropYieldField.toString().equalsIgnoreCase("0.0")) {
            return "";
        } else {
            String s1 = DECIMALFORMATTER.format(expCropYieldField.floatValue());
            return AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(s1);
        }
    }

    public String getExpCropYieldFieldStrWithOneDecimal() {
        if (expCropYieldField == null || expCropYieldField.toString().equalsIgnoreCase("0.0")) {
            return "0.0";
        } else {
            return DECIMALFORMATTER.format(expCropYieldField.floatValue());
        }
    }

    public void setExpCropYieldField(Double expCropYieldField) {
        this.expCropYieldField = expCropYieldField;
    }

    public Double getMinCropYieldField() {
        return minCropYieldField;
    }

    public String getMinCropYieldFieldStr() {
        if (minCropYieldField == null || minCropYieldField == 0.0) {
            return "";
        } else {
            return AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(minCropYieldField.toString());
        }
    }

    public void setMinCropYieldField(Double minCropYieldField) {
        this.minCropYieldField = minCropYieldField;
    }

    public Double getMaxCropYieldField() {
        return maxCropYieldField;
    }

    public String getMaxCropYieldFieldStr() {
        if (maxCropYieldField == null || maxCropYieldField == 0.0) {
            return "";
        } else {
            return AgricultureStandardUtils.commaSeparaterForPriceWithOneDecimal(maxCropYieldField.toString());
        }
    }

    public Double getVarProductionCost() {
        return varProductionCost;
    }

    public String getVarProductionCostStr() {
        if (varProductionCost == null
                || varProductionCost.toString().equalsIgnoreCase("0.0")) {
            return "";
        } else {
            Float varProductionCostFloat = Float.parseFloat(varProductionCost
                    .toString());
            String s1 = DECIMALFORMATTER.format(varProductionCostFloat);
            return "$" + AgricultureStandardUtils.commaSeparaterForPrice(s1);
        }
    }

    public void setVarProductionCost(Double varProductionCost) {
        this.varProductionCost = varProductionCost;
    }

    public void setMaxCropYieldField(Double maxCropYieldField) {
        this.maxCropYieldField = maxCropYieldField;
    }

    public Integer getCropIdForVariences() {
        return cropIdForVariences;
    }

    public void setCropIdForVariences(Integer cropIdForVariences) {
        this.cropIdForVariences = cropIdForVariences;
    }

    public Double getForwardAcres() {
        return forwardAcres;
    }

    public String getAcresStr() {
        if (forwardAcres == null || forwardAcres.toString().equalsIgnoreCase("0.0")) {
            return "";
        } else {
            // return forwardAcres.toString();
            return AgricultureStandardUtils.commaSeparaterForField(forwardAcres.toString());
        }
    }

    public void setForwardAcres(Double forwardAcres) {
        this.forwardAcres = forwardAcres;
    }

    public Integer getFieldIdForVariances() {
        return fieldIdForVariances;
    }

    public void setFieldIdForVariances(Integer fieldIdForVariances) {
        this.fieldIdForVariances = fieldIdForVariances;
    }

    public Set<CropsGroup> getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(Set<CropsGroup> groupSet) {
        this.groupSet = groupSet;
    }

    public Boolean getProposedchecked() {
        return proposedchecked;
    }

    public void setProposedchecked(Boolean proposedchecked) {
        this.proposedchecked = proposedchecked;
    }

    public String getFieldNameForVariances() {
        return fieldNameForVariances;
    }

    public void setFieldNameForVariances(String fieldNameForVariances) {
        this.fieldNameForVariances = fieldNameForVariances;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public Double getGovernmentPayments() {
        return governmentPayments;
    }

    public String getGovernmentPaymentsString() {
//        return "$" + AgricultureStandardUtils.commaSeparaterForDoublePrice(governmentPayments);
        if (governmentPayments != null && governmentPayments != 0.0) {
            return "$" + AgricultureStandardUtils.commaSeparaterForDoublePrice(governmentPayments);
        } else {
            return "";
        }
    }

    public void setGovernmentPayments(Double governmentPayments) {
        this.governmentPayments = governmentPayments;
    }

    public Double getCoProductsPrice() {
        return coProductsPrice;
    }

    public String getCoProductsPriceString() {
//        return "$" + AgricultureStandardUtils.commaSeparaterForDoublePrice(coProductsPrice);
        if (coProductsPrice != null && coProductsPrice != 0.0) {
            return "$" + AgricultureStandardUtils.commaSeparaterForDoublePrice(coProductsPrice);
        } else {
            return "";
        }
    }

    public void setCoProductsPrice(Double coProductsPrice) {
        this.coProductsPrice = coProductsPrice;
    }

    public Double getAdditionalVariableProductionCost() {
        return additionalVariableProductionCost;
    }

    public String getAdditionalVariableProductionCostString() {
        if (additionalVariableProductionCost != null && additionalVariableProductionCost != 0.0) {
            return "$" + AgricultureStandardUtils.commaSeparaterForDoublePrice(additionalVariableProductionCost);
        } else {
            return "";
        }
    }

    public void setAdditionalVariableProductionCost(Double additionalVariableProductionCost) {
        this.additionalVariableProductionCost = additionalVariableProductionCost;
    }

    public Double getAdditionalIncome() {
        if (additionalIncome != null) {
            return additionalIncome;
        } else {
            return 0.0;
        }
    }

    public String getAdditionalIncomeString() {
        if (additionalIncome != null && additionalIncome != 0.0) {
            return "$" + AgricultureStandardUtils.commaSeparaterForDoublePrice(additionalIncome);
        } else {
            return "";
        }
    }

    public void setAdditionalIncome(Double additionalIncome) {
        this.additionalIncome = additionalIncome;
    }

    @Override
    public CropTypeView clone() throws CloneNotSupportedException {
        return (CropTypeView) super.clone();
    }
}
