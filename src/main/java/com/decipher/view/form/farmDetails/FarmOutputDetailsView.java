package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farmOutput.FarmOutputDetails;
import com.decipher.util.AgricultureStandardUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.Comparator;

public class FarmOutputDetailsView implements Comparator<FarmOutputDetailsView>{

	private Integer id;
	private String profit;
	private String usedAcres;
	private String usedCapital;
	private Double profitDouble;
	private Double usedAcresDouble;
	private Double usedCapitalDouble;
	private CropTypeView cropTypeView;
	/**
	 * @changed - Abhishek
	 * @date - 09-02-2016
	 * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
	 */
	@JsonIgnore
	private FarmInfo farmInfo;
	private Integer usedAcresPercentage;
	private Integer usedCapitalPercentage;
	private Boolean forFirm;
	private Boolean forProposed;
	private Double ratio;
	private Double profitIndex;
	private String rating;

	private Double maximumProfit;
	private Double minimumProfit;

	public FarmOutputDetailsView() {
	}

	public FarmOutputDetailsView(FarmOutputDetails farmOutputDetail) {
		id = farmOutputDetail.getId();
		profit = AgricultureStandardUtils.commaSeparaterForInteger(farmOutputDetail.getProfit());
		usedAcres = AgricultureStandardUtils.commaSeparaterForInteger(farmOutputDetail.getUsedAcres());
		usedCapital = AgricultureStandardUtils.commaSeparaterForInteger(farmOutputDetail.getUsedCapital());
		profitDouble = farmOutputDetail.getProfit();
		usedAcresDouble = farmOutputDetail.getUsedAcres();
		usedCapitalDouble = farmOutputDetail.getUsedCapital();
		cropTypeView = new CropTypeView(farmOutputDetail.getCropType());
		farmInfo = farmOutputDetail.getFarmInfo();
		forFirm = farmOutputDetail.getForContract();
//		forProposed = farmOutputDetail.getCropType().getCropForwardSales().getProposedchecked();
		forProposed = farmOutputDetail.getForProposed();
		usedAcresPercentage = 0;
		usedCapitalPercentage = 0;

		maximumProfit = farmOutputDetail.getMaximumProfit();
		minimumProfit = farmOutputDetail.getMinimumProfit();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getUsedAcres() {
		return usedAcres;
	}

	public void setUsedAcres(String usedAcres) {
		this.usedAcres = usedAcres;
	}

	public String getUsedCapital() {
		return usedCapital;
	}

	public void setUsedCapital(String usedCapital) {
		this.usedCapital = usedCapital;
	}

	public FarmInfo getFarmInfo() {
		return farmInfo;
	}

	public void setFarmInfo(FarmInfo farmInfo) {
		this.farmInfo = farmInfo;
	}


	public CropTypeView getCropTypeView() {
		return cropTypeView;
	}

	public void setCropTypeView(CropTypeView cropTypeView) {
		this.cropTypeView = cropTypeView;
	}

	public Double getUsedCapitalAsDouble() {
		return Double.parseDouble(AgricultureStandardUtils
				.removeAllCommas(usedCapital));
	}

	public Double getProfitAsDouble() {
		return Double.parseDouble(AgricultureStandardUtils
				.removeAllCommas(profit));
	}

	public Double getUsedAcresAsDouble() {
		return Double.parseDouble(AgricultureStandardUtils
				.removeAllCommas(usedAcres));
	}

	public Integer getUsedCapitalAsInteger() {
		return Integer.parseInt(AgricultureStandardUtils
				.removeAllCommas(usedCapital));
	}

	public Integer getProfitAsInteger() {
		return Integer.parseInt(AgricultureStandardUtils
				.removeAllCommas(usedCapital));
	}

	public Integer getUsedAcresAsInteger() {
		return Integer.parseInt(AgricultureStandardUtils
				.removeAllCommas(usedAcres));
	}

	public Integer getUsedAcresPercentage() {
		return usedAcresPercentage;
	}

	public void setUsedAcresPercentage(Integer usedAcresPercentage) {
		this.usedAcresPercentage = usedAcresPercentage;
	}

	public Integer getUsedCapitalPercentage() {
		return usedCapitalPercentage;
	}

	public void setUsedCapitalPercentage(Integer usedCapitalPercentage) {
		this.usedCapitalPercentage = usedCapitalPercentage;
	}

	public Double getProfitDouble() {
		return profitDouble;
	}

	public void setProfitDouble(Double profitDouble) {
		this.profitDouble = profitDouble;
	}

	public Double getUsedAcresDouble() {
		return usedAcresDouble;
	}

	public void setUsedAcresDouble(Double usedAcresDouble) {
		this.usedAcresDouble = usedAcresDouble;
	}

	public Double getUsedCapitalDouble() {
		return usedCapitalDouble;
	}

	public void setUsedCapitalDouble(Double usedCapitalDouble) {
		this.usedCapitalDouble = usedCapitalDouble;
	}

	public Boolean getForFirm() {
		return forFirm;
	}

	public void setForFirm(Boolean forFirm) {
		this.forFirm = forFirm;
	}

	public Boolean getForProposed() {
		return forProposed;
	}

	public void setForProposed(Boolean forProposed) {
		this.forProposed = forProposed;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public Double getProfitIndex() {
		return profitIndex;
	}

	public void setProfitIndex(Double profitIndex) {
		this.profitIndex = profitIndex;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Double getMaximumProfit() {
		return maximumProfit;
	}

	public void setMaximumProfit(Double maximumProfit) {
		this.maximumProfit = maximumProfit;
	}

	public Double getMinimumProfit() {
		return minimumProfit;
	}

	public void setMinimumProfit(Double minimumProfit) {
		this.minimumProfit = minimumProfit;
	}

	@Override
	public int compare(FarmOutputDetailsView o1, FarmOutputDetailsView o2) {
		return o2.getUsedAcresPercentage() - o1.getUsedAcresPercentage();
	}
}
