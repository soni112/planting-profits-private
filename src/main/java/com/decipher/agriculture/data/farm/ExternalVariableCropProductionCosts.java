package com.decipher.agriculture.data.farm;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "ExternalVariableCropProductionCosts")
@Table(name = "EXT_VAR_CROP_PRO_COSTS", uniqueConstraints = @UniqueConstraint(columnNames = "EXT_VAR_CROP_PRO_COSTS_ID"))
public class ExternalVariableCropProductionCosts implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXT_VAR_CROP_PRO_COSTS_ID")
	private Integer id;
	@Column(name = "EXT_SUM_VAR_PROD_COST_SOURCE")
	private String extSumVarProdCostSource;
	@Column(name = "CAL_VAR_PRODUCTION_COST", columnDefinition = "Decimal(19,4)")
	private BigDecimal calculatedVarProductionCost;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExtSumVarProdCostSource() {
		return extSumVarProdCostSource;
	}

	public void setExtSumVarProdCostSource(String extSumVarProdCostSource) {
		this.extSumVarProdCostSource = extSumVarProdCostSource;
	}

	public BigDecimal getCalculatedVarProductionCost() {
		return calculatedVarProductionCost;
	}

	public void setCalculatedVarProductionCost(
			BigDecimal calculatedVarProductionCost) {
		this.calculatedVarProductionCost = calculatedVarProductionCost;
	}

}
