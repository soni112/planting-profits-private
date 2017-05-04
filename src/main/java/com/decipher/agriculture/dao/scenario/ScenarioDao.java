package com.decipher.agriculture.dao.scenario;

import com.decipher.agriculture.data.scenario.FarmStrategyScenario;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;

import java.util.List;

/**
 * Created by raja on 12/25/15.
 */
public interface ScenarioDao {
    FarmStrategyScenarioView getScenarioById(int scenarioId);
    FarmStrategyScenarioView getScenarioByNameFarmStrategy(String scenarioName, int strategyId, int farmId);
    List<FarmStrategyScenarioView> getAllScenarioByStrategy(int strategyId);
    long saveScenario(FarmStrategyScenario farmStrategyScenario, int strategyId);
    boolean updateScenario(int scenarioId, FarmStrategyScenario farmStrategyScenario);
    boolean deleteScenarioCropCpecificData(FarmStrategyScenario farmStrategyScenario);
}