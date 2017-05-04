package com.decipher.view.form.scenario;

import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.decipher.agriculture.data.scenario.FarmStrategyScenario;
import com.decipher.agriculture.data.scenario.FarmStrategyScenarioCropSpecific;

import java.util.Set;

/**
 * Created by raja on 12/25/15.
 */
public class FarmStrategyScenarioView implements Comparable<FarmStrategyScenarioView>{
    private Integer scenarioId;
    private String scenarioName;
    private Integer globalCropPrice;
    private Integer globalCropYields;
    private Integer globalCropProdCost;

    private String globalComment;
    private String cropSpecificComment;

    private Set<FarmStrategyScenarioCropSpecific> cropSpecifics;
    private FarmCustomStrategy farmCustomStrategy;

    public FarmStrategyScenarioView(FarmStrategyScenario farmStrategyScenario) {
        this.scenarioId = farmStrategyScenario.getScenarioId();
        this.scenarioName = farmStrategyScenario.getScenarioName();
        this.globalCropPrice = farmStrategyScenario.getGlobalCropPrice();
        this.globalCropYields = farmStrategyScenario.getGlobalCropYields();
        this.globalCropProdCost = farmStrategyScenario.getGlobalCropProdCost();
        this.cropSpecifics = farmStrategyScenario.getCropSpecifics();
        this.farmCustomStrategy = farmStrategyScenario.getFarmCustomStrategy();

        this.globalComment = farmStrategyScenario.getGlobalCropComment();
        this.cropSpecificComment = farmStrategyScenario.getCropScpecificComment();


    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public Set<FarmStrategyScenarioCropSpecific> getCropSpecifics() {
        return cropSpecifics;
    }

    public void setCropSpecifics(Set<FarmStrategyScenarioCropSpecific> cropSpecifics) {
        this.cropSpecifics = cropSpecifics;
    }

    public Integer getGlobalCropPrice() {
        return globalCropPrice;
    }

    public void setGlobalCropPrice(Integer globalCropPrice) {
        this.globalCropPrice = globalCropPrice;
    }

    public Integer getGlobalCropYields() {
        return globalCropYields;
    }

    public void setGlobalCropYields(Integer globalCropYields) {
        this.globalCropYields = globalCropYields;
    }

    public Integer getGlobalCropProdCost() {
        return globalCropProdCost;
    }

    public void setGlobalCropProdCost(Integer globalCropProdCost) {
        this.globalCropProdCost = globalCropProdCost;
    }

    public Integer getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(Integer scenarioId) {
        this.scenarioId = scenarioId;
    }

    public FarmCustomStrategy getFarmCustomStrategy() {
        return farmCustomStrategy;
    }

    public void setFarmCustomStrategy(FarmCustomStrategy farmCustomStrategy) {
        this.farmCustomStrategy = farmCustomStrategy;
    }

    public String getGlobalComment() {
        return globalComment;
    }

    public void setGlobalComment(String globalComment) {
        this.globalComment = globalComment;
    }

    public String getCropSpecificComment() {
        return cropSpecificComment;
    }

    public void setCropSpecificComment(String cropSpecificComment) {
        this.cropSpecificComment = cropSpecificComment;
    }

    @Override
    public int compareTo(FarmStrategyScenarioView farmStrategyScenarioView) {
        if(this.getScenarioId() == farmStrategyScenarioView.getScenarioId()){
            return 0;
        } else if(this.getScenarioId() > farmStrategyScenarioView.getScenarioId()){
            return 1;
        } else{
            return -1;
        }
    }
}
