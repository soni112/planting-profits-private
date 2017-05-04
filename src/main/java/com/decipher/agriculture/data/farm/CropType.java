package com.decipher.agriculture.data.farm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "CropType")
@Table(name = "CROP_TYPE")
public class CropType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CROP_TYPE_ID")
    private Integer id;
    @Column(name = "CROP_TYPE_NAME")
    private String cropTypeName;
    @Column(name = "CROP_NAME")
    private String cropName;
    @Column(columnDefinition = "TINYINT", name = "CROP_SELECTED")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean selected;


    @ManyToOne
    @JoinColumn(name = "FARM_INFO_ID")
    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private FarmInfo farmInfo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cropType")
    /* @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID") */
    private CropUnitOfMeasure cropUnitOfMeasure;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cropType")
	/* @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID") */
    private InternalCropYieldInfo cropYieldInfo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cropType")
	/* @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID") */
    private InternalVariableCropProductionCosts costsCropProductionCosts;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cropType")
	/* @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID") */
    private SummaryCropInfo cropInfo;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cropType")
	/* @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID") */
    private OptionalCropPlantingDates cropPlantingDates;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID")
    private Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariances = new HashSet<CropResourceUsageFieldVariances>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cropType")
	/* @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID") */
    private InternalCropPricesInfo cropPricesInfo;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID")
    private Set<OptionalCropProductionCostsDetails> optCropCost = new HashSet<OptionalCropProductionCostsDetails>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cropType")
	/* @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID") */
    private CropLimit cropLimit;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cropType")
	/* @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID") */
    private CropForwardSales cropForwardSales;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID")
    private Set<CropFieldChocies> chocies = new HashSet<CropFieldChocies>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cropType")
	/* @JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID") */
    private CropYieldFieldVariances cropYieldFieldVariances;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "cropTypes")
    private Set<CropsGroup> cropsGroup = new HashSet<CropsGroup>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cropType")
    private AdditionalCropIncome additionalCropIncome;

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

    public FarmInfo getFarmInfo() {
        return farmInfo;
    }

    public void setFarmInfo(FarmInfo farmInfo) {
        this.farmInfo = farmInfo;
    }

    public CropUnitOfMeasure getCropUnitOfMeasure() {
        return cropUnitOfMeasure;
    }

    public void setCropUnitOfMeasure(CropUnitOfMeasure cropUnitOfMeasure) {
        this.cropUnitOfMeasure = cropUnitOfMeasure;
    }

    public InternalCropYieldInfo getCropYieldInfo() {
        return cropYieldInfo;
    }

    public void setCropYieldInfo(InternalCropYieldInfo cropYieldInfo) {
        this.cropYieldInfo = cropYieldInfo;
    }

    public InternalVariableCropProductionCosts getCostsCropProductionCosts() {
        return costsCropProductionCosts;
    }

    public void setCostsCropProductionCosts(InternalVariableCropProductionCosts costsCropProductionCosts) {
        this.costsCropProductionCosts = costsCropProductionCosts;
    }

    public SummaryCropInfo getCropInfo() {
        return cropInfo;
    }

    public void setCropInfo(SummaryCropInfo cropInfo) {
        this.cropInfo = cropInfo;
    }

    public OptionalCropPlantingDates getCropPlantingDates() {
        return cropPlantingDates;
    }

    public void setCropPlantingDates(OptionalCropPlantingDates cropPlantingDates) {
        this.cropPlantingDates = cropPlantingDates;
    }

    public Set<CropResourceUsageFieldVariances> getCropResourceUsageFieldVariances() {
        return cropResourceUsageFieldVariances;
    }

    public void setCropResourceUsageFieldVariances(Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariances) {
        this.cropResourceUsageFieldVariances = cropResourceUsageFieldVariances;
    }

    public InternalCropPricesInfo getCropPricesInfo() {
        return cropPricesInfo;
    }

    public void setCropPricesInfo(InternalCropPricesInfo cropPricesInfo) {
        this.cropPricesInfo = cropPricesInfo;
    }

    public Set<OptionalCropProductionCostsDetails> getOptCropCost() {
        return optCropCost;
    }

    public void setOptCropCost(Set<OptionalCropProductionCostsDetails> optCropCost) {
        this.optCropCost = optCropCost;
    }

    public CropLimit getCropLimit() {
        return cropLimit;
    }

    public void setCropLimit(CropLimit cropLimit) {
        this.cropLimit = cropLimit;
    }

    public CropForwardSales getCropForwardSales() {
        return cropForwardSales;
    }

    public void setCropForwardSales(CropForwardSales cropForwardSales) {
        this.cropForwardSales = cropForwardSales;
    }

    public Set<CropFieldChocies> getChocies() {
        return chocies;
    }

    public void setChocies(Set<CropFieldChocies> chocies) {
        this.chocies = chocies;
    }

    public CropYieldFieldVariances getCropYieldFieldVariances() {
        return cropYieldFieldVariances;
    }

    public void setCropYieldFieldVariances(CropYieldFieldVariances cropYieldFieldVariances) {
        this.cropYieldFieldVariances = cropYieldFieldVariances;
    }

    public Set<CropsGroup> getCropsGroup() {
        return cropsGroup;
    }

    public void setCropsGroup(Set<CropsGroup> cropsGroup) {
        this.cropsGroup = cropsGroup;
    }

    public AdditionalCropIncome getAdditionalCropIncome() {
        return additionalCropIncome;
    }

    public void setAdditionalCropIncome(AdditionalCropIncome additionalCropIncome) {
        this.additionalCropIncome = additionalCropIncome;
    }
}
