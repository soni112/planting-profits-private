package com.decipher.agriculture.controller;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.agriculture.service.strategy.FarmCustomStrategyService;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.scenario.ScenarioService;
import com.decipher.util.JsonResponse;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Created by raja on 12/24/15.
 */

@Controller
@RequestMapping(value = "/scenario")
public class ScenarioController {

    @Autowired
    private FarmCustomStrategyService farmCustomStrategyService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ScenarioService scenarioService;
    @Autowired
    private FarmService farmService;
    @Autowired
    private FarmDetailsContainerService farmDetailsContainerService;


    @RequestMapping(value = "saveScenario", method = RequestMethod.POST)
    public @ResponseBody JsonResponse saveOrUpdateScenarioInformation(
            @RequestParam(value = "scenarioName") String scenarioName,
            @RequestParam(value = "farmId") Integer farmId,
            @RequestParam(value = "strategyId") Integer strategyId,
            @RequestParam(value = "scenario_global_crop_price", required = false, defaultValue = "0") Integer global_crop_price,
            @RequestParam(value = "scenario_global_crop_yields", required = false, defaultValue = "0") Integer global_crop_yields,
            @RequestParam(value = "scenario_global_crop_prod_cost", required = false, defaultValue = "0") Integer global_crop_prod_cost,
            @RequestParam(value = "scenario_Comment", required = false) String scenario_Comment,
            @RequestParam(value = "crop_specific_comment", required = false) String crop_specific_comment,
            @RequestParam(value = "cropSpecific", required = false) String cropSpecific,
            @RequestParam(value = "scenarioId", required = false) Integer scenarioId
    ) {
        // Check if valid user
        Account account = accountService.getCurrentUser();
        int userId = account.getId();

        JsonResponse jsonResponse = new JsonResponse();

        if (scenarioId != null) {

            // check if scenario exists
            if (scenarioService.isExist(scenarioId, strategyId, farmId)) {
                // Check if Other Scenario With Name is there for this user or not.
                if (scenarioService.isExistExcept(scenarioId, strategyId, farmId, userId, scenarioName)) {
                    jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
                    jsonResponse.setResult("Scenario with this name already exists !!");
                } else {
                    // Update scenario
                    jsonResponse.setResult(scenarioId + ", " + strategyId);
                    scenarioService.updateScenario(scenarioId, scenarioName, scenario_Comment, crop_specific_comment, farmId, strategyId, global_crop_price, global_crop_yields, global_crop_prod_cost, cropSpecific);
                    jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
                }
            } else {
                jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
                jsonResponse.setResult(JsonResponse.RESULT_NOT_EXISTS);
            }
        } else {

            // Check if scenario Exists with this name already or not
            if (scenarioService.isExist(strategyId, farmId, userId, scenarioName)) {
                jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
                jsonResponse.setResult("Scenario with this name already exists !!");
            } else {
                // Add New Scenario
                jsonResponse = scenarioService.saveScenario(scenarioName, farmId, scenario_Comment, crop_specific_comment, strategyId, global_crop_price, global_crop_yields, global_crop_prod_cost, cropSpecific);
                jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
            }
        }


        return jsonResponse;
    }

    @RequestMapping(value = "getScenario/{farmId}/{strategyId}/{scenarioId}", method = RequestMethod.GET)
    public
    @ResponseBody
    JsonResponse getScenarioData(@PathVariable(value = "farmId") Integer farmId,
                                 @PathVariable(value = "strategyId") Integer strategyId,
                                 @PathVariable(value = "scenarioId") Integer scenarioId) {
        JsonResponse jsonResponse = new JsonResponse();

        FarmStrategyScenarioView scenarioView = null;

        if (scenarioService.isExist(scenarioId, strategyId)) {
            scenarioView = scenarioService.getScenarioById(scenarioId);
        }

        // If result is available then supply data with farm - crop information
        if (scenarioView != null) {
            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
            jsonResponse.setResult(scenarioService.getJsonFrom(scenarioView));
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
            jsonResponse.setResult("Invalid Input Data Supplied !!");
        }


        return jsonResponse;
    }
}
