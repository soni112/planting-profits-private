package com.decipher.view.form.farmDetails;

import java.util.Date;

import com.decipher.agriculture.data.farm.FieldPlanningParameters;

public class FieldPlanningParametersView
{
	private Integer id;
	private Date lastCropped;
	
	
	public FieldPlanningParametersView()
	{
		
	}
	public FieldPlanningParametersView(FieldPlanningParameters fieldPlanningParameters)
	{
		this.id=fieldPlanningParameters.getId();
		this.lastCropped=fieldPlanningParameters.getLastCropped();
	}
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Date getLastCropped()
	{
		return lastCropped;
	}
	public void setLastCropped(Date lastCropped)
	{
		this.lastCropped = lastCropped;
	}
}
