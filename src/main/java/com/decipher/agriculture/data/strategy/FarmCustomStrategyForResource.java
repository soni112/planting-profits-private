package com.decipher.agriculture.data.strategy;

import com.decipher.agriculture.data.farm.CropResourceUsage;
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
@Entity(name = "FarmCustomStrategyForResourse")
@Table(name = "FARM_CUSTOM_STRATEGY_FOR_RESOURSE", uniqueConstraints = @UniqueConstraint(columnNames = "FARM_CUSTOM_STRATEGY_FOR_RESOURSE_ID"))
public class FarmCustomStrategyForResource {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FARM_CUSTOM_STRATEGY_FOR_RESOURSE_ID")
	private Integer id;

	@Column(name = "RESOURSE_NAME")
	private String resourseName;

	@Column(name = "RESOURSE_VALUE")
	private long resourseValue;

	@OneToOne
	@JoinColumn(name = "CROP_RESOURCE_USAGE_ID")
	private CropResourceUsage cropResourceUsage;

	@ManyToOne
	@JoinColumn(name = "FARM_CUSTOM_STRATEGY_ID")
	/**
	 * @changed - Abhishek
	 * @date - 07-01-2016
	 * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
	 */
	@JsonIgnore
	private FarmCustomStrategy farmCustomStrategy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourseName() {
		return resourseName;
	}

	public void setResourseName(String resourseName) {
		this.resourseName = resourseName;
	}

	public long getResourseValue() {
		return resourseValue;
	}

	public void setResourseValue(long resourseValue) {
		this.resourseValue = resourseValue;
	}

	public CropResourceUsage getCropResourceUsage() {
		return cropResourceUsage;
	}

	public void setCropResourceUsage(CropResourceUsage cropResourceUsage) {
		this.cropResourceUsage = cropResourceUsage;
	}

	public FarmCustomStrategy getFarmCustomStrategy() {
		return farmCustomStrategy;
	}

	public void setFarmCustomStrategy(FarmCustomStrategy farmCustomStrategy) {
		this.farmCustomStrategy = farmCustomStrategy;
	}

}
