package com.decipher.agriculture.data.scenario;

import com.decipher.agriculture.data.farm.CropType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Entity(name = "FarmStrategyScenarioCropSpecific")
@Table(name = "FARM_STRATEGY_SCENARIO_CROP_SPECIFIC", uniqueConstraints = @UniqueConstraint(columnNames = "SCENARIO_CROP_SPECIFIC_ID"))
public class FarmStrategyScenarioCropSpecific {
    @Id
    @GeneratedValue
    @Column(name = "SCENARIO_CROP_SPECIFIC_ID")
    private Integer id;
    @Column(name = "CROP_PRICE")
    private Integer price;
    @Column(name = "CROP_YIELDS")
    private Integer yields;
    @Column(name = "CROP_PROD_COST")
    private Integer prodCost;

    @ManyToOne
    @JoinColumn(name = "SCENARIO_ID")
    @JsonIgnore
    private FarmStrategyScenario farmStrategyScenario;

    @OneToOne
    @JoinColumn(name = "CROP_TYPE_ID")
    @JsonIgnore
    private CropType cropType;

    public FarmStrategyScenario getFarmStrategyScenario() {
        return farmStrategyScenario;
    }

    public void setFarmStrategyScenario(FarmStrategyScenario farmStrategyScenario) {
        this.farmStrategyScenario = farmStrategyScenario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getYields() {
        return yields;
    }

    public void setYields(Integer yields) {
        this.yields = yields;
    }

    public Integer getProdCost() {
        return prodCost;
    }

    public void setProdCost(Integer prodCost) {
        this.prodCost = prodCost;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }
}
