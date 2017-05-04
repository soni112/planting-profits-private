package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.CandidatesCrops;

public class CandidatesCropsView
{
	private Integer id;
	private String farm_Name;
	private String crop_Type;
	private String candidate_Crops;
	
	
	public CandidatesCropsView()
	{
		
	}
	public CandidatesCropsView(CandidatesCrops candidatesCrops)
	{
		this.id = candidatesCrops.getId();
		this.farm_Name = candidatesCrops.getFarm_Name();
		this.crop_Type = candidatesCrops.getCrop_Type();
		this.candidate_Crops = candidatesCrops.getCandidate_Crops();
		
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getFarm_Name()
	{
		return farm_Name;
	}

	public void setFarm_Name(String farm_Name)
	{
		this.farm_Name = farm_Name;
	}

	public String getCrop_Type()
	{
		return crop_Type;
	}

	public void setCrop_Type(String crop_Type)
	{
		this.crop_Type = crop_Type;
	}

	public String getCandidate_Crops()
	{
		return candidate_Crops;
	}

	public void setCandidate_Crops(String candidate_Crops)
	{
		this.candidate_Crops = candidate_Crops;
	}
}
