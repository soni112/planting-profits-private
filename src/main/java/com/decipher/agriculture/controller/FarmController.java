package com.decipher.agriculture.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import com.decipher.agriculture.dao.util.HibernateUtils;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.agriculture.service.strategy.FarmCustomStrategyService;
import com.decipher.util.quartzScheduler.AgricultureScheduler;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.agriculture.service.farmDetails.FarmOutputCalculationService;
import com.decipher.util.JsonResponse;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.FarmInfoView;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/farmController")
public class FarmController {

    @Autowired
    private FarmService farmService;
    @Autowired
    private FarmInfoService farmInfoService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FarmOutputCalculationService farmOutputCalculationService;
    @Autowired
    private FarmCustomStrategyService farmCustomStrategyService;

    /**
     * @changed - Abhishek
     * @date - 07-05-2016
     * @desc - changed for optimization of the application
     */
    @Autowired
    private FarmDetailsContainerService farmDetailsContainerService;

    @RequestMapping(value = "createFarm", method = RequestMethod.POST)
    public JsonResponse createFarm(@RequestParam(value = "farmName") String farmName,
                            @RequestParam(value = "physicalLocation") String physicalLocation) {
        PlantingProfitLogger.info("inside createFarm " + farmName);
        JsonResponse jsonResponse = new JsonResponse();
        Account account = accountService.getCurrentUser();
        if (account != null) {
            boolean exists = farmService.isFarmExitsWithNameAndUserId(farmName.trim(), account.getId());
            if (!exists) {

                Farm farm = new Farm();
                farm.setFarmName(farmName.trim());
                farm.setPhysicalLocation(physicalLocation.trim());
                farm.setAccount(account);
                farm.setFarmCreatedTime(new java.sql.Date(new java.util.Date().getTime()));
                int farmId = farmService.saveFarm(farm);
                PlantingProfitLogger.info("inside farminfo : " + farmId);
                if (farmId != 0) {
                    jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
                    jsonResponse.setResult(farmId);
                }
            } else {
                jsonResponse.setStatus(JsonResponse.RESULT_ALREADY_EXISTS);
            }
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_INVALID_USER_NOT_EXISTS);
        }
        return jsonResponse;
    }

    @RequestMapping(value = "saveFarmInformation", method = RequestMethod.POST)
    public JsonResponse saveFarmInformation(
            HttpServletRequest request,
            @RequestParam(value = "farmId", required = true) int farmId,
            @RequestParam(value = "irrigate_val", required = false) String irrigate_val,
            @RequestParam(value = "evaluate_forward_sales_val", required = false) boolean evaluate_forward_sales_val,
            @RequestParam(value = "evaluate_storage_sales_val", required = false) boolean evaluate_storage_sales_val,
            @RequestParam(value = "evaluate_crop_insurance_val", required = false) boolean evaluate_crop_insurance_val,
            @RequestParam(value = "strategy", required = true) String strategy,
            @RequestParam(value = "total_land", required = false) String total_land,
            @RequestParam(value = "crop_str[]", required = true) String[] crop_str,
            @RequestParam(value = "crop_information_detail[]", required = true) String[] crop_information_detail,
            @RequestParam(value = "cal_var_cost_crops[]", required = false) String[] cal_var_cost_crops,
            @RequestParam(value = "option_crop_info_array[]", required = false) String[] option_crop_info_array,
            @RequestParam(value = "optional_planting_date_array[]", required = false) String[] optional_planting_date_array,
            @RequestParam(value = "manage_resource_tbody_array[]", required = false) String[] manage_resource_tbody_array,
            @RequestParam(value = "crop_resource_usage_tbody_array[]", required = false) String[] crop_resource_usage_tbody_array,
            @RequestParam(value = "crop_limits_table_tbody_array[]", required = false) String[] crop_limits_table_tbody_array,
            @RequestParam(value = "forward_sales_information_tbody_array[]", required = false) String[] forward_sales_information_tbody_array,
            @RequestParam(value = "plan_by_field_tbody_array[]", required = false) String[] plan_by_field_tbody_array,
            @RequestParam(value = "field_choice_crop_tbody_row_array[]", required = false) String[] field_choice_crop_tbody_row_array,
            @RequestParam(value = "crop_resources_usages_difference_tbody_array[]", required = false) String[] crop_resources_usages_difference_tbody_array,
            @RequestParam(value = "field_difference_str", required = false) String field_difference_str,
            @RequestParam(value = "crop_group_array[]", required = false) String[] crop_group_array,
            @RequestParam(value = "montyCarloStatus", required = false) boolean montyCarloStatus,
            @RequestParam(value = "additionalCropCostObj", required = false) String additionalCropCostObj,
            @RequestParam(value = "baselineVal", required = false) String baselineVal,
            @RequestParam(value = "strategyName", required = false) String strategyName

    ) {

        PlantingProfitLogger.info("inside saveFarmInformation method farmId : " + farmId);
        JsonResponse jsonResponse = new JsonResponse();
        FarmInfo farmInfo;
        boolean isUpdated;
        Farm farm = farmService.getFarmById(farmId);

        //  if farm do not have any information
        if(!farm.getSaveFlag()){
            farmInfo = new FarmInfo();
            farmInfo.setFixedCost("0");
            farmInfo.setLivingExpenses("0");
            farmInfo.setAdditionalProfit("0");
            farmInfo.setProfitGoal("0");
            farmInfo.setLandUOM("Acre");
            farmInfo.setCurrencyUOM("$");
            farmInfo.setFarm(farm);
            farmInfo.setBaselineFlag(true);

            farmInfo = farmInfoService.saveFarmInfo(farmId, irrigate_val,
                    evaluate_forward_sales_val, evaluate_storage_sales_val,
                    evaluate_crop_insurance_val, strategy, total_land,
                    farmInfo, crop_str, crop_information_detail,
                    cal_var_cost_crops, option_crop_info_array,
                    optional_planting_date_array, manage_resource_tbody_array,
                    crop_resource_usage_tbody_array,
                    crop_limits_table_tbody_array,
                    forward_sales_information_tbody_array,
                    plan_by_field_tbody_array,
                    field_choice_crop_tbody_row_array,
                    crop_resources_usages_difference_tbody_array,
                    field_difference_str, crop_group_array, additionalCropCostObj);

            farm.setSaveFlag(true);

            farmInfo.setMontyCarloStatus(montyCarloStatus);
            isUpdated = farmInfoService.updateFarmInfo(farmInfo);

            farmCustomStrategyService.saveAsBaseLineStrategy(farmInfo);

        } else if(baselineVal != null && baselineVal.equalsIgnoreCase("new")){
            //  if strategy is created for the farm details for comparison

            farmInfo = new FarmInfo();
            farmInfo.setFixedCost("0");
            farmInfo.setLivingExpenses("0");
            farmInfo.setAdditionalProfit("0");
            farmInfo.setProfitGoal("0");
            farmInfo.setLandUOM("Acre");
            farmInfo.setCurrencyUOM("$");
            farmInfo.setFarm(farm);
            farmInfo.setBaselineFlag(false);
            farmInfo = farmInfoService.saveFarmInfo(farmId, irrigate_val,
                    evaluate_forward_sales_val, evaluate_storage_sales_val,
                    evaluate_crop_insurance_val, strategy, total_land,
                    farmInfo, crop_str, crop_information_detail,
                    cal_var_cost_crops, option_crop_info_array,
                    optional_planting_date_array, manage_resource_tbody_array,
                    crop_resource_usage_tbody_array,
                    crop_limits_table_tbody_array,
                    forward_sales_information_tbody_array,
                    plan_by_field_tbody_array,
                    field_choice_crop_tbody_row_array,
                    crop_resources_usages_difference_tbody_array,
                    field_difference_str, crop_group_array, additionalCropCostObj);

            farmInfo.setMontyCarloStatus(montyCarloStatus);
            isUpdated = farmInfoService.updateFarmInfo(farmInfo);

            //  create new strategy according to selection
            FarmCustomStrategy farmCustomStrategy = new FarmCustomStrategy();
            farmCustomStrategy.setStrategyName(strategyName.trim());
            farmCustomStrategy.setStrategyForCrop(false);
            farmCustomStrategy.setStrategyForResourse(false);
            farmCustomStrategy.setFarm(farm);
            farmCustomStrategy.setFarmInfo(farmInfo);
            farmCustomStrategyService.saveFarmCustomStrategy(farmCustomStrategy);
        } else {
            FarmInfoView farmInfoView = farmService.getBaselineFarmDetails(farmId);
            farmInfo = farmInfoView.getFarmInfo();
            //  update the farm with the new details
            boolean isAllRecordDeleted = farmInfoService.deleteAllFarmRecords(farmInfo.getId());
            PlantingProfitLogger.info("Some table deleted using procedure " + isAllRecordDeleted);
            farmInfo = farmInfoService.updateFarmInfo(farmId, irrigate_val,
                    evaluate_forward_sales_val, evaluate_storage_sales_val,
                    evaluate_crop_insurance_val, strategy, total_land,
                    crop_str, crop_information_detail, cal_var_cost_crops,
                    option_crop_info_array, optional_planting_date_array,
                    manage_resource_tbody_array,
                    crop_resource_usage_tbody_array,
                    crop_limits_table_tbody_array,
                    forward_sales_information_tbody_array,
                    plan_by_field_tbody_array,
                    field_choice_crop_tbody_row_array,
                    crop_resources_usages_difference_tbody_array,
                    field_difference_str, crop_group_array, farmInfo, additionalCropCostObj);

            farmInfo.setMontyCarloStatus(montyCarloStatus);
            isUpdated = farmInfoService.updateFarmInfo(farmInfo);
        }

        PlantingProfitLogger.info("isCreated " + isUpdated);

        farmOutputCalculationService.calculateFarmOutputStatistics(farmInfo);
        PlantingProfitLogger.warn("************************************Output Calculation Scheduler Start************************************");
        try {
            AgricultureScheduler.startQuartsSchedulerForFarmUpdate(farm, accountService.getCurrentUser());
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }
        PlantingProfitLogger.warn("************************************Output Calculation Scheduler End************************************");
        if (isUpdated) {
            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
        }
        return jsonResponse;
    }

    @RequestMapping(value = "deleteSelectedFarms", method = RequestMethod.POST)
    public JsonResponse deleteSelectedFarms(HttpServletRequest request,
                                     @RequestParam(value = "farmIdsArray[]", required = false) int[] farmIdsArray) {
        PlantingProfitLogger.info(Arrays.toString(farmIdsArray));
        JsonResponse jsonResponse = new JsonResponse();
        PlantingProfitLogger.info("inside deleteSelectedFarms farmId");
//        Account account = accountService.getCurrentUser();
//        boolean result = farmInfoService.deleteAllSelectedFarms(account.getId(), farmIdsArray);
        boolean result = farmService.deleteFarmByIds(farmIdsArray);
        if (result) {
            /**
             * @changed - Abhishek
             * @date - 07-05-2016
             * @desc - changed for optimization of the application
             */
            farmDetailsContainerService.deleteSelectedFarmDetails(farmIdsArray);
            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
        }
        return jsonResponse;
    }

    @RequestMapping(value = "deleteAllFarms", method = RequestMethod.POST)
    public JsonResponse deleteAllFarms(HttpServletRequest request) {
        JsonResponse jsonResponse = new JsonResponse();
        PlantingProfitLogger.info("inside deleteSelectedFarms  farmId ");
        Account account = accountService.getCurrentUser();
//        boolean result = farmInfoService.deleteAllFarms(account.getId());
        boolean result = farmService.deleteAllFarmsForUser(account);
        if (result) {
            /**
             * @changed - Abhishek
             * @date - 07-05-2016
             * @desc - changed for optimization of the application
             */
            farmDetailsContainerService.deleteAllFarmDetails(account);

            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
        }
        return jsonResponse;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getFarmDetails", method = RequestMethod.POST)
    public JsonResponse getFarmDetails(@RequestParam(value = "farmId", required = false) int farmId) {

        JsonResponse jsonResponse = new JsonResponse();
        Farm farm = farmService.getFarmById(farmId);
        if (farm != null) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", farm.getFarmId());
            jsonObject.put("name", farm.getFarmName());
            jsonObject.put("physical_Location", farm.getPhysicalLocation());

            jsonResponse.setResult(jsonObject);
            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);

        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
            jsonResponse.setResult("No farm found with the details provided");
        }

        return jsonResponse;
    }

    @RequestMapping(value = "updateFarmDetails", method = RequestMethod.POST)
    public JsonResponse updateFarmDetails(
            HttpServletRequest request,
            @RequestParam(value = "farmId", required = false) int farmId,
            @RequestParam(value = "farmName", required = false) String farmName) {
        JsonResponse jsonResponse = new JsonResponse();
        Farm farm = farmService.getFarmById(farmId);
        farm.setFarmName(farmName.trim());
        boolean result = farmService.updateFarm(farm);
        if (result) {
            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
        }
        return jsonResponse;
    }

}