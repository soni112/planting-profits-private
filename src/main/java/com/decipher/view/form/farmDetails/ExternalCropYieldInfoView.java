package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.ExternalCropYieldInfo;

public class ExternalCropYieldInfoView
{

	private Integer id;
	private Double externalCropYield;
	private Double externalMinCropYield;
	private Double externalMaxCropYield;

	public ExternalCropYieldInfoView()
	{

	}

	public ExternalCropYieldInfoView(ExternalCropYieldInfo cropYieldInfo)
	{
		this.id = cropYieldInfo.getId();
		this.externalCropYield = cropYieldInfo.getExternalCropYield();
		this.externalMinCropYield = cropYieldInfo.getExternalMinCropYield();
		this.externalMaxCropYield = cropYieldInfo.getExternalMaxCropYield();
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Double getExternalCropYield()
	{
		return externalCropYield;
	}

	public void setExternalCropYield(Double externalCropYield)
	{
		this.externalCropYield = externalCropYield;
	}

	public Double getExternalMinCropYield()
	{
		return externalMinCropYield;
	}

	public void setExternalMinCropYield(Double externalMinCropYield)
	{
		this.externalMinCropYield = externalMinCropYield;
	}

	public Double getExternalMaxCropYield()
	{
		return externalMaxCropYield;
	}

	public void setExternalMaxCropYield(Double externalMaxCropYield)
	{
		this.externalMaxCropYield = externalMaxCropYield;
	}
}
