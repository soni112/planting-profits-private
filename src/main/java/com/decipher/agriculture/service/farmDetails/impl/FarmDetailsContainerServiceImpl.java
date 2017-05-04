package com.decipher.agriculture.service.farmDetails.impl;

import com.decipher.agriculture.bean.StrategyDataBean;
import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.agriculture.service.scenario.ScenarioService;
import com.decipher.agriculture.service.strategy.FarmCustomStrategyService;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by abhishek on 5/5/16.
 */
@Repository
@Transactional
@SuppressWarnings("unchecked")
public class FarmDetailsContainerServiceImpl implements FarmDetailsContainerService {
    private static final String BASELINE_DETAILS = "BaselineDetails";
    private static final String STRATEGY_DETAILS = "StrategyDetails";
    private static final String SCENARIO_DETAILS = "ScenarioDetails";

    private Map<Account, Object> accountDataContainerMap = Collections.synchronizedMap(new TreeMap<Account, Object>());

    @Autowired
    private ScenarioService scenarioService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FarmCustomStrategyService farmCustomStrategyService;

    @Override
    public void updateAllDetails() throws JSONException {
        synchronized (accountDataContainerMap) {
            PlantingProfitLogger.info("Started getting all information of all farms for all accounts into middleware");
            Set<Account> usersSet = accountService.getAllUsers();

            for (Account account : usersSet) {
                PlantingProfitLogger.info("Getting all farms details for : " + account.getEmail_Address());

                Map<Farm, Object> farmDataContainerMap = Collections.synchronizedMap(new TreeMap<Farm, Object>());
                Set<Farm> farmList = account.getFarmList();
                if (farmList != null && farmList.size() > 0) {
                    for (Farm farm : farmList) {
                        PlantingProfitLogger.info("Processing farm details for " + farm.getFarmName() + " farm");

                        //  adding all details of farms i.e. Baseline Strategy, Custom Strategies and Scenario details in collection
                        farmDataContainerMap.put(farm, getDetailsForFarmFromDatabase(farm));

                        PlantingProfitLogger.info("Completed processing of farm details for " + farm.getFarmName() + " farm");
                    }
                }

                //  adding all farms information of account to collection
                accountDataContainerMap.put(account, farmDataContainerMap);
                PlantingProfitLogger.info("completed getting all farms details for : " + account.getEmail_Address());

            }
        }
    }

    @Override
    public Map<FarmInfoView, Object> getFarmDetails(FarmInfoView farmInfoView) {

        Account currentUser = accountService.getCurrentUser();

        Map<FarmInfoView, Object> farmDetails = (Map<FarmInfoView, Object>) accountDataContainerMap.get(currentUser);

        farmDetails = (Map<FarmInfoView, Object>) farmDetails.get(farmInfoView);

        return farmDetails;

    }

    @Override
    public JSONObject getBaseLineDetails(Farm farm) {
        Account currentUser = accountService.getCurrentUser();
        Map<Farm, Object> farmDetails = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);
        Map<String, Object> farmOutputDetails = null;
        try {
            if (farmDetails != null && farmDetails.get(farm) != null) {
                farmOutputDetails = (Map<String, Object>) farmDetails.get(farm);

            } else {
                Map<Farm, Object> farmDataContainerMap = Collections.synchronizedMap(new TreeMap<Farm, Object>());
                synchronized (farmDataContainerMap) {
                    farmOutputDetails = getDetailsForFarmFromDatabase(farm);
                    farmDataContainerMap.put(farm, farmOutputDetails);
                    accountDataContainerMap.put(currentUser, farmDataContainerMap);

                }
            }
        } catch (JSONException e) {
            PlantingProfitLogger.error(e);
        }


