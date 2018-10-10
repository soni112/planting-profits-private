package com.decipher.agriculture.data.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "InternalCropYieldInfo")
@Table(name = "INTERNAL_CROP_YIELD_INFO", uniqueConstraints = @UniqueConstraint(columnNames = "INTERNAL_CROP_YIELD_INFO_ID"))
public class InternalCropYieldInfo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INTERNAL_CROP_YIELD_INFO_ID")
	private Integer id;
	@Column(name = "INT_EXP_CROP_YIELD")
	private String intExpCropYield;
	@Column(name = "INT_MIN_CROP_YIELD")
	private String intMinCropYield;
	@Column(name = "INT_MAX_CROP_YIELD")
	private String intMaxCropYield;
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CROP_TYPE_ID")
	/**
	 * @changed - Abhishek
	 * @date - 07-01-2016
	 * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
	 */
	@JsonIgnore
	private CropType cropType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIntExpCropYield() {
		return intExpCropYield;
	}
	public void setIntExpCropYield(String intExpCropYield) {
		this.intExpCropYield = intExpCropYield;
	}
	public String getIntMinCropYield() {
		return intMinCropYield;
	}
	public void setIntMinCropYield(String intMinCropYield) {
		this.intMinCropYield = intMinCropYield;
	}
	public String getIntMaxCropYield() {
		return intMaxCropYield;
	}
	public void setIntMaxCropYield(String intMaxCropYield) {
		this.intMaxCropYield = intMaxCropYield;
	}
	public CropType getCropType()
	{
		return cropType;
	}
	public void setCropType(CropType cropType)
	{
		this.cropType = cropType;
	}

}
