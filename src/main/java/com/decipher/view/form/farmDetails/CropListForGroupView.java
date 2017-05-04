package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.CropType;

public class CropListForGroupView
{
	private String cropsGroupName;
	private String cropName;
	
	public CropListForGroupView(CropType detailsList)
	{
		this.cropName=detailsList.getCropName();
//		PlantingProfitLogger.info("crop Name : "+cropName);
//		CropsGroup cropsGroup=detailsList.getCropsGroup();
//		if (cropsGroup != null)
//		{
//			this.cropsGroupName=cropsGroup.getCropsGroupName();
////			PlantingProfitLogger.info("cropsGroupName : "+cropsGroupName);
//		}		
	}

	public String getCropsGroupName()
	{
		return cropsGroupName;
	}

	public void setCropsGroupName(String cropsGroupName)
	{
		this.cropsGroupName = cropsGroupName;
	}

	public String getCropName()
	{
		return cropName;
	}

	public void setCropName(String cropName)
	{
		this.cropName = cropName;
	}
}