        return (JSONObject) farmOutputDetails.get(BASELINE_DETAILS);
    }

    @Override
    public Map<FarmCustomStrategyView, JSONObject> getAllStrategiesDetails(FarmInfoView farmInfoView) {
        Account currentUser = accountService.getCurrentUser();
        Map<Farm, Object> farmDetails = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);
        Farm farm = farmInfoView.getFarmInfo().getFarm();

        Map<String, Object> farmOutputDetails = (Map<String, Object>) farmDetails.get(farm);

        return (Map<FarmCustomStrategyView, JSONObject>) farmOutputDetails.get(STRATEGY_DETAILS);
    }

    @Override
    public Map<FarmCustomStrategyView, JSONObject> getSpecificStrategiesDetails(FarmInfoView farmInfoView, int[] strategyIdArray) {
        Map<FarmCustomStrategyView, JSONObject> allStrategiesDetails = getAllStrategiesDetails(farmInfoView);

        Map<FarmCustomStrategyView, JSONObject> specificStrategyDetails = new TreeMap<>();

        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = allStrategiesDetails.entrySet();
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {

            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();
            for (int strategyId : strategyIdArray) {

                if (farmCustomStrategyView.getId() == strategyId) {
                    specificStrategyDetails.put(farmCustomStrategyView, entry.getValue());
                }
            }

        }

        return specificStrategyDetails;

    }

    @Override
    public JSONObject getStrategyDetails(FarmInfoView farmInfoView, FarmCustomStrategyView farmCustomStrategyView) {
        Account currentUser = accountService.getCurrentUser();
        Map<Farm, Object> allFarmDetails = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);
        Farm farm = farmInfoView.getFarmInfo().getFarm();

        Map<String, Object> farmDetails = (Map<String, Object>) allFarmDetails.get(farm);

        Map<FarmCustomStrategyView, JSONObject> strategyDetails = (Map<FarmCustomStrategyView, JSONObject>) farmDetails.get(STRATEGY_DETAILS);

        return strategyDetails.get(farmCustomStrategyView);

    }

    @Override
    public Set<FarmCustomStrategyView> getAllStrategiesForFarm(Farm farm) {
        Account currentUser = accountService.getCurrentUser();

        Map<Farm, Object> farmDetails = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);

        Map<String, Object> allDetails = (Map<String, Object>) farmDetails.get(farm);
        if (allDetails != null) {
            Map<FarmCustomStrategyView, JSONObject> strategyDetails = (Map<FarmCustomStrategyView, JSONObject>) allDetails.get(STRATEGY_DETAILS);

            return strategyDetails.keySet();
        } else {
            return null;
        }

    }

    @Override
    public Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>> getAllScenarioDetails(FarmInfoView farmInfoView) {
        Account currentUser = accountService.getCurrentUser();
        Map<Farm, Object> allFarmDetails = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);
        Farm farm = farmInfoView.getFarmInfo().getFarm();

        Map<String, Object> farmDetails = (Map<String, Object>) allFarmDetails.get(farm);

        return (Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>>) farmDetails.get(SCENARIO_DETAILS);
    }

    @Override
    public Set<FarmStrategyScenarioView> getAllScenarioList(FarmInfoView farmInfoView, FarmCustomStrategyView farmCustomStrategyView) {
        Account currentUser = accountService.getCurrentUser();
        Map<Farm, Object> allFarmDetails = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);
        Farm farm = farmInfoView.getFarmInfo().getFarm();

        Map<String, Object> farmDetails = (Map<String, Object>) allFarmDetails.get(farm);

        Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>> scenarioDetails = (Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>>) farmDetails.get(SCENARIO_DETAILS);

        Set<FarmStrategyScenarioView> farmStrategyScenarioViewSet = scenarioDetails.keySet();

        Set<FarmStrategyScenarioView> scenarioViewSet = new HashSet<>();

        for (FarmStrategyScenarioView farmStrategyScenarioView : farmStrategyScenarioViewSet) {

            if (farmStrategyScenarioView.getFarmCustomStrategy().getId().equals(farmCustomStrategyView.getId())) {

                scenarioViewSet.add(farmStrategyScenarioView);

            }

        }

        return scenarioViewSet;

    }

    @Override
    public Map<FarmCustomStrategyView, JSONObject> getScenarioDetails(FarmInfoView farmInfoView, FarmStrategyScenarioView farmStrategyScenarioView) {

        Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>> scenarioDetails = getAllScenarioDetails(farmInfoView);

        return scenarioDetails.get(farmStrategyScenarioView);
    }

    @Override
    public Map<FarmStrategyScenarioView, JSONObject> getAllScenarioForStrategy(FarmInfoView farmInfoView, FarmCustomStrategyView farmCustomStrategyView) {
        Map<FarmStrategyScenarioView, JSONObject> allScenarioOutputForStrategy = new HashMap<>();

        //  getting all scenarios for the given strategy
        Set<FarmStrategyScenarioView> allScenarioList = getAllScenarioList(farmInfoView, farmCustomStrategyView);
        for (FarmStrategyScenarioView farmStrategyScenarioView : allScenarioList) {
            Map<FarmCustomStrategyView, JSONObject> scenarioDetails = getScenarioDetails(farmInfoView, farmStrategyScenarioView);

            //  getting all scenario output details
            Set<FarmCustomStrategyView> farmCustomStrategyViewList = scenarioDetails.keySet();
            for (FarmCustomStrategyView customStrategyView : farmCustomStrategyViewList) {
                //  getting the output details of the parent strategy of the scenario and adding it into the
                if (customStrategyView.getId().equals(farmCustomStrategyView.getId())) {

                    allScenarioOutputForStrategy.put(farmStrategyScenarioView, scenarioDetails.get(customStrategyView));

                }
            }

        }

        return allScenarioOutputForStrategy;
    }

    @Override
    public boolean updateFarmDetails(Farm farm, Account currentUser) {

        Map<Farm, Object> farmDetails = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);
        try {

            synchronized (farmDetails) {
                farmDetails.remove(farm);
                farmDetails.put(farm, getDetailsForFarmFromDatabase(farm));
                return true;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            return false;
        }

    }

    @Override
    public boolean updateStrategyDetails(FarmInfoView farmInfoView, FarmCustomStrategyView farmCustomStrategyView) {

        Account currentUser = accountService.getCurrentUser();

        Map<Farm, Object> allFarmsOutputDetail = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);

        try {
            Farm farm = farmInfoView.getFarmInfo().getFarm();
            Map<String, Object> farmOutputDetail = (Map<String, Object>) allFarmsOutputDetail.get(farm);
            Map<FarmCustomStrategyView, JSONObject> strategyDetails = (Map<FarmCustomStrategyView, JSONObject>) farmOutputDetail.get(STRATEGY_DETAILS);

            if (strategyDetails != null) {
                synchronized (strategyDetails) {

                    strategyDetails.remove(farmCustomStrategyView);
                    strategyDetails.put(farmCustomStrategyView, getStrategyDetailsForFarmFromDatabase(farmInfoView, farmCustomStrategyView));

                    return true;
                }
            } else {
                Map<FarmCustomStrategyView, JSONObject> strategyContainer = Collections.synchronizedMap(new TreeMap<FarmCustomStrategyView, JSONObject>());
                synchronized (strategyContainer) {
                    strategyContainer.put(farmCustomStrategyView, getStrategyDetailsForFarmFromDatabase(farmInfoView, farmCustomStrategyView));
                    farmOutputDetail.put(STRATEGY_DETAILS, strategyContainer);
                }

                return true;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            return false;
        }

    }

    @Override
    public boolean deleteStrategies(Farm farm, int[] strategyIdArray) {
        Account currentUser = accountService.getCurrentUser();
        Map<Farm, Object> allFarmDetails = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);
        boolean result = false;
        synchronized (allFarmDetails) {

            Map<String, Object> farmDetails = (Map<String, Object>) allFarmDetails.get(farm);
            Map<FarmCustomStrategyView, JSONObject> strategyDetails = (Map<FarmCustomStrategyView, JSONObject>) farmDetails.get(STRATEGY_DETAILS);

            Set<FarmCustomStrategyView> farmCustomStrategyViewSet = strategyDetails.keySet();
            Iterator<FarmCustomStrategyView> farmCustomStrategyViewIterator = farmCustomStrategyViewSet.iterator();

            while (farmCustomStrategyViewIterator.hasNext()){
                FarmCustomStrategyView farmCustomStrategyView = farmCustomStrategyViewIterator.next();
                for (int strategyId : strategyIdArray) {
                    if (farmCustomStrategyView.getId().equals(strategyId)) {
                        strategyDetails.remove(farmCustomStrategyView);
                        result = true;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public boolean updateScenarioDetails(FarmInfoView farmInfoView, FarmStrategyScenarioView farmStrategyScenarioView) {
        Account currentUser = accountService.getCurrentUser();
        Map<Farm, Object> allFarmsOutputDetail = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);
        Farm farm = farmInfoView.getFarmInfo().getFarm();
        try {
            Map<String, Object> farmDetails = (Map<String, Object>) allFarmsOutputDetail.get(farm);
            Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>> scenarioDetails = (Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>>) farmDetails.get(SCENARIO_DETAILS);
            if (scenarioDetails != null) {
                synchronized (scenarioDetails) {
                    scenarioDetails.remove(farmStrategyScenarioView);
                    scenarioDetails.put(farmStrategyScenarioView, getScenarioDetailsForFarmFromDatabase(new FarmInfoView(farmStrategyScenarioView.getFarmCustomStrategy().getFarmInfo()), farmStrategyScenarioView));
                    return true;
                }
            } else {
                Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>> scenarioContainer = Collections.synchronizedMap(new TreeMap<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>>());
                synchronized (scenarioContainer) {
                    scenarioContainer.put(farmStrategyScenarioView, getScenarioDetailsForFarmFromDatabase(new FarmInfoView(farmStrategyScenarioView.getFarmCustomStrategy().getFarmInfo()), farmStrategyScenarioView));
                    farmDetails.put(SCENARIO_DETAILS, scenarioContainer);
                    return true;
                }
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteSelectedFarmDetails(int[] farmIdsArray) {
        Account currentUser = accountService.getCurrentUser();
        Map<Farm, Object> farmDetails = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);
        boolean result = false;
        try {
            if (farmDetails != null) {
                synchronized (farmDetails) {
                    Set<Farm> farmSet = farmDetails.keySet();
                    Iterator<Farm> iterator = farmSet.iterator();

                    while (iterator.hasNext()) {
                        Farm farm = iterator.next();
                        for (int farmId : farmIdsArray) {
                            if (farm.getFarmId().equals(farmId)) {
                                iterator.remove();
                                result = true;
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            result = false;
        }

        return result;
    }

    @Override
    public boolean deleteAllFarmDetails(Account account) {

        try {
            accountDataContainerMap.remove(account);
            return true;
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            return false;
        }
    }

    @Override
    public Farm getFarmById(int farmId) {
        Account currentUser = accountService.getCurrentUser();
        Map<Farm, Object> farmDetails = (Map<Farm, Object>) accountDataContainerMap.get(currentUser);

        Set<Farm> farms = farmDetails.keySet();
        for (Farm farm : farms) {
            if (farm.getFarmId().equals(farmId)) {
                return farm;
            }
        }
        return null;

    }

    @Override
    public Set<Farm> getAllFarmForUser(Account account) {
        Map<Farm, Object> farmDetails = (Map<Farm, Object>) accountDataContainerMap.get(account);
        return farmDetails.keySet();

    }

    private Map<String, Object> getDetailsForFarmFromDatabase(Farm farm) throws JSONException {

        Map<String, Object> farmDetails = Collections.synchronizedMap(new TreeMap<String, Object>());
        Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>> scenarioContainer = Collections.synchronizedMap(new TreeMap<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>>());
        //  Getting all strategies for farm
        List<FarmCustomStrategyView> farmCustomStrategyViewList = farmCustomStrategyService.getDataForCustomStrategy(farm.getFarmId());
        Map<FarmCustomStrategyView, JSONObject> strategyContainer = Collections.synchronizedMap(new TreeMap<FarmCustomStrategyView, JSONObject>());

//        Map<FarmCustomStrategyView, StrategyDataBean> strategyBaseValuesDetailsContainer = new TreeMap<>();

        //  Iterating all strategies for getting corresponding details
        for (FarmCustomStrategyView farmCustomStrategyView : farmCustomStrategyViewList) {

            FarmInfoView farmInfoView = new FarmInfoView(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo());

            //  getting base values of strategy
            StrategyDataBean strategyDataBean = farmCustomStrategyService.getStrategyBaseValuesForStrategy(farmCustomStrategyView.getId(), farmInfoView);
//            StrategyDataBean strategyDataBeanForScenario = null;
//            try {
//                strategyDataBeanForScenario = (StrategyDataBean) strategyDataBean.clone();
//            } catch (CloneNotSupportedException e) {
//                PlantingProfitLogger.error(e);
//            }
//            strategyBaseValuesDetailsContainer.put(farmCustomStrategyView, strategyDataBeanForScenario);

            //  getting all details for strategy
            JSONObject strategyOutputDetails = scenarioService.getFarmOutputDetails(strategyDataBean);

            //  Strategy details calculation process started
            if (farmCustomStrategyView.getStrategyName().equalsIgnoreCase("Baseline Strategy")) {
                //  adding baseline strategy details to container
                farmDetails.put(BASELINE_DETAILS, strategyOutputDetails);
                strategyContainer.put(farmCustomStrategyView, strategyOutputDetails);
            } else {
                //  adding other custom strategy details to container
                strategyContainer.put(farmCustomStrategyView, strategyOutputDetails);
            }
        }
        //  Strategy details calculation process completed

        farmDetails.put(STRATEGY_DETAILS, strategyContainer);

        //  Scenario details calculation process started
        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = strategyContainer.entrySet();

        List<FarmStrategyScenarioView> farmStrategyScenarioViewList = new ArrayList<>();
        //  getting all scenarios for farm by all strategies
        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {

            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();

            farmStrategyScenarioViewList.addAll(scenarioService.getAllScenarioByStrategy(farmCustomStrategyView.getId()));
        }

        //  calculation of scenario output details
        for (FarmStrategyScenarioView farmStrategyScenarioView : farmStrategyScenarioViewList) {

//            Map<FarmCustomStrategyView, JSONObject> scenarioDetailsContainer = Collections.synchronizedMap(new TreeMap<FarmCustomStrategyView, JSONObject>());

//            FarmInfoView farmInfoView = new FarmInfoView(farmStrategyScenarioView.getFarmCustomStrategy().getFarmInfo());
//            for (FarmCustomStrategyView farmCustomStrategyView : farmCustomStrategyViewList) {
//                StrategyDataBean strategyDataBeanForScenario = farmCustomStrategyService.getStrategyBaseValuesForStrategy(farmCustomStrategyView.getId(), farmInfoView);
//
//                //  getting scenario updated values for crops
//                List<CropTypeView> cropTypeViewList = scenarioService.getScenarioCropValuesByStrategyBean(strategyDataBeanForScenario, farmStrategyScenarioView);
//
//                List<CropType> cropTypeList = new ArrayList<>();
//                for (CropTypeView cropTypeView : cropTypeViewList) {
//                    cropTypeList.add(cropTypeView.getCropType());
//                }
//
//                //  updating crop details according to scenario for output calculation purpose
//                strategyDataBeanForScenario.setCropTypeList(cropTypeList);
//                JSONObject scenarioOutputDetails = scenarioService.getFarmOutputDetails(strategyDataBeanForScenario);
//
//                scenarioDetailsContainer.put(farmCustomStrategyView, scenarioOutputDetails);
//
//            }
            scenarioContainer.put(farmStrategyScenarioView, this.getScenarioDetailsForFarmFromDatabase(new FarmInfoView(farmStrategyScenarioView.getFarmCustomStrategy().getFarmInfo()), farmStrategyScenarioView));
        }

        farmDetails.put(SCENARIO_DETAILS, scenarioContainer);

        return farmDetails;

    }

    private Map<FarmCustomStrategyView, JSONObject> getScenarioDetailsForFarmFromDatabase(FarmInfoView farmInfoView, FarmStrategyScenarioView farmStrategyScenarioView) throws JSONException {

        Map<FarmCustomStrategyView, JSONObject> scenarioContainer = Collections.synchronizedMap(new TreeMap<FarmCustomStrategyView, JSONObject>());
        List<FarmCustomStrategyView> farmCustomStrategyViewList = farmCustomStrategyService.getDataForCustomStrategy(farmInfoView.getFarmInfo().getFarm().getFarmId());

        //  calculation of scenario output details
        for (FarmCustomStrategyView farmCustomStrategyView : farmCustomStrategyViewList) {

            StrategyDataBean strategyDataBeanForScenario = farmCustomStrategyService.getStrategyBaseValuesForStrategy(farmCustomStrategyView.getId(), new FarmInfoView(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo()));

            //  getting scenario updated values for crops
            List<CropTypeView> cropTypeViewList = scenarioService.getScenarioCropValuesByStrategyBean(strategyDataBeanForScenario, farmStrategyScenarioView);

            List<CropType> cropTypeList = new ArrayList<>();
            for (CropTypeView cropTypeView : cropTypeViewList) {
                cropTypeList.add(cropTypeView.getCropType());
            }

            //  updating crop details according to scenario for output calculation purpose
            strategyDataBeanForScenario.setCropTypeList(cropTypeList);
            strategyDataBeanForScenario.setCropTypeViewList(cropTypeViewList);

            scenarioContainer.remove(farmCustomStrategyView);
            scenarioContainer.put(farmCustomStrategyView, scenarioService.getFarmOutputDetails(strategyDataBeanForScenario));

        }

        return scenarioContainer;

    }

    private JSONObject getStrategyDetailsForFarmFromDatabase(FarmInfoView farmInfoView, FarmCustomStrategyView farmCustomStrategyView) {
        JSONObject strategyOutputDetails = null;
        try {
            //  getting base values of strategy
            StrategyDataBean strategyDataBean = farmCustomStrategyService.getStrategyBaseValuesForStrategy(farmCustomStrategyView.getId(), farmInfoView);
            //  getting all details for strategy
            strategyOutputDetails = scenarioService.getFarmOutputDetails(strategyDataBean);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }

        return strategyOutputDetails;
    }

}
