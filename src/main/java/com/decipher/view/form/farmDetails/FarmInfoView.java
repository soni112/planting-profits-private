package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.util.AgricultureStandardUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

public class FarmInfoView implements Comparable<FarmInfoView>, Cloneable, Serializable {
	private Integer id;
	private String farmName;
	private String physicalLocation;
	private String irrigate;
	private String land;
	private PlanByStrategy strategy;
	private Boolean evaluate_forward_sales;
	private Boolean evaluate_storage;
	private Boolean evaluate_crop_insurance;
	private String fixedCost;
	private String livingExpenses;
	private String additionalProfit;
	private String profitGoal;
	private Boolean saveFlag;
	private String currencyUOM;
	private String landUOM;

	private Boolean montyCarloStatus;

	@JsonIgnore
	private FarmInfo farmInfo;

	public FarmInfoView(FarmInfo farmInfo) {
		this.id = farmInfo.getId();
		this.farmName = farmInfo.getFarm().getFarmName();
		this.physicalLocation = farmInfo.getFarm().getPhysicalLocation();
		this.irrigate = farmInfo.getIrrigate();
		this.land = farmInfo.getLand();
		this.strategy = farmInfo.getStrategy();
		this.evaluate_forward_sales = farmInfo.getEvaluate_forward_sales();
		this.evaluate_crop_insurance = farmInfo.getEvaluate_crop_insurance();
		this.fixedCost = farmInfo.getFixedCost();
		this.livingExpenses = farmInfo.getLivingExpenses();
		this.additionalProfit = farmInfo.getAdditionalProfit();
		this.profitGoal = farmInfo.getProfitGoal();
		this.saveFlag = farmInfo.getSaveFlag();
		this.currencyUOM = farmInfo.getCurrencyUOM();
		this.landUOM = farmInfo.getLandUOM();
		this.evaluate_storage = farmInfo.getEvaluate_storage();

		this.montyCarloStatus = farmInfo.getMontyCarloStatus();

		this.farmInfo = farmInfo;

	}

	public FarmInfoView() {

	}

	public Integer getId() {
		return id;
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

	public String getLandStr() {
		if (land == null) {
			return "";
		} else {
			return AgricultureStandardUtils.commaSeparaterForField(land);
		}

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

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public String getPhysicalLocation() {
		return physicalLocation;
	}

	public void setPhysicalLocation(String physicalLocation) {
		this.physicalLocation = physicalLocation;
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

	public Boolean getEvaluate_storage() {
		return evaluate_storage;
	}

	public void setEvaluate_storage(Boolean evaluate_storage) {
		this.evaluate_storage = evaluate_storage;
	}

	public Boolean getMontyCarloStatus() {
		return montyCarloStatus;
	}

	public void setMontyCarloStatus(Boolean montyCarloStatus) {
		this.montyCarloStatus = montyCarloStatus;
	}

	public FarmInfo getFarmInfo() {
		return farmInfo;
	}

	public void setFarmInfo(FarmInfo farmInfo) {
		this.farmInfo = farmInfo;
	}

	@Override
	public int compareTo(FarmInfoView farmInfoView) {
		if(this.getId().equals(farmInfoView.getId())){
			return 0;
		} else if(this.getId() > farmInfoView.getId()){
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public FarmInfoView clone() throws CloneNotSupportedException {
		FarmInfoView farmInfoView = (FarmInfoView) super.clone();
		return farmInfoView;
	}
}
