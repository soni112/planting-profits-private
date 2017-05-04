package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.CropYieldFieldVariances;

public class CropYieldFieldVariancesView
{
	private Integer id;
	private Double expCropYieldField;
	private Double minCropYieldField;
	private Double maxCropYieldField;

	public CropYieldFieldVariancesView()
	{

	}

	public CropYieldFieldVariancesView(
			CropYieldFieldVariances cropYieldFieldVariances)
	{
		this.id = cropYieldFieldVariances.getId();
		this.expCropYieldField = cropYieldFieldVariances.getExpCropYieldField();
		this.minCropYieldField = cropYieldFieldVariances.getMinCropYieldField();
		this.maxCropYieldField = cropYieldFieldVariances.getMaxCropYieldField();
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Double getExpCropYieldField()
	{
		return expCropYieldField;
	}

	public void setExpCropYieldField(Double expCropYieldField)
	{
		this.expCropYieldField = expCropYieldField;
	}

	public Double getMinCropYieldField()
	{
		return minCropYieldField;
	}

	public void setMinCropYieldField(Double minCropYieldField)
	{
		this.minCropYieldField = minCropYieldField;
	}

	public Double getMaxCropYieldField()
	{
		return maxCropYieldField;
	}

	public void setMaxCropYieldField(Double maxCropYieldField)
	{
		this.maxCropYieldField = maxCropYieldField;
	}
}
