package com.decipher.view.form.farmDetails;

import java.io.Serializable;
import java.util.Set;

import com.decipher.agriculture.data.farm.CropFieldChocies;
import com.decipher.agriculture.data.farm.CropYieldFieldVariances;
import com.decipher.agriculture.data.farm.FieldInfo;
import com.decipher.util.AgricultureStandardUtils;

@SuppressWarnings("rawtypes")
public class FieldInfoView implements Cloneable, Serializable {
	private Integer id;
	private String fieldName;
	private String fieldSize;

	private String lastCrop;
	private String fallow;
	private String divide;
	private String irrigate;
	private Integer fieldVariancesId;
	private Integer fieldIdForVariances;
	private Double expCropYieldField;
	private Double minCropYieldField;
	private Double maxCropYieldField;
	private Set<CropFieldChocies> chocies;

	// private static String sortingOrder = "DESC";
	public FieldInfoView() {

	}

	public FieldInfoView(FieldInfo fieldInfo) {
		this.id = fieldInfo.getId();
		this.fieldName = fieldInfo.getFieldName();
		this.fieldSize = fieldInfo.getFieldSize();
		this.lastCrop = fieldInfo.getLastCrop();
		this.fallow = fieldInfo.getFallow();
		this.divide = fieldInfo.getDivide();
		this.irrigate = fieldInfo.getIrrigate();
		this.chocies = fieldInfo.getChocies();

		CropYieldFieldVariances fieldVariances = fieldInfo
				.getCropYieldFieldVariances();
		if (fieldVariances != null) {
			this.fieldVariancesId = fieldVariances.getId();
			this.expCropYieldField = fieldVariances.getExpCropYieldField();
			this.maxCropYieldField = fieldVariances.getMaxCropYieldField();
			this.minCropYieldField = fieldVariances.getMinCropYieldField();
			FieldInfo info = fieldVariances.getFieldInfo();
			if (info != null) {
				this.fieldIdForVariances = info.getId();
			}
		}
	}

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
		// return AgricultureStandardUtils.commaSeparaterForNumber(fieldSize);
	}

	/*
	 * public String getFieldSizeStr() { return
	 * AgricultureStandardUtils.commaSeparaterForNumber(fieldSize); }
	 */
	public String getFieldSizeStr() {

		String fieldSize1 = (fieldSize.toString());
		// String s1=DECIMALFORMATTER.format(maxCropYieldFieldFloat1);
		return AgricultureStandardUtils.commaSeparaterForField(fieldSize1);

	}

	public Double getFieldSizeFloat() {
		return Double.parseDouble(fieldSize);
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

	public Integer getFieldVariancesId() {
		return fieldVariancesId;
	}

	public void setFieldVariancesId(Integer fieldVariancesId) {
		this.fieldVariancesId = fieldVariancesId;
	}

	public Integer getFieldIdForVariances() {
		return fieldIdForVariances;
	}

	public void setFieldIdForVariances(Integer fieldIdForVariances) {
		this.fieldIdForVariances = fieldIdForVariances;
	}

	public Double getExpCropYieldField() {
		return expCropYieldField;
	}

	public void setExpCropYieldField(Double expCropYieldField) {
		this.expCropYieldField = expCropYieldField;
	}

	public Double getMinCropYieldField() {
		return minCropYieldField;
	}

	public void setMinCropYieldField(Double minCropYieldField) {
		this.minCropYieldField = minCropYieldField;
	}

	public Double getMaxCropYieldField() {
		return maxCropYieldField;
	}

	public void setMaxCropYieldField(Double maxCropYieldField) {
		this.maxCropYieldField = maxCropYieldField;
	}

	public Set<CropFieldChocies> getChocies() {
		return chocies;
	}

	public void setChocies(Set<CropFieldChocies> chocies) {
		this.chocies = chocies;
	}
	/*public int compareTo(Object obj) {
	if (FieldInfoView.sortingOrder.equals("DESC")){
		return compareToDESC(obj);
	}
	else if (FieldInfoView.sortingOrder.equals("ASC")){
		return compareToASC(obj);
	}
	return -1;
}
public int compareToASC(Object obj) {
	FieldInfoView fieldInfoView = (FieldInfoView) obj;
	if (Long.parseLong(AgricultureStandardUtils.removeAllCommas(fieldSize)) == Long
			.parseLong(AgricultureStandardUtils
					.removeAllCommas(fieldInfoView.fieldSize)))
		return 0;
	else if (Long.parseLong(AgricultureStandardUtils
			.removeAllCommas(fieldSize)) > Long
			.parseLong(AgricultureStandardUtils
					.removeAllCommas(fieldInfoView.fieldSize)))
		return 1;
	else
		return -1;
}
public int compareToDESC(Object obj) {
	FieldInfoView fieldInfoView = (FieldInfoView) obj;
	if (Long.parseLong(AgricultureStandardUtils.removeAllCommas(fieldSize)) == Long
			.parseLong(AgricultureStandardUtils
					.removeAllCommas(fieldInfoView.fieldSize)))
		return 0;
	else if (Long.parseLong(AgricultureStandardUtils
			.removeAllCommas(fieldSize)) < Long
			.parseLong(AgricultureStandardUtils
					.removeAllCommas(fieldInfoView.fieldSize)))
		return 1;
	else
		return -1;
}

public static String getSortingOrder() {
	return sortingOrder;
}

public static void setSortingOrder(String sortingOrder) {
	FieldInfoView.sortingOrder = sortingOrder;
}*/

	@Override
	public FieldInfoView clone() throws CloneNotSupportedException {
		return (FieldInfoView)super.clone();
	}
}
