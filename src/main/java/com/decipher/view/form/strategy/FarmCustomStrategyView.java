package com.decipher.view.form.strategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.decipher.agriculture.data.strategy.FarmCustomStrategyForCrop;
import com.decipher.agriculture.data.strategy.FarmCustomStrategyForGroup;
import com.decipher.agriculture.data.strategy.FarmCustomStrategyForResource;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FarmCustomStrategyView implements Comparable<FarmCustomStrategyView>, Cloneable, Serializable {
    private Integer id;
    private String strategyName;
    private boolean strategyForCrop;
    private boolean strategyForResource;
    private Double potentialProfit;
    private Double totalAcreage;
    private Set<FarmCustomStrategyForCropView> customStrategyForCropsViews;
    private Set<FarmCustomStrategyForResourceView> customStrategyForResourcesView;
    private Set<FarmCustomStrategyForGroupView> customStrategyForGroupsView;

    /**
     * @added - Abhishek
     * @date - 01-02-2016
     * @desc - for identifying strategy farm specific
     */
    private Integer farmID;
    @JsonIgnore
    private FarmCustomStrategy farmCustomStrategy;

    public FarmCustomStrategyView() {

    }

    public FarmCustomStrategyView(FarmCustomStrategy farmCustomStrategy) {
        this.id = farmCustomStrategy.getId();
        this.strategyName = farmCustomStrategy.getStrategyName();
        this.strategyForCrop = farmCustomStrategy.isStrategyForCrop();
        this.strategyForResource = farmCustomStrategy.isStrategyForResourse();

        if (farmCustomStrategy.getCustomStrategyForCrops().size() > 0) {
            this.customStrategyForCropsViews = new HashSet<FarmCustomStrategyForCropView>();
            for (FarmCustomStrategyForCrop customStrategyForCrop : farmCustomStrategy.getCustomStrategyForCrops()) {
                if (customStrategyForCrop != null) {
                    this.customStrategyForCropsViews.add(new FarmCustomStrategyForCropView(customStrategyForCrop));
                }
            }
        }

        if (farmCustomStrategy.getCustomStrategyForResourses().size() > 0) {
            this.customStrategyForResourcesView = new HashSet<FarmCustomStrategyForResourceView>();
            for (FarmCustomStrategyForResource farmCustomStrategyForResource : farmCustomStrategy.getCustomStrategyForResourses()) {
                if (farmCustomStrategyForResource != null) {
                    this.customStrategyForResourcesView.add(new FarmCustomStrategyForResourceView(farmCustomStrategyForResource));
                }
            }
        }
        if (farmCustomStrategy.getCustomStrategyForGroup().size() > 0) {
            this.customStrategyForGroupsView = new HashSet<FarmCustomStrategyForGroupView>();
            for (FarmCustomStrategyForGroup customStrategyForGroup : farmCustomStrategy.getCustomStrategyForGroup()) {
                if (customStrategyForGroup != null) {
                    this.customStrategyForGroupsView.add(new FarmCustomStrategyForGroupView(customStrategyForGroup));
                }
            }
        }

        /**
         * @added - Abhishek
         * @date - 01-02-2016
         * @desc - for identifying strategy farm specific
         */
        this.farmID = farmCustomStrategy.getFarm().getFarmId();
        this.farmCustomStrategy = farmCustomStrategy;

        /*this.potentialProfit = farmCustomStrategy.getPotentialProfit();
        this.totalAcreage = farmCustomStrategy.getTotalAcreage();*/


    }

    public Integer getFarmID() {
        return farmID;
    }

    public void setFarmID(Integer farmID) {
        this.farmID = farmID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public boolean isStrategyForCrop() {
        return strategyForCrop;
    }

    public void setStrategyForCrop(boolean strategyForCrop) {
        this.strategyForCrop = strategyForCrop;
    }

    public boolean isStrategyForResource() {
        return strategyForResource;
    }

    public void setStrategyForResource(boolean strategyForResource) {
        this.strategyForResource = strategyForResource;
    }

    public Set<FarmCustomStrategyForCropView> getCustomStrategyForCropsViews() {
        return customStrategyForCropsViews;
    }

    public void setCustomStrategyForCropsViews(Set<FarmCustomStrategyForCropView> customStrategyForCropsViews) {
        this.customStrategyForCropsViews = customStrategyForCropsViews;
    }

    public Set<FarmCustomStrategyForResourceView> getCustomStrategyForResourcesView() {
        return customStrategyForResourcesView;
    }

    public void setCustomStrategyForResourcesView(Set<FarmCustomStrategyForResourceView> customStrategyForResourcesView) {
        this.customStrategyForResourcesView = customStrategyForResourcesView;
    }

    public Set<FarmCustomStrategyForGroupView> getCustomStrategyForGroupsView() {
        return customStrategyForGroupsView;
    }

    public void setCustomStrategyForGroupsView(Set<FarmCustomStrategyForGroupView> customStrategyForGroupsView) {
        this.customStrategyForGroupsView = customStrategyForGroupsView;
    }


    public Double getPotentialProfit() {
        return potentialProfit;
    }

    public void setPotentialProfit(Double potentialProfit) {
        this.potentialProfit = potentialProfit;
    }

    public Double getTotalAcreage() {
        return totalAcreage;
    }

    public void setTotalAcreage(Double totalAcreage) {
        this.totalAcreage = totalAcreage;
    }

    public FarmCustomStrategy getFarmCustomStrategy() {
        return farmCustomStrategy;
    }

    public void setFarmCustomStrategy(FarmCustomStrategy farmCustomStrategy) {
        this.farmCustomStrategy = farmCustomStrategy;
    }

    @Override
    public int compareTo(FarmCustomStrategyView farmCustomStrategyView) {
        if (this.getId().equals(farmCustomStrategyView.getId())) {
            return 0;
        } else if (this.getId() > farmCustomStrategyView.getId()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public FarmCustomStrategyView clone() throws CloneNotSupportedException {
        return (FarmCustomStrategyView)super.clone();
    }
}
