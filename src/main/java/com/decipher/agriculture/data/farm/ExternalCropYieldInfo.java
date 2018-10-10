package com.decipher.agriculture.data.farm;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "ExternalCropYieldInfo")
@Table(name = "EXTERNAL_CROP_YIELD_INFO", uniqueConstraints = @UniqueConstraint(columnNames = "EXTERNAL_CROP_YIELD_INFO_ID"))
public class ExternalCropYieldInfo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXTERNAL_CROP_YIELD_INFO_ID")
	private Integer id;
	@Column(name = "EXTERNAL_CROP_YIELD")
	private Double externalCropYield;
	@Column(name = "EXTERNAL_MIN_CROP_YIELD")
	private Double externalMinCropYield;
	@Column(name = "EXTERNAL_MAX_CROP_YIELD")
	private Double externalMaxCropYield;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getExternalCropYield() {
		return externalCropYield;
	}

	public void setExternalCropYield(Double externalCropYield) {
		this.externalCropYield = externalCropYield;
	}

	public Double getExternalMinCropYield() {
		return externalMinCropYield;
	}

	public void setExternalMinCropYield(Double externalMinCropYield) {
		this.externalMinCropYield = externalMinCropYield;
	}

	public Double getExternalMaxCropYield() {
		return externalMaxCropYield;
	}

	public void setExternalMaxCropYield(Double externalMaxCropYield) {
		this.externalMaxCropYield = externalMaxCropYield;
	}

}
