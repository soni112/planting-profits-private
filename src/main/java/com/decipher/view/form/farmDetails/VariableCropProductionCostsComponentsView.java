package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponents;

import java.io.Serializable;

public class VariableCropProductionCostsComponentsView implements Serializable {

    private Integer id;
    private String seed;
    private String herbicide;
    private String insecticide;
    private String fungicide;
    private String fertilizer;
    private String micro_Nutrients;
    private String labor;
    private String equipment;
    private String irrigation;
    private String rent;
    private String professionalServices;
    private String cropInsurance;
    private String financing;
    private String other;

    public VariableCropProductionCostsComponentsView() {

    }

    public VariableCropProductionCostsComponentsView(VariableCropProductionCostsComponents variableCropProductionCostsComponents) {
        this.id = variableCropProductionCostsComponents.getId();
        this.seed = variableCropProductionCostsComponents.getSeed();
        this.herbicide = variableCropProductionCostsComponents.getHerbicide();
        this.insecticide = variableCropProductionCostsComponents.getInsecticide();
        this.fungicide = variableCropProductionCostsComponents.getFungicide();
        this.fertilizer = variableCropProductionCostsComponents.getFertilizer();
        this.micro_Nutrients = variableCropProductionCostsComponents.getMicro_Nutrients();
        this.labor = variableCropProductionCostsComponents.getLabor();
        this.equipment = variableCropProductionCostsComponents.getEquipment();
        this.irrigation = variableCropProductionCostsComponents.getIrrigation();
        this.rent = variableCropProductionCostsComponents.getRent();
        this.professionalServices = variableCropProductionCostsComponents.getProfessionalServices();
        this.cropInsurance = variableCropProductionCostsComponents.getCropInsurance();
        this.financing = variableCropProductionCostsComponents.getFinancing();
        this.other = variableCropProductionCostsComponents.getOther();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getHerbicide() {
        return herbicide;
    }

    public void setHerbicide(String herbicide) {
        this.herbicide = herbicide;
    }

    public String getInsecticide() {
        return insecticide;
    }

    public void setInsecticide(String insecticide) {
        this.insecticide = insecticide;
    }

    public String getFungicide() {
        return fungicide;
    }

    public void setFungicide(String fungicide) {
        this.fungicide = fungicide;
    }

    public String getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getMicro_Nutrients() {
        return micro_Nutrients;
    }

    public void setMicro_Nutrients(String micro_Nutrients) {
        this.micro_Nutrients = micro_Nutrients;
    }

    public String getLabor() {
        return labor;
    }

    public void setLabor(String labor) {
        this.labor = labor;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getProfessionalServices() {
        return professionalServices;
    }

    public void setProfessionalServices(String professionalServices) {
        this.professionalServices = professionalServices;
    }

    public String getCropInsurance() {
        return cropInsurance;
    }

    public void setCropInsurance(String cropInsurance) {
        this.cropInsurance = cropInsurance;
    }

    public String getFinancing() {
        return financing;
    }

    public void setFinancing(String financing) {
        this.financing = financing;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
