package com.decipher.agriculture.data.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "FieldInfo")
@Table(name = "FIELD_INFO", uniqueConstraints = @UniqueConstraint(columnNames = "FIELD_INFO_ID"))
public class FieldInfo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FIELD_INFO_ID")
	private Integer id;
	@Column(name = "FIELD_NAME")
	private String fieldName;
	@Column(name = "FIELD_SIZE")
	private String fieldSize;

	@Column(name = "LAST_CROP")
	private String lastCrop;
	@Column(name = "FALLOW")
	private String fallow;
	@Column(name = "DIVIDE")
	private String divide;
	@Column(name = "IRRIGATE")
	private String irrigate;

	@ManyToOne
	@JoinColumn(name = "FARM_INFO_ID")
	/**
	 * @changed - Abhishek
	 * @date - 07-01-2016
	 * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
	 */
	@JsonIgnore
	private FarmInfo farmInfo;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fieldInfo")
	@JoinColumn(name = "FIELD_INFO_ID", referencedColumnName = "FIELD_INFO_ID")
	private CropYieldFieldVariances cropYieldFieldVariances;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name = "FIELD_INFO_ID", referencedColumnName = "FIELD_INFO_ID")
	private Set<CropFieldChocies> chocies = new HashSet<CropFieldChocies>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldSize() {
		return fieldSize;
	}

	public void setFieldSize(String fieldSize) {
		this.fieldSize = fieldSize;
	}

	public String getLastCrop() {
		return lastCrop;
	}

	public void setLastCrop(String lastCrop) {
		this.lastCrop = lastCrop;
	}

	public String getFallow() {
		return fallow;
	}

	public void setFallow(String fallow) {
		this.fallow = fallow;
	}

	public String getDivide() {
		return divide;
	}

	public void setDivide(String divide) {
		this.divide = divide;
	}

	public String getIrrigate() {
		return irrigate;
	}

	public void setIrrigate(String irrigate) {
		this.irrigate = irrigate;
	}

	public FarmInfo getFarmInfo() {
		return farmInfo;
	}

	public void setFarmInfo(FarmInfo farmInfo) {
		this.farmInfo = farmInfo;
	}

	public CropYieldFieldVariances getCropYieldFieldVariances() {
		return cropYieldFieldVariances;
	}

	public void setCropYieldFieldVariances(
			CropYieldFieldVariances cropYieldFieldVariances) {
		this.cropYieldFieldVariances = cropYieldFieldVariances;
	}

	public Set<CropFieldChocies> getChocies() {
		return chocies;
	}

	public void setChocies(Set<CropFieldChocies> chocies) {
		this.chocies = chocies;
	}
}
