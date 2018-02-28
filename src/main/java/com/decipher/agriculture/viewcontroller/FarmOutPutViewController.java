package com.decipher.agriculture.viewcontroller;

import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.agriculture.service.farmDetails.FarmOutputDetailsService;
import com.decipher.util.JsonResponse;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.CropsGroupView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
public class FarmOutPutViewController {
    /*
     * @Autowired private AccountService accountService;
	 */

    /*@Autowired
    private AccountService accountService;
    @Autowired
    private FarmInfoService farmInfoService;
    @Autowired
    private FieldInfoService fieldInfoService;
    @Autowired
    private CropFieldChociesService cropFieldChociesService;
    @Autowired
    private CropResourceUsageService cropResourceUsageService;
    @Autowired
    private CropTypeService cropTypeService;
    @Autowired
    private CropGroupService cropGroupService;
    @Autowired
    private CropResourceUsageFieldVariancesService variancesService;
    @Autowired
    private FarmOutputCalculationService farmOutputCalculationService;
    @Autowired
    private FarmOutputCalculationDao farmOutputCalculationDao;*/
    @Autowired
    private FarmDetailsContainerService farmDetailsContainerService;
    @Autowired
    private FarmService farmService;
    @Autowired
    private FarmOutputDetailsService farmOutputDetailsService;

	/*@Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
	@RequestMapping(value = "/output-farm.htm")
	public ModelAndView farmPage() {
		Map<String, Object> model = new HashMap<String, Object>();
		PlantingProfitLogger.info("User requesting for farm.htm page .... ");
		Account account = sessionService.getLoggedInUser();
		int userId = sessionService.getUserId();
		String page = "";
		if (account != null) {
			List<FarmInfoView> farmList = farmInfoService.getAllFarmByUser(userId);
			model.put("farmList", farmList);
			page = "output-farm";
			PlantingProfitLogger.info("User requesting for farm.htm page .... userId "+ userId);
		} else {
			PlantingProfitLogger.info("User requesting for farm.htm page .... userId " + userId);
			page = "home";
		}
		return new ModelAndView(page, "model", model);

	}*/

