package com.decipher.agriculture.data.farm;

public enum PlanByStrategy {
	PLAN_BY_FIELDS(0), PLAN_BY_ACRES(1), BOTH(2);

	private Integer strategy;

	private PlanByStrategy(Integer s) {
		strategy = s;
	}

	public Integer getStatusCode() {
		return strategy;
	}
	public String getStatusCodeStr() {
		return toString();
	}
}
