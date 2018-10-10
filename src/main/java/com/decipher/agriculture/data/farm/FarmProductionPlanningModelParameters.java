package com.decipher.agriculture.data.farm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "FarmProductionPlanningModelParameters")
@Table(name = "FARM_PRODUCTION_PLANNING", uniqueConstraints = @UniqueConstraint(columnNames = "FARM_PRODUCTION_PLANNING_ID"))
public class FarmProductionPlanningModelParameters implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FARM_PRODUCTION_PLANNING_ID")
	private Integer id;
	@Column(columnDefinition = "TINYINT",name="PLAN_BY_FIELDS")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean planByFields;
	@Column(columnDefinition = "TINYINT",name="PLAN_BY_ACRES")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean planByAcres;
	@Column(columnDefinition = "TINYINT",name="IRRIGATION")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean irrigation;
	@Column(columnDefinition = "TINYINT",name="FORWARD_SALES")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean forwardSales;
	@Column(columnDefinition = "TINYINT",name="CROP_INSURANCE")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean cropInsurance;
	@Column(name="PLANTING_YEAR")
	private Date plantingYear;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getPlanByFields() {
		return planByFields;
	}
	public void setPlanByFields(Boolean planByFields) {
		this.planByFields = planByFields;
	}
	public Boolean getPlanByAcres() {
		return planByAcres;
	}
	public void setPlanByAcres(Boolean planByAcres) {
		this.planByAcres = planByAcres;
	}
	public Boolean getIrrigation() {
		return irrigation;
	}
	public void setIrrigation(Boolean irrigation) {
		this.irrigation = irrigation;
	}
	public Boolean getForwardSales() {
		return forwardSales;
	}
	public void setForwardSales(Boolean forwardSales) {
		this.forwardSales = forwardSales;
	}
	public Boolean getCropInsurance() {
		return cropInsurance;
	}
	public void setCropInsurance(Boolean cropInsurance) {
		this.cropInsurance = cropInsurance;
	}
	public Date getPlantingYear() {
		return plantingYear;
	}
	public void setPlantingYear(Date plantingYear) {
		this.plantingYear = plantingYear;
	}
	
}