    @SuppressWarnings("unchecked")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/output-farm-info.htm")
    public ModelAndView outputFarmInfoPage(@RequestParam(value = "farmId", required = true) int farmId) {

        JSONObject myModel = new JSONObject();
//        Account account = accountService.getCurrentUser();
//        FarmInfoView farmInfoView = farmInfoService.getFarmByIdAfterCheckForAccount(farmId, account.getId());
        FarmInfoView farmInfoView = farmService.getBaselineFarmDetails(farmId);
//        ApplicationMode applicationMode = ApplicationStandard.getApplicationMode();
//        if (Objects.equals(applicationMode, ApplicationMode.DEVELOPMENT)) {
//            PlantingProfitLogger.info("request for output-farm-info.htm ");
//            List<FieldInfoView> fieldInfoViews = fieldInfoService.getAllFieldsByFarmId(farmId);
//            List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(farmId);
//            List<CropTypeView> cropTypeView = cropTypeService.getAllCropByFarm(farmId);
//            int[] cropIdArray = new Integer[cropTypeView.size()];
//            int i = 0;
//            for (CropTypeView view : cropTypeView) {
//                cropIdArray[i] = view.getId();
//                i++;
//            }
//            List<CropFieldChociesView> cropFieldsDetails = cropFieldChociesService.getAllCropFiledsCropIds(cropIdArray);
//            List<CropResourceUsageFieldVariancesView> resourceUsageVariances = variancesService.getAllResourceByCropIds(cropIdArray);
//            boolean checkValidStrategyForFarm = false;
//            List<FarmOutputDetailsView> farmOutputDetailsViewList = null;
//            List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViews = null;
//            Map<String, HashMap<String, String>> map = null;
//            Map<String, Object> mapCompleteObject = null;
//            Map<String, String> mapDifferentValues = new HashMap<String, String>();
//            Double totalForwardSales = new Double(0);
//            Map<String, HashMap<String, String>> mapForCropsForField = null;
//
//            /**
//             * @changed - Abhishek
//             * @date - 08-01-2016
//             * @desc - Used org.json.simple.JSONObject and JSONArray instead of org.codehaus.jettison.json.JSONObject and JSONArray for removing "no serializer found for class and no properties discovered to create beanserializer" exception
//             */
//            JSONObject jsonObjectForGraphs = null;
//
//            String potentialProfit = null;
//            List<CropsGroupView> cropsGroupViews = cropGroupService.getAllCropGroupByFarm(farmId);
//            if (farmInfoView.getStrategy() == PlanByStrategy.PLAN_BY_ACRES) {
//			/*
//			 * Output process for Farm by Acre By Harshit Gupta Start
//			 */
//                checkValidStrategyForFarm = farmOutputCalculationService.checkIfFarmHasValidStrategy(farmId);
//                farmOutputDetailsViewList = farmOutputCalculationService.getAllFarmOutputDetailsByFarm(farmId);
//			/*
//			 * Calculate used and unused resources by Harshit Gupta on
//			 * 13-05-2015 Start
//			 */
//                map = farmOutputCalculationDao.calculateUsedAndUnusedResourcesForAcre(resourceUsageViews, farmOutputDetailsViewList, resourceUsageVariances);
//                PlantingProfitLogger.info(map);
//
//			/* Create jsonObject For Graphs by Harshit Gupta on 13-05-2015 Start */
//
//                jsonObjectForGraphs = farmOutputCalculationDao.createJSONObjectForGraphForAcre(farmOutputDetailsViewList, map.get("cropResourceUnused").get("Land"));
//                PlantingProfitLogger.info(jsonObjectForGraphs);
//                potentialProfit = AgricultureStandardUtils.commaSeparaterForInteger(farmOutputCalculationDao.calculatePotentialProfitForAcre(farmOutputDetailsViewList));
//                for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
//                    if (farmOutputDetailsView.getForFirm() || farmOutputDetailsView.getForProposed()) {
//                        /**
//                         * @changed - abhishek
//                         * @date - 07-12-2015
//                         */
////					totalForwardSales += Integer.parseInt(farmOutputDetailsView.getUsedAcres());
//                        totalForwardSales += Integer.parseInt(farmOutputDetailsView.getUsedAcres().replaceAll("\\,", ""));
//                    }
//                }
//
//			/* Output process for Farm by Acre By Harshit Gupta End */
//
//            } else if (farmInfoView.getStrategy() == PlanByStrategy.PLAN_BY_FIELDS) {
//                farmOutputDetailsForFieldViews = farmOutputCalculationService.getAllFarmOutputDetailsForFieldByFarm(farmId);
//                checkValidStrategyForFarm = ((farmOutputDetailsForFieldViews.size() > 0) ? true : false);
//                map = farmOutputCalculationDao.calculateUsedAndUnusedResourcesForField(resourceUsageViews, farmOutputDetailsForFieldViews, resourceUsageVariances);
//                PlantingProfitLogger.info(map);
//                mapCompleteObject = farmOutputCalculationDao.createJSONObjectAndMapObjectForGraphForField(cropTypeView, farmOutputDetailsForFieldViews, map.get("cropResourceUnused").get("Land"));
//                jsonObjectForGraphs = (JSONObject) mapCompleteObject.get("jsonObjectForGraphs");
//                mapForCropsForField = (Map<String, HashMap<String, String>>) mapCompleteObject.get("mapForCropsForField");
//                PlantingProfitLogger.info(jsonObjectForGraphs);
//                PlantingProfitLogger.info(mapForCropsForField);
//                for (FarmOutputDetailsForFieldView farmOutputDetailsView : farmOutputDetailsForFieldViews) {
//                    if (farmOutputDetailsView.isForFirm() || farmOutputDetailsView.isForProposed()) {
//                        /**
//                         * @changed - Abhishek
//                         * @date - 07-12-2015
//                         */
////					totalForwardSales += Integer.parseInt(farmOutputDetailsView.getUsedAcres());
//                        totalForwardSales += Integer.parseInt(farmOutputDetailsView.getUsedAcres().replaceAll("\\,", ""));
//                    }
//                }
//                potentialProfit = AgricultureStandardUtils.commaSeparaterForInteger(farmOutputCalculationDao.calculatePotentialProfitForField(farmOutputDetailsForFieldViews));
//            }
//            Double forwardPercentage = (totalForwardSales * 100) / AgricultureStandardUtils.stringToLong(AgricultureStandardUtils.removeAllCommas(map.get("cropResourceUsed").get("Land")));
//            /**
//             * @Changed - abhishek
//             * @Date - 27-11-2015
//             */
////		mapDifferentValues.put("usedForwardAcresP", ""+forwardPercentage);
//            mapDifferentValues.put("usedForwardAcresP", "" + AgricultureStandardUtils.doubleUptoSingleDecimalPoint(forwardPercentage));
//
//
//            JSONArray cropResourceUsed = new JSONArray();
//            JSONArray cropResourceUnused = new JSONArray();
//            for (Entry<String, String> entry : map.get("cropResourceUsed").entrySet()) {
//                JSONObject jsonObjectForResourceUsed = new JSONObject();
//                JSONObject jsonObjectForResourceUnused = new JSONObject();
//
//                jsonObjectForResourceUsed.put("label", entry.getKey());
//                jsonObjectForResourceUsed.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(map.get("cropResourceUsed").get(entry.getKey()))));
//                jsonObjectForResourceUnused.put("label", entry.getKey());
//                jsonObjectForResourceUnused.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(map.get("cropResourceUnused").get(entry.getKey()))));
//                cropResourceUsed.add(jsonObjectForResourceUsed);
//                cropResourceUnused.add(jsonObjectForResourceUnused);
//
//            }
//
//            jsonObjectForGraphs.put("cropResourceUsedForBarGraph", cropResourceUsed);
//            jsonObjectForGraphs.put("cropResourceUnusedForBarGraph", cropResourceUnused);
//
//            PlantingProfitLogger.info(jsonObjectForGraphs);
//            if (farmInfoView != null) {
//
//                //	Adding only those crops which are selected
//                List<CropTypeView> cropTypeViewList = new ArrayList<>();
//                for (CropTypeView cropType : cropTypeView) {
//                    if (cropType.getSelected()) {
//                        cropTypeViewList.add(cropType);
//                    }
//                }
//
//
//                myModel.put("farmInfoView", farmInfoView);
//                myModel.put("fieldInfoList", fieldInfoViews);
//                myModel.put("resourceList", resourceUsageViews);
//                myModel.put("cropTypeView", cropTypeViewList);
//                myModel.put("cropsGroupViews", cropsGroupViews);
//                myModel.put("cropFieldsDetails", cropFieldsDetails);
//                myModel.put("mapDifferentValues", mapDifferentValues);
//                Map<String, ResourceDualValueView> mapResourceDualValueView = new HashMap<String, ResourceDualValueView>();
//
//
//                Map<String, CropLimitDualValueView> mapCropLimitDualValueView = new HashMap<String, CropLimitDualValueView>();
//                Map<String, GroupLimitDualValueViews> mapGroupLimitDualValueView = new HashMap<String, GroupLimitDualValueViews>();
//
//                if (farmInfoView.getStrategy() == PlanByStrategy.PLAN_BY_ACRES) {
//                    FarmInfo farmInfo = farmInfoService.getFarmInfoOldById(farmId, null, null, null, null, "cropDualValues", "resourceDual", "groupDualValue");
//
//
//                    for (ResourceDualValue resourceDualValue : farmInfo.getResourceDualValues()) {
//                        for (Entry<String, String> entry : map.get("cropResourceUsed").entrySet()) {
//                            if (entry.getKey().equals(resourceDualValue.getCropResourceUsage().getCropResourceUse())) {
//                                ResourceDualValueView dualValueView = new ResourceDualValueView(resourceDualValue);
////							PlantingProfitLogger.info(potentialProfit+"---------"+entry.getValue());
//                                dualValueView.setProfitPerUnit(AgricultureStandardUtils.stringToLong(AgricultureStandardUtils.removeAllCommas(potentialProfit)) / AgricultureStandardUtils.stringToLong(AgricultureStandardUtils.removeAllCommas(entry.getValue())));
////							PlantingProfitLogger.info("***************************"+dualValueView.getProfitPerUnit());
//                                mapResourceDualValueView.put(entry.getKey(), dualValueView);
//                            }
//                        }
//                    }
//
//                    /**
//                     * @added - Abhishek
//                     * @date - 04-01-2016
//                     * @desc - guidelines for Crop Acreage
//                     */
//                    for (CropLimitDualValue cropLimitDualValue : farmInfo.getCropLimitDualValues()) {
//                        for (CropTypeView crop : cropTypeView) {
//                            if (crop.getSelected()) {
//                                if (crop.getCropName().equalsIgnoreCase(cropLimitDualValue.getCropType().getCropName())) {
//                                    CropLimitDualValueView cropLimitDualValueView = new CropLimitDualValueView(cropLimitDualValue);
//                                    mapCropLimitDualValueView.put(crop.getCropName(), cropLimitDualValueView);
//                                }
//                            }
//                        }
//
//                    }
//
//                    /**
//                     * @added - Abhishek
//                     * @date - 04-01-2016
//                     * @desc - guidelines for Group Acreage
//                     */
//                    for (GroupLimitDualValue groupLimitDualValue : farmInfo.getGroupLimitDualValues()) {
//                        for (CropsGroupView cropsGroupView : cropsGroupViews) {
//                            if (cropsGroupView.getCropsGroupName().equalsIgnoreCase(groupLimitDualValue.getCropsGroup().getCropsGroupName())) {
//                                GroupLimitDualValueViews groupLimitDualValueViews = new GroupLimitDualValueViews(groupLimitDualValue);
//                                mapGroupLimitDualValueView.put(cropsGroupView.getCropsGroupName(), groupLimitDualValueViews);
//                            }
//                        }
//                    }
//
//                    /**
//                     * @added - Abhishek
//                     * @date - 04-01-2016
//                     * @desc - guidelines for Group Acreage
//                     */
//                    myModel.put("mapCropLimitDualValueView", mapCropLimitDualValueView);
//                    myModel.put("mapGroupLimitDualValueView", mapGroupLimitDualValueView);
//
//                    myModel.put("checkStrategyForFarm", checkValidStrategyForFarm);
//                    myModel.put("farmOutputDetails", farmOutputDetailsViewList);
//                    myModel.put("cropResourceUsed", map.get("cropResourceUsed"));
//                    myModel.put("cropResourceUnused", map.get("cropResourceUnused"));
//                    myModel.put("mapResourceDualValueView", mapResourceDualValueView);
//                    myModel.put("jsonObjectForGraphs", jsonObjectForGraphs);
//                    myModel.put("potentialProfit", potentialProfit);
//                } else if (farmInfoView.getStrategy() == PlanByStrategy.PLAN_BY_FIELDS) {
//                    myModel.put("checkStrategyForFarm", checkValidStrategyForFarm);
//                    myModel.put("farmOutputDetails", farmOutputDetailsForFieldViews);
//                    myModel.put("cropResourceUsed", map.get("cropResourceUsed"));
//                    myModel.put("cropResourceUnused", map.get("cropResourceUnused"));
//                    myModel.put("jsonObjectForGraphs", jsonObjectForGraphs);
//                    myModel.put("hashMapForAcre", mapForCropsForField.get("hashMapForAcre"));
//                    myModel.put("hashMapForProfit", mapForCropsForField.get("hashMapForProfit"));
//                    myModel.put("hashMapForRatio", mapForCropsForField.get("hashMapForRatio"));
//                    myModel.put("hashMapForProfitIndex", mapForCropsForField.get("hashMapForProfitIndex"));
//                    myModel.put("hashMapForRating", mapForCropsForField.get("hashMapForRating"));
//                    myModel.put("potentialProfit", potentialProfit);
//                }
//            }
//        } else {
//        }
        myModel = farmDetailsContainerService.getBaseLineDetails(farmInfoView.getFarmInfo().getFarm());

        JSONArray forwardSalesJsonArray = farmOutputDetailsService.buildForwardSalesContent(myModel);
        myModel.put("forwardSalesJsonArray", forwardSalesJsonArray);

        JSONArray cropLimitsJsonArray = farmOutputDetailsService.buildCropLimitContent(myModel);
        myModel.put("cropLimitsJsonArray", cropLimitsJsonArray);

        JSONArray cropAcreageJsonArray = farmOutputDetailsService.buildCropAcreageContent(myModel);
        myModel.put("cropAcreageJsonArray", cropAcreageJsonArray);

        JSONObject resourceJsonObject = farmOutputDetailsService.buildResourcesContent(myModel);
        myModel.put("resourceJsonObject", resourceJsonObject);


        if (farmInfoView != null) {
            return new ModelAndView("output-farm-info", "model", myModel);
        } else {
            return new ModelAndView("redirect:farm.htm", "model", myModel);
        }

    }

