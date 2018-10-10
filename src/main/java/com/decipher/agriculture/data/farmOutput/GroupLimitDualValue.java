package com.decipher.agriculture.data.farmOutput;

import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.agriculture.data.farm.FarmInfo;
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
@Entity(name = "GroupLimitDualValue")
@Table(name = "GROUP_LIMIT_DUAL_VALUE", uniqueConstraints = @UniqueConstraint(columnNames = "GROUP_LIMIT_DUAL_VALUE_ID"))
public class GroupLimitDualValue implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GROUP_LIMIT_DUAL_VALUE_ID")
	private Integer id;

	@OneToOne
	@JoinColumn(name = "CROPS_GROUP_ID", referencedColumnName = "CROPS_GROUP_ID")
	private CropsGroup cropsGroup;

	@Column(name = "MINIMUM_PRIMAL_VALUE")
	private Double minimumPrimalValue;

	@Column(name = "MINIMUM_DUAL_VALUE")
	private Double minimumDualValue;

	@Column(name = "MAXIMUM_PRIMAL_VALUE")
	private Double maximumPrimalValue;

	@Column(name = "MAXIMUM_DUAL_VALUE")
	private Double maximumDualValue;

	@ManyToOne
	@JoinColumn(name = "FARM_INFO_ID")
	/**
	 * @changed - Abhishek
	 * @date - 07-01-2016
	 * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
	 */
	@JsonIgnore
	private FarmInfo farmInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getMinimumPrimalValue() {
		return minimumPrimalValue;
	}

	public void setMinimumPrimalValue(Double minimumPrimalValue) {
		this.minimumPrimalValue = minimumPrimalValue;
	}

	public Double getMinimumDualValue() {
		return minimumDualValue;
	}

	public void setMinimumDualValue(Double minimumDualValue) {
		this.minimumDualValue = minimumDualValue;
	}

	public Double getMaximumPrimalValue() {
		return maximumPrimalValue;
	}

	public void setMaximumPrimalValue(Double maximumPrimalValue) {
		this.maximumPrimalValue = maximumPrimalValue;
	}

	public Double getMaximumDualValue() {
		return maximumDualValue;
	}

	public void setMaximumDualValue(Double maximumDualValue) {
		this.maximumDualValue = maximumDualValue;
	}

	public FarmInfo getFarmInfo() {
		return farmInfo;
	}

	public void setFarmInfo(FarmInfo farmInfo) {
		this.farmInfo = farmInfo;
	}

	public CropsGroup getCropsGroup() {
		return cropsGroup;
	}

	public void setCropsGroup(CropsGroup cropsGroup) {
		this.cropsGroup = cropsGroup;
	}

}