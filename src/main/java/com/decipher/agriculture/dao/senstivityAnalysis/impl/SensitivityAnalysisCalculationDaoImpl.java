/**
 *
 */
package com.decipher.agriculture.dao.senstivityAnalysis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.decipher.AppConstants;
import com.decipher.agriculture.bean.OutputBeanForStrategy;
import com.decipher.agriculture.dao.farmOutput.FarmOutputCalculationDao;
import com.decipher.agriculture.dao.farmOutput.LinearProgramingSolveDao;
import com.decipher.agriculture.dao.senstivityAnalysis.SensitivityAnalysisCalculationDao;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.farmDetails.FarmOutputCalculationService;
import com.decipher.agriculture.service.farmDetails.FarmOutputDetailsService;
import com.decipher.agriculture.service.strategy.FarmCustomStrategyService;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsForFieldView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import net.sf.javailp.Result;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.bean.CropBeanForOutput;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.service.farm.CropResourceUsageService;
import com.decipher.agriculture.service.farm.CropTypeService;
import com.decipher.agriculture.service.farm.FieldInfoService;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.FieldInfoView;

/**
 * @author Harshit Gupta
 */

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class SensitivityAnalysisCalculationDaoImpl implements SensitivityAnalysisCalculationDao {

    @Autowired
    private FarmOutputDetailsService farmOutputDetailsService;
    @Autowired
    private CropResourceUsageService cropResourceUsageService;
    @Autowired
    private CropTypeService cropTypeService;
    @Autowired
    private FarmOutputCalculationDao farmOutputCalculationDao;
    @Autowired
    private LinearProgramingSolveDao linearProgramingSolveDao;
    @Autowired
    private FieldInfoService fieldInfoService;
    @Autowired
    private FarmInfoService farmInfoService;
    @Autowired
    private FarmCustomStrategyService farmCustomStrategyService;
    @Autowired
    private FarmOutputCalculationService farmOutputCalculationService;

    private static final Double zeroDouble = new Double(0);

    @Override
    public JSONObject calcluateFarmOutputStatistics(int farmId,
                                                    String[] resourceArray, String[] cropsArray, String[] cropContractArray, String[] cropsGroupArray, String[] cropProposedArray) {
        FarmInfo farmInfo = farmInfoService.getFarmInfoByIdForUpdate(farmId);
        PlantingProfitLogger.info("" + PlanByStrategy.PLAN_BY_ACRES + "------------" + farmInfo.getStrategy() + "---------");
        PlantingProfitLogger.info(PlanByStrategy.PLAN_BY_ACRES == farmInfo.getStrategy());
        JSONObject jsonObject = null;
        if (PlanByStrategy.PLAN_BY_ACRES == farmInfo.getStrategy()) {
            jsonObject = calcluateFarmOutputStatisticsForAcres(farmInfo, resourceArray, cropsArray, cropContractArray, cropsGroupArray, cropProposedArray);
        } else if (PlanByStrategy.PLAN_BY_FIELDS == farmInfo.getStrategy()) {
            jsonObject = calcluateFarmOutputStatisticsForField(farmInfo, resourceArray, cropsArray, cropContractArray, cropsGroupArray, cropProposedArray);
        }
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    private JSONObject calcluateFarmOutputStatisticsForField(FarmInfo farmInfo, String[] resourceArray, String[] cropsArray, String[] cropContractArray, String[] cropsGroupArray, String[] cropProposedArray) {
        JSONObject jsonObject = new JSONObject();
        List<CropType> cropTypeList = cropTypeService.getAllCropByFarmId(farmInfo.getId());
        List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(farmInfo.getId());
        List<CropBeanForOutput> cropBeanForOutputList = farmOutputCalculationDao.getCropBeanForCalculation(cropTypeList, resourceUsageViews);
        List<FieldInfoView> fieldInfoViews = fieldInfoService.getAllFieldsByFarmId(farmInfo.getId());
        Set<CropsGroup> cropsGroups = farmInfo.getCropsGroup();
        if (resourceArray != null) {
//			int resourceSize = resourceUsageViews.size();
            outer:
            for (String str : resourceArray) {
                for (CropResourceUsageView resourceUsageView : resourceUsageViews) {
                    if (str.split("#-#-#")[0].equals(resourceUsageView.getCropResourceUse())) {
                        resourceUsageView.setCropResourceUseAmount(str.split("#-#-#")[1]);
                        continue outer;
                    }
                }
                /*for (int i = 0; i < resourceSize; i++) {
                    if(str.split("#-#-#")[0].equals(resourceUsageViews.get(i).getCropResourceUse())){
						resourceUsageViews.get(i).setCropResourceUseAmount(str.split("#-#-#")[1]);
						continue outer;
					}
				}*/
            }
        }
        if (cropsArray != null) {
//            int size = cropBeanForOutputList.size();
            outer:
            for (String str : cropsArray) {
                for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                    if (str.split("#-#-#")[0].equals(beanForOutput.getCropType().getCropName())) {
                        if (str.split("#-#-#")[1].equals("max")) {
                            Double maxAcres = Double.parseDouble(str.split("#-#-#")[2]);
                            beanForOutput.setMaxAcre(maxAcres);
                            continue outer;
                        } else if (str.split("#-#-#")[1].equals("min")) {
                            Double minAcres = Double.parseDouble(str.split("#-#-#")[2]);
                            beanForOutput.setMinAcre(minAcres);
                            continue outer;
                        }
                    }


                }

					/*if(str.split("#-#-#")[0].equals(cropBeanForOutputList.get(i).getCropType().getCropName())){
                        if(str.split("#-#-#")[1].equals("max")){
							Double maxAcres = Double.parseDouble(str.split("#-#-#")[2]);
							cropBeanForOutputList.get(i).setMaxAcre(maxAcres);
							continue outer;
						}else if(str.split("#-#-#")[1].equals("min")){
							Double minAcres = Double.parseDouble(str.split("#-#-#")[2]);
							cropBeanForOutputList.get(i).setMinAcre(minAcres);
							continue outer;
						}
					}*/
            }
        }
        if (cropContractArray != null) {
//            int size = cropBeanForOutputList.size();
            outer:
            for (String str : cropContractArray) {
                for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                    if (str.split("#-#-#")[0].equals(beanForOutput.getCropType().getCropName())) {
                        beanForOutput.setFirmAcres(Double.parseDouble(str.split("#-#-#")[2]));
                        continue outer;
                    }
                }

					/*if(str.split("#-#-#")[0].equals(cropBeanForOutputList.get(i).getCropType().getCropName())){
                        cropBeanForOutputList.get(i).setFirmAcres(Double.parseDouble(str.split("#-#-#")[2]));
						continue outer;
					}*/
            }
        }
        if (cropProposedArray != null) {
//            int size = cropBeanForOutputList.size();
            outer:
            for (String str : cropProposedArray) {

                for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                    if (str.split("#-#-#")[0].equals(beanForOutput.getCropType().getCropName())) {
                        beanForOutput.setProposedAcres(Double.parseDouble(str.split("#-#-#")[2]));
                        continue outer;
                    }
                }

                /*for (int i = 0; i < size; i++) {
                    if (str.split("#-#-#")[0].equals(cropBeanForOutputList.get(i).getCropType().getCropName())) {
                        cropBeanForOutputList.get(i).setProposedAcres(Double.parseDouble(str.split("#-#-#")[2]));
                        continue outer;
                    }
                }*/
            }
        }
        if (cropsGroupArray != null) {
            outer:
            for (String str : cropsGroupArray) {
                for (CropsGroup cropsGroup : cropsGroups) {
                    if (str.split("#-#-#")[0].equals(cropsGroup.getCropsGroupName())
                            && str.split("#-#-#")[1].equals("max")) {
                        cropsGroup.setMaximumAcres(str.split("#-#-#")[2]);
                        continue outer;
                    } else if (str.split("#-#-#")[0].equals(cropsGroup.getCropsGroupName())
                            && str.split("#-#-#")[1].equals("min")) {
                        cropsGroup.setMinimumAcres(str.split("#-#-#")[2]);
                        continue outer;
                    }
                }
            }
        }

        /**
         * @added - Abhishek
         * @Date - 19-10-2016
         * @desc -for generating senstivity analysis graph by profit
         */
        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();
        outputBeanForStrategy.setBaselineFlag(false);
        outputBeanForStrategy.setSaveFlag(false);
        outputBeanForStrategy.setFarmInfo(cropBeanForOutputList.get(0).getCropType().getFarmInfo());
        outputBeanForStrategy.setCropTypeList(cropTypeList);
        outputBeanForStrategy.setFarmID(outputBeanForStrategy.getFarmInfo().getId());
        outputBeanForStrategy.setStrategyID(farmCustomStrategyService.getBaseLineStrategyForFarm(outputBeanForStrategy.getFarmInfo()).getId());
        outputBeanForStrategy.setSensitivityFlag(true);
        outputBeanForStrategy.setResourceUsageViews(resourceUsageViews);

