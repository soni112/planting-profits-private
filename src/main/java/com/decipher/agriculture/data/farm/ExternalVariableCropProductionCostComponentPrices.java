package com.decipher.agriculture.data.farm;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "ExternalVariableCropProductionCostComponentPrices")
@Table(name = "EXT_VAR_CROP_PRO_COST_COMP_PRICES", uniqueConstraints = @UniqueConstraint(columnNames = "EXT_VAR_CROP_PRO_COST_COMP_PRICES_ID"))
public class ExternalVariableCropProductionCostComponentPrices implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXT_VAR_CROP_PRO_COST_COMP_PRICES_ID")
	private Integer id;
	@Column(name = "PRODUCT", columnDefinition = "Decimal(19,4)")
	private BigDecimal product;
	@Column(name = "USAGE_PER_UOM_LAND", columnDefinition = "Decimal(19,4)")
	private BigDecimal usagePerUoMLand;
	@Column(name = "EXT_UNIT_PRODUCT_PRICE", columnDefinition = "Decimal(19,4)")
	private BigDecimal extUnitProductPrice;
	@Column(name = "OFFEROR_SOURCE")
	private String offeror_Source;
	@Column(name = "DATE")
	private Date date;
	@Column(name = "EXPIRATION")
	private Date expiration;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getProduct() {
		return product;
	}

	public void setProduct(BigDecimal product) {
		this.product = product;
	}

	public BigDecimal getUsagePerUoMLand() {
		return usagePerUoMLand;
	}

	public void setUsagePerUoMLand(BigDecimal usagePerUoMLand) {
		this.usagePerUoMLand = usagePerUoMLand;
	}

	public BigDecimal getExtUnitProductPrice() {
		return extUnitProductPrice;
	}

	public void setExtUnitProductPrice(BigDecimal extUnitProductPrice) {
		this.extUnitProductPrice = extUnitProductPrice;
	}

	public String getOfferor_Source() {
		return offeror_Source;
	}

	public void setOfferor_Source(String offeror_Source) {
		this.offeror_Source = offeror_Source;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
}