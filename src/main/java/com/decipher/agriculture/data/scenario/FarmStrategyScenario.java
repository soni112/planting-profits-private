package com.decipher.agriculture.data.scenario;

import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by raja on 12/25/15.
 */

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "FarmStrategyScenario")
@Table(name = "FARM_STRATEGY_SCENARIO", uniqueConstraints = @UniqueConstraint(columnNames = "SCENARIO_ID"))
public class FarmStrategyScenario {
    @Id
    @GeneratedValue
    @Column(name = "SCENARIO_ID")
    private Integer scenarioId;
    @Column(name = "SCENARIO_NAME")
    private String scenarioName;
    @Column(name = "GLOBAL_CROP_PRICE")
    private Integer globalCropPrice;
    @Column(name = "GLOBAL_CROP_YIELDS")
    private Integer globalCropYields;
    @Column(name = "GLOBAL_CROP_PROD_COST")
    private Integer globalCropProdCost;

    @Column(name = "GLOBAL_CROP_COMMENT")
    @Type(type="org.hibernate.type.StringClobType")
    private String globalCropComment;
    @Column(name = "CROP_SPECIFIC_COMMENT")
    @Type(type="org.hibernate.type.StringClobType")
    private String cropScpecificComment;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "SCENARIO_ID", referencedColumnName = "SCENARIO_ID")
    private Set<FarmStrategyScenarioCropSpecific> cropSpecifics;

    @ManyToOne
    @JoinColumn(name = "FARM_CUSTOM_STRATEGY_ID")
    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private FarmCustomStrategy farmCustomStrategy;

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

    public String getGlobalCropComment() {
        return globalCropComment;
    }

    public void setGlobalCropComment(String globalCropComment) {
        this.globalCropComment = globalCropComment;
    }

    public String getCropScpecificComment() {
        return cropScpecificComment;
    }

    public void setCropScpecificComment(String cropScpecificComment) {
        this.cropScpecificComment = cropScpecificComment;
    }
}
