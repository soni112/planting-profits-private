package com.decipher.agriculture.bean;

import java.util.Map;

import com.decipher.agriculture.data.farm.CropType;

public class CropBeanForOutput {

	private CropType cropType;
	private double profit;
	private double forwardprofit;
	private double acres;
	private double variableProductionCost;
	private double minAcre;
	private double proposedAcres;
	private double firmAcres;
	private double maxAcre;
	private Map<String, Double> resourceList;

	public CropType getCropType() {
		return cropType;
	}

	public void setCropType(CropType cropType) {
		this.cropType = cropType;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getAcres() {
		return acres;
	}

	public void setAcres(double acres) {
		this.acres = acres;
	}

	public double getVariableProductionCost() {
		return variableProductionCost;
	}

	public void setVariableProductionCost(double variableProductionCost) {
		this.variableProductionCost = variableProductionCost;
	}

	public double getMinAcre() {
		return minAcre;
	}

	public void setMinAcre(double minAcre) {
		this.minAcre = minAcre;
	}

	public double getMaxAcre() {
		return maxAcre;
	}

	public void setMaxAcre(double maxAcre) {
		this.maxAcre = maxAcre;
	}

	public Map<String, Double> getResourceList() {
		return resourceList;
	}

	public void setResourceList(Map<String, Double> resourceList) {
		this.resourceList = resourceList;
	}

	public double getProposedAcres() {
		return proposedAcres;
	}

	public void setProposedAcres(double proposedAcres) {
		this.proposedAcres = proposedAcres;
	}

	public double getForwardprofit() {
		return forwardprofit;
	}

	public void setForwardprofit(double forwardprofit) {
		this.forwardprofit = forwardprofit;
	}

	public double getFirmAcres() {
		return firmAcres;
	}

	public void setFirmAcres(double firmAcres) {
		this.firmAcres = firmAcres;
	}
}