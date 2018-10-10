package com.decipher.view.form.strategy;

import com.decipher.agriculture.data.strategy.FarmCustomStrategyForGroup;

import java.io.Serializable;

public class FarmCustomStrategyForGroupView implements Serializable {
	private Integer id;
	private String groupname;
	private String maximum;
	private String minimum;	
	public FarmCustomStrategyForGroupView(){
		
	}
	public FarmCustomStrategyForGroupView(FarmCustomStrategyForGroup farmCustomStrategyForGroup)
	{
		this.id=farmCustomStrategyForGroup.getId();
		this.groupname=farmCustomStrategyForGroup.getGroupname();
		this.maximum=farmCustomStrategyForGroup.getMaximum();
		this.minimum=farmCustomStrategyForGroup.getMinimum();
	}
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getGroupname(){
		return groupname;
	}
	public void setGroupname(String groupname){
		this.groupname = groupname;
	}
	public String getMaximum(){
		return maximum;
	}
	public void setMaximum(String maximum){
		this.maximum = maximum;
	}
	public String getMinimum(){
		return minimum;
	}
	public void setMinimum(String minimum){
		this.minimum = minimum;
	}
	
	
}
