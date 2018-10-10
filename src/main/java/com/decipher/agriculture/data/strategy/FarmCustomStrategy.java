package com.decipher.agriculture.data.strategy;

import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.scenario.FarmStrategyScenario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "FarmCustomStrategy")
@Table(name = "FARM_CUSTOM_STRATEGY", uniqueConstraints = @UniqueConstraint(columnNames = "FARM_CUSTOM_STRATEGY_ID"))
public class FarmCustomStrategy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FARM_CUSTOM_STRATEGY_ID")
    private Integer id;
    @Column(name = "STRATEGY_NAME")
    private String strategyName;
    @Column(name = "STRATEGY_FOR_CROP")
    private Boolean strategyForCrop;
    @Column(name = "STRATEGY_FOR_RESOURSE")
    private Boolean strategyForResourse;

    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FARM_ID")
    @JsonIgnore
    private Farm farm;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "FARM_INFO_ID")
    @JsonIgnore
    private FarmInfo farmInfo;

    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - for Removing lazy load exception
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)*/
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)*/
    @JoinColumn(name = "FARM_CUSTOM_STRATEGY_ID", referencedColumnName = "FARM_CUSTOM_STRATEGY_ID")
    private Set<FarmStrategyScenario> farmStrategyScenarios;

    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - for Removing lazy load exception
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	/*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)*/
    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)*/
    @JoinColumn(name = "FARM_CUSTOM_STRATEGY_ID", referencedColumnName = "FARM_CUSTOM_STRATEGY_ID")
    private Set<FarmCustomStrategyForResource> customStrategyForResourses = new HashSet<FarmCustomStrategyForResource>();

    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - for Removing lazy load exception
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	/*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)*/
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)*/
    @JoinColumn(name = "FARM_CUSTOM_STRATEGY_ID", referencedColumnName = "FARM_CUSTOM_STRATEGY_ID")
    private Set<FarmCustomStrategyForCrop> customStrategyForCrops = new HashSet<FarmCustomStrategyForCrop>();

    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - for Removing lazy load exception
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	/*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)*/
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)*/
    @JoinColumn(name = "FARM_CUSTOM_STRATEGY_ID", referencedColumnName = "FARM_CUSTOM_STRATEGY_ID")
    private Set<FarmCustomStrategyForGroup> customStrategyForGroup = new HashSet<FarmCustomStrategyForGroup>();

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

    public Boolean isStrategyForCrop() {
        return strategyForCrop;
    }

    public void setStrategyForCrop(Boolean strategyForCrop) {
        this.strategyForCrop = strategyForCrop;
    }

    public Boolean isStrategyForResourse() {
        return strategyForResourse;
    }

    public void setStrategyForResourse(Boolean strategyForResourse) {
        this.strategyForResourse = strategyForResourse;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public FarmInfo getFarmInfo() {
        return farmInfo;
    }

    public void setFarmInfo(FarmInfo farmInfo) {
        this.farmInfo = farmInfo;
    }

    public Set<FarmStrategyScenario> getFarmStrategyScenarios() {
        return farmStrategyScenarios;
    }

    public void setFarmStrategyScenarios(Set<FarmStrategyScenario> farmStrategyScenarios) {
        this.farmStrategyScenarios = farmStrategyScenarios;
    }

    public Set<FarmCustomStrategyForResource> getCustomStrategyForResourses() {
        return customStrategyForResourses;
    }

    public void setCustomStrategyForResourses(Set<FarmCustomStrategyForResource> customStrategyForResourses) {
        this.customStrategyForResourses = customStrategyForResourses;
    }

    public Set<FarmCustomStrategyForCrop> getCustomStrategyForCrops() {
        return customStrategyForCrops;
    }

    public void setCustomStrategyForCrops(Set<FarmCustomStrategyForCrop> customStrategyForCrops) {
        this.customStrategyForCrops = customStrategyForCrops;
    }

    public Set<FarmCustomStrategyForGroup> getCustomStrategyForGroup() {
        return customStrategyForGroup;
    }

    public void setCustomStrategyForGroup(Set<FarmCustomStrategyForGroup> customStrategyForGroup) {
        this.customStrategyForGroup = customStrategyForGroup;
    }
}
