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
@Entity(name = "CropResourceUsageFieldDifferences")
@Table(name = "CROP_RES_USAGE_FIELD_DIFF", uniqueConstraints = @UniqueConstraint(columnNames = "CROP_RES_USAGE_FIELD_DIFF_ID"))
public class CropResourceUsageFieldDifferences implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CROP_RES_USAGE_FIELD_DIFF_ID")
	private Integer id;
	@Column(name = "RESOURCE_OVERRIDE_AMOUNT")
	private String resourseOverrideAmount;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "CROP_TYPE_ID") private CropType cropType;
	 */

	@OneToOne
	@JoinColumn(name = "CROP_RESOURCE_USAGE_ID")
	private CropResourceUsage cropResourceUsage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourseOverrideAmount() {
		return resourseOverrideAmount;
	}

	public void setResourseOverrideAmount(String resourseOverrideAmount) {
		this.resourseOverrideAmount = resourseOverrideAmount;
	}

	public CropResourceUsage getCropResourceUsage() {
		return cropResourceUsage;
	}

	public void setCropResourceUsage(CropResourceUsage cropResourceUsage) {
		this.cropResourceUsage = cropResourceUsage;
	}
}
