package com.decipher.view.form.farmDetails;

import java.io.Serializable;
import java.math.BigDecimal;

import com.decipher.agriculture.data.farm.VariableCropProductionComponentUnitPrices;

public class VariableCropProductionComponentUnitPricesView implements Serializable {
    private Integer id;
    private BigDecimal priceSeed;
    private BigDecimal priceHerbicide;
    private BigDecimal priceInsecticide;
    private BigDecimal priceFungicide;
    private BigDecimal priceFertilizer;
    private BigDecimal priceMicro_Nutrients;
    private BigDecimal priceLabor;
    private BigDecimal priceEquipment;
    private BigDecimal priceIrrigation;
    private BigDecimal priceRent;
    private BigDecimal priceProfessionalServices;
    private BigDecimal priceCropInsurance;
    private BigDecimal priceFinancing;
    private BigDecimal priceOther;

    public VariableCropProductionComponentUnitPricesView() {

    }

    public VariableCropProductionComponentUnitPricesView(VariableCropProductionComponentUnitPrices variableCropProductionComponentUnitPrices) {
        this.id = variableCropProductionComponentUnitPrices.getId();
        this.priceSeed = variableCropProductionComponentUnitPrices.getPriceSeed();
        this.priceHerbicide = variableCropProductionComponentUnitPrices.getPriceHerbicide();
        this.priceInsecticide = variableCropProductionComponentUnitPrices.getPriceInsecticide();
        this.priceFungicide = variableCropProductionComponentUnitPrices.getPriceFungicide();
        this.priceFertilizer = variableCropProductionComponentUnitPrices.getPriceFertilizer();
        this.priceMicro_Nutrients = variableCropProductionComponentUnitPrices.getPriceMicro_Nutrients();
        this.priceLabor = variableCropProductionComponentUnitPrices.getPriceLabor();
        this.priceEquipment = variableCropProductionComponentUnitPrices.getPriceEquipment();
        this.priceIrrigation = variableCropProductionComponentUnitPrices.getPriceIrrigation();
        this.priceRent = variableCropProductionComponentUnitPrices.getPriceRent();
        this.priceProfessionalServices = variableCropProductionComponentUnitPrices.getPriceProfessionalServices();
        this.priceCropInsurance = variableCropProductionComponentUnitPrices.getPriceCropInsurance();
        this.priceFinancing = variableCropProductionComponentUnitPrices.getPriceFinancing();
        this.priceOther = variableCropProductionComponentUnitPrices.getPriceOther();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPriceSeed() {
        return priceSeed;
    }

    public void setPriceSeed(BigDecimal priceSeed) {
        this.priceSeed = priceSeed;
    }

    public BigDecimal getPriceHerbicide() {
        return priceHerbicide;
    }

    public void setPriceHerbicide(BigDecimal priceHerbicide) {
        this.priceHerbicide = priceHerbicide;
    }

    public BigDecimal getPriceInsecticide() {
        return priceInsecticide;
    }

    public void setPriceInsecticide(BigDecimal priceInsecticide) {
        this.priceInsecticide = priceInsecticide;
    }

    public BigDecimal getPriceFungicide() {
        return priceFungicide;
    }

    public void setPriceFungicide(BigDecimal priceFungicide) {
        this.priceFungicide = priceFungicide;
    }

    public BigDecimal getPriceFertilizer() {
        return priceFertilizer;
    }

    public void setPriceFertilizer(BigDecimal priceFertilizer) {
        this.priceFertilizer = priceFertilizer;
    }

    public BigDecimal getPriceMicro_Nutrients() {
        return priceMicro_Nutrients;
    }

    public void setPriceMicro_Nutrients(BigDecimal priceMicro_Nutrients) {
        this.priceMicro_Nutrients = priceMicro_Nutrients;
    }

    public BigDecimal getPriceLabor() {
        return priceLabor;
    }

    public void setPriceLabor(BigDecimal priceLabor) {
        this.priceLabor = priceLabor;
    }

    public BigDecimal getPriceEquipment() {
        return priceEquipment;
    }

    public void setPriceEquipment(BigDecimal priceEquipment) {
        this.priceEquipment = priceEquipment;
    }

    public BigDecimal getPriceIrrigation() {
        return priceIrrigation;
    }

    public void setPriceIrrigation(BigDecimal priceIrrigation) {
        this.priceIrrigation = priceIrrigation;
    }

    public BigDecimal getPriceRent() {
        return priceRent;
    }

    public void setPriceRent(BigDecimal priceRent) {
        this.priceRent = priceRent;
    }

    public BigDecimal getPriceProfessionalServices() {
        return priceProfessionalServices;
    }

    public void setPriceProfessionalServices(BigDecimal priceProfessionalServices) {
        this.priceProfessionalServices = priceProfessionalServices;
    }

    public BigDecimal getPriceCropInsurance() {
        return priceCropInsurance;
    }

    public void setPriceCropInsurance(BigDecimal priceCropInsurance) {
        this.priceCropInsurance = priceCropInsurance;
    }

    public BigDecimal getPriceFinancing() {
        return priceFinancing;
    }

    public void setPriceFinancing(BigDecimal priceFinancing) {
        this.priceFinancing = priceFinancing;
    }

    public BigDecimal getPriceOther() {
        return priceOther;
    }

    public void setPriceOther(BigDecimal priceOther) {
        this.priceOther = priceOther;
    }
}