    @SuppressWarnings("unchecked")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/checkMinMaxForCrop", method = RequestMethod.POST)
    public @ResponseBody JsonResponse checkMinMaxForCrop(@RequestParam(value = "farmId")Integer farmId,
                                    @RequestParam(value = "cropId", required = false)Integer cropId,
                                    @RequestParam(value = "groupId", required = false)Integer groupId){

        PlantingProfitLogger.info("Checking for min max crop acreage");

        JsonResponse response = new JsonResponse();
        try {
            FarmInfoView farmInfoView = farmService.getBaselineFarmDetails(farmId);
            Farm farm = farmInfoView.getFarmInfo().getFarm();

            JSONObject baseLineDetails = farmDetailsContainerService.getBaseLineDetails(farm);

            List<CropTypeView> cropTypeViewList = (List<CropTypeView>)baseLineDetails.get("cropTypeView");
            JSONObject jsonObject = new JSONObject();
            for (CropTypeView cropTypeView : cropTypeViewList) {
                if(cropTypeView.getSelected()){
                    if (Objects.equals(cropTypeView.getId(), cropId)){
                        if(!cropTypeView.getMaximumAcres().equalsIgnoreCase("")){
                            jsonObject.put("Maximum", cropTypeView.getMaximumAcres());
                        }
                        if(!cropTypeView.getMinimumAcres().equalsIgnoreCase("")){
                            jsonObject.put("Minimum", cropTypeView.getMinimumAcres());
                        }
                        if(cropTypeView.getMaximumAcres().equalsIgnoreCase("") && cropTypeView.getMinimumAcres().equalsIgnoreCase("")){
                            jsonObject.put("No Crop Limit Specified", "");
                        }
                    }
                }
            }


            List<CropsGroupView> cropsGroupViewList = (List<CropsGroupView>)baseLineDetails.get("cropsGroupViews");

            for (CropsGroupView cropsGroupView : cropsGroupViewList) {
                if (Objects.equals(cropsGroupView.getId(), groupId)){
                    if(!cropsGroupView.getMaximumAcres().equalsIgnoreCase("")){
                        jsonObject.put("Maximum", cropsGroupView.getMaximumAcres());
                    }
                    if(!cropsGroupView.getMinimumAcres().equalsIgnoreCase("")){
                        jsonObject.put("Minimum", cropsGroupView.getMinimumAcres());
                    }
                    if(cropsGroupView.getMaximumAcres().equalsIgnoreCase("") && cropsGroupView.getMinimumAcres().equalsIgnoreCase("")){
                        jsonObject.put("No Crop Limit Specified", "");
                    }
                }
            }


            response.setStatus(JsonResponse.RESULT_SUCCESS);
            response.setResult(jsonObject);
        } catch (Exception e) {
            response.setStatus(JsonResponse.RESULT_FAILED);
            PlantingProfitLogger.error(e);
        }

        return response;
    }

}
