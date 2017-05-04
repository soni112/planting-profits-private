package com.decipher.agriculture.data.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "CropUnitOfMeasure")
@Table(name = "CROP_UNIT_OF_MEASURE", uniqueConstraints = @UniqueConstraint(columnNames = "CROP_UNIT_OF_MEASURE_ID"))
public class CropUnitOfMeasure {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CROP_UNIT_OF_MEASURE_ID")
	private Integer id;
	@Column(name = "UNIT_OF_MEASURE")
	private String unitOfMeasure;
	@OneToOne(fetch = FetchType.LAZY)
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

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public CropType getCropType() {
		return cropType;
	}

	public void setCropType(CropType cropType) {
		this.cropType = cropType;
	}

}
