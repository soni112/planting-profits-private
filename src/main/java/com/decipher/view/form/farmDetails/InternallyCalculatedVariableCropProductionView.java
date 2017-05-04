package com.decipher.view.form.farmDetails;

import java.math.BigDecimal;

import com.decipher.agriculture.data.farm.InternallyCalculatedVariableCropProduction;


public class InternallyCalculatedVariableCropProductionView
{
	private Integer id;
	private BigDecimal calculatedVarProductionCost;
	private BigDecimal costSeed;
	private BigDecimal costHerbicide;
	private BigDecimal costInsecticide;
	private BigDecimal costFungicide;
	private BigDecimal costFertilizer;
	private BigDecimal costMicro_Nutrients;
	private BigDecimal costLabor;
	private BigDecimal costEquipment;
	private BigDecimal costIrrigation;
	private BigDecimal costRent;
	private BigDecimal costProfessionalServices;
	private BigDecimal costCropInsurance;
	private BigDecimal costFinancing;
	private BigDecimal costOther;
	
	public InternallyCalculatedVariableCropProductionView()
	{
		
	}
	
	

	public InternallyCalculatedVariableCropProductionView(InternallyCalculatedVariableCropProduction internallyCalculatedVariableCropProduction)
	{
		this.id = internallyCalculatedVariableCropProduction.getId();;
		this.calculatedVarProductionCost = internallyCalculatedVariableCropProduction.getCalculatedVarProductionCost();
		this.costSeed = internallyCalculatedVariableCropProduction.getCostSeed();
		this.costHerbicide = internallyCalculatedVariableCropProduction.getCostHerbicide();
		this.costInsecticide = internallyCalculatedVariableCropProduction.getCostInsecticide();
		this.costFungicide = internallyCalculatedVariableCropProduction.getCostFungicide();
		this.costFertilizer = internallyCalculatedVariableCropProduction.getCostFertilizer();
		this.costMicro_Nutrients = internallyCalculatedVariableCropProduction.getCostMicro_Nutrients();
		this.costLabor = internallyCalculatedVariableCropProduction.getCostLabor();
		this.costEquipment = internallyCalculatedVariableCropProduction.getCostEquipment();
		this.costIrrigation = internallyCalculatedVariableCropProduction.getCostIrrigation();
		this.costRent = internallyCalculatedVariableCropProduction.getCostRent();
		this.costProfessionalServices = internallyCalculatedVariableCropProduction.getCostProfessionalServices();
		this.costCropInsurance = internallyCalculatedVariableCropProduction.getCostCropInsurance();
		this.costFinancing = internallyCalculatedVariableCropProduction.getCostFinancing();
		this.costOther = internallyCalculatedVariableCropProduction.getCostOther();
	}



	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public BigDecimal getCalculatedVarProductionCost()
	{
		return calculatedVarProductionCost;
	}

	public void setCalculatedVarProductionCost(
			BigDecimal calculatedVarProductionCost)
	{
		this.calculatedVarProductionCost = calculatedVarProductionCost;
	}

	public BigDecimal getCostSeed()
	{
		return costSeed;
	}

	public void setCostSeed(BigDecimal costSeed)
	{
		this.costSeed = costSeed;
	}

	public BigDecimal getCostHerbicide()
	{
		return costHerbicide;
	}

	public void setCostHerbicide(BigDecimal costHerbicide)
	{
		this.costHerbicide = costHerbicide;
	}

	public BigDecimal getCostInsecticide()
	{
		return costInsecticide;
	}

	public void setCostInsecticide(BigDecimal costInsecticide)
	{
		this.costInsecticide = costInsecticide;
	}

	public BigDecimal getCostFungicide()
	{
		return costFungicide;
	}

	public void setCostFungicide(BigDecimal costFungicide)
	{
		this.costFungicide = costFungicide;
	}

	public BigDecimal getCostFertilizer()
	{
		return costFertilizer;
	}

	public void setCostFertilizer(BigDecimal costFertilizer)
	{
		this.costFertilizer = costFertilizer;
	}

	public BigDecimal getCostMicro_Nutrients()
	{
		return costMicro_Nutrients;
	}

	public void setCostMicro_Nutrients(BigDecimal costMicro_Nutrients)
	{
		this.costMicro_Nutrients = costMicro_Nutrients;
	}

	public BigDecimal getCostLabor()
	{
		return costLabor;
	}

	public void setCostLabor(BigDecimal costLabor)
	{
		this.costLabor = costLabor;
	}

	public BigDecimal getCostEquipment()
	{
		return costEquipment;
	}

	public void setCostEquipment(BigDecimal costEquipment)
	{
		this.costEquipment = costEquipment;
	}

	public BigDecimal getCostIrrigation()
	{
		return costIrrigation;
	}

	public void setCostIrrigation(BigDecimal costIrrigation)
	{
		this.costIrrigation = costIrrigation;
	}

	public BigDecimal getCostRent()
	{
		return costRent;
	}

	public void setCostRent(BigDecimal costRent)
	{
		this.costRent = costRent;
	}

	public BigDecimal getCostProfessionalServices()
	{
		return costProfessionalServices;
	}

	public void setCostProfessionalServices(BigDecimal costProfessionalServices)
	{
		this.costProfessionalServices = costProfessionalServices;
	}

	public BigDecimal getCostCropInsurance()
	{
		return costCropInsurance;
	}

	public void setCostCropInsurance(BigDecimal costCropInsurance)
	{
		this.costCropInsurance = costCropInsurance;
	}

	public BigDecimal getCostFinancing()
	{
		return costFinancing;
	}

	public void setCostFinancing(BigDecimal costFinancing)
	{
		this.costFinancing = costFinancing;
	}

	public BigDecimal getCostOther()
	{
		return costOther;
	}

	public void setCostOther(BigDecimal costOther)
	{
		this.costOther = costOther;
	}
}
