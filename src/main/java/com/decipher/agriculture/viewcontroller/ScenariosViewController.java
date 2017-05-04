package com.decipher.agriculture.viewcontroller;

/**
 * @author Abhishek
 * Date 24-11-2015
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.service.account.impl.SessionService;
import com.decipher.agriculture.service.farm.CropTypeService;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.agriculture.service.scenario.ScenarioService;
import com.decipher.util.JsonResponse;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.decipher.agriculture.data.account.Account;
import com.decipher.util.PlantingProfitLogger;

@Controller
public class ScenariosViewController {

    @Autowired
    private FarmInfoService farmInfoService;
    @Autowired
    private FarmDetailsContainerService farmDetailsContainerService;
    @Autowired
    private CropTypeService cropTypeService;
    @Autowired
    private ScenarioService scenarioService;
    @Autowired
    private FarmService farmService;


    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/view-farm-scenarios.htm")
    public Object view_scenarios(@RequestParam(value = "farmId", required = false) Integer farmId) {

        Map<String, Object> model = new HashMap<String, Object>();

        PlantingProfitLogger.info("User requesting for view-farm-scenarios.htm page .... ");
        Farm farm = farmService.getFarmById(farmId);

        if(farm != null && !farm.getSaveFlag()){
            return "redirect:farm-info.htm?farmId=" + farm.getFarmId();
        }

        if (farm != null && farm.getSaveFlag()) {

//          FarmInfoView farmInfoView = farmInfoService.getFarmByFarmIdAndUserId(Integer.parseInt(farmId), account.getId());

            model.put("farm", farm);
            FarmInfoView farmInfoView = farmService.getBaselineFarmDetails(farmId);
            model.put("farmInfoView", farmInfoView);

            // Load Complete Strategy Data
            Map<FarmStrategyScenarioView, Map<FarmCustomStrategyView, JSONObject>> allScenarioDetails = farmDetailsContainerService.getAllScenarioDetails(farmInfoView);
            Set<FarmStrategyScenarioView> farmStrategyScenarioViewSet = allScenarioDetails.keySet();

            Map<FarmCustomStrategyView, JSONObject> allStrategiesDetails = farmDetailsContainerService.getAllStrategiesDetails(farmInfoView);
            Set<FarmCustomStrategyView> farmCustomStrategyViewSet = allStrategiesDetails.keySet();

            List<CropTypeView> cropTypeViewList = new ArrayList<>();
            FarmInfo farmInfo = farmInfoView.getFarmInfo();
            Set<CropType> cropTypes = farmInfo.getCropTypes();
            for (CropType cropType : cropTypes) {
                cropTypeViewList.add(new CropTypeView(cropType));
            }


            model.put("cropList", cropTypeViewList);
            model.put("strategyList", farmCustomStrategyViewSet);
            model.put("savedScenarioData", farmStrategyScenarioViewSet);

            return new ModelAndView("scenarios", "model", model);
        } else {
            return "redirect:farm.htm";
        }

    }

    /**
     * @added - Abhishek
     * @date - 06-01-2016
     */
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/getAllScenarioData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    JsonResponse getAllScenarioData(@RequestParam(value = "farmId", required = false) int farmId,
                                    @RequestParam(value = "strategyId", required = false) int strategyId,
                                    @RequestParam(value = "scenarioId", required = false) int scenarioId) throws ParseException {

        JsonResponse response = new JsonResponse();
        FarmStrategyScenarioView farmStrategyScenarioView = scenarioService.getScenarioById(scenarioId);
        FarmInfoView farmInfoView = farmInfoService.getFarmInfoById(farmId);
        try {

            Map<FarmCustomStrategyView, JSONObject> scenarioDetails = farmDetailsContainerService.getScenarioDetails(farmInfoView, farmStrategyScenarioView);

            response.setStatus(JsonResponse.RESULT_SUCCESS);
            response.setResult(scenarioDetails);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            response.setStatus(JsonResponse.RESULT_FAILED);
            response.setResult(e.getMessage());
        }

        PlantingProfitLogger.info("getAllScenarioData response : " + response);
        return response;

    }

    /**
     * @added - Abhishek
     * @date - 19-01-2016
     * @desc - for getting 'Apply To All' output details
     */
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/getApplyToAllScenariosOutput", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    JsonResponse getApplyToAllScenariosOutput(@RequestParam(value = "farmId", required = false) int farmId,
                                              @RequestParam(value = "scenarioId", required = false) int scenarioId,
                                              @RequestParam(value = "strategyIdArray[]", required = false) int[] strategyIdArray) {

        PlantingProfitLogger.info("Applying scenario details to strategy");

        JsonResponse response = new JsonResponse();

        try {

//            FarmInfoView farmInfoView = farmInfoService.getFarmInfoById(farmId);

            FarmInfoView farmInfoView = farmService.getBaselineFarmDetails(farmId);

            JSONObject scenarioComparisonDetails = scenarioService.getScenarioComparisonDetails(farmInfoView, scenarioId, strategyIdArray);

            response.setResult(scenarioComparisonDetails);
            response.setStatus(JsonResponse.RESULT_SUCCESS);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            response.setStatus(JsonResponse.RESULT_FAILED);
        }

        return response;

    }


}
