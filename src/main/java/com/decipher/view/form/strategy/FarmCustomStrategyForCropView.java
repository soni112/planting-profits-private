package com.decipher.view.form.strategy;

import com.decipher.agriculture.data.strategy.FarmCustomStrategyForCrop;

public class FarmCustomStrategyForCropView {
	private Integer id;
	private String cropname;
	private String maximum;
	private String minimum;	
	private Boolean proposedchecked;
	private Boolean contractchecked;
	
	public FarmCustomStrategyForCropView(){
		
	}
	public FarmCustomStrategyForCropView(FarmCustomStrategyForCrop farmCustomStrategyForCrop)
	{
		this.id=farmCustomStrategyForCrop.getId();
		this.cropname=farmCustomStrategyForCrop.getCropname();
		this.maximum=farmCustomStrategyForCrop.getMaximum();
		this.minimum=farmCustomStrategyForCrop.getMinimum();
		this.proposedchecked=farmCustomStrategyForCrop.getProposedchecked();
		this.contractchecked=farmCustomStrategyForCrop.getContractchecked();
		
	}
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getCropname(){
		return cropname;
	}
	public void setCropname(String cropname){
		this.cropname = cropname;
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
	public Boolean getProposedchecked(){
		return proposedchecked;
	}
	public void setProposedchecked(Boolean proposedchecked){
		this.proposedchecked = proposedchecked;
	}
	public Boolean getContractchecked(){
		return contractchecked;
	}
	public void setContractchecked(Boolean contractchecked){
		this.contractchecked = contractchecked;
	}
	
	
}
