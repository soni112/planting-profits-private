package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.CropResourceUsageFieldVariances;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.util.AgricultureStandardUtils;

public class CropResourceUsageFieldVariancesView implements Cloneable{
	private Integer id;
	private String cropFieldResourceUse;
	private String cropResourceAmount;
	private Integer cropId;
	private String cropName;

	CropResourceUsageFieldVariancesView() {

	}

	public CropResourceUsageFieldVariancesView(CropResourceUsageFieldVariances cropResourceUsageFieldVariances) {
		this.id = cropResourceUsageFieldVariances.getId();
		this.cropFieldResourceUse = cropResourceUsageFieldVariances.getCropFieldResourceUse();
		this.cropResourceAmount = cropResourceUsageFieldVariances.getCropResourceAmount();
		// PlantingProfitLogger.info("cropResourceAmount : "+cropResourceAmount);
		CropType type = cropResourceUsageFieldVariances.getCropType();
		if (type != null) {
			this.cropName = type.getCropName();
			this.cropId = type.getId();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCropFieldResourceUse() {
		return cropFieldResourceUse;
	}

	public void setCropFieldResourceUse(String cropFieldResourceUse) {
		this.cropFieldResourceUse = cropFieldResourceUse;
	}

	public String getCropResourceAmount() {
		if (cropResourceAmount == null || cropResourceAmount.toString().equalsIgnoreCase("0")) {
			return "";
		} else {
			return AgricultureStandardUtils.commaSeparaterForPrice(cropResourceAmount.toString());
		}
	}

	public void setCropResourceAmount(String cropResourceAmount) {
		this.cropResourceAmount = cropResourceAmount;
	}

	public Integer getCropId() {
		return cropId;
	}

	public void setCropId(Integer cropId) {
		this.cropId = cropId;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	@Override
	public CropResourceUsageFieldVariancesView clone() throws CloneNotSupportedException {
		return (CropResourceUsageFieldVariancesView)super.clone();
	}
}