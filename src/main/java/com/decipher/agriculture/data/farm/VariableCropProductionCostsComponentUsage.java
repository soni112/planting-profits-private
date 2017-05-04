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
@Entity(name = "VariableCropProductionCostsComponentUsage")
@Table(name = "VAR_CROP_PRO_COSTS_COMP_USAGE", uniqueConstraints = @UniqueConstraint(columnNames = "VAR_CROP_PRO_COSTS_COMP_USAGE_ID"))
public class VariableCropProductionCostsComponentUsage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VAR_CROP_PRO_COSTS_COMP_USAGE_ID")
	private Integer id;
	@Column(name="USAGE_PER_UOM_LAND")
	private Double usagePerUoMLand;
	@Column(name="OTHER")
	private String Other;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getUsagePerUoMLand() {
		return usagePerUoMLand;
	}
	public void setUsagePerUoMLand(Double usagePerUoMLand) {
		this.usagePerUoMLand = usagePerUoMLand;
	}
	public String getOther() {
		return Other;
	}
	public void setOther(String other) {
		Other = other;
	}

}
