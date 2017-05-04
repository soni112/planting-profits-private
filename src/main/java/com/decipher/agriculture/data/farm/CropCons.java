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
@Entity(name = "CropCons")
@Table(name = "CROP_CONS", uniqueConstraints = @UniqueConstraint(columnNames = "CROP_CONS_ID"))
public class CropCons {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CROP_CONS_ID")
	private Integer id;
	@Column(name = "SPECIAL_CROPS_CONSIDERATION")
	private String specialCropConsiderations;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpecialCropConsiderations() {
		return specialCropConsiderations;
	}

	public void setSpecialCropConsiderations(String specialCropConsiderations) {
		this.specialCropConsiderations = specialCropConsiderations;
	}

}
