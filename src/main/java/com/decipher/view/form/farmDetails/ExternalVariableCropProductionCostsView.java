package com.decipher.view.form.farmDetails;

import java.math.BigDecimal;

import com.decipher.agriculture.data.farm.ExternalVariableCropProductionCosts;

public class ExternalVariableCropProductionCostsView
{

	private Integer id;
	private String extSumVarProdCostSource;
	private BigDecimal calculatedVarProductionCost;

	public ExternalVariableCropProductionCostsView()
	{

	}

	public ExternalVariableCropProductionCostsView(
			ExternalVariableCropProductionCosts costs)
	{
		this.id = costs.getId();
		this.extSumVarProdCostSource = costs.getExtSumVarProdCostSource();
		this.calculatedVarProductionCost = costs
				.getCalculatedVarProductionCost();
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getExtSumVarProdCostSource()
	{
		return extSumVarProdCostSource;
	}

	public void setExtSumVarProdCostSource(String extSumVarProdCostSource)
	{
		this.extSumVarProdCostSource = extSumVarProdCostSource;
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
}
