/**
 *
 */
package com.decipher.agriculture.service.farmDetails.impl;

import com.decipher.agriculture.service.farmDetails.SensetivityAnalysisService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.senstivityAnalysis.SensitivityAnalysisCalculationDao;
import com.decipher.util.PlantingProfitLogger;

/**
 * @author Harshit Gupta
 */
@Repository
@Transactional
public class SensetivityAnalysisServiceImpl implements SensetivityAnalysisService {

    @Autowired
    private SensitivityAnalysisCalculationDao sensitivityAnalysisCalculationDao;

    @Override
    public JSONObject getSAStrategyByMultipleResource(int farmId,
                                                      String[] resourceArray, String[] cropsArray, String[] cropContractArray, String[] cropsGroupArray, String[] cropProposedArray) {
        JSONObject jsonObject = null;
        try {
            jsonObject = sensitivityAnalysisCalculationDao.calcluateFarmOutputStatistics(farmId, resourceArray, cropsArray, cropContractArray, cropsGroupArray, cropProposedArray);
        } catch (Exception exception) {
            PlantingProfitLogger.error(exception);
        }
        return jsonObject;
    }

    @Override
    public JSONObject getSAForCastGraphForSingleResource(int farmId,
                                                         String resourceName, long differenceValue, String cropName, String selectionType, String rangeType, Long currentPotentialProfit) {
        PlantingProfitLogger.info("Case2:" + differenceValue);
            return sensitivityAnalysisCalculationDao.getSAForCastGraphForSingleResource(farmId, resourceName, differenceValue, cropName, selectionType, rangeType, currentPotentialProfit);
    }
}
