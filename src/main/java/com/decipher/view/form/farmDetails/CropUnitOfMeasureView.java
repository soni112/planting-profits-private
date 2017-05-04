package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.CropUnitOfMeasure;

public class CropUnitOfMeasureView
{

	private Integer id;
	private String unitOfMeasure;

	public CropUnitOfMeasureView()
	{

	}

	public CropUnitOfMeasureView(CropUnitOfMeasure cropUnitOfMeasure)
	{
		this.id = cropUnitOfMeasure.getId();
		this.unitOfMeasure = cropUnitOfMeasure.getUnitOfMeasure();
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getUnitOfMeasure()
	{
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure)
	{
		this.unitOfMeasure = unitOfMeasure;
	}

}
