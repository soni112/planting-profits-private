package com.decipher.agriculture.data.farm;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "VariableCropProductionCostsComponentsUoM")
@Table(name = "VAR_CROP_PRO_COSTS_COMP_UOM", uniqueConstraints = @UniqueConstraint(columnNames = "VAR_CROP_PRO_COSTS_COMP_UOM_ID"))
public class VariableCropProductionCostsComponentsUoM {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VAR_CROP_PRO_COSTS_COMP_UOM_ID")
	private Integer id;
	@Column(name="SEED")
	private String seed;
	@Column(name="HERBICIDE")
	private String herbicide;
	@Column(name="INSECTICIDE")
	private String insecticide;
	@Column(name="FUNGICIDE")
	private String fungicide;
	@Column(name="FERTILIZER")
	private String fertilizer;
	@Column(name="MICRO_NUTRIENTS")
	private String micro_Nutrients;
	@Column(name="LABOR")
	private String labor;
	@Column(name="EQUIPMENT")
	private String equipment;
	@Column(name="IRRIGATION")
	private String irrigation;
	@Column(name="RENT")
	private String rent;
	@Column(name="PROFESSIONAL_SERVICES")
	private String professionalServices;
	@Column(name="CROP_INSURANCE")
	private String cropInsurance;
	@Column(name="FINANCING")
	private String financing;
	@Column(name="OTHER")
	private String other;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSeed() {
		return seed;
	}
	public void setSeed(String seed) {
		this.seed = seed;
	}
	public String getHerbicide() {
		return herbicide;
	}
	public void setHerbicide(String herbicide) {
		this.herbicide = herbicide;
	}
	public String getInsecticide() {
		return insecticide;
	}
	public void setInsecticide(String insecticide) {
		this.insecticide = insecticide;
	}
	public String getFungicide() {
		return fungicide;
	}
	public void setFungicide(String fungicide) {
		this.fungicide = fungicide;
	}
	public String getFertilizer() {
		return fertilizer;
	}
	public void setFertilizer(String fertilizer) {
		this.fertilizer = fertilizer;
	}
	public String getMicro_Nutrients() {
		return micro_Nutrients;
	}
	public void setMicro_Nutrients(String micro_Nutrients) {
		this.micro_Nutrients = micro_Nutrients;
	}
	public String getLabor() {
		return labor;
	}
	public void setLabor(String labor) {
		this.labor = labor;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getIrrigation() {
		return irrigation;
	}
	public void setIrrigation(String irrigation) {
		this.irrigation = irrigation;
	}
	public String getRent() {
		return rent;
	}
	public void setRent(String rent) {
		this.rent = rent;
	}
	public String getProfessionalServices() {
		return professionalServices;
	}
	public void setProfessionalServices(String professionalServices) {
		this.professionalServices = professionalServices;
	}
	public String getCropInsurance() {
		return cropInsurance;
	}
	public void setCropInsurance(String cropInsurance) {
		this.cropInsurance = cropInsurance;
	}
	public String getFinancing() {
		return financing;
	}
	public void setFinancing(String financing) {
		this.financing = financing;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}

}
