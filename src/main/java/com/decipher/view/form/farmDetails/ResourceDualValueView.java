package com.decipher.view.form.farmDetails;

import com.decipher.agriculture.data.farmOutput.ResourceDualValue;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ResourceDualValueView implements Serializable {

	private Double primalValue;

	private Double dualValue;
	
	private String resourceName;

	private String resourceUnitName;

	transient private long profitPerUnit;

	public ResourceDualValueView(ResourceDualValue resourceDualValue) {
		/**
		 * @changed - Abhishek
		 * @date - 31-12-2015
		 */
		/*this.primalValue = resourceDualValue.getPrimalValue();
		this.dualValue = resourceDualValue.getDualValue();*/
		DecimalFormat formatter = new DecimalFormat("#.00");
		this.primalValue = Double.parseDouble(formatter.format(resourceDualValue.getPrimalValue()));
		this.dualValue = Double.parseDouble(formatter.format(resourceDualValue.getDualValue()));

		this.resourceName = resourceDualValue.getCropResourceUsage().getCropResourceUse();
		this.setResourceUnitName(resourceDualValue.getCropResourceUsage().getUoMResource());
	}
	
	public Double getPrimalValue() {
		return primalValue;
	}

	public void setPrimalValue(Double primalValue) {
		this.primalValue = primalValue;
	}

	public Double getDualValue() {
		return dualValue;
	}

	public void setDualValue(Double dualValue) {
		this.dualValue = dualValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public long getProfitPerUnit() {
		return profitPerUnit;
	}

	public void setProfitPerUnit(long profitPerUnit) {
		this.profitPerUnit = profitPerUnit;
	}

	public String getResourceUnitName() {
		return resourceUnitName;
	}

	public void setResourceUnitName(String resourceUnitName) {
		this.resourceUnitName = resourceUnitName;
	}

}