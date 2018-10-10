package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farmOutput.FarmOutputDetailsForField;
import com.decipher.util.AgricultureStandardUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class FarmOutputDetailsForFieldView  implements Serializable {

	private Integer id;
	private String profit;
	private String usedAcres;
	private String usedCapital;
	private Double profitDouble;
	private Double usedAcresDouble;
	private Double usedCapitalDouble;
	private CropTypeView cropTypeView;
	private FieldInfoView fieldInfoView;
	/**
	 * @changed - Abhishek
	 * @date - 09-02-2016
	 * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
	 */
	@JsonIgnore
	private FarmInfo farmInfo;
	private Integer usedAcresPercentage;
	private Integer usedCapitalPercentage;
	private boolean forFirm;
	private boolean forProposed;

	private Double maximumProfit;
	private Double minimumProfit;

	private Double ratio;
	private Double profitIndex;
	private String rating;


	public FarmOutputDetailsForFieldView(FarmOutputDetailsForField farmOutputDetailsForField) {
		id = farmOutputDetailsForField.getId();
		profit = AgricultureStandardUtils.commaSeparaterForInteger(farmOutputDetailsForField.getProfit());
		usedAcres = AgricultureStandardUtils.commaSeparaterForInteger(farmOutputDetailsForField.getUsedAcres());
		usedCapital = AgricultureStandardUtils.commaSeparaterForInteger(farmOutputDetailsForField.getUsedCapital());
		profitDouble = farmOutputDetailsForField.getProfit();
		usedAcresDouble = farmOutputDetailsForField.getUsedAcres();
		usedCapitalDouble = farmOutputDetailsForField.getUsedCapital();
		cropTypeView = new CropTypeView(farmOutputDetailsForField.getCropType());
		farmInfo = farmOutputDetailsForField.getFarmInfo();
		fieldInfoView = new FieldInfoView(farmOutputDetailsForField.getFieldInfo());
		forFirm = farmOutputDetailsForField.getForFirm();
//		forProposed = farmOutputDetailsForField.getCropType().getCropForwardSales().getProposedchecked();
		forProposed = farmOutputDetailsForField.getForProposed();
		usedAcresPercentage = 0;
		usedCapitalPercentage = 0;

		maximumProfit = farmOutputDetailsForField.getMaximumProfit();
		minimumProfit = farmOutputDetailsForField.getMinimumProfit();
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
				.removeAllCommas(usedCapital));
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

	public FieldInfoView getFieldInfoView() {
		return fieldInfoView;
	}

	public void setFieldInfoView(FieldInfoView fieldInfoView) {
		this.fieldInfoView = fieldInfoView;
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

	public boolean isForFirm() {
		return forFirm;
	}

	public void setForFirm(boolean forFirm) {
		this.forFirm = forFirm;
	}

	public boolean isForProposed() {
		return forProposed;
	}

	public void setForProposed(boolean forProposed) {
		this.forProposed = forProposed;
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
}
