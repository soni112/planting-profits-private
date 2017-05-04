package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.CropResourceUsage;
import com.decipher.agriculture.data.farm.CropResourceUsageFieldDifferences;
import com.decipher.util.AgricultureStandardUtils;

public class CropResourceUsageView implements Cloneable{
	private int id;
	private String cropResourceUse;
	private String uoMResource;
	private String cropResourceUseAmount;
	private String resourseOverrideAmount;
	private String cropResourceUseLowerCase;
	private int resourseUsageId;
	private boolean isActive;

	public CropResourceUsageView() {

	}

	public CropResourceUsageView(CropResourceUsage cropResourceUsage) {
		if(!cropResourceUsage.getCropResourceUseAmount().isEmpty()
				&& !cropResourceUsage.getCropResourceUseAmount().equalsIgnoreCase("0")) {


			this.id = cropResourceUsage.getId();
			this.cropResourceUse = cropResourceUsage.getCropResourceUse();
			this.cropResourceUseLowerCase = cropResourceUsage.getCropResourceUse();
			this.uoMResource = cropResourceUsage.getUoMResource();
			this.cropResourceUseAmount = cropResourceUsage.getCropResourceUseAmount();
			CropResourceUsageFieldDifferences fieldDifferences = cropResourceUsage.getDifferences();
			// PlantingProfitLogger.info("start Resource");
			if (fieldDifferences != null) {
				this.resourseOverrideAmount = fieldDifferences.getResourseOverrideAmount();
				CropResourceUsage resourseUsage = fieldDifferences.getCropResourceUsage();
				if (resourseUsage != null) {
					this.resourseUsageId = resourseUsage.getId();
				}

				// CropType cropType=
				// PlantingProfitLogger.info("resourseOverrideAmount : "+resourseOverrideAmount);
			}
			// PlantingProfitLogger.info("End fieldDifferences : "+resourseOverrideAmount);


			if (cropResourceUsage.getResourceActive() != null) {
				this.isActive = cropResourceUsage.getResourceActive();
			}
		}
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCropResourceUse() {
		return cropResourceUse;
	}

	public void setCropResourceUse(String cropResourceUse) {
		this.cropResourceUse = cropResourceUse;
	}

	public String getUoMResource() {
		return uoMResource;
	}

	public void setUoMResource(String uoMResource) {
		this.uoMResource = uoMResource;
	}

	public String getCropResourceUseAmount() {
		if (cropResourceUseAmount == null || cropResourceUseAmount.equalsIgnoreCase("0")) {
			return "";
		} else {
			return AgricultureStandardUtils.commaSeparaterForField(cropResourceUseAmount);
		}
		// return cropResourceUseAmount;
	}

	public String getCropResourceUseAmountWithoutComma() {
		if (cropResourceUseAmount == null || cropResourceUseAmount.equalsIgnoreCase("0")) {
			return "";
		} else {
			return AgricultureStandardUtils.removeAllCommas(cropResourceUseAmount);
		}
		// return cropResourceUseAmount;
	}

	public String getCropResourceUseAmountZeroIfBlank() {
		if (cropResourceUseAmount == null) {
			return "";
		} else {
			return AgricultureStandardUtils.removeAllCommas(cropResourceUseAmount);
		}
	}

	public void setCropResourceUseAmount(String cropResourceUseAmount) {
		this.cropResourceUseAmount = cropResourceUseAmount;
	}

	public String getResourseOverrideAmount() {
		return resourseOverrideAmount;
	}

	public void setResourseOverrideAmount(String resourseOverrideAmount) {
		this.resourseOverrideAmount = resourseOverrideAmount;
	}

	public String getCropResourceUseLowerCase() {
		return cropResourceUseLowerCase.toLowerCase();
	}

	public void setCropResourceUseLowerCase(String cropResourceUseLowerCase) {
		this.cropResourceUseLowerCase = cropResourceUseLowerCase;
	}

	public int getResourseUsageId() {
		return resourseUsageId;
	}

	public void setResourseUsageId(int resourseUsageId) {
		this.resourseUsageId = resourseUsageId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	@Override
	public CropResourceUsageView clone() throws CloneNotSupportedException {
		return (CropResourceUsageView)super.clone();
	}
}