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
@Entity(name = "VariableCropProductionComponentUnitPrices")
@Table(name = "VAR_CROP_PRO_COMP_UNIT_PRICES", uniqueConstraints = @UniqueConstraint(columnNames = "VAR_CROP_PRO_COMP_UNIT_PRICES_ID"))
public class VariableCropProductionComponentUnitPrices implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VAR_CROP_PRO_COMP_UNIT_PRICES_ID")
	private Integer id;
	@Column(name="PRICE_SEED",columnDefinition="Decimal(19,4)")
	private BigDecimal priceSeed;
	@Column(name="PRICE_HERBICIDE",columnDefinition="Decimal(19,4)")
	private BigDecimal priceHerbicide;
	@Column(name="PRICE_INSECTICIDE",columnDefinition="Decimal(19,4)")
	private BigDecimal priceInsecticide;
	@Column(name="PRICE_FUNGICIDE",columnDefinition="Decimal(19,4)")
	private BigDecimal priceFungicide;
	@Column(name="PRICE_FERTILIZER",columnDefinition="Decimal(19,4)")
	private BigDecimal priceFertilizer;
	@Column(name="PRICE_MICRO_NUTRIENTS",columnDefinition="Decimal(19,4)")
	private BigDecimal priceMicro_Nutrients;
	@Column(name="PRICE_LABOR",columnDefinition="Decimal(19,4)")
	private BigDecimal priceLabor;
	@Column(name="PRICE_EQUIPMENT",columnDefinition="Decimal(19,4)")
	private BigDecimal priceEquipment;
	@Column(name="PRICE_IRRIGATION",columnDefinition="Decimal(19,4)")
	private BigDecimal priceIrrigation;
	@Column(name="PRICE_RENT",columnDefinition="Decimal(19,4)")
	private BigDecimal priceRent;
	@Column(name="PRICE_PROFESSIONAL_SERVICES",columnDefinition="Decimal(19,4)")
	private BigDecimal priceProfessionalServices;
	@Column(name="PRICE_CROP_INSURANCE",columnDefinition="Decimal(19,4)")
	private BigDecimal priceCropInsurance;
	@Column(name="PRICE_FINANCING",columnDefinition="Decimal(19,4)")
	private BigDecimal priceFinancing;
	@Column(name="PRICE_OTHER",columnDefinition="Decimal(19,4)")
	private BigDecimal priceOther;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getPriceSeed() {
		return priceSeed;
	}
	public void setPriceSeed(BigDecimal priceSeed) {
		this.priceSeed = priceSeed;
	}
	public BigDecimal getPriceHerbicide() {
		return priceHerbicide;
	}
	public void setPriceHerbicide(BigDecimal priceHerbicide) {
		this.priceHerbicide = priceHerbicide;
	}
	public BigDecimal getPriceInsecticide() {
		return priceInsecticide;
	}
	public void setPriceInsecticide(BigDecimal priceInsecticide) {
		this.priceInsecticide = priceInsecticide;
	}
	public BigDecimal getPriceFungicide() {
		return priceFungicide;
	}
	public void setPriceFungicide(BigDecimal priceFungicide) {
		this.priceFungicide = priceFungicide;
	}
	public BigDecimal getPriceFertilizer() {
		return priceFertilizer;
	}
	public void setPriceFertilizer(BigDecimal priceFertilizer) {
		this.priceFertilizer = priceFertilizer;
	}
	public BigDecimal getPriceMicro_Nutrients() {
		return priceMicro_Nutrients;
	}
	public void setPriceMicro_Nutrients(BigDecimal priceMicro_Nutrients) {
		this.priceMicro_Nutrients = priceMicro_Nutrients;
	}
	public BigDecimal getPriceLabor() {
		return priceLabor;
	}
	public void setPriceLabor(BigDecimal priceLabor) {
		this.priceLabor = priceLabor;
	}
	public BigDecimal getPriceEquipment() {
		return priceEquipment;
	}
	public void setPriceEquipment(BigDecimal priceEquipment) {
		this.priceEquipment = priceEquipment;
	}
	public BigDecimal getPriceIrrigation() {
		return priceIrrigation;
	}
	public void setPriceIrrigation(BigDecimal priceIrrigation) {
		this.priceIrrigation = priceIrrigation;
	}
	public BigDecimal getPriceRent() {
		return priceRent;
	}
	public void setPriceRent(BigDecimal priceRent) {
		this.priceRent = priceRent;
	}
	public BigDecimal getPriceProfessionalServices() {
		return priceProfessionalServices;
	}
	public void setPriceProfessionalServices(BigDecimal priceProfessionalServices) {
		this.priceProfessionalServices = priceProfessionalServices;
	}
	public BigDecimal getPriceCropInsurance() {
		return priceCropInsurance;
	}
	public void setPriceCropInsurance(BigDecimal priceCropInsurance) {
		this.priceCropInsurance = priceCropInsurance;
	}
	public BigDecimal getPriceFinancing() {
		return priceFinancing;
	}
	public void setPriceFinancing(BigDecimal priceFinancing) {
		this.priceFinancing = priceFinancing;
	}
	public BigDecimal getPriceOther() {
		return priceOther;
	}
	public void setPriceOther(BigDecimal priceOther) {
		this.priceOther = priceOther;
	}

}
