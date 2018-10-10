package com.decipher.agriculture.data.farm;

import com.decipher.agriculture.data.farmOutput.ResourceDualValue;
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
@Entity(name = "CropResourceUsage")
@Table(name = "CROP_RESOURCE_USAGE", uniqueConstraints = @UniqueConstraint(columnNames = "CROP_RESOURCE_USAGE_ID"))
public class CropResourceUsage implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CROP_RESOURCE_USAGE_ID")
	private Integer id;
	@Column(name = "CROP_RESOURCE_USE")
	private String cropResourceUse;

	@Column(name = "UOM_RESOURCES")
	private String uoMResource;

	@Column(name = "CROP_RESOURCE_USE_AMOUNT")
	private String cropResourceUseAmount;
	@ManyToOne
	@JoinColumn(name = "FARM_INFO_ID")
	/**
	 * @changed - Abhishek
	 * @date - 07-01-2016
	 * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
	 */
	@JsonIgnore
	private FarmInfo farmInfo;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "CROP_RESOURCE_USAGE_ID", referencedColumnName = "CROP_RES_USAGE_FIELD_VARI_ID")
	private CropResourceUsageFieldVariances cropResourceUsageFieldVariances;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "cropResourceUsage")
	@JoinColumn(name = "CROP_RESOURCE_USAGE_ID", referencedColumnName = "CROP_RESOURCE_USAGE_ID")
	private CropResourceUsageFieldDifferences differences;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "cropResourceUsage")
	@JoinColumn(name = "CROP_RESOURCE_USAGE_ID", referencedColumnName = "CROP_RESOURCE_USAGE_ID")
	private ResourceDualValue resourceDualValue;

	@Column(name = "IS_RESOURCE_ACTIVE")
	private Boolean isResourceActive;

	public Integer getId() {
		return id;
	}

	public String getUoMResource() {
		return uoMResource;
	}

	public void setUoMResource(String uoMResource) {
		this.uoMResource = uoMResource;
	}

	public String getCropResourceUseAmount() {
		return cropResourceUseAmount;
	}

	public void setCropResourceUseAmount(String cropResourceUseAmount) {
		this.cropResourceUseAmount = cropResourceUseAmount;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCropResourceUse() {
		return cropResourceUse;
	}

	public void setCropResourceUse(String cropResourceUse) {
		this.cropResourceUse = cropResourceUse;
	}

	public FarmInfo getFarmInfo() {
		return farmInfo;
	}

	public void setFarmInfo(FarmInfo farmInfo) {
		this.farmInfo = farmInfo;
	}

	public CropResourceUsageFieldVariances getCropResourceUsageFieldVariances() {
		return cropResourceUsageFieldVariances;
	}

	public void setCropResourceUsageFieldVariances(
			CropResourceUsageFieldVariances cropResourceUsageFieldVariances) {
		this.cropResourceUsageFieldVariances = cropResourceUsageFieldVariances;
	}

	public CropResourceUsageFieldDifferences getDifferences() {
		return differences;
	}

	public void setDifferences(CropResourceUsageFieldDifferences differences) {
		this.differences = differences;
	}

	public ResourceDualValue getResourceDualValue() {
		return resourceDualValue;
	}

	public void setResourceDualValue(ResourceDualValue resourceDualValue) {
		this.resourceDualValue = resourceDualValue;
	}

	public Boolean getResourceActive() {
		return isResourceActive;
	}

	public void setResourceActive(Boolean resourceActive) {
		isResourceActive = resourceActive;
	}
}