//        List<Object> outputDetailsList = farmOutputCalculationDao.calculateFarmOutputStatisticsForField(outputBeanForStrategy);
        List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = farmOutputCalculationService.getAllFarmOutputDetailsForFieldByFarm(outputBeanForStrategy);
        List<CropTypeView> cropTypeViewList = cropTypeService.getAllCropByFarm(outputBeanForStrategy.getFarmID());

        Map<String, Object> mapObjectForGraphForField = farmOutputCalculationDao.createJSONObjectAndMapObjectForGraphForField(cropTypeViewList, farmOutputDetailsForFieldViewList, "");

        Map<String, Object> mapForCropsForField = (Map<String, Object>) mapObjectForGraphForField.get("mapForCropsForField");
        Map<String, String> hashMapForProfit = (Map<String, String>) mapForCropsForField.get("hashMapForProfit");

        List<String[]> array = linearProgramingSolveDao.generateCombination(cropBeanForOutputList, cropsGroups, fieldInfoViews);
        Map<String, Object> map = linearProgramingSolveDao.getBestResultFromLinearProgramingForField(cropBeanForOutputList, resourceUsageViews, cropsGroups, fieldInfoViews, array);
        String[] bestCase = (String[]) map.get("Best_Case");
        Result bestResult = (Result) map.get("Best_Result");
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArrayObjectForGraphByField = new JSONArray();
        if (bestResult != null) {
            PlantingProfitLogger.info("Best result is " + bestResult);
            try {
                jsonObject.put("Potential_Profit", "$" + AgricultureStandardUtils.commaSeparaterForField("" + bestResult.getObjective().longValue()));
            } catch (Exception e) {
                PlantingProfitLogger.error(e);
            }
            boolean flag = false;
            for (FieldInfoView fieldInfoView : fieldInfoViews) {
                flag = false;
                for (String str : bestCase) {
                    if (fieldInfoView.getFieldName().equals(str.split("###")[0])) {
                        for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                            if (beanForOutput.getCropType().getCropName().equals(str.split("###")[1])) {
                                if (bestResult.get(str).longValue() > 0) {
                                    flag = true;
                                    try {
                                        JSONObject object = new JSONObject();
                                        object.put("Field_Info", fieldInfoView.getFieldName() + " (" + AgricultureStandardUtils.withoutDecimalAndComma(bestResult.get(str).doubleValue()) + ")");
                                        object.put("Crop_Info", beanForOutput.getCropType().getCropName());
                                        jsonArray.add(object);

                                        JSONObject objectForGraphByField = new JSONObject();
                                        objectForGraphByField.put("Field_Info", fieldInfoView.getFieldName() + " (" + beanForOutput.getCropType().getCropName() + ")");
                                        objectForGraphByField.put("Crop_Name", beanForOutput.getCropType().getCropName());
                                        objectForGraphByField.put("Land", AgricultureStandardUtils.withoutDecimalAndCommaToLong(bestResult.get(str).doubleValue()));

                                        CropType cropType = beanForOutput.getCropType();
                                        String profit = hashMapForProfit.get(cropType.getCropName()).split(" ")[0];
                                        objectForGraphByField.put("Profit", Integer.parseInt(AgricultureStandardUtils.removeAllCommas(profit)));

                                        putGraphObject(objectForGraphByField, jsonArrayObjectForGraphByField, cropType.getCropName());

                                    } catch (Exception e) {
                                        PlantingProfitLogger.error(e);
                                    }
                                }
                                if (beanForOutput.getFirmAcres() > zeroDouble) {
                                    flag = true;
                                    try {
                                        JSONObject object = new JSONObject();
                                        object.put("Field_Info", fieldInfoView.getFieldName() + " (" + AgricultureStandardUtils.withoutDecimalAndComma(bestResult.get(str + " (Contract)").doubleValue()) + ")");
                                        object.put("Crop_Info", beanForOutput.getCropType().getCropName() + " (Firm)");
                                        jsonArray.add(object);
                                        JSONObject objectForGraphByField = new JSONObject();
                                        objectForGraphByField.put("Field_Info", fieldInfoView.getFieldName() + " (" + beanForOutput.getCropType().getCropName() + " (Firm)" + ")");
                                        objectForGraphByField.put("Crop_Name", beanForOutput.getCropType().getCropName() + " (Firm)");
                                        objectForGraphByField.put("Land", AgricultureStandardUtils.withoutDecimalAndCommaToLong(bestResult.get(str + " (Contract)").doubleValue()));
                                        /**
                                         * @changed - Abhishek
                                         * @Date - 19-10-2016
                                         * @desc -for generating senstivity analysis graph by profit
                                         */
                                        CropType cropType = beanForOutput.getCropType();
                                        String profit = hashMapForProfit.get(cropType.getCropName() + " (Firm)").split(" ")[0];
                                        objectForGraphByField.put("Profit", Integer.parseInt(AgricultureStandardUtils.removeAllCommas(profit)));

                                        putGraphObject(objectForGraphByField, jsonArrayObjectForGraphByField, cropType.getCropName() + " (Firm)");
                                    } catch (Exception e) {
                                        PlantingProfitLogger.error(e);
                                    }
                                }

                                if (beanForOutput.getProposedAcres() > zeroDouble && bestResult.get(str + " (Proposed)").doubleValue() > zeroDouble) {
                                    flag = true;
                                    try {
                                        JSONObject object = new JSONObject();
                                        object.put("Field_Info", fieldInfoView.getFieldName() + " (" + AgricultureStandardUtils.withoutDecimalAndComma(bestResult.get(str + " (Proposed)").doubleValue()) + ")");
                                        object.put("Crop_Info", beanForOutput.getCropType().getCropName() + " (Proposed)");
                                        jsonArray.add(object);
                                        JSONObject objectForGraphByField = new JSONObject();
                                        objectForGraphByField.put("Field_Info", fieldInfoView.getFieldName() + " (" + beanForOutput.getCropType().getCropName() + " (Proposed)" + ")");
                                        objectForGraphByField.put("Crop_Name", beanForOutput.getCropType().getCropName() + " (Proposed)");
                                        objectForGraphByField.put("Land", AgricultureStandardUtils.withoutDecimalAndCommaToLong(bestResult.get(str + " (Proposed)").doubleValue()));

                                        /**
                                         * @changed - Abhishek
                                         * @Date - 19-10-2016
                                         * @desc -for generating senstivity analysis graph by profit
                                         */
                                        CropType cropType = beanForOutput.getCropType();
                                        String profit = hashMapForProfit.get(cropType.getCropName() + " (Proposed)").split(" ")[0];
                                        objectForGraphByField.put("Profit", Integer.parseInt(AgricultureStandardUtils.removeAllCommas(profit)));

                                        putGraphObject(objectForGraphByField, jsonArrayObjectForGraphByField, cropType.getCropName() + " (Proposed)");
                                    } catch (Exception e) {
                                        PlantingProfitLogger.error(e);
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                if (!flag) {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("Field_Info", fieldInfoView.getFieldName() + " (" + AgricultureStandardUtils.withoutDecimalAndComma(fieldInfoView.getFieldSize()) + ")");
                        object.put("Crop_Info", "Not Planted");
                        jsonArray.add(object);
                        JSONObject objectForGraphByField = new JSONObject();
                        objectForGraphByField.put("Field_Info", fieldInfoView.getFieldName());
                        objectForGraphByField.put("Land", 0);
                        /**
                         * @changed - Abhishek
                         * @Date - 19-10-2016
                         * @desc -for generating senstivity analysis graph by profit
                         */
                        objectForGraphByField.put("Profit", 0);

                        jsonArrayObjectForGraphByField.add(objectForGraphByField);
                    } catch (Exception e) {

                        PlantingProfitLogger.error(e);
                    }
                }
            }
        } else {
            try {
                jsonObject.put("Potential_Profit", "$0");
            } catch (Exception e) {

                PlantingProfitLogger.error(e);
            }
            for (FieldInfoView fieldInfoView : fieldInfoViews) {
                JSONObject object = new JSONObject();
                try {
                    object.put("Field_Info", fieldInfoView.getFieldName() + " (" + AgricultureStandardUtils.withoutDecimalAndComma(fieldInfoView.getFieldSize()) + ")");
                    object.put("Crop_Info", "Not Planted");
                    JSONObject objectForGraphByField = new JSONObject();
                    objectForGraphByField.put("Field_Info", fieldInfoView.getFieldName());
                    objectForGraphByField.put("Land", 0);
                    /**
                     * @changed - Abhishek
                     * @Date - 19-10-2016
                     * @desc -for generating senstivity analysis graph by profit
                     */
                    objectForGraphByField.put("Profit", 0);

                    jsonArrayObjectForGraphByField.add(objectForGraphByField);
                } catch (Exception e) {
                    PlantingProfitLogger.error(e);
                }
                jsonArray.add(object);
            }
        }
        try {
            mapForCropsForField.put("farmInfoView", new FarmInfoView(farmInfo));
            JSONObject object = new JSONObject();
            object.putAll(mapForCropsForField);

            jsonObject.put("outputDetails", farmOutputDetailsService.buildCropAcreageContent(object));
            jsonObject.put("Field_Crop_Info", jsonArray);
            jsonObject.put("JSONArrayObjectForGraphByField", jsonArrayObjectForGraphByField);
            jsonObject.put("Strategy", "Field");
        } catch (Exception e) {

            PlantingProfitLogger.error(e);
        }
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    private void putGraphObject(JSONObject jsonObject, JSONArray jsonArray,String cropName){
        if(jsonArray.size() == 0){
            jsonArray.add(jsonObject);
        } else {
            boolean cropFlag = true;
            for (Object o : jsonArray) {
                JSONObject jObject = (JSONObject) o;
                if (String.valueOf(jObject.get("Crop_Name")).equalsIgnoreCase(cropName)) {
                    cropFlag = false;
                    int land1 = Integer.parseInt(jObject.get("Land").toString());
                    int land2 = Integer.parseInt(jsonObject.get("Land").toString());
                    jObject.put("Land", land1 + land2);
                }
            }
            if(cropFlag){
                jsonArray.add(jsonObject);
            }
        }
    }

    private JSONObject calcluateFarmOutputStatisticsForAcres(FarmInfo farmInfo, String[] resourceArray, String[] cropsArray, String[] cropContractArray, String[] cropsGroupArray, String[] cropProposedArray) {
        JSONObject jsonObject = null;
        List<CropType> cropTypeList = cropTypeService.getAllCropByFarmId(farmInfo.getId());
        List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(farmInfo.getId());
        List<CropBeanForOutput> cropBeanForOutput = farmOutputCalculationDao.getCropBeanForCalculation(cropTypeList, resourceUsageViews);
        Set<CropsGroup> cropsGroups = farmInfo.getCropsGroup();
        if (resourceArray != null) {
            int resourceSize = resourceUsageViews.size();
            outer:
            for (String str : resourceArray) {
                for (int i = 0; i < resourceSize; i++) {
                    if (str.split("#-#-#")[0].equals(resourceUsageViews.get(i).getCropResourceUse())) {
                        resourceUsageViews.get(i).setCropResourceUseAmount(str.split("#-#-#")[1]);
                        continue outer;
                    }
                }
            }
        }
        if (cropsArray != null) {
            int size = cropBeanForOutput.size();
            outer:
            for (String str : cropsArray) {
                for (int i = 0; i < size; i++) {
                    if (str.split("#-#-#")[0].equals(cropBeanForOutput.get(i).getCropType().getCropName())) {
                        if (str.split("#-#-#")[1].equals("max")) {
                            Double maxAcres = Double.parseDouble(str.split("#-#-#")[2]);
                            cropBeanForOutput.get(i).setMaxAcre(maxAcres);
                            continue outer;
                        } else if (str.split("#-#-#")[1].equals("min")) {
                            Double minAcres = Double.parseDouble(str.split("#-#-#")[2]);
                            cropBeanForOutput.get(i).setMinAcre(minAcres);
                            continue outer;
                        }
                    }
                }
            }
        }
        if (cropContractArray != null) {
            int size = cropBeanForOutput.size();
            outer:
            for (String str : cropContractArray) {
                for (int i = 0; i < size; i++) {
                    if (str.split("#-#-#")[0].equals(cropBeanForOutput.get(i).getCropType().getCropName())) {
                        if (str.split("#-#-#")[1].equals("min")) {
                            Double minAcres = Double.parseDouble(str.split("#-#-#")[2]);
                            cropBeanForOutput.get(i).setFirmAcres(minAcres);
                            continue outer;
                        }
                    }
                }
            }
        }
        if (cropProposedArray != null) {
            int size = cropBeanForOutput.size();
            outer:
            for (String str : cropProposedArray) {
                for (int i = 0; i < size; i++) {
                    if (str.split("#-#-#")[0].equals(cropBeanForOutput.get(i).getCropType().getCropName())) {
                        if (str.split("#-#-#")[1].equals("min")) {
                            Double minAcres = Double.parseDouble(str.split("#-#-#")[2]);
                            cropBeanForOutput.get(i).setProposedAcres(minAcres);
                            continue outer;
                        }
                    }
                }
            }
        }
        if (cropsGroupArray != null) {
            outer:
            for (String str : cropsGroupArray) {
                for (CropsGroup cropsGroup : cropsGroups) {
                    if (str.split("#-#-#")[0].equals(cropsGroup.getCropsGroupName())) {
                        if (str.split("#-#-#")[1].equals("max")) {
                            cropsGroup.setMaximumAcres(str.split("#-#-#")[2]);
                            continue outer;
                        } else if (str.split("#-#-#")[1].equals("min")) {
                            cropsGroup.setMinimumAcres(str.split("#-#-#")[2]);
                            continue outer;
                        }
                    }
                }
            }
        }
        try {
            jsonObject = calculateAcresForEachCropForAcres(cropBeanForOutput, farmInfo.getLand(), resourceUsageViews, cropsGroups);
        } catch (Exception exception) {
            PlantingProfitLogger.error(exception);
        }
        return jsonObject;
    }

    private JSONObject calculateAcresForEachCropForAcres(List<CropBeanForOutput> cropBeanForOutputList, String land, List<CropResourceUsageView> resourceUsageViews, Set<CropsGroup> cropsGroups) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArrayForGraphByCrop = new JSONArray();

        /**
         * @added - Abhishek
         * @Date - 19-10-2016
         * @desc -for generating senstivity analysis graph by profit
         */
        List<CropType> cropTypeList = new ArrayList<>();
        for (CropBeanForOutput cropBeanForOutput : cropBeanForOutputList) {
            cropTypeList.add(cropBeanForOutput.getCropType());
        }

        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();
        outputBeanForStrategy.setBaselineFlag(false);
        outputBeanForStrategy.setSaveFlag(false);
        outputBeanForStrategy.setFarmInfo(cropBeanForOutputList.get(0).getCropType().getFarmInfo());
        outputBeanForStrategy.setCropTypeList(cropTypeList);
        outputBeanForStrategy.setFarmID(outputBeanForStrategy.getFarmInfo().getId());
        outputBeanForStrategy.setStrategyID(farmCustomStrategyService.getBaseLineStrategyForFarm(outputBeanForStrategy.getFarmInfo()).getId());
        outputBeanForStrategy.setSensitivityFlag(true);
        outputBeanForStrategy.setResourceUsageViews(resourceUsageViews);

//		List<Object> outputDetailsList = farmOutputCalculationDao.calculateFarmOutputStatisticsForAcres(outputBeanForStrategy);
        List<FarmOutputDetailsView> farmOutputDetailsByFarmList = farmOutputCalculationService.getAllFarmOutputDetailsByFarm(outputBeanForStrategy);

        Result result = linearProgramingSolveDao.getLinearProgramingResultForAcerage(cropBeanForOutputList, land, resourceUsageViews, cropsGroups);
        if (result != null) {
            PlantingProfitLogger.info("Result for calculation is " + result);
            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                try {
                    JSONObject object = new JSONObject();
                    Long acreUsed = AgricultureStandardUtils.withoutDecimalAndCommaToLong(result.get(beanForOutput.getCropType().getCropName()).doubleValue());
                    object.put("Crop_Name", beanForOutput.getCropType().getCropName());
                    object.put("land", AgricultureStandardUtils.commaSeparaterForLong(acreUsed));
                    jsonArray.add(object);
                    JSONObject objectForGraphByCrop = new JSONObject();
                    objectForGraphByCrop.put("Crop_Name", beanForOutput.getCropType().getCropName());
                    objectForGraphByCrop.put("Land", acreUsed);
                    /**
                     * @changed - Abhishek
                     * @Date - 19-10-2016
                     * @desc -for generating senstivity analysis graph by profit
                     */
                    for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsByFarmList) {
                        if (Objects.equals(farmOutputDetailsView.getCropTypeView().getId(), beanForOutput.getCropType().getId()) &&
                                Objects.equals(acreUsed, farmOutputDetailsView.getUsedAcresAsDouble().longValue())) {
                            objectForGraphByCrop.put("Profit", farmOutputDetailsView.getUsedAcresAsDouble().intValue());
                        }
                    }

//                        for (Object o : outputDetailsList) {
//							FarmOutputDetails farmOutputDetails = (FarmOutputDetails)o;
//							if(Objects.equals(farmOutputDetails.getCropType().getId(), beanForOutput.getCropType().getId()) &&
//									Objects.equals(acreUsed, farmOutputDetails.getUsedAcres().longValue())){
//								objectForGraphByCrop.put("Profit", farmOutputDetails.getProfit().intValue());
//							}
//
//						}

                    jsonArrayForGraphByCrop.add(objectForGraphByCrop);
                } catch (Exception e) {
                    PlantingProfitLogger.error(e);
                }
                if (beanForOutput.getFirmAcres() > zeroDouble) {
                    try {
                        JSONObject object = new JSONObject();
                        Long acreUsed = AgricultureStandardUtils.withoutDecimalAndCommaToLong(beanForOutput.getFirmAcres());
                        object.put("Crop_Name", beanForOutput.getCropType().getCropName() + " (Contract)");
                        object.put("land", AgricultureStandardUtils.commaSeparaterForLong(acreUsed));
                        jsonArray.add(object);
                        JSONObject objectForGraphByCrop = new JSONObject();
                        objectForGraphByCrop.put("Crop_Name", beanForOutput.getCropType().getCropName() + " (Contract)");
                        objectForGraphByCrop.put("Land", acreUsed);
                        /**
                         * @changed - Abhishek
                         * @Date - 19-10-2016
                         * @desc -for generating senstivity analysis graph by profit
                         */
                        for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsByFarmList) {
                            if (Objects.equals(farmOutputDetailsView.getCropTypeView().getId(), beanForOutput.getCropType().getId()) &&
                                    Objects.equals(acreUsed, farmOutputDetailsView.getUsedAcresAsDouble().longValue())) {
                                objectForGraphByCrop.put("Profit", farmOutputDetailsView.getUsedAcresAsDouble().intValue());
                            }
                        }

//							for (Object o : outputDetailsList) {
//								FarmOutputDetails farmOutputDetails = (FarmOutputDetails)o;
//								if(Objects.equals(farmOutputDetails.getCropType().getId(), beanForOutput.getCropType().getId()) &&
//										Objects.equals(acreUsed, farmOutputDetails.getUsedAcres().longValue())){
//									objectForGraphByCrop.put("Profit", farmOutputDetails.getProfit().intValue());
//								}
//
//							}

                        jsonArrayForGraphByCrop.add(objectForGraphByCrop);
                    } catch (Exception e) {
                        PlantingProfitLogger.error(e);
                    }
                } else if (beanForOutput.getProposedAcres() > zeroDouble) {
                    try {
                        if (result.get(beanForOutput.getCropType().getCropName() + " (Proposed)").doubleValue() > zeroDouble) {
                            JSONObject object = new JSONObject();
                            Long acreUsed = AgricultureStandardUtils.withoutDecimalAndCommaToLong(result.get(beanForOutput.getCropType().getCropName() + " (Proposed)").doubleValue());
                            object.put("Crop_Name", beanForOutput.getCropType().getCropName() + " (Proposed)");
                            object.put("land", AgricultureStandardUtils.commaSeparaterForLong(acreUsed));
                            jsonArray.add(object);
                            JSONObject objectForGraphByCrop = new JSONObject();
                            objectForGraphByCrop.put("Crop_Name", beanForOutput.getCropType().getCropName() + " (Proposed)");
                            objectForGraphByCrop.put("Land", acreUsed);
                            /**
                             * @changed - Abhishek
                             * @Date - 19-10-2016
                             * @desc -for generating senstivity analysis graph by profit
                             */
                            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsByFarmList) {
                                if (Objects.equals(farmOutputDetailsView.getCropTypeView().getId(), beanForOutput.getCropType().getId()) &&
                                        Objects.equals(acreUsed, farmOutputDetailsView.getUsedAcresAsDouble().longValue())) {
                                    objectForGraphByCrop.put("Profit", farmOutputDetailsView.getUsedAcresAsDouble().intValue());
                                }
                            }
//								for (Object o : outputDetailsList) {
//									FarmOutputDetails farmOutputDetails = (FarmOutputDetails)o;
//									if(Objects.equals(farmOutputDetails.getCropType().getId(), beanForOutput.getCropType().getId()) &&
//											Objects.equals(acreUsed, farmOutputDetails.getUsedAcres().longValue())){
//										objectForGraphByCrop.put("Profit", farmOutputDetails.getProfit().intValue());
//									}
//
//								}

                            jsonArrayForGraphByCrop.add(objectForGraphByCrop);
                        }
                    } catch (Exception exception) {
                        PlantingProfitLogger.error(exception);
                    }
                }
            }
            try {
                jsonObject.put("Potential_Profit", "$" + AgricultureStandardUtils.commaSeparaterForLong(result.getObjective().longValue()));
            } catch (Exception e) {
                PlantingProfitLogger.error(e);
            }
        } else {
            for (CropBeanForOutput beanForOutput : cropBeanForOutputList) {
                JSONObject object = new JSONObject();
                JSONObject objectForGraphByCrop = new JSONObject();
                try {
                    object.put("Crop_Name", beanForOutput.getCropType().getCropName());
                    object.put("land", 0);
                    objectForGraphByCrop.put("Crop_Name", beanForOutput.getCropType().getCropName() + " (Proposed)");
                    objectForGraphByCrop.put("Land", 0);
                    /**
                     * @changed - Abhishek
                     * @Date - 19-10-2016
                     * @desc -for generating senstivity analysis graph by profit
                     */
                    objectForGraphByCrop.put("Profit", 0);
                } catch (Exception e) {
                    PlantingProfitLogger.error(e);
                }
                jsonArray.add(object);
                jsonArrayForGraphByCrop.add(objectForGraphByCrop);
            }
            try {
                jsonObject.put("Potential_Profit", "$0");
            } catch (Exception e) {
                PlantingProfitLogger.error(e);
            }
        }
        try {
            JSONObject object = new JSONObject();
            object.put("farmInfoView", new FarmInfoView(outputBeanForStrategy.getFarmInfo()));
            object.put("farmOutputDetails", farmOutputDetailsByFarmList);

            jsonObject.put("outputDetails", farmOutputDetailsService.buildCropAcreageContent(object));
            jsonObject.put("Crop_Details", jsonArray);
            jsonObject.put("JSONArrayObjectForGraphByCrop", jsonArrayForGraphByCrop);
            jsonObject.put("Strategy", "Acerage");
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }
        return jsonObject;
    }

    @Override
    public JSONObject getSAForCastGraphForSingleResource(int farmId, String resourceName, long differenceValue, String cropName, String selectionType, String rangeType, Long currentPotentialProfit) {
        FarmInfo farmInfo = farmInfoService.getFarmInfoByIdForUpdate(farmId);
        PlantingProfitLogger.info("Case3:" + differenceValue);
        PlantingProfitLogger.info("" + PlanByStrategy.PLAN_BY_ACRES + "------------" + farmInfo.getStrategy() + "---------");
        PlantingProfitLogger.info(PlanByStrategy.PLAN_BY_ACRES == farmInfo.getStrategy());
        JSONObject jsonObject = null;
        if (PlanByStrategy.PLAN_BY_ACRES == farmInfo.getStrategy()) {
            jsonObject = calcluateFarmOutputStatisticsForAcresForCastGraph(farmInfo, resourceName, differenceValue, cropName, selectionType, rangeType, currentPotentialProfit);
        } else if (PlanByStrategy.PLAN_BY_FIELDS == farmInfo.getStrategy()) {
            jsonObject = calcluateFarmOutputStatisticsForFieldForCastGraph(farmInfo, resourceName, differenceValue, cropName, selectionType, rangeType, currentPotentialProfit);
        }
        return jsonObject;
    }

    private JSONObject calcluateFarmOutputStatisticsForFieldForCastGraph(FarmInfo farmInfo, String resourceName, Long differenceValue, String cropName, String selectionType, String rangeType, Long currentPotentialProfit) {
        JSONObject outerJsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String uomOfResource = null;
        List<CropType> cropTypeList = cropTypeService.getAllCropByFarmId(farmInfo.getId());
        List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(farmInfo.getId());
        List<CropBeanForOutput> cropBeanForOutput = farmOutputCalculationDao.getCropBeanForCalculation(cropTypeList, resourceUsageViews);
        List<FieldInfoView> fieldInfoViews = fieldInfoService.getAllFieldsByFarmId(farmInfo.getId());
        Set<CropsGroup> cropsGroups = farmInfo.getCropsGroup();
        List<String[]> array = linearProgramingSolveDao.generateCombination(cropBeanForOutput, cropsGroups, fieldInfoViews);
        long oldProfit = 0;
        boolean continueFlag = true;
        CropResourceUsageView resource = null;

        Double amount = 0.0;

        long tempHolder = differenceValue.longValue();
        outer:
        for (int i = 0; i <= 5; i++) {

            if (i == 0){
                differenceValue = 0L;
            } else {
                differenceValue = tempHolder;
            }

            /**
             * @changed - Abhishek
             * @date - 09-12-2015
             */
            if (continueFlag) {
                JSONObject jsonObject = new JSONObject();
                if (resourceName != null) {
                    for (CropResourceUsageView cropResourceUsageView : resourceUsageViews) {
                        if (cropResourceUsageView.getCropResourceUse().equals(resourceName)) {
                            resource = cropResourceUsageView;
                            /**
                             * @changed - Abhishek
                             * @date - 25-02-2016
                             * @decs - according to slide#6 of 02212016
                             */
							/* if (i == 1) {
								cropResourceUsageView.setCropResourceUseAmount(""+(Long.parseLong(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank())));
							} else {*/

                            amount = Double.parseDouble(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()) + differenceValue;
                            if (amount < 0) {
//                                break outer;
                            }
                            cropResourceUsageView.setCropResourceUseAmount("" + (Long.parseLong(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()) + (differenceValue)));
							/*}*/
//						cropResourceUsageView.setCropResourceUseAmount(""+(Long.parseLong(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank())+((i>1)?differenceValue:0)));
                            try {
                                /**
                                 * @changed - Abhishek
                                 * @date - 09-12-2015
                                 */
                                String resourceValue = cropResourceUsageView.getCropResourceUseAmount().contains("-") ? "0" : cropResourceUsageView.getCropResourceUseAmount();
                                jsonObject.put("resourceValue", resourceValue);
                                if (uomOfResource == null)
                                    uomOfResource = cropResourceUsageView.getUoMResource();
                            } catch (Exception e) {
                                PlantingProfitLogger.error(e);
                            }
                            break;
                        }
                    }
                } else if (cropName != null) {
                    long cropValue = 0l;
                    if (!selectionType.equals("Group")) {
                        for (CropBeanForOutput beanForOutput : cropBeanForOutput) {
                            if (beanForOutput.getCropType().getCropName().equals(cropName)) {
                                if (selectionType.equals("Crop") && rangeType.equals("Minimum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = beanForOutput.getMinAcre().longValue();
									} else {*/
                                    amount = beanForOutput.getMinAcre() + differenceValue;
                                    if (amount < 0) {
//                                        break outer;
                                    }
                                    beanForOutput.setMinAcre(beanForOutput.getMinAcre() + (differenceValue));
                                    cropValue = new Double(beanForOutput.getMinAcre()).longValue();
									/*}*/
                                } else if (selectionType.equals("Crop") && rangeType.equals("Maximum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = beanForOutput.getMaxAcre().longValue();
									} else {*/
                                    amount = beanForOutput.getMaxAcre() + differenceValue;
                                    if (amount < 0) {
//                                        break outer;
                                    }
                                    beanForOutput.setMaxAcre(beanForOutput.getMaxAcre() + (differenceValue));
                                    cropValue = new Double(beanForOutput.getMaxAcre()).longValue();
									/*}*/
                                } else if (selectionType.equals("Contract") && rangeType.equals("Minimum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = beanForOutput.getFirmAcres().longValue();
									} else {*/
                                    amount = beanForOutput.getFirmAcres() + differenceValue;
                                    if (amount < 0) {
//                                        break outer;
                                    }
                                    beanForOutput.setFirmAcres(beanForOutput.getFirmAcres() + (differenceValue));
                                    cropValue = new Double(beanForOutput.getFirmAcres()).longValue();
									/*}*/
                                } else if (selectionType.equals("Proposed") && rangeType.equals("Minimum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = beanForOutput.getProposedAcres().longValue();
									} else {*/
                                    amount = beanForOutput.getProposedAcres() + differenceValue;
                                    if (amount < 0) {
//                                        break outer;
                                    }
                                    beanForOutput.setProposedAcres(beanForOutput.getProposedAcres() + (differenceValue));
                                    cropValue = new Double(beanForOutput.getProposedAcres()).longValue();
									/*}*/
                                }
                                break;
                            }
                        }
                    } else if (selectionType.equals("Group")) {
                        for (CropsGroup cropsGroup : cropsGroups) {
                            if (cropsGroup.getCropsGroupName().equals(cropName)) {
                                if (rangeType.equals("Minimum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = AgricultureStandardUtils.stringToLong(cropsGroup.getMinimumAcres());
									} else {*/
                                    amount = Double.parseDouble(cropsGroup.getMinimumAcres()) + differenceValue;
                                    if (amount < 0) {
//                                        break outer;
                                    }
                                    PlantingProfitLogger.info("Case4:" + differenceValue);
                                    cropsGroup.setMinimumAcres("" + (Integer.parseInt(cropsGroup.getMinimumAcres()) + (differenceValue)));
                                    PlantingProfitLogger.info("Case1:" + cropsGroup.getMinimumAcres());
                                    cropValue = AgricultureStandardUtils.stringToLong(cropsGroup.getMinimumAcres());
									/*}*/
                                } else if (rangeType.equals("Maximum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = AgricultureStandardUtils.stringToLong(cropsGroup.getMaximumAcres());
									} else {*/
                                    amount = Double.parseDouble(cropsGroup.getMaximumAcres()) + differenceValue;
                                    if (amount < 0) {
//                                        break outer;
                                    }
                                    cropsGroup.setMaximumAcres("" + (Integer.parseInt(cropsGroup.getMaximumAcres()) + (differenceValue)));
                                    cropValue = AgricultureStandardUtils.stringToLong(cropsGroup.getMaximumAcres());
									/*}*/
                                }
                                break;
                            }
                        }
                    }
                    /**
                     * @changed - Abhishek
                     * @date - 09-12-2015
                     */
                    if (cropValue <= 0) {
                        continueFlag = false;
                        cropValue = 0;
                    }
                    jsonObject.put("cropValue", cropValue);

                }
                Map<String, Object> map = linearProgramingSolveDao.getBestResultFromLinearProgramingForField(cropBeanForOutput, resourceUsageViews, cropsGroups, fieldInfoViews, array);
                Result bestResult = (Result) map.get("Best_Result");
                String[] bestCase = (String[]) map.get("Best_Case");
                if (bestResult != null && amount > 0) {
                    try {
                        if(i == 0) {
                            jsonObject.put("Potential_Profit", currentPotentialProfit);
                        } else {
                            jsonObject.put("Potential_Profit", bestResult.getObjective().longValue());
                        }
//                        jsonObject.put("Potential_Profit", bestResult.getObjective().longValue());
                    } catch (Exception e) {

                        PlantingProfitLogger.error(e);
                    }
                    JSONArray jsonArrayInner = new JSONArray();
                    boolean flag = false;
                    for (FieldInfoView fieldInfoView : fieldInfoViews) {
                        flag = false;
                        for (String str : bestCase) {
                            if (fieldInfoView.getFieldName().equals(str.split("###")[0])) {
                                for (CropBeanForOutput beanForOutput : cropBeanForOutput) {
                                    if (beanForOutput.getCropType().getCropName().equals(str.split("###")[1])) {
                                        if (bestResult.get(str).longValue() > 0) {
                                            flag = true;
                                            JSONObject object = new JSONObject();
                                            object.put("Field_Info", fieldInfoView.getFieldName() + " (" + AgricultureStandardUtils.withoutDecimalAndComma(bestResult.get(str).doubleValue()) + ")");
                                            object.put("Crop_Info", beanForOutput.getCropType().getCropName());
                                            jsonArrayInner.add(object);

                                        }
                                        if (beanForOutput.getFirmAcres() > zeroDouble) {
                                            flag = true;
                                            JSONObject object = new JSONObject();
                                            object.put("Field_Info", fieldInfoView.getFieldName() + " (" + AgricultureStandardUtils.withoutDecimalAndComma(bestResult.get(str + " (Contract)").doubleValue()) + ")");
                                            object.put("Crop_Info", beanForOutput.getCropType().getCropName() + " (Contract)");
                                            jsonArrayInner.add(object);

                                        } else if (beanForOutput.getProposedAcres() > zeroDouble && bestResult.get(str + " (Proposed)").doubleValue() > zeroDouble) {
                                            flag = true;
                                            JSONObject object = new JSONObject();
                                            object.put("Field_Info", fieldInfoView.getFieldName() + " (" + AgricultureStandardUtils.withoutDecimalAndComma(bestResult.get(str + " (Proposed)").doubleValue()) + ")");
                                            object.put("Crop_Info", beanForOutput.getCropType().getCropName() + " (Proposed)");
                                            jsonArrayInner.add(object);

                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if (!flag) {
                            JSONObject object = new JSONObject();
                            object.put("Field_Info", fieldInfoView.getFieldName() + " (" + AgricultureStandardUtils.withoutDecimalAndComma(fieldInfoView.getFieldSize()) + ")");
                            object.put("Crop_Info", "Not Planted");
                            jsonArrayInner.add(object);

                        }
                    }

                    jsonObject.put("Field_Crop_Info", jsonArrayInner);

                    long profit, temp = bestResult.getObjective().longValue();
                    profit = temp - currentPotentialProfit;
                    if(profit == 0.5 || profit == -0.5 || profit == -1 || profit == 1){
                        profit = currentPotentialProfit;
//                        currentPotentialProfit = temp;
                    } else {
                        profit = temp;
                    }

                    PlantingProfitLogger.info("Called 1 iteration : " + i);
                    PlantingProfitLogger.info("difference value : " + differenceValue);
                    PlantingProfitLogger.info(profit != 0 ? "Estimated Income value : " + profit : 0);
                    PlantingProfitLogger.info("old profit value : " + oldProfit);
                    PlantingProfitLogger.info("currentPotentialProfit value : " + currentPotentialProfit);

                    /**
                     * @Changed Abhishek
                     * @Date 02-12-2015
                     * @updated - 30-12-2015
                     */
                    String differenceString, resourceStr = resourceName;
                    if (resourceName != null) {
                        if (resource.getCropResourceUse().equalsIgnoreCase("capital")) {
                            differenceString = "$" + AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue);
                            resourceStr = AppConstants.WORKING_CAPITAL;
                        } else
                            differenceString = AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue) + " " + resource.getUoMResource();
                    } else {
                        differenceString = AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue);
                    }
                    if(i == 0){
//                        jsonObject.put("bubbleMessage", "This is the baseline amount for " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr)));
                        jsonObject.put("bubbleMessage", "This is the baseline " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " crop acreage limit for " : "") + cropName) : (" amount for " + resourceStr)));
                    } else if (differenceValue > 0) {
                        if ((profit < currentPotentialProfit) || (profit < oldProfit)) {

                            jsonObject.put("bubbleMessage", "Increasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) decreases Estimated Income by $" + AgricultureStandardUtils.commaSeparaterForLong(currentPotentialProfit - profit < 0 ? 0 : currentPotentialProfit - profit) + ".");
//						jsonObject.put("bubbleMessage", "on increasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+(i*differenceValue)+" amount you will get the Estimated Income decreased to $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");

                        } else if ((profit == currentPotentialProfit) || (bestResult.getObjective().longValue() == oldProfit)) {

                            jsonObject.put("bubbleMessage", "Increasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) will not change Estimated Income i.e $" + AgricultureStandardUtils.commaSeparaterForLong(profit) + ".");
//						jsonObject.put("bubbleMessage", "on increasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+(i*differenceValue)+" amount your Estimated Income will remain same i.e $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");

                        } else {

                            jsonObject.put("bubbleMessage", "Increasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) increases Estimated Income by $" + AgricultureStandardUtils.commaSeparaterForLong(profit - currentPotentialProfit < 0 ? 0 : profit - currentPotentialProfit) + ".");
//						jsonObject.put("bubbleMessage", "On increasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+(i*differenceValue)+" amount you will get the Estimated Income increased to $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");

                        }
                    } else {
                        if ((profit < currentPotentialProfit) || (bestResult.getObjective().longValue() < oldProfit)) {

                            jsonObject.put("bubbleMessage", "Decreasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) decreases Estimated Income by $" + AgricultureStandardUtils.commaSeparaterForLong(currentPotentialProfit - profit < 0 ? 0 : currentPotentialProfit - profit) + ".");
//						jsonObject.put("bubbleMessage", "On decreasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+Math.abs(i*differenceValue)+" amount you will get the Estimated Income decreased to $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");

                        } else if ((profit == currentPotentialProfit) || (bestResult.getObjective().longValue() == oldProfit)) {

                            jsonObject.put("bubbleMessage", "Decreasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + differenceString + (resourceStr == null ? " acres" : "") + " (from the original amount) will not change Estimated Income i.e $" + AgricultureStandardUtils.commaSeparaterForLong(profit) + ".");
//						jsonObject.put("bubbleMessage", "On decreasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+Math.abs(i*differenceValue)+" amount your Estimated Income will remain same i.e $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");

                        } else {

                            jsonObject.put("bubbleMessage", "Decreasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) increases Estimated Income by $" + AgricultureStandardUtils.commaSeparaterForLong(profit - currentPotentialProfit < 0 ? 0 : profit - currentPotentialProfit) + ".");
//						jsonObject.put("bubbleMessage", "On decreasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+Math.abs(i*differenceValue)+" amount you will get the Estimated Income increased to $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");

                        }
                    }
                    oldProfit = profit;
                } else {
                    oldProfit = 0;
                    String differenceString, resourceStr = resourceName;
                    if (resourceName != null) {
                        if (resource.getCropResourceUse().equalsIgnoreCase("capital")) {
                            differenceString = "$" + AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue);
                            resourceStr = AppConstants.WORKING_CAPITAL;
                        } else
                            differenceString = AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue) + " " + resource.getUoMResource();
                    } else {
                        differenceString = AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue);
                    }

                    jsonObject.put("Potential_Profit", 0);
                    if (differenceValue > 0) {
                        /**
                         * @chanegd - Abhishek
                         * @date - 09-12-2015
                         * @updated - 30-12-2015
                         */
                        continueFlag = false;
                        jsonObject.put("bubbleMessage", "A feasible solution cannot be generated if " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType
                                + " acres of " : "") + cropName) : (resourceStr + " resource")) + " is increased by " + (differenceString) + (resourceStr == null ? " acres" : ""));
//						jsonObject.put("bubbleMessage", "A feasible solution cannot be generated if "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" acres of ":"")+cropName):(resourceName+" resource"))+" is increased to " + (i *differenceValue) + (resourceName == null? " acres" : ""));
//						jsonObject.put("bubbleMessage", "Increasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+(i*differenceValue)+" amount a valid strategy can not be generated so there will be no Estimated Income, we suggest you to change the amount and try again.");
                    } else {
                        /**
                         * @chanegd - Abhishek
                         * @date - 09-12-2015
                         * @updated - 30-12-2015
                         */
                        continueFlag = false;
                        jsonObject.put("bubbleMessage", "A feasible solution cannot be generated if " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType
                                + " acres of " : "") + cropName) : (resourceStr + " resource")) + " is reduced by " + (differenceString) + (resourceStr == null ? " acres" : ""));
//						jsonObject.put("bubbleMessage", "A feasible solution cannot be generated if "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" acres of ":"")+cropName):(resourceName+" resource"))+" is reduced to " + (i *differenceValue) + (resourceName == null? " acres" : ""));
//      	            jsonObject.put("bubbleMessage", "On decreasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+Math.abs(i*differenceValue)+" amount a valid strategy can not be generated so there will be no Estimated Income, we suggest you to change the amount and try again.");
                    }

                    JSONArray jsonArrayInner = new JSONArray();
                    for (FieldInfoView fieldInfoView : fieldInfoViews) {
                        JSONObject object = new JSONObject();

                        object.put("Field_Info", fieldInfoView.getFieldName() + " (" + AgricultureStandardUtils.withoutDecimalAndComma(fieldInfoView.getFieldSize()) + ")");
                        object.put("Crop_Info", "Not Planted");

                        jsonArrayInner.add(object);
                    }
                    jsonObject.put("Field_Crop_Info", jsonArrayInner);

                }
                jsonObject.put("Strategy", "Field");
                jsonArray.add(jsonObject);
            }
        }
        outerJsonObject.put("resourceUnit", uomOfResource != null ? uomOfResource : "");
        outerJsonObject.put("Resource_Array", jsonArray);

        return outerJsonObject;
    }

    private JSONObject calcluateFarmOutputStatisticsForAcresForCastGraph(FarmInfo farmInfo, String resourceName, Long differenceValue, String cropName, String selectionType, String rangeType, Long currentPotentialProfit) {
        JSONObject outerJsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String uomOfResource = null;
        List<CropType> cropTypeList = cropTypeService.getAllCropByFarmId(farmInfo.getId());
        List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(farmInfo.getId());
        List<CropBeanForOutput> cropBeanForOutput = farmOutputCalculationDao.getCropBeanForCalculation(cropTypeList, resourceUsageViews);
        Set<CropsGroup> cropsGroups = farmInfo.getCropsGroup();
        long oldProfit = 0;
        boolean continueFlag = true;
        CropResourceUsageView resource = null;

        Long amount = 0L;

        long tempHolder = differenceValue.longValue();

        outer:
        for (int i = 0; i <= 5; i++) {

            if (i == 0){
                differenceValue = 0L;
            } else {
                differenceValue = tempHolder;
            }

            if (continueFlag) {
                JSONObject jsonObject = new JSONObject();
                if (resourceName != null) {
                    for (CropResourceUsageView cropResourceUsageView : resourceUsageViews) {
                        if (cropResourceUsageView.getCropResourceUse().equals(resourceName)) {
                            resource = cropResourceUsageView;
                            /**
                             * @changed - Abhishek
                             * @date - 25-02-2016
                             * @decs - according to slide#6 of 02212016
                             */
							/*if (i == 1) {
								cropResourceUsageView.setCropResourceUseAmount(""+(Long.parseLong(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank())));
							} else {*/
                            amount = Long.parseLong(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()) + differenceValue;
                            cropResourceUsageView.setCropResourceUseAmount("" + (Long.parseLong(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()) + (differenceValue)));
							/*}*/
                            if (Long.parseLong(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank()) < 0) {
                                break outer;
                            }

                            jsonObject.put("resourceValue", AgricultureStandardUtils.commaSeparaterForLong(Long.parseLong(cropResourceUsageView.getCropResourceUseAmountZeroIfBlank())));
                            if (uomOfResource == null)
                                uomOfResource = cropResourceUsageView.getUoMResource();

                            break;
                        }
                    }
                } else if (cropName != null) {
                    long cropValue = 0l;
                    if (!selectionType.equals("Group")) {
                        for (CropBeanForOutput beanForOutput : cropBeanForOutput) {
                            if (beanForOutput.getCropType().getCropName().equals(cropName)) {
                                if (selectionType.equals("Crop") && rangeType.equals("Minimum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = beanForOutput.getMinAcre().longValue();
									} else {*/

                                    amount = new Double(beanForOutput.getMinAcre()).longValue() + differenceValue;
                                    if (amount < 0) {
                                        break outer;
                                    }
                                    beanForOutput.setMinAcre(beanForOutput.getMinAcre() + (differenceValue));
                                    PlantingProfitLogger.debug("Crop Limit Updated tto = " + beanForOutput.getMinAcre());
                                    cropValue = new Double(beanForOutput.getMinAcre()).longValue();
									/*}*/
                                } else if (selectionType.equals("Crop") && rangeType.equals("Maximum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = beanForOutput.getMaxAcre().longValue();
									} else {*/
                                    amount = new Double(beanForOutput.getMaxAcre()).longValue() + differenceValue;
                                    if (amount < 0) {
                                        break outer;
                                    }
                                    beanForOutput.setMaxAcre(beanForOutput.getMaxAcre() + (differenceValue));
                                    cropValue = new Double(beanForOutput.getMaxAcre()).longValue();
									/*}*/
                                } else if (selectionType.equals("Contract") && rangeType.equals("Minimum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = beanForOutput.getFirmAcres().longValue();
									} else {*/
                                    amount = new Double(beanForOutput.getFirmAcres()).longValue() + differenceValue;
                                    if (amount < 0) {
                                        break outer;
                                    }
                                    beanForOutput.setFirmAcres(beanForOutput.getFirmAcres() + (differenceValue));
                                    cropValue = new Double(beanForOutput.getFirmAcres()).longValue();
									/*}*/
                                } else if (selectionType.equals("Proposed") && rangeType.equals("Minimum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = beanForOutput.getProposedAcres().longValue();
									} else {*/
                                    amount = new Double(beanForOutput.getProposedAcres()).longValue() + differenceValue;
                                    if (amount < 0) {
                                        break outer;
                                    }
                                    beanForOutput.setProposedAcres(beanForOutput.getProposedAcres() + (differenceValue));
                                    cropValue = new Double(beanForOutput.getProposedAcres()).longValue();
									/*}*/
                                }
                                break;
                            }
                        }
                    } else if (selectionType.equals("Group")) {
                        for (CropsGroup cropsGroup : cropsGroups) {
                            if (cropsGroup.getCropsGroupName().equals(cropName)) {
                                if (rangeType.equals("Minimum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = AgricultureStandardUtils.stringToLong(cropsGroup.getMinimumAcres());
									} else {*/
                                    amount = Long.parseLong(cropsGroup.getMinimumAcres()) + differenceValue;
                                    if (amount < 0) {
                                        break outer;
                                    }
                                    cropsGroup.setMinimumAcres("" + (Integer.parseInt(cropsGroup.getMinimumAcres()) + (differenceValue)));
                                    cropValue = AgricultureStandardUtils.stringToLong(cropsGroup.getMinimumAcres());
									/*}*/
                                } else if (rangeType.equals("Maximum")) {
                                    /**
                                     * @changed - Abhishek
                                     * @date - 25-02-2016
                                     * @decs - according to slide#6 of 02212016
                                     */
									/*if (i == 1) {
										cropValue = AgricultureStandardUtils.stringToLong(cropsGroup.getMaximumAcres());
									} else {*/
                                    amount = Long.parseLong(cropsGroup.getMaximumAcres()) + differenceValue;
                                    if (amount < 0) {
                                        break outer;
                                    }
                                    cropsGroup.setMaximumAcres("" + (Integer.parseInt(cropsGroup.getMaximumAcres()) + (differenceValue)));
                                    cropValue = AgricultureStandardUtils.stringToLong(cropsGroup.getMaximumAcres());
									/*}*/
                                }
                                break;
                            }
                        }
                    }
                    /**
                     * @changed - Abhishek
                     * @date - 09-12-2015
                     */
                    if (cropValue <= 0) {
                        continueFlag = false;
                        cropValue = 0;
                    }
                    jsonObject.put("cropValue", cropValue);

                }
                Result result = linearProgramingSolveDao.getLinearProgramingResultForAcerage(cropBeanForOutput, farmInfo.getLand(), resourceUsageViews, cropsGroups);
                if (result != null) {
                    JSONArray innerJsonArray = new JSONArray();
                    for (CropBeanForOutput beanForOutput : cropBeanForOutput) {

                        JSONObject object = new JSONObject();
                        Long acreUsed = AgricultureStandardUtils.withoutDecimalAndCommaToLong(result.get(beanForOutput.getCropType().getCropName()).doubleValue());
                        object.put("Crop_Name", beanForOutput.getCropType().getCropName());
                        object.put("land", AgricultureStandardUtils.commaSeparaterForLong(acreUsed));
                        innerJsonArray.add(object);

                        if (beanForOutput.getFirmAcres() > zeroDouble) {

                            JSONObject object1 = new JSONObject();
                            Long acreUsed1 = AgricultureStandardUtils.withoutDecimalAndCommaToLong(beanForOutput.getFirmAcres());
                            object1.put("Crop_Name", beanForOutput.getCropType().getCropName() + " (Contract)");
                            object1.put("land", AgricultureStandardUtils.commaSeparaterForLong(acreUsed1));
                            innerJsonArray.add(object1);

                        } else if (beanForOutput.getProposedAcres() > zeroDouble) {

                            if (result.get(beanForOutput.getCropType().getCropName() + " (Proposed)").doubleValue() > zeroDouble) {
                                JSONObject object1 = new JSONObject();
                                Long acreUsed1 = AgricultureStandardUtils.withoutDecimalAndCommaToLong(result.get(beanForOutput.getCropType().getCropName() + " (Proposed)").doubleValue());
                                object1.put("Crop_Name", beanForOutput.getCropType().getCropName() + " (Proposed)");
                                object1.put("land", AgricultureStandardUtils.commaSeparaterForLong(acreUsed1));
                                innerJsonArray.add(object1);
                            }

                        }
                    }
                    if(i == 0) {
                        jsonObject.put("Potential_Profit", currentPotentialProfit);
                    } else {
                        jsonObject.put("Potential_Profit", result.getObjective().longValue());
                    }
                    jsonObject.put("Crop_Details", innerJsonArray);
                    jsonObject.put("Strategy", "Acerage");

                } else {
                    JSONArray innerJsonArray = new JSONArray();

                    for (CropBeanForOutput beanForOutput : cropBeanForOutput) {
                        JSONObject object = new JSONObject();
                        object.put("Crop_Name", beanForOutput.getCropType().getCropName());
                        object.put("land", 0);
                        innerJsonArray.add(object);
                    }

                    jsonObject.put("Potential_Profit", 0);
                    jsonObject.put("Crop_Details", innerJsonArray);
                    jsonObject.put("Strategy", "Acerage");

                }

                try {
                    PlantingProfitLogger.info(result.getObjective().longValue() != 0 ? "Estimated Income value : " + result.getObjective().longValue() : 0);
                } catch (Exception e) {
                    PlantingProfitLogger.error(e.getMessage());
                    PlantingProfitLogger.debug(e);
                }
                PlantingProfitLogger.info("old profit value : " + oldProfit);
                PlantingProfitLogger.info("currentPotentialProfit value : " + currentPotentialProfit);

                if (result != null && amount > 0) {
//                    long profit = result.getObjective().longValue();
                    long profit, temp = result.getObjective().longValue();
                    profit = temp - currentPotentialProfit;
                    if(profit == 0.5 || profit == -0.5 || profit == -1 || profit == 1){
                        profit = currentPotentialProfit;
//                        currentPotentialProfit = temp;
                    } else {
                        profit = temp;
                    }
                    /**
                     * @changed - Abhishek
                     * @date - 02-12-2015
                     * @updated - 30-12-2015
                     */
                    String differenceString, resourceStr = resourceName;
                    if (resourceName != null) {
                        if (resource.getCropResourceUse().equalsIgnoreCase("capital")) {
                            differenceString = "$" + AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue);
                            resourceStr = AppConstants.WORKING_CAPITAL;
                        }else
                            differenceString = AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue) + " " + resource.getUoMResource();
                    } else {
                        differenceString = AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue);
                    }
                    if(i == 0){
//                        jsonObject.put("bubbleMessage", "This is the baseline amount for " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr)));
                        jsonObject.put("bubbleMessage", "This is the baseline " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " crop acreage limit for " : "") + cropName) : (" amount for " + resourceStr)));
                    } else if (differenceValue > 0) {
                        if ((profit < currentPotentialProfit) || (profit < oldProfit)) {

                            jsonObject.put("bubbleMessage", "Increasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) decreases Estimated Income by $" + AgricultureStandardUtils.commaSeparaterForLong(currentPotentialProfit - profit < 0 ? 0 : currentPotentialProfit - profit) + ".");
//						jsonObject.put("bubbleMessage", "Increasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+(i*differenceValue)+" amount you will get the Estimated Income decreased to $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");

                        } else if ((profit == currentPotentialProfit) || (profit == oldProfit)) {

                            jsonObject.put("bubbleMessage", "Increasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) will not change Estimated Income i.e $" + AgricultureStandardUtils.commaSeparaterForLong(profit) + ".");
//						jsonObject.put("bubbleMessage", "Increasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+(i*differenceValue)+" amount your Estimated Income will remain same i.e $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");

                        } else {

                            jsonObject.put("bubbleMessage", "Increasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) increases Estimated Income by $" + AgricultureStandardUtils.commaSeparaterForLong(profit - currentPotentialProfit < 0 ? 0 : profit - currentPotentialProfit) + ".");
//						jsonObject.put("bubbleMessage", "Increasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+(i*differenceValue)+" amount you will get the Estimated Income increased to $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");
                        }
                    } else {
                        if ((profit < currentPotentialProfit) || (profit < oldProfit)) {

                            jsonObject.put("bubbleMessage", "Decreasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) decreases Estimated Income by $" + AgricultureStandardUtils.commaSeparaterForLong(currentPotentialProfit - profit < 0 ? 0 : currentPotentialProfit - profit) + ".");
//						jsonObject.put("bubbleMessage", "On decreasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+Math.abs(i*differenceValue)+" amount you will get the Estimated Income decreased to $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");
                        } else if ((profit == currentPotentialProfit) || (profit == oldProfit)) {

                            jsonObject.put("bubbleMessage", "Decreasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) will not change Estimated Income i.e $" + AgricultureStandardUtils.commaSeparaterForLong(profit) + ".");
//						jsonObject.put("bubbleMessage", "On decreasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+Math.abs(i*differenceValue)+" amount your Estimated Income will remain same i.e $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");
                        } else {

                            jsonObject.put("bubbleMessage", "Decreasing " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType + " acres of " : "") + cropName) : (resourceStr + " resource")) +
                                    " by " + (differenceString) + (resourceStr == null ? " acres" : "") + " (from the original amount) increases Estimated Income by $" + AgricultureStandardUtils.commaSeparaterForLong(profit - currentPotentialProfit < 0 ? 0 : profit - currentPotentialProfit) + ".");
//					jsonObject.put("bubbleMessage", "On decreasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+Math.abs(i*differenceValue)+" amount you will get the Estimated Income increased to $"+AgricultureStandardUtils.commaSeparaterForLong(profit)+".");
                        }
                    }
                    oldProfit = profit;
                } else {
                    oldProfit = 0;
                    String differenceString, resourceStr = resourceName;
                    if (resourceName != null) {
                        if (resource.getCropResourceUse().equalsIgnoreCase("capital")) {
                            differenceString = "$" + AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue);
                            resourceStr = AppConstants.WORKING_CAPITAL;
                        }else
                            differenceString = AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue) + " " + resource.getUoMResource();
                    } else {
                        differenceString = AgricultureStandardUtils.commaSeparaterForLong(i * differenceValue);
                    }
                    if (differenceValue > 0) {
                        /**
                         * @changed - Abhishek
                         * @date - 02-12-2015
                         * @updated - 30-12-2015
                         */
                        jsonObject.put("bubbleMessage", "A feasible solution cannot be generated if " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType
                                + " acres of " : "") + cropName) : (resourceStr + " resource")) + " is increased by " + (differenceString) + (resourceStr == null ? " acres" : ""));
