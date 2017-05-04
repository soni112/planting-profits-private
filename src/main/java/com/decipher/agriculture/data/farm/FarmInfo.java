package com.decipher.agriculture.data.farm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.decipher.agriculture.data.farmOutput.CropLimitDualValue;
import com.decipher.agriculture.data.farmOutput.GroupLimitDualValue;
import com.decipher.agriculture.data.farmOutput.ResourceDualValue;
import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "FarmInfo")
@Table(name = "FARM_INFO")
public class FarmInfo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FARM_INFO_ID")
	private Integer id;
	@Column(name = "IRRIGATE")
	private String irrigate;
	@Column(name = "LAND")
	private String land;
	@Column(name = "STRATEGY")
	private PlanByStrategy strategy;
	@Column(columnDefinition = "TINYINT", name = "EVA_FORWARD_SALES")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean evaluate_forward_sales;
	@Column(columnDefinition = "TINYINT", name = "EVA_STORAGE")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean evaluate_storage;
	@Column(columnDefinition = "TINYINT", name = "EVA_CROP_INSURANCE")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean evaluate_crop_insurance;
	@Column(name = "FIXED_COST")
	private String fixedCost;
	@Column(name = "LIVING_EXPENSES")
	private String livingExpenses;
	@Column(name = "ADDITIONAL_PROFIT")
	private String additionalProfit;
	@Column(name = "PROFIT_GOAL")
	private String profitGoal;
	@Column(name = "SAVE_FLAG")
	private Boolean saveFlag;
	@Column(name = "CURRENCY_UOM")
	private String currencyUOM;
	@Column(name = "LAND_UOM")
	private String landUOM;
	@Column(name = "MONTY_CARLO_STATUS")
	private Boolean montyCarloStatus = false;
	@Column(name = "BASELINE_FLAG")
	private Boolean baselineFlag= false;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "FARM_ID")
	@JsonIgnore
	private Farm farm;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "farmInfo")
	@JsonIgnore
	private Set<FarmCustomStrategy> farmCustomStrategySet;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	/*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)*/
	@JoinColumn(name = "FARM_INFO_ID", referencedColumnName = "FARM_INFO_ID")
	private Set<CropType> cropTypes = new HashSet<CropType>();

	/**
	 * @changed - Abhishek
	 * @date - 07-01-2016
	 * @desc - for Removing lazy load exception
	 */	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "FARM_INFO_ID", referencedColumnName = "FARM_INFO_ID")
	private List<CropResourceUsage> cropResourceUsage = new ArrayList<CropResourceUsage>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "FARM_INFO_ID", referencedColumnName = "FARM_INFO_ID")
	private List<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "FARM_INFO_ID", referencedColumnName = "FARM_INFO_ID")
	private Set<CropsGroup> cropsGroup = new HashSet<CropsGroup>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name = "FARM_INFO_ID", referencedColumnName = "FARM_INFO_ID")
	private Set<ResourceDualValue> resourceDualValues = new HashSet<ResourceDualValue>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name = "FARM_INFO_ID", referencedColumnName = "FARM_INFO_ID")
	private Set<CropLimitDualValue> cropLimitDualValues = new HashSet<CropLimitDualValue>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name = "FARM_INFO_ID", referencedColumnName = "FARM_INFO_ID")
	private Set<GroupLimitDualValue> groupLimitDualValues = new HashSet<GroupLimitDualValue>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIrrigate() {
		return irrigate;
	}

	public void setIrrigate(String irrigate) {
		this.irrigate = irrigate;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public PlanByStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(PlanByStrategy strategy) {
		this.strategy = strategy;
	}

	public Boolean getEvaluate_forward_sales() {
		return evaluate_forward_sales;
	}

	public void setEvaluate_forward_sales(Boolean evaluate_forward_sales) {
		this.evaluate_forward_sales = evaluate_forward_sales;
	}

	public Boolean getEvaluate_storage() {
		return evaluate_storage;
	}

	public void setEvaluate_storage(Boolean evaluate_storage) {
		this.evaluate_storage = evaluate_storage;
	}

	public Boolean getEvaluate_crop_insurance() {
		return evaluate_crop_insurance;
	}

	public void setEvaluate_crop_insurance(Boolean evaluate_crop_insurance) {
		this.evaluate_crop_insurance = evaluate_crop_insurance;
	}

	public String getFixedCost() {
		return fixedCost;
	}

	public void setFixedCost(String fixedCost) {
		this.fixedCost = fixedCost;
	}

	public String getLivingExpenses() {
		return livingExpenses;
	}

	public void setLivingExpenses(String livingExpenses) {
		this.livingExpenses = livingExpenses;
	}

	public String getAdditionalProfit() {
		return additionalProfit;
	}

	public void setAdditionalProfit(String additionalProfit) {
		this.additionalProfit = additionalProfit;
	}

	public String getProfitGoal() {
		return profitGoal;
	}

	public void setProfitGoal(String profitGoal) {
		this.profitGoal = profitGoal;
	}

	public Boolean getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(Boolean saveFlag) {
		this.saveFlag = saveFlag;
	}

	public String getCurrencyUOM() {
		return currencyUOM;
	}

	public void setCurrencyUOM(String currencyUOM) {
		this.currencyUOM = currencyUOM;
	}

	public String getLandUOM() {
		return landUOM;
	}

	public void setLandUOM(String landUOM) {
		this.landUOM = landUOM;
	}

	public Boolean getMontyCarloStatus() {
		return montyCarloStatus;
	}

	public void setMontyCarloStatus(Boolean montyCarloStatus) {
		this.montyCarloStatus = montyCarloStatus;
	}

	public Boolean getBaselineFlag() {
		return baselineFlag;
	}

	public void setBaselineFlag(Boolean baselineFlag) {
		this.baselineFlag = baselineFlag;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public Set<FarmCustomStrategy> getFarmCustomStrategySet() {
		return farmCustomStrategySet;
	}

	public void setFarmCustomStrategySet(Set<FarmCustomStrategy> farmCustomStrategySet) {
		this.farmCustomStrategySet = farmCustomStrategySet;
	}

	public Set<CropType> getCropTypes() {
		return cropTypes;
	}

	public void setCropTypes(Set<CropType> cropTypes) {
		this.cropTypes = cropTypes;
	}

	public List<CropResourceUsage> getCropResourceUsage() {
		return cropResourceUsage;
	}

	public void setCropResourceUsage(List<CropResourceUsage> cropResourceUsage) {
		this.cropResourceUsage = cropResourceUsage;
	}

	public List<FieldInfo> getFieldInfos() {
		return fieldInfos;
	}

	public void setFieldInfos(List<FieldInfo> fieldInfos) {
		this.fieldInfos = fieldInfos;
	}

	public Set<CropsGroup> getCropsGroup() {
		return cropsGroup;
	}

	public void setCropsGroup(Set<CropsGroup> cropsGroup) {
		this.cropsGroup = cropsGroup;
	}

	public Set<ResourceDualValue> getResourceDualValues() {
		return resourceDualValues;
	}

	public void setResourceDualValues(Set<ResourceDualValue> resourceDualValues) {
		this.resourceDualValues = resourceDualValues;
	}

	public Set<CropLimitDualValue> getCropLimitDualValues() {
		return cropLimitDualValues;
	}

	public void setCropLimitDualValues(Set<CropLimitDualValue> cropLimitDualValues) {
		this.cropLimitDualValues = cropLimitDualValues;
	}

	public Set<GroupLimitDualValue> getGroupLimitDualValues() {
		return groupLimitDualValues;
	}

	public void setGroupLimitDualValues(Set<GroupLimitDualValue> groupLimitDualValues) {
		this.groupLimitDualValues = groupLimitDualValues;
	}
}