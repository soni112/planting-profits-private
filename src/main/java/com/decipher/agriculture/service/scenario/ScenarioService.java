package com.decipher.agriculture.service.scenario;

import com.decipher.agriculture.bean.StrategyDataBean;
import com.decipher.agriculture.data.scenario.FarmStrategyScenario;
import com.decipher.util.JsonResponse;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by raja on 12/25/15.
 */

public interface ScenarioService {
    boolean isExist(int scenarioId);

    boolean isExist(int scenarioId, int strategyId);

    boolean isExist(int scenarioId, int strategyId, int farmId);

    boolean isExist(int scenarioId, int strategyId, int farmId, int userId);

    boolean isExistExcept(int scenarioId, int strategyId, int farmId, int userId, String scenarioName);

    boolean isExist(int strategyId, int farmId, int userId, String scenarioName);

    JSONObject getJsonFrom(FarmStrategyScenarioView farmStrategyScenarioView);

    JsonResponse saveScenario(String scenarioName, int farmId, String scenarioComment, String cropSpecificComment, int strategyId, int global_crop_price, int global_crop_yields, int global_crop_prod_cost, String cropSpecific);

    JsonResponse updateScenario(int scenarioId, String scenarioName, String scenarioComment, String cropSpecificComment, int farmId, int strategyId, int global_crop_price, int global_crop_yields, int global_crop_prod_cost, String cropSpecific);

    FarmStrategyScenarioView getScenarioById(int scenarioId);

    FarmStrategyScenarioView getScenarioByNameFarmStrategy(String scenarioName, int farmId, int strategyId);

    List<FarmStrategyScenarioView> getAllScenarioByStrategy(int strategyId);

    boolean deleteScenarioCropSpecificData(FarmStrategyScenario farmStrategyScenario);

    /**
     * @added - Abhishek
     * @date - 06-01-2016
     */
    List<CropTypeView> getScenarioCropValuesByStrategyBean(StrategyDataBean strategyDataBean, FarmStrategyScenarioView farmStrategyScenarioView);

    /**
     * @added - Abhishek
     * @date - 06-01-2016
     */
    JSONObject getFarmOutputDetails(StrategyDataBean strategyDataBean) throws JSONException;

    JSONObject getScenarioComparisonDetails(FarmInfoView farmInfoView, int scenarioId, int[] strategyIdArray);

}
