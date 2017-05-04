package com.decipher.view.form.strategy;

import com.decipher.agriculture.data.strategy.FarmCustomStrategyForResource;

public class FarmCustomStrategyForResourseView {
	private Integer id;
	private String resourseName;
	private long resourseValue;
	public FarmCustomStrategyForResourseView(){
		
	}
	public FarmCustomStrategyForResourseView(FarmCustomStrategyForResource farmCustomStrategyForResource)
	{
		this.id= farmCustomStrategyForResource.getId();
		this.resourseName= farmCustomStrategyForResource.getResourseName();
		this.resourseValue= farmCustomStrategyForResource.getResourseValue();
	}
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getResourseName(){
		return resourseName;
	}
	public void setResourseName(String resourseName){
		this.resourseName = resourseName;
	}
	public long getResourseValue(){
		return resourseValue;
	}
	public void setResourseValue(long resourseValue){
		this.resourseValue = resourseValue;
	}
	
	
}