//						jsonObject.put("bubbleMessage", "A feasible solution cannot be generated if "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" acres of ":"")+cropName):(resourceName+" resource"))+" is increased to "+ (i *differenceValue) + (resourceName == null? " acres" : "") );
                        //  jsonObject.put("bubbleMessage", "Increasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+(i*differenceValue)+" amount a valid strategy can not be generated so there will be no Estimated Income, we sugges you to change the amount and try again.");
                    } else {
                        /**
                         * @changed - Abhishek
                         * @date - 02-12-2015
                         * @updated - 30-12-2015
                         */
                        jsonObject.put("bubbleMessage", "A feasible solution cannot be generated if " + (resourceStr == null ? (((selectionType.equals("Crop") || selectionType.equals("Group")) ? rangeType
                                + " acres of " : "") + cropName) : (resourceStr + " resource")) + " is reduced by " + (differenceString) + (resourceStr == null ? " acres" : ""));
//						jsonObject.put("bubbleMessage", "A feasible solution cannot be generated if "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" acres of ":"")+cropName):(resourceName+" resource"))+" is reduced to "+ (i *differenceValue) + (resourceName == null? " acres" : "") );
                        //jsonObject.put("bubbleMessage", "On decreasing "+(resourceName == null?(((selectionType.equals("Crop") || selectionType.equals("Group"))?rangeType+" of ":"")+cropName+" "+selectionType):(resourceName+" resource"))+" with "+Math.abs(i*differenceValue)+" amount a valid strategy can not be generated so there will be no Estimated Income, we sugges you to change the amount and try again.");
                    }

                }
                jsonArray.add(jsonObject);
            }
        }

        outerJsonObject.put("resourceUnit", uomOfResource != null ? uomOfResource : "");
        outerJsonObject.put("Resource_Array", jsonArray);

        return outerJsonObject;
    }
}