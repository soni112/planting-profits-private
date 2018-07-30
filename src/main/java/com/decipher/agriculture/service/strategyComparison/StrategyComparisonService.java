package com.decipher.agriculture.service.strategyComparison;

import com.decipher.agriculture.data.strategy.StrategyComparisonType;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Created by abhishek on 20/4/16.
 */
public interface StrategyComparisonService {
    JSONObject getGranularComparisonResult( FarmInfoView farmInfoView,int[] strategyIdArray)throws JSONException;

    JSONObject getGraphComparisonResult(StrategyComparisonType xAxisType, StrategyComparisonType yAxisType, FarmInfoView farmInfoView, int[] strategyIdArray) throws JSONException;

    JSONObject getGraphComparisonResult(int xAxisIndex, int yAxisIndex, FarmInfoView farmInfoView, int[] strategyIdArray) throws JSONException;

    Map<FarmCustomStrategyView, JSONObject> getStrategyDetailsForFarm(FarmInfoView farmInfoView) throws JSONException;

    boolean updateSessionWithStrategyOutput(FarmInfoView farmInfoView);

    JSONObject getStrategyComparisonDetails(FarmInfoView farmInfoView, int[] strategyIdArray) throws JSONException;

    JSONArray getVarianceGraphCustomisedDetails(FarmInfoView farmInfoView, int[] strategyIdArray, int[] cropPriceSelection, int[] cropYieldSelection, int[] cropProductionCostSelection, JSONObject rangeValuesObject) throws JSONException;

    JSONObject getAllScenarioAnalysisDetails(FarmInfoView farmInfoView, int farmId, int scenarioId) throws JSONException;

}
