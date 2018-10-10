package com.decipher.view.form.farmDetails;

import java.io.Serializable;
import java.math.BigDecimal;

import com.decipher.agriculture.data.farm.InternallyCalculatedVariableCropProductionCostComponents;

public class InternallyCalculatedVariableCropProductionCostComponentsView implements Serializable {
    private Integer id;
    private BigDecimal calculatedVarProductionCost;
    private BigDecimal costSeed;
    private BigDecimal costHerbicide;
    private BigDecimal costInsecticide;
    private BigDecimal costFungicide;
    private BigDecimal costFertilizer;
    private BigDecimal costMicro_Nutrients;
    private BigDecimal costLabor;
    private BigDecimal costEquipment;
    private BigDecimal costIrrigation;
    private BigDecimal costRent;
    private BigDecimal costProfessionalServices;
    private BigDecimal costCropInsurance;
    private BigDecimal costFinancing;
    private BigDecimal costOther;

    public InternallyCalculatedVariableCropProductionCostComponentsView() {

    }

    public InternallyCalculatedVariableCropProductionCostComponentsView(
            InternallyCalculatedVariableCropProductionCostComponents internallyCalculatedVariableCropProductionCostComponents) {
        this.id = internallyCalculatedVariableCropProductionCostComponents
                .getId();
        this.calculatedVarProductionCost = internallyCalculatedVariableCropProductionCostComponents
                .getCalculatedVarProductionCost();
        this.costSeed = internallyCalculatedVariableCropProductionCostComponents
                .getCostSeed();
        this.costHerbicide = internallyCalculatedVariableCropProductionCostComponents
                .getCostHerbicide();
        this.costInsecticide = internallyCalculatedVariableCropProductionCostComponents
                .getCostInsecticide();
        this.costFungicide = internallyCalculatedVariableCropProductionCostComponents
                .getCostFungicide();
        this.costFertilizer = internallyCalculatedVariableCropProductionCostComponents
                .getCostFertilizer();
        this.costMicro_Nutrients = internallyCalculatedVariableCropProductionCostComponents
                .getCostMicro_Nutrients();
        this.costLabor = internallyCalculatedVariableCropProductionCostComponents
                .getCostLabor();
        this.costEquipment = internallyCalculatedVariableCropProductionCostComponents
                .getCostEquipment();
        this.costIrrigation = internallyCalculatedVariableCropProductionCostComponents
                .getCostIrrigation();
        this.costRent = internallyCalculatedVariableCropProductionCostComponents
                .getCostRent();
        this.costProfessionalServices = internallyCalculatedVariableCropProductionCostComponents
                .getCostProfessionalServices();
        this.costCropInsurance = internallyCalculatedVariableCropProductionCostComponents
                .getCostCropInsurance();
        this.costFinancing = internallyCalculatedVariableCropProductionCostComponents
                .getCostFinancing();
        this.costOther = internallyCalculatedVariableCropProductionCostComponents
                .getCostOther();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCalculatedVarProductionCost() {
        return calculatedVarProductionCost;
    }

    public void setCalculatedVarProductionCost(
            BigDecimal calculatedVarProductionCost) {
        this.calculatedVarProductionCost = calculatedVarProductionCost;
    }

    public BigDecimal getCostSeed() {
        return costSeed;
    }

    public void setCostSeed(BigDecimal costSeed) {
        this.costSeed = costSeed;
    }

    public BigDecimal getCostHerbicide() {
        return costHerbicide;
    }

    public void setCostHerbicide(BigDecimal costHerbicide) {
        this.costHerbicide = costHerbicide;
    }

    public BigDecimal getCostInsecticide() {
        return costInsecticide;
    }

    public void setCostInsecticide(BigDecimal costInsecticide) {
        this.costInsecticide = costInsecticide;
    }

    public BigDecimal getCostFungicide() {
        return costFungicide;
    }

    public void setCostFungicide(BigDecimal costFungicide) {
        this.costFungicide = costFungicide;
    }

    public BigDecimal getCostFertilizer() {
        return costFertilizer;
    }

    public void setCostFertilizer(BigDecimal costFertilizer) {
        this.costFertilizer = costFertilizer;
    }

    public BigDecimal getCostMicro_Nutrients() {
        return costMicro_Nutrients;
    }

    public void setCostMicro_Nutrients(BigDecimal costMicro_Nutrients) {
        this.costMicro_Nutrients = costMicro_Nutrients;
    }

    public BigDecimal getCostLabor() {
        return costLabor;
    }

    public void setCostLabor(BigDecimal costLabor) {
        this.costLabor = costLabor;
    }

    public BigDecimal getCostEquipment() {
        return costEquipment;
    }

    public void setCostEquipment(BigDecimal costEquipment) {
        this.costEquipment = costEquipment;
    }

    public BigDecimal getCostIrrigation() {
        return costIrrigation;
    }

    public void setCostIrrigation(BigDecimal costIrrigation) {
        this.costIrrigation = costIrrigation;
    }

    public BigDecimal getCostRent() {
        return costRent;
    }

    public void setCostRent(BigDecimal costRent) {
        this.costRent = costRent;
    }

    public BigDecimal getCostProfessionalServices() {
        return costProfessionalServices;
    }

    public void setCostProfessionalServices(BigDecimal costProfessionalServices) {
        this.costProfessionalServices = costProfessionalServices;
    }

    public BigDecimal getCostCropInsurance() {
        return costCropInsurance;
    }

    public void setCostCropInsurance(BigDecimal costCropInsurance) {
        this.costCropInsurance = costCropInsurance;
    }

    public BigDecimal getCostFinancing() {
        return costFinancing;
    }

    public void setCostFinancing(BigDecimal costFinancing) {
        this.costFinancing = costFinancing;
    }

    public BigDecimal getCostOther() {
        return costOther;
    }

    public void setCostOther(BigDecimal costOther) {
        this.costOther = costOther;
    }
}
