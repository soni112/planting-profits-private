package com.decipher.agriculture.data.farm;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "ExternalCropPriceInfo")
@Table(name = "EXTERNAL_CROP_PRICE_INFO", uniqueConstraints = @UniqueConstraint(columnNames = "EXTERNAL_CROP_PRICE_INFO_ID"))
public class ExternalCropPriceInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXTERNAL_CROP_PRICE_INFO_ID")
	private Integer id;
	@Column(name = "INT_EXP_CROP_PRICE", columnDefinition = "Decimal(19,4)")
	private BigDecimal intExpCropPrice;
	@Column(name = "INT_MIN_CROP_PRICE", columnDefinition = "Decimal(19,4)")
	private BigDecimal intMinCropPrice;
	@Column(name = "INT_MAX_CROP_PRICE", columnDefinition = "Decimal(19,4)")
	private BigDecimal intMaxCropPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getIntExpCropPrice() {
		return intExpCropPrice;
	}

	public void setIntExpCropPrice(BigDecimal intExpCropPrice) {
		this.intExpCropPrice = intExpCropPrice;
	}

	public BigDecimal getIntMinCropPrice() {
		return intMinCropPrice;
	}

	public void setIntMinCropPrice(BigDecimal intMinCropPrice) {
		this.intMinCropPrice = intMinCropPrice;
	}

	public BigDecimal getIntMaxCropPrice() {
		return intMaxCropPrice;
	}

	public void setIntMaxCropPrice(BigDecimal intMaxCropPrice) {
		this.intMaxCropPrice = intMaxCropPrice;
	}

}
