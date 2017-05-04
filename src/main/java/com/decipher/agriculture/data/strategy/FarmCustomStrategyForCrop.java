package com.decipher.agriculture.data.strategy;

import com.decipher.agriculture.data.farm.CropType;
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
@Entity(name = "FarmCustomStrategyForCrop")
@Table(name = "FARM_CUSTOM_STRATEGY_FOR_CROP", uniqueConstraints = @UniqueConstraint(columnNames = "FARM_CUSTOM_STRATEGY_FOR_CROP_ID"))
public class FarmCustomStrategyForCrop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FARM_CUSTOM_STRATEGY_FOR_CROP_ID")
	private Integer id;
	@Column(name = "CROP_NAME")
	private String cropname;
	@Column(name = "MAXIMUM")
	private String maximum;
	@Column(name = "MINIMUM")
	private String minimum;
	@Column(name = "PROPOSED_CHECKED")
	private Boolean proposedchecked;
	@Column(name = "CONTRACT_CHECKED")
	private Boolean contractchecked;

	@ManyToOne
	@JoinColumn(name = "FARM_CUSTOM_STRATEGY_ID")
	/**
	 * @changed - Abhishek
	 * @date - 07-01-2016
	 * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
	 */
	@JsonIgnore
	private FarmCustomStrategy farmCustomStrategy;

	@ManyToOne
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

	public String getCropname() {
		return cropname;
	}

	public void setCropname(String cropname) {
		this.cropname = cropname;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public Boolean getProposedchecked() {
		return proposedchecked;
	}

	public void setProposedchecked(Boolean proposedchecked) {
		this.proposedchecked = proposedchecked;
	}

	public Boolean getContractchecked() {
		return contractchecked;
	}

	public void setContractchecked(Boolean contractchecked) {
		this.contractchecked = contractchecked;
	}

	public FarmCustomStrategy getFarmCustomStrategy() {
		return farmCustomStrategy;
	}

	public void setFarmCustomStrategy(FarmCustomStrategy farmCustomStrategy) {
		this.farmCustomStrategy = farmCustomStrategy;
	}

	public CropType getCropType() {
		return cropType;
	}

	public void setCropType(CropType cropType) {
		this.cropType = cropType;
	}
}