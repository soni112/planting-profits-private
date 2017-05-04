package com.decipher.agriculture.data.strategy;

import com.decipher.agriculture.data.farm.CropsGroup;
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
@Entity(name = "FarmCustomStrategyForGroup")
@Table(name = "FARM_CUSTOM_STRATEGY_FOR_GROUP", uniqueConstraints = @UniqueConstraint(columnNames = "FARM_CUSTOM_STRATEGY_FOR_GROUP_ID"))
public class FarmCustomStrategyForGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FARM_CUSTOM_STRATEGY_FOR_GROUP_ID")
	private Integer id;
	@Column(name = "GROUP_NAME")
	private String groupname;
	@Column(name = "MAXIMUM")
	private String maximum;
	@Column(name = "MINIMUM")
	private String minimum;

	@ManyToOne
	@JoinColumn(name = "FARM_CUSTOM_STRATEGY_ID")
	/**
	 * @changed - Abhishek
	 * @date - 07-01-2016
	 * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
	 */
	@JsonIgnore
	private FarmCustomStrategy farmCustomStrategy;

	@OneToOne
	@JoinColumn(name = "CROPS_GROUP_ID")
	private CropsGroup cropsGroup;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
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

	public FarmCustomStrategy getFarmCustomStrategy() {
		return farmCustomStrategy;
	}

	public void setFarmCustomStrategy(FarmCustomStrategy farmCustomStrategy) {
		this.farmCustomStrategy = farmCustomStrategy;
	}

	public CropsGroup getCropsGroup() {
		return cropsGroup;
	}

	public void setCropsGroup(CropsGroup cropsGroup) {
		this.cropsGroup = cropsGroup;
	}

}
