package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farm.CropFieldChocies;
import com.decipher.agriculture.data.farm.CropType;

import java.io.Serializable;

public class CropFieldChociesView implements Cloneable, Serializable {
	private Integer id;
	private String cropFieldChoice;
	private FieldInfoView fieldName;
	private Integer cropId;
	private CropType cropType;

	public CropFieldChociesView() {

	}

	public CropFieldChociesView(CropFieldChocies chocies) {
		this.id = chocies.getId();
		this.cropFieldChoice = chocies.getCropFieldChoice();
		this.fieldName = new FieldInfoView(chocies.getName());
		CropType type = chocies.getCropType();

		if (type != null) {
			this.cropId = type.getId();
			this.cropType = type;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCropFieldChoice() {
		return cropFieldChoice;
	}

	public void setCropFieldChoice(String cropFieldChoice) {
		this.cropFieldChoice = cropFieldChoice;
	}

	public FieldInfoView getFieldName() {
		return fieldName;
	}

	public void setFieldName(FieldInfoView fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getCropId() {
		return cropId;
	}

	public void setCropId(Integer cropId) {
		this.cropId = cropId;
	}

	public CropType getCropType() {
		return cropType;
	}

	public void setCropType(CropType cropType) {
		this.cropType = cropType;
	}

	@Override
	public CropFieldChociesView clone() throws CloneNotSupportedException {
		return (CropFieldChociesView)super.clone();
	}
}
