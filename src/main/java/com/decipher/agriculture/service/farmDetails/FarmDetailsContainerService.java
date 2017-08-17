package com.decipher.agriculture.service.farmDetails;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * Created by abhishek on 3/5/16.
 */
public interface FarmDetailsContainerService {

    void updateAllDetails() throws JSONException;

    Map<FarmInfoView, Object> getFarmDetails(FarmInfoView farmInfoView);

    boolean updateFarmDetails(Farm farm, Account currentUser) throws JSONException;

    JSONObject getBaseLineDetails(Farm farm);

    Map<FarmCustomStrategyView, JSONObject> getAllStrategiesDetails(FarmInfoView farmInfoView);

    Map<FarmCustomStrategyView, JSONObject> getSpecificStrategiesDetails(FarmInfoView farmInfoView, int[] strategyIdArray);

    JSONObject getStrategyDetails(FarmInfoView farmInfoView, FarmCustomStrategyView farmCustomStrategyView);

    Set<FarmCustomStrategyView> getAllStrategiesForFarm(Farm farm);

    boolean updateStrategyDetails(FarmInfoView farmInfoView, FarmCustomStrategyView farmCustomStrategyView);

    boolean deleteStrategies(Farm farm, int[] straetgyIdArray);


    Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>> getAllScenarioDetails(FarmInfoView farmInfoView);

    Set<FarmStrategyScenarioView> getAllScenarioList(FarmInfoView farmInfoView, FarmCustomStrategyView farmCustomStrategyView);

    Map<FarmCustomStrategyView, JSONObject> getScenarioDetails(FarmInfoView farmInfoView, FarmStrategyScenarioView farmStrategyScenarioView);

    /**
     * @param farmInfoView
     * @param farmCustomStrategyView
     * @return list containing all scenarios for given strategy
     * @added - Abhishek
     * @date - 09-10-2016
     * @desc - for getting all scenarios for the given strategy
     */
    Map<FarmStrategyScenarioView, JSONObject> getAllScenarioForStrategy(FarmInfoView farmInfoView, FarmCustomStrategyView farmCustomStrategyView);

    boolean updateScenarioDetails(FarmInfoView farmInfoView, FarmStrategyScenarioView farmStrategyScenarioView);

    boolean deleteSelectedFarmDetails(int[] farmIdsArray);

    boolean deleteAllFarmDetails(Account account);

    Farm getFarmById(int farmId);

    Set<Farm> getAllFarmForUser(Account account);

}
