package com.decipher.agriculture.data.farmOutput;

import javax.persistence.*;

import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farm.FieldInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "FarmOutputDetailsForField")
@Table(name = "FARM_OUTPUT_DETAILS_FOR_FIELD", uniqueConstraints = @UniqueConstraint(columnNames = "FARM_OUTPUT_DETAILS_FOR_FIELD_ID"))
public class FarmOutputDetailsForField {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FARM_OUTPUT_DETAILS_FOR_FIELD_ID")
	private Integer id;
	@Column(name = "CROP_PROFIT")
	private Double profit;

	@Column(name = "USED_ACRES")
	private Double usedAcres;
	@Column(name = "USED_CAPITAL")
	private Double usedCapital;

	@Column(name = "FOR_FIRM")
	private Boolean forFirm;

	@Column(name = "FOR_PROPOSED")
	private Boolean forProposed;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CROP_TYPE_ID", referencedColumnName = "CROP_TYPE_ID")
	private CropType cropType;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "FIELD_INFO_ID", referencedColumnName = "FIELD_INFO_ID")
	@JsonIgnore
	private FieldInfo fieldInfo;

	@ManyToOne
	@JoinColumn(name = "FARM_INFO_ID")
	private FarmInfo farmInfo;

	@Column(name = "MAXIMUM_PROFIT")
	private Double maximumProfit;

	@Column(name = "MINIMUM_PROFIT")
	private Double minimumProfit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public CropType getCropType() {
		return cropType;
	}

	public void setCropType(CropType cropType) {
		this.cropType = cropType;
	}

	public Double getUsedAcres() {
		return usedAcres;
	}

	public void setUsedAcres(Double usedAcres2) {
		this.usedAcres = usedAcres2;
	}

	public Double getUsedCapital() {
		return usedCapital;
	}

	public void setUsedCapital(Double usedCapital) {
		this.usedCapital = usedCapital;
	}

	public FarmInfo getFarmInfo() {
		return farmInfo;
	}

	public void setFarmInfo(FarmInfo farmInfo) {
		this.farmInfo = farmInfo;
	}

	public FieldInfo getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(FieldInfo fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public Boolean getForFirm() {
		return forFirm;
	}

	public void setForFirm(Boolean forFirm) {
		this.forFirm = forFirm;
	}

	public Boolean getForProposed() {
		return forProposed;
	}

	public void setForProposed(Boolean forProposed) {
		this.forProposed = forProposed;
	}

	public Double getMaximumProfit() {
		return maximumProfit;
	}

	public void setMaximumProfit(Double maximumProfit) {
		this.maximumProfit = maximumProfit;
	}

	public Double getMinimumProfit() {
		return minimumProfit;
	}

	public void setMinimumProfit(Double minimumProfit) {
		this.minimumProfit = minimumProfit;
	}
}