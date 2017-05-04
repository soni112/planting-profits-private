package com.decipher.view.form.farmDetails;

import java.math.BigDecimal;

import com.decipher.agriculture.data.farm.InternalVariableCropProductionCosts;

public class InternalVariableCropProductionCostsView
{
	private Integer id;
	private BigDecimal summaryVariableProductionCost;
	private BigDecimal calculatedVariableProductionCost;
	
	public InternalVariableCropProductionCostsView()
	{
		
	}
	
	public InternalVariableCropProductionCostsView(InternalVariableCropProductionCosts internalVariableCropProductionCosts)
	{
		this.id = internalVariableCropProductionCosts.getId();
		this.summaryVariableProductionCost = internalVariableCropProductionCosts.getSummaryVariableProductionCost();
		this.calculatedVariableProductionCost = internalVariableCropProductionCosts.getCalculatedVariableProductionCost();
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public BigDecimal getSummaryVariableProductionCost()
	{
		return summaryVariableProductionCost;
	}

	public void setSummaryVariableProductionCost(
			BigDecimal summaryVariableProductionCost)
	{
		this.summaryVariableProductionCost = summaryVariableProductionCost;
	}

	public BigDecimal getCalculatedVariableProductionCost()
	{
		return calculatedVariableProductionCost;
	}

	public void setCalculatedVariableProductionCost(
			BigDecimal calculatedVariableProductionCost)
	{
		this.calculatedVariableProductionCost = calculatedVariableProductionCost;
	}
}
