package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponentsUoM;

public class VariableCropProductionCostsComponentsUoMView
{
	private Integer id;
	private String seed;
	private String herbicide;
	private String insecticide;
	private String fungicide;
	private String fertilizer;
	private String micro_Nutrients;
	private String labor;
	private String equipment;
	private String irrigation;
	private String rent;
	private String professionalServices;
	private String cropInsurance;
	private String financing;
	private String other;
	
	public VariableCropProductionCostsComponentsUoMView()
	{
		
	}
	
	public VariableCropProductionCostsComponentsUoMView(VariableCropProductionCostsComponentsUoM variableCropProductionCostsComponentsUoM)
	{
		this.id = variableCropProductionCostsComponentsUoM.getId();
		this.seed = variableCropProductionCostsComponentsUoM.getSeed();
		this.herbicide = variableCropProductionCostsComponentsUoM.getHerbicide();
		this.insecticide = variableCropProductionCostsComponentsUoM.getInsecticide();
		this.fungicide = variableCropProductionCostsComponentsUoM.getFungicide();
		this.fertilizer = variableCropProductionCostsComponentsUoM.getFertilizer();
		this.micro_Nutrients =variableCropProductionCostsComponentsUoM.getMicro_Nutrients();
		this.labor = variableCropProductionCostsComponentsUoM.getLabor();
		this.equipment = variableCropProductionCostsComponentsUoM.getEquipment();
		this.irrigation = variableCropProductionCostsComponentsUoM.getIrrigation();
		this.rent = variableCropProductionCostsComponentsUoM.getRent();
		this.professionalServices = variableCropProductionCostsComponentsUoM.getProfessionalServices();
		this.cropInsurance = variableCropProductionCostsComponentsUoM.getCropInsurance();
		this.financing = variableCropProductionCostsComponentsUoM.getFinancing();
		this.other = variableCropProductionCostsComponentsUoM.getOther();
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getSeed()
	{
		return seed;
	}

	public void setSeed(String seed)
	{
		this.seed = seed;
	}

	public String getHerbicide()
	{
		return herbicide;
	}

	public void setHerbicide(String herbicide)
	{
		this.herbicide = herbicide;
	}

	public String getInsecticide()
	{
		return insecticide;
	}

	public void setInsecticide(String insecticide)
	{
		this.insecticide = insecticide;
	}

	public String getFungicide()
	{
		return fungicide;
	}

	public void setFungicide(String fungicide)
	{
		this.fungicide = fungicide;
	}

	public String getFertilizer()
	{
		return fertilizer;
	}

	public void setFertilizer(String fertilizer)
	{
		this.fertilizer = fertilizer;
	}

	public String getMicro_Nutrients()
	{
		return micro_Nutrients;
	}

	public void setMicro_Nutrients(String micro_Nutrients)
	{
		this.micro_Nutrients = micro_Nutrients;
	}

	public String getLabor()
	{
		return labor;
	}

	public void setLabor(String labor)
	{
		this.labor = labor;
	}

	public String getEquipment()
	{
		return equipment;
	}

	public void setEquipment(String equipment)
	{
		this.equipment = equipment;
	}

	public String getIrrigation()
	{
		return irrigation;
	}

	public void setIrrigation(String irrigation)
	{
		this.irrigation = irrigation;
	}

	public String getRent()
	{
		return rent;
	}

	public void setRent(String rent)
	{
		this.rent = rent;
	}

	public String getProfessionalServices()
	{
		return professionalServices;
	}

	public void setProfessionalServices(String professionalServices)
	{
		this.professionalServices = professionalServices;
	}

	public String getCropInsurance()
	{
		return cropInsurance;
	}

	public void setCropInsurance(String cropInsurance)
	{
		this.cropInsurance = cropInsurance;
	}

	public String getFinancing()
	{
		return financing;
	}

	public void setFinancing(String financing)
	{
		this.financing = financing;
	}

	public String getOther()
	{
		return other;
	}

	public void setOther(String other)
	{
		this.other = other;
	}	
}
