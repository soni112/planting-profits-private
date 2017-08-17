/**
 *
 */
package com.decipher.agriculture.service.farmDetails;


import org.json.simple.JSONObject;

/**
 * @author Harshit Gupta
 */
public interface SensetivityAnalysisService {

    JSONObject getSAStrategyByMultipleResource(int farmId,
                                               String[] resourceArray, String[] cropsArray, String[] cropContractArray, String[] cropsGroupArray, String[] cropProposedArray);

    JSONObject getSAForCastGraphForSingleResource(int farmId, String resourceName, long differenceValue, String cropName, String selectionType, String rangeType, Long currentPotentialProfit);

}