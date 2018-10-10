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
@Entity(name = "InternallyCalculatedVariableCropProduction")
@Table(name = "INT_CAL_VAR_CROP_PRODUCTION", uniqueConstraints = @UniqueConstraint(columnNames = "INT_CAL_VAR_CROP_PRODUCTION_ID"))
public class InternallyCalculatedVariableCropProduction implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INT_CAL_VAR_CROP_PRODUCTION_ID")
	private Integer id;
	@Column(name="CAL_VAR_PRODUCTION_COST",columnDefinition = "Decimal(19,4)")
	private BigDecimal calculatedVarProductionCost;
	@Column(name="COST_SEED",columnDefinition = "Decimal(19,4)")
	private BigDecimal costSeed;
	@Column(name="COST_HERBICIDE",columnDefinition = "Decimal(19,4)")
	private BigDecimal costHerbicide;
	@Column(name="COST_INSECTICIDE",columnDefinition = "Decimal(19,4)")
	private BigDecimal costInsecticide;
	@Column(name="COST_FUNGICIDE",columnDefinition = "Decimal(19,4)")
	private BigDecimal costFungicide;
	@Column(name="COST_FERTILIZER",columnDefinition = "Decimal(19,4)")
	private BigDecimal costFertilizer;
	@Column(name="COST_MICRO_NUTRIENTS",columnDefinition = "Decimal(19,4)")
	private BigDecimal costMicro_Nutrients;
	@Column(name="COST_LABOR",columnDefinition = "Decimal(19,4)")
	private BigDecimal costLabor;
	@Column(name="COST_EQUIPMENT",columnDefinition = "Decimal(19,4)")
	private BigDecimal costEquipment;
	@Column(name="COST_IRRIGATION",columnDefinition = "Decimal(19,4)")
	private BigDecimal costIrrigation;
	@Column(name="COST_RENT",columnDefinition = "Decimal(19,4)")
	private BigDecimal costRent;
	@Column(name="COST_PROFESSIONAL_SERVICES",columnDefinition = "Decimal(19,4)")
	private BigDecimal costProfessionalServices;
	@Column(name="COST_CROP_INSURANCE",columnDefinition = "Decimal(19,4)")
	private BigDecimal costCropInsurance;
	@Column(name="COST_FINANCING",columnDefinition = "Decimal(19,4)")
	private BigDecimal costFinancing;
	@Column(name="COST_OTHER",columnDefinition = "Decimal(19,4)")
	private BigDecimal costOther;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getCalculatedVarProductionCost() {
		return calculatedVarProductionCost;
	}
	public void setCalculatedVarProductionCost(
			BigDecimal calculatedVarProductionCost) {
		this.calculatedVarProductionCost = calculatedVarProductionCost;
	}
	public BigDecimal getCostSeed() {
		return costSeed;
	}
	public void setCostSeed(BigDecimal costSeed) {
		this.costSeed = costSeed;
	}
	public BigDecimal getCostHerbicide() {
		return costHerbicide;
	}
	public void setCostHerbicide(BigDecimal costHerbicide) {
		this.costHerbicide = costHerbicide;
	}
	public BigDecimal getCostInsecticide() {
		return costInsecticide;
	}
	public void setCostInsecticide(BigDecimal costInsecticide) {
		this.costInsecticide = costInsecticide;
	}
	public BigDecimal getCostFungicide() {
		return costFungicide;
	}
	public void setCostFungicide(BigDecimal costFungicide) {
		this.costFungicide = costFungicide;
	}
	public BigDecimal getCostFertilizer() {
		return costFertilizer;
	}
	public void setCostFertilizer(BigDecimal costFertilizer) {
		this.costFertilizer = costFertilizer;
	}
	public BigDecimal getCostMicro_Nutrients() {
		return costMicro_Nutrients;
	}
	public void setCostMicro_Nutrients(BigDecimal costMicro_Nutrients) {
		this.costMicro_Nutrients = costMicro_Nutrients;
	}
	public BigDecimal getCostLabor() {
		return costLabor;
	}
	public void setCostLabor(BigDecimal costLabor) {
		this.costLabor = costLabor;
	}
	public BigDecimal getCostEquipment() {
		return costEquipment;
	}
	public void setCostEquipment(BigDecimal costEquipment) {
		this.costEquipment = costEquipment;
	}
	public BigDecimal getCostIrrigation() {
		return costIrrigation;
	}
	public void setCostIrrigation(BigDecimal costIrrigation) {
		this.costIrrigation = costIrrigation;
	}
	public BigDecimal getCostRent() {
		return costRent;
	}
	public void setCostRent(BigDecimal costRent) {
		this.costRent = costRent;
	}
	public BigDecimal getCostProfessionalServices() {
		return costProfessionalServices;
	}
	public void setCostProfessionalServices(BigDecimal costProfessionalServices) {
		this.costProfessionalServices = costProfessionalServices;
	}
	public BigDecimal getCostCropInsurance() {
		return costCropInsurance;
	}
	public void setCostCropInsurance(BigDecimal costCropInsurance) {
		this.costCropInsurance = costCropInsurance;
	}
	public BigDecimal getCostFinancing() {
		return costFinancing;
	}
	public void setCostFinancing(BigDecimal costFinancing) {
		this.costFinancing = costFinancing;
	}
	public BigDecimal getCostOther() {
		return costOther;
	}
	public void setCostOther(BigDecimal costOther) {
		this.costOther = costOther;
	}
}
