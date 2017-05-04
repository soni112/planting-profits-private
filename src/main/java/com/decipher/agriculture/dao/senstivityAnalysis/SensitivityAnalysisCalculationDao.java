package com.decipher.agriculture.dao.senstivityAnalysis;


import org.json.simple.JSONObject;

public interface SensitivityAnalysisCalculationDao {

	JSONObject calcluateFarmOutputStatistics(int farmId,
                                             String[] resourceArray, String[] cropsArray, String[] cropContractArray,
                                             String[] cropsGroupArray, String[] cropProposedArray);

	JSONObject getSAForCastGraphForSingleResource(int farmId,
                                                  String resourceName, long differenceValue, String cropName, String selectionType, String rangeType, Long currentPotentialProfit);
}
