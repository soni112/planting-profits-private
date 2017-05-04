package com.decipher.view.form.farmDetails;

import java.util.Date;

import com.decipher.agriculture.data.farm.FarmProductionPlanningModelParameters;

public class FarmProductionPlanningModelParametersView
{
	private Integer id;
	private Boolean planByFields;
	private Boolean planByAcres;
	private Boolean irrigation;
	private Boolean forwardSales;
	private Boolean cropInsurance;
	private Date plantingYear;
	
	public FarmProductionPlanningModelParametersView()
	{
		
	}

	public FarmProductionPlanningModelParametersView(FarmProductionPlanningModelParameters farmProductionPlanningModelParameters)
	{
		this.id = farmProductionPlanningModelParameters.getId();
		this.planByFields = farmProductionPlanningModelParameters.getPlanByFields();
		this.planByAcres = farmProductionPlanningModelParameters.getPlanByAcres();
		this.irrigation = farmProductionPlanningModelParameters.getIrrigation();
		this.forwardSales = farmProductionPlanningModelParameters.getForwardSales();
		this.cropInsurance = farmProductionPlanningModelParameters.getCropInsurance();
		this.plantingYear = farmProductionPlanningModelParameters.getPlantingYear();
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Boolean getPlanByFields()
	{
		return planByFields;
	}

	public void setPlanByFields(Boolean planByFields)
	{
		this.planByFields = planByFields;
	}

	public Boolean getPlanByAcres()
	{
		return planByAcres;
	}

	public void setPlanByAcres(Boolean planByAcres)
	{
		this.planByAcres = planByAcres;
	}

	public Boolean getIrrigation()
	{
		return irrigation;
	}

	public void setIrrigation(Boolean irrigation)
	{
		this.irrigation = irrigation;
	}

	public Boolean getForwardSales()
	{
		return forwardSales;
	}

	public void setForwardSales(Boolean forwardSales)
	{
		this.forwardSales = forwardSales;
	}

	public Boolean getCropInsurance()
	{
		return cropInsurance;
	}

	public void setCropInsurance(Boolean cropInsurance)
	{
		this.cropInsurance = cropInsurance;
	}

	public Date getPlantingYear()
	{
		return plantingYear;
	}

	public void setPlantingYear(Date plantingYear)
	{
		this.plantingYear = plantingYear;
	}
}
