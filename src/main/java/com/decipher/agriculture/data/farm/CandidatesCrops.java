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
@Entity(name = "CandidatesCrops")
@Table(name = "CANDIDATES_CROPS", uniqueConstraints = @UniqueConstraint(columnNames = "CANDIDATES_CROPS_ID"))
public class CandidatesCrops {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CANDIDATES_CROPS_ID")
	private Integer id;
	@Column(name = "FARM_NAME")
	private String farm_Name;
	@Column(name = "CROP_TYPE")
	private String crop_Type;
	@Column(name = "CANDIDATES_CROPS")
	private String candidate_Crops;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFarm_Name() {
		return farm_Name;
	}

	public void setFarm_Name(String farm_Name) {
		this.farm_Name = farm_Name;
	}

	public String getCrop_Type() {
		return crop_Type;
	}

	public void setCrop_Type(String crop_Type) {
		this.crop_Type = crop_Type;
	}

	public String getCandidate_Crops() {
		return candidate_Crops;
	}

	public void setCandidate_Crops(String candidate_Crops) {
		this.candidate_Crops = candidate_Crops;
	}

}
