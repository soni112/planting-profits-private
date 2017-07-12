package com.decipher.agriculture.viewcontroller;

/**
 * @author Abhishek
 * @date 27-11-2015
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.decipher.agriculture.AppConstants;
import com.decipher.agriculture.farmreport.ReportDataPage2;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.decipher.agriculture.farmreport.ReportDataPage1;
import com.decipher.agriculture.farmreport.ReportDataPage3;
import com.decipher.view.form.account.AccountView;
import com.decipher.view.form.farmDetails.FarmInfoView;

@Controller
@SuppressWarnings("unchecked")
public class GenerateReportController {

    /**
     * @changed - Abhishek
     * @date - 09-01-2016
     */
    @Autowired
    private AccountService accountService;
    @Autowired
    private FarmDetailsContainerService farmDetailsContainerService;
    @Autowired
    private FarmService farmService;



    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "Generate-Report.htm", method = RequestMethod.GET)
    public ModelAndView getReportData(@RequestParam(value = "farmId") int farmId,
                                      @RequestParam(value = "strategyID") String strategies) throws JSONException {

        Map<String, Object> model = new HashMap<>();
        PlantingProfitLogger.info("inside Generate-Report.htm");

        AccountView accountView = new AccountView(accountService.getCurrentUser());
        FarmInfoView farmInfoView = farmService.getBaselineFarmDetails(farmId), baseFarmInfoView = null;

        JSONObject baseSelectedStrategyOutputDetails = new JSONObject();
        FarmCustomStrategyView selectedFarmCustomStrategyView = null;
        Map<FarmCustomStrategyView, JSONObject> allStrategyOutputDetailsContainer = new HashMap<>();

        /*  Last strategyID in this array is the base selected strategy for Report Generation   */
        String[] strategiesArray = strategies.split(",");
        int loopCount = 0;
        int strategyId;
        int scenarioCount = 0;
        for (String s : strategiesArray) {
            loopCount++;
            strategyId = Integer.parseInt(s);

            //  getting all strategies for the farm
            Map<FarmCustomStrategyView, JSONObject> allStrategiesDetails = farmDetailsContainerService.getAllStrategiesDetails(farmInfoView);
            Set<FarmCustomStrategyView> farmCustomStrategyViewSet = allStrategiesDetails.keySet();

            for (FarmCustomStrategyView farmCustomStrategyView : farmCustomStrategyViewSet) {

                if (farmCustomStrategyView.getId().equals(strategyId)) {

                    JSONObject strategyOutputDetails = allStrategiesDetails.get(farmCustomStrategyView);

                    if(loopCount == strategiesArray.length){
                        selectedFarmCustomStrategyView = farmCustomStrategyView;
                        baseSelectedStrategyOutputDetails = strategyOutputDetails;

                        baseFarmInfoView = new FarmInfoView(farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo());
                    }
                    //  getting all scenario for the given strategy
                    Map<FarmStrategyScenarioView, JSONObject> allScenarioForStrategy = farmDetailsContainerService.getAllScenarioForStrategy(farmInfoView, farmCustomStrategyView);

                    scenarioCount += allScenarioForStrategy.keySet().size();

                    strategyOutputDetails.put("scenarioDetails", allScenarioForStrategy);

                    //  Putting all details of the custom strategy into container
                    allStrategyOutputDetailsContainer.put(farmCustomStrategyView, strategyOutputDetails);

                }

            }

        }


//        /**
//         * @changed - Abhishek
//         * @date - 09-01-2016
//         * @desc - changed from previous baseline strategy data to the base selected strategy and other selected strategies data
//         */
//
//        /*
//         *  Getting OutputDetails for base selected strategy for Report Generation
//         */
//
//
//
//        /*      Getting base values for strategy    */
//        StrategyDataBean beanBaseValuesForStrategy = null;
//
//        /*      Getting outPut details for Base Strategy    */
//        JSONObject baseSelectedStrategyOutputDetailsJsonObj = null;
//
//        /*
//         *  Getting OutputDetails for all selected strategy for Report Generation
//         */
//        List<JSONObject> allSelectedStrategiesOutputDetailsJsonObjectList = new ArrayList<JSONObject>();
//
//        int loopCount = 0;
//        int scenarioCount = 0;
//        for (String strategyId : strategiesArray) {
//            loopCount++;
//            /*      Getting base values for strategy    */
//            StrategyDataBean baseValuesForStrategy = farmCustomStrategyService.getStrategyBaseValuesForStrategy(Integer.parseInt(strategyId), farmInfoService.getFarmInfoById(farmId));
//
//            /*      Getting outPut details for Strategy    */
//            JSONObject strategyOutputDetailsJsonObj = scenarioService.getFarmOutputDetails(baseValuesForStrategy);
//
//            /*      Setting base selected strategy data    */
//            if(loopCount == strategiesArray.length){
//                beanBaseValuesForStrategy = baseValuesForStrategy;
//                baseSelectedStrategyOutputDetailsJsonObj = strategyOutputDetailsJsonObj;
//            }
//
//            JSONArray jsonArrayScenarioOutput = new JSONArray();
//
//            /*      applying scenario to strategy and getting data      */
//            List<FarmStrategyScenarioView> allScenarioByStrategy = scenarioService.getAllScenarioByStrategy(Integer.parseInt(strategyId));
//            for (FarmStrategyScenarioView farmStrategyScenarioView : allScenarioByStrategy) {
//                scenarioCount++;
//
//                /*      Applying scenario to selected strategy      */
//                baseValuesForStrategy.setCropTypeViewList(scenarioService.getScenarioCropValuesByStrategyBean(baseValuesForStrategy, farmStrategyScenarioView));
//
//                JSONObject scenarioOutputDetailsJsonObject = new JSONObject();
//
//                scenarioOutputDetailsJsonObject.put("farmStrategyScenarioView", farmStrategyScenarioView);
//                scenarioOutputDetailsJsonObject.put("scenarioOutputDetails", scenarioService.getFarmOutputDetails(baseValuesForStrategy));
//
//                /*      Adding scenario output details to JsonArray       */
//                jsonArrayScenarioOutput.add(scenarioOutputDetailsJsonObject);
//            }
//
//            /*      Setting strategy data combined with scenario data       */
//            strategyOutputDetailsJsonObj.put("scenarioOutputList", jsonArrayScenarioOutput);
//
//            /*      Adding Scenario Output details to all strategy/scenario output details      */
//            allSelectedStrategiesOutputDetailsJsonObjectList.add(strategyOutputDetailsJsonObj);
//        }
//
        PlantingProfitLogger.info("loopCount = " + loopCount);
        PlantingProfitLogger.info("scenarioCount = " + scenarioCount);

        ReportDataPage1 reportDataPage1 = new ReportDataPage1(accountView, baseFarmInfoView, baseSelectedStrategyOutputDetails);
        ReportDataPage2 reportDataPage2 = new ReportDataPage2(baseSelectedStrategyOutputDetails, allStrategyOutputDetailsContainer, scenarioCount);
        ReportDataPage3 reportDataPage3 = new ReportDataPage3(baseSelectedStrategyOutputDetails);

        model.put(AppConstants.ACCOUNT_VIEW, accountView);
        model.put(AppConstants.BASE_FARM_INFO_REPORT, baseFarmInfoView);
        model.put(AppConstants.FARM_INFO_VIEW, farmInfoView);
        model.put(AppConstants.REPORT_DATA_1, reportDataPage1);
        model.put(AppConstants.REPORT_DATA_2, reportDataPage2);
        model.put(AppConstants.REPORT_DATA_3, reportDataPage3);

        /**
         * @changed - Abhishek
         * @date - 09-12-2015
         */
        model.put(AppConstants.SELECTED_STRATEGY_NAME, selectedFarmCustomStrategyView.getStrategyName());
        model.put(AppConstants.ESTIMATED_INCOME, baseSelectedStrategyOutputDetails.get("potentialProfit").toString());

        model.put(AppConstants.STRATEGY_ID_ARRAY, strategies);
        model.put(AppConstants.FARM_ID, farmId);

        return new ModelAndView("farmReportGenerator", model);
    }

}
