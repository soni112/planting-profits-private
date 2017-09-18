package com.decipher.agriculture.service.scenario.impl;

import com.decipher.agriculture.bean.OutputBeanForStrategy;
import com.decipher.agriculture.bean.StrategyDataBean;
import com.decipher.agriculture.dao.farmOutput.FarmOutputCalculationDao;
import com.decipher.agriculture.dao.util.HibernateUtils;
import com.decipher.agriculture.dao.scenario.ScenarioDao;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.InternalCropPricesInfo;
import com.decipher.agriculture.data.farm.InternalCropYieldInfo;
import com.decipher.agriculture.data.farm.InternalVariableCropProductionCosts;
import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.data.farmOutput.CropLimitDualValue;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farmOutput.GroupLimitDualValue;
import com.decipher.agriculture.data.farmOutput.ResourceDualValue;
import com.decipher.agriculture.data.scenario.FarmStrategyScenario;
import com.decipher.agriculture.data.scenario.FarmStrategyScenarioCropSpecific;
import com.decipher.agriculture.service.farm.CropGroupService;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.agriculture.service.farmDetails.FarmOutputCalculationService;
import com.decipher.agriculture.service.scenario.ScenarioService;
import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.JsonResponse;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.*;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by raja on 12/25/15.
 */

@Service
@SuppressWarnings("unchecked")
public class ScenarioServiceImpl implements ScenarioService {

    @Autowired
    private ScenarioDao scenarioDao;
    @Autowired
    private CropGroupService cropGroupService;
    @Autowired
    private FarmOutputCalculationService farmOutputCalculationService;
    @Autowired
    private FarmInfoService farmInfoService;
    @Autowired
    private FarmOutputCalculationDao farmOutputCalculationDao;
    @Autowired
    private HibernateUtils hibernateUtils;
    @Autowired
    private FarmDetailsContainerService farmDetailsContainerService;
    @Autowired
    private FarmService farmService;

    @Override
    public boolean isExist(int scenarioId) {
        FarmStrategyScenarioView scenarioView = getScenarioById(scenarioId);
        return scenarioView != null;
    }

    @Override
    public boolean isExist(int scenarioId, int strategyId) {
        FarmStrategyScenarioView scenarioView = getScenarioById(scenarioId);
        return scenarioView != null && scenarioView.getFarmCustomStrategy().getId().equals(strategyId);
    }

    @Override
    public boolean isExist(int scenarioId, int strategyId, int farmId) {
        FarmStrategyScenarioView scenarioView = getScenarioById(scenarioId);
        return scenarioView != null && scenarioView.getFarmCustomStrategy().getId().equals(strategyId) && scenarioView.getFarmCustomStrategy().getFarm().getFarmId().equals(farmId);
    }

    @Override
    public boolean isExist(int scenarioId, int strategyId, int farmId, int userId) {
        FarmStrategyScenarioView scenarioView = getScenarioById(scenarioId);
        return scenarioView != null && scenarioView.getFarmCustomStrategy().getId().equals(strategyId) && scenarioView.getFarmCustomStrategy().getFarm().getFarmId().equals(farmId) && scenarioView.getFarmCustomStrategy().getFarmInfo().getFarm().getAccount().getId().equals(userId);
    }

    @Override
    public boolean isExist(int strategyId, int farmId, int userId, String scenarioName) {
        FarmStrategyScenarioView scenarioView = getScenarioByNameFarmStrategy(scenarioName, farmId, strategyId);
        return scenarioView != null && scenarioView.getFarmCustomStrategy().getId().equals(strategyId) && scenarioView.getFarmCustomStrategy().getFarm().getFarmId().equals(farmId) && scenarioView.getFarmCustomStrategy().getFarmInfo().getFarm().getAccount().getId().equals(userId) && scenarioView.getScenarioName().equalsIgnoreCase(scenarioName);
    }

    @Override
    public boolean isExistExcept(int scenarioId, int strategyId, int farmId, int userId, String scenarioName) {
        FarmStrategyScenarioView scenarioView = getScenarioByNameFarmStrategy(scenarioName, farmId, strategyId);
        return scenarioView != null && scenarioView.getFarmCustomStrategy().getId().equals(strategyId) && scenarioView.getFarmCustomStrategy().getFarm().getFarmId().equals(farmId) && scenarioView.getFarmCustomStrategy().getFarmInfo().getFarm().getAccount().getId().equals(userId) && scenarioView.getScenarioName().equalsIgnoreCase(scenarioName) && !(scenarioId == scenarioView.getScenarioId());
    }

    @Override
    public JSONObject getJsonFrom(FarmStrategyScenarioView farmStrategyScenarioView) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("scenarioId", farmStrategyScenarioView.getScenarioId());
        jsonObject.put("scenarioName", farmStrategyScenarioView.getScenarioName());
        jsonObject.put("scenarioGlobalCropPrice", farmStrategyScenarioView.getGlobalCropPrice());
        jsonObject.put("scenarioGlobalCropYields", farmStrategyScenarioView.getGlobalCropYields());
        jsonObject.put("scenarioGlobalCropProdCost", farmStrategyScenarioView.getGlobalCropProdCost());

        jsonObject.put("globalComment", farmStrategyScenarioView.getGlobalComment());
        jsonObject.put("cropSpecificComment", farmStrategyScenarioView.getCropSpecificComment());

        JSONObject cropSpecificDetails = new JSONObject();
        for (FarmStrategyScenarioCropSpecific farmStrategyScenarioCropSpecific : farmStrategyScenarioView.getCropSpecifics()) {
            JSONObject jObj = new JSONObject();
            jObj.put("cropId", farmStrategyScenarioCropSpecific.getCropType().getId());
            jObj.put("cropName", farmStrategyScenarioCropSpecific.getCropType().getCropName());
            jObj.put("price", farmStrategyScenarioCropSpecific.getPrice());
            jObj.put("yields", farmStrategyScenarioCropSpecific.getYields());
            jObj.put("prodCost", farmStrategyScenarioCropSpecific.getProdCost());
            cropSpecificDetails.put(farmStrategyScenarioCropSpecific.getCropType().getId(), jObj);
        }
        jsonObject.put("cropSpecific", cropSpecificDetails);
        return jsonObject;
    }

    @Override
    public JsonResponse saveScenario(String scenarioName, int farmId, String scenarioComment, String cropSpecificComment, int strategyId, int global_crop_price, int global_crop_yields, int global_crop_prod_cost, String cropSpecific) {
        JsonResponse jsonResponse = new JsonResponse();

        FarmStrategyScenario farmStrategyScenario = new FarmStrategyScenario();
        farmStrategyScenario.setScenarioName(scenarioName);
        farmStrategyScenario.setGlobalCropPrice(global_crop_price);
        farmStrategyScenario.setGlobalCropYields(global_crop_yields);
        farmStrategyScenario.setGlobalCropProdCost(global_crop_prod_cost);

        farmStrategyScenario.setCropScpecificComment(cropSpecificComment);
        farmStrategyScenario.setGlobalCropComment(scenarioComment);

        Set<FarmStrategyScenarioCropSpecific> farmStrategyScenarioCropSpecifics = new HashSet<FarmStrategyScenarioCropSpecific>();
        int checkCounter = 0;
        try {
            JSONArray cropSpecificJson = (JSONArray) new JSONParser().parse(cropSpecific);
            for (Object obj : cropSpecificJson) {
                JSONObject jObj = (JSONObject) obj;
                FarmStrategyScenarioCropSpecific f = new FarmStrategyScenarioCropSpecific();
                if ((jObj.get("crop_price")) != "" && ((String) jObj.get("crop_price")).length() != 0) {
                    f.setPrice(new Integer((String) jObj.get("crop_price")));
                    checkCounter++;
                }

                if ((jObj.get("crop_yield")) != "" && ((String) jObj.get("crop_yield")).length() != 0) {
                    f.setYields(new Integer((String) jObj.get("crop_yield")));
                    checkCounter++;
                }

                if ((jObj.get("crop_prod_cost")) != "" && ((String) jObj.get("crop_prod_cost")).length() != 0) {
                    f.setProdCost(new Integer((String) jObj.get("crop_prod_cost")));
                    checkCounter++;
                }
                if (checkCounter != 0) {
                    CropType cropType = (CropType) hibernateUtils.getPersistedObject(CropType.class, new Integer((String) jObj.get("crop_id")));
                    if (cropType == null) {
                        throw new RuntimeException("Crop id is invalid or not matched with stored values [" + jObj.get("crop_id") + "]");
                    }
                    if (!cropType.getCropName().equalsIgnoreCase(((String) jObj.get("crop_name")).trim())) {
                        throw new IllegalAccessException("Crop name not matched");
                    }
                    f.setCropType(cropType);
                    f.setFarmStrategyScenario(farmStrategyScenario);
                    farmStrategyScenarioCropSpecifics.add(f);
                } else {
                    f = null;
                }
                checkCounter = 0;
            }
            farmStrategyScenario.setCropSpecifics(farmStrategyScenarioCropSpecifics);
            Long saveScenario = scenarioDao.saveScenario(farmStrategyScenario, strategyId);
            if (saveScenario > 0) {
                /**
                 * @changed - Abhishek
                 * @date - 07-05-2016
                 * @desc - changed for optimization of the application
                 */
                farmDetailsContainerService.updateScenarioDetails(farmService.getBaselineFarmDetails(farmId), getScenarioById(saveScenario.intValue()));

                jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
                String res = saveScenario.toString() + ", " + strategyId;
                jsonResponse.setResult(res);
            } else {
                jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
            }

        } catch (ParseException e) {
            PlantingProfitLogger.error(e);
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
            jsonResponse.setResult("Invalid values of Crop Specific");
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
            jsonResponse.setResult(e.getMessage());
        }

        return jsonResponse;
    }

    @Override
    public JsonResponse updateScenario(int scenarioId, String scenarioName, String scenarioComment, String cropSpecificComment, int farmId, int strategyId, int global_crop_price, int global_crop_yields, int global_crop_prod_cost, String cropSpecific) {
        JsonResponse jsonResponse = new JsonResponse();

        FarmStrategyScenario farmStrategyScenario = (FarmStrategyScenario) hibernateUtils.getPersistedObject(FarmStrategyScenario.class, scenarioId);
        farmStrategyScenario.setScenarioId(scenarioId);
        farmStrategyScenario.setScenarioName(scenarioName);
        farmStrategyScenario.setGlobalCropPrice(global_crop_price);
        farmStrategyScenario.setGlobalCropYields(global_crop_yields);
        farmStrategyScenario.setGlobalCropProdCost(global_crop_prod_cost);

        farmStrategyScenario.setCropScpecificComment(cropSpecificComment);
        farmStrategyScenario.setGlobalCropComment(scenarioComment);

        Set<FarmStrategyScenarioCropSpecific> farmStrategyScenarioCropSpecifics = new HashSet<FarmStrategyScenarioCropSpecific>();
        int checkCounter = 0;
        // Delete crop specific data first
        deleteScenarioCropSpecificData(farmStrategyScenario);

        try {
            JSONArray cropSpecificJson = (JSONArray) new JSONParser().parse(cropSpecific);
            for (Object obj : cropSpecificJson) {
                JSONObject jObj = (JSONObject) obj;
                FarmStrategyScenarioCropSpecific farmStrategyScenarioCropSpecific = new FarmStrategyScenarioCropSpecific();
                if ((jObj.get("crop_price")) != "" && ((String) jObj.get("crop_price")).length() != 0) {
                    farmStrategyScenarioCropSpecific.setPrice(new Integer((String) jObj.get("crop_price")));
                    checkCounter++;
                }

                if ((jObj.get("crop_yield")) != "" && ((String) jObj.get("crop_yield")).length() != 0) {
                    farmStrategyScenarioCropSpecific.setYields(new Integer((String) jObj.get("crop_yield")));
                    checkCounter++;
                }

                if ((jObj.get("crop_prod_cost")) != "" && ((String) jObj.get("crop_prod_cost")).length() != 0) {
                    farmStrategyScenarioCropSpecific.setProdCost(new Integer((String) jObj.get("crop_prod_cost")));
                    checkCounter++;
                }
                if (checkCounter != 0) {
                    CropType cropType = (CropType) hibernateUtils.getPersistedObject(CropType.class, new Integer((String) jObj.get("crop_id")));
                    if (cropType == null) {
                        throw new RuntimeException("Crop id is invalid or not matched with stored values [" + jObj.get("crop_id") + "]");
                    }
                    if (!cropType.getCropName().equalsIgnoreCase(((String) jObj.get("crop_name")).trim())) {
                        throw new IllegalAccessException("Crop name not matched");
                    }
                    farmStrategyScenarioCropSpecific.setCropType(cropType);
                    farmStrategyScenarioCropSpecific.setFarmStrategyScenario(farmStrategyScenario);
                    farmStrategyScenarioCropSpecifics.add(farmStrategyScenarioCropSpecific);
                } else {
                    farmStrategyScenarioCropSpecific = null;
                }
                checkCounter = 0;
            }
            farmStrategyScenario.setCropSpecifics(farmStrategyScenarioCropSpecifics);
            boolean saveScenario = scenarioDao.updateScenario(scenarioId, farmStrategyScenario);
            if (saveScenario) {

                /**
                 * @changed - Abhishek
                 * @date - 07-05-2016
                 * @desc - changed for optimization of the application
                 */
                farmDetailsContainerService.updateScenarioDetails(farmService.getBaselineFarmDetails(farmId), new FarmStrategyScenarioView(farmStrategyScenario));

                jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
            } else {
                jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
            }

        } catch (ParseException e) {
            PlantingProfitLogger.error(e);
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
            jsonResponse.setResult("Invalid values of Crop Specific");
        } catch (IllegalAccessException | RuntimeException e) {
            PlantingProfitLogger.error(e);
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
            jsonResponse.setResult(e.getMessage());
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
            jsonResponse.setResult(e.getMessage());
        }

        return jsonResponse;
    }

    @Override
    public FarmStrategyScenarioView getScenarioById(int scenarioId) {
        return scenarioDao.getScenarioById(scenarioId);
    }

    @Override
    public FarmStrategyScenarioView getScenarioByNameFarmStrategy(String scenarioName, int farmId, int strategyId) {
        return scenarioDao.getScenarioByNameFarmStrategy(scenarioName, strategyId, farmId);
    }

    @Override
    public List<FarmStrategyScenarioView> getAllScenarioByStrategy(int strategyId) {
        return scenarioDao.getAllScenarioByStrategy(strategyId);
    }

    @Override
    public boolean deleteScenarioCropSpecificData(FarmStrategyScenario farmStrategyScenario) {
        return scenarioDao.deleteScenarioCropCpecificData(farmStrategyScenario);
    }

    /**
     * @added - Abhishek
     * @date - 06-01-2016
     */
    @Override
    public List<CropTypeView> getScenarioCropValuesByStrategyBean(StrategyDataBean strategyDataBean, FarmStrategyScenarioView farmStrategyScenarioView) {

        List<CropTypeView> cropTypeViewList = strategyDataBean.getCropTypeViewList();

        int globalCropPrice = farmStrategyScenarioView.getGlobalCropPrice();
        int globalCropProdCost = farmStrategyScenarioView.getGlobalCropProdCost();
        int globalCropYields = farmStrategyScenarioView.getGlobalCropYields();

        List<String> cropNameIfUpdatedByCropSpecific = new ArrayList<String>();

        List<CropTypeView> updatedCropTypeViewList = new ArrayList<CropTypeView>();


        ListIterator<CropTypeView> cropTypeViewIterator = cropTypeViewList.listIterator();
        while (cropTypeViewIterator.hasNext()) {
            CropTypeView cropTypeView = cropTypeViewIterator.next();
            if (cropTypeView.getSelected()) {
                /*      Crop Specific Updation      */
                Set<FarmStrategyScenarioCropSpecific> cropSpecificsList = farmStrategyScenarioView.getCropSpecifics();
                for (FarmStrategyScenarioCropSpecific farmStrategyScenarioCropSpecific : cropSpecificsList) {

                    /**
                     * @changed - Abhishek
                     * @date - 04-03-2016
                     * @desc - included crops which are not contracted
                     */
                    if (cropTypeView.getCropName().equalsIgnoreCase(farmStrategyScenarioCropSpecific.getCropType().getCropName())
                            && !cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {

                        /*  Adding crop name to identify values updated by crop specific updation*/
                        cropNameIfUpdatedByCropSpecific.add(cropTypeView.getCropName());

                        DecimalFormat decimalFormatter = new DecimalFormat("#.00");

                        boolean changeFlag = false;

                        /*  Updating values for price CropSpecific  */
                        if (globalCropPrice == 0 && farmStrategyScenarioCropSpecific.getPrice() != null
                                && farmStrategyScenarioCropSpecific.getPrice() != 0) {
                            InternalCropPricesInfo cropPricesInfo = cropTypeView.getCropType().getCropPricesInfo();
                            double valueToChange;

                            valueToChange = (cropPricesInfo.getIntExpCropPrice().doubleValue() * farmStrategyScenarioCropSpecific.getPrice()) / 100;
                            cropPricesInfo.setIntExpCropPrice(new BigDecimal(Double.parseDouble(decimalFormatter.format(cropPricesInfo.getIntExpCropPrice().doubleValue() + valueToChange))));

                            cropTypeView.setIntExpCropPrice(new BigDecimal(decimalFormatter.format(cropTypeView.getIntExpCropPrice().doubleValue() + valueToChange)));
                            cropTypeView.getCropType().setCropPricesInfo(cropPricesInfo);

                            changeFlag = true;

                        }

                        /*  Updating values for yield CropSpecific  */
                        if (globalCropYields == 0 && farmStrategyScenarioCropSpecific.getYields() != null
                                && farmStrategyScenarioCropSpecific.getYields() != 0) {
                            InternalCropYieldInfo cropYieldInfo = cropTypeView.getCropType().getCropYieldInfo();
                            double valueToChange;

                            valueToChange = (Double.parseDouble(cropYieldInfo.getIntExpCropYield()) * farmStrategyScenarioCropSpecific.getYields()) / 100;
                            cropYieldInfo.setIntExpCropYield(decimalFormatter.format(Double.parseDouble(cropYieldInfo.getIntExpCropYield()) + valueToChange));

                            cropTypeView.setIntExpCropYield(decimalFormatter.format(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntExpCropYield())) + valueToChange));
                            cropTypeView.getCropType().setCropYieldInfo(cropYieldInfo);

                            changeFlag = true;
                        }

                        /*  Updating values for Variable Cost Production CropSpecific   */
                        if (globalCropProdCost == 0 && farmStrategyScenarioCropSpecific.getProdCost() != null
                                && farmStrategyScenarioCropSpecific.getProdCost() != 0) {
                            InternalVariableCropProductionCosts costsCropProductionCosts = cropTypeView.getCropType().getCostsCropProductionCosts();
                            double valueToChange;

                            valueToChange = (costsCropProductionCosts.getCalculatedVariableProductionCost().doubleValue() * farmStrategyScenarioCropSpecific.getProdCost()) / 100;

//                            if (farmStrategyScenarioCropSpecific.getProdCost() < 0) {
//                                costsCropProductionCosts.setCalculatedVariableProductionCost(new BigDecimal(Double.parseDouble(decimalFormatter.format(costsCropProductionCosts.getCalculatedVariableProductionCost().doubleValue() - valueToChange))));
//                                cropTypeView.setCalculatedVariableProductionCost(new BigDecimal(decimalFormatter.format(cropTypeView.getCalculatedVariableProductionCost().doubleValue() - valueToChange)));
//                            } else {
                                costsCropProductionCosts.setCalculatedVariableProductionCost(new BigDecimal(Double.parseDouble(decimalFormatter.format(costsCropProductionCosts.getCalculatedVariableProductionCost().doubleValue() + valueToChange))));
                                cropTypeView.setCalculatedVariableProductionCost(new BigDecimal(decimalFormatter.format(cropTypeView.getCalculatedVariableProductionCost().doubleValue() + valueToChange)));
//                            }
//                            cropTypeView.setCalculatedVariableProductionCost(new BigDecimal(decimalFormatter.format(cropTypeView.getCalculatedVariableProductionCost().doubleValue() + valueToChange)));
                            cropTypeView.getCropType().setCostsCropProductionCosts(costsCropProductionCosts);

                            changeFlag = true;
                        }

//                        if (changeFlag) {
                              /*    Adding updated crop details to final list   */
//                            cropTypeViewIterator.add(new CropTypeView(cropTypeView.getCropType()));

                              /*    removing base CropType from list     */
//                            cropTypeViewIterator.remove();
//                        }

                    }
                }

                /**
                 * @changed - Abhishek
                 * @date - 04-03-2016
                 * @desc - included crops which are not contracted
                 */
                /*      Global Updation      */
                if (!cropNameIfUpdatedByCropSpecific.contains(cropTypeView) && !cropTypeView.getFirmchecked().equalsIgnoreCase("true")) {
                    DecimalFormat decimalFormatter = new DecimalFormat("#.00");

                    /**
                     * Important Note : - need to add the change value always
                     */

                    boolean changeFlag = false;
                          /*Updating values for price Global*/
                    if (globalCropPrice != 0) {
                        InternalCropPricesInfo cropPricesInfo = cropTypeView.getCropType().getCropPricesInfo();
                        double valueToChange;

                        valueToChange = (cropPricesInfo.getIntExpCropPrice().doubleValue() * globalCropPrice) / 100;
                        valueToChange = Double.parseDouble(decimalFormatter.format(valueToChange));
//                        if (globalCropPrice < 0) {
//                            cropPricesInfo.setIntExpCropPrice(new BigDecimal(Double.parseDouble(decimalFormatter.format(cropPricesInfo.getIntExpCropPrice().doubleValue() - valueToChange))));
//                            cropTypeView.setIntExpCropPrice(new BigDecimal(Double.parseDouble(decimalFormatter.format(cropTypeView.getIntExpCropPrice().doubleValue() - valueToChange))));
//                        } else {
                        cropPricesInfo.setIntExpCropPrice(new BigDecimal(Double.parseDouble(decimalFormatter.format(cropPricesInfo.getIntExpCropPrice().doubleValue() + valueToChange))));
                        cropTypeView.setIntExpCropPrice(new BigDecimal(Double.parseDouble(decimalFormatter.format(cropTypeView.getIntExpCropPrice().doubleValue() + valueToChange))));
//                        }
                        cropTypeView.getCropType().setCropPricesInfo(cropPricesInfo);

                        changeFlag = true;
                    }

                          /*Updating values for yield Global  */
                    if (globalCropYields != 0) {
                        InternalCropYieldInfo cropYieldInfo = cropTypeView.getCropType().getCropYieldInfo();
                        double valueToChange;

                        valueToChange = (Double.parseDouble(cropYieldInfo.getIntExpCropYield()) * globalCropYields) / 100;
                        valueToChange = Double.parseDouble(decimalFormatter.format(valueToChange));
//                        if (globalCropYields < 0) {
//                            cropYieldInfo.setIntExpCropYield(decimalFormatter.format(Double.parseDouble(cropYieldInfo.getIntExpCropYield()) - valueToChange));
//                            cropTypeView.setIntExpCropYield(decimalFormatter.format(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntExpCropYield())) - valueToChange));
//                        } else {
                        cropYieldInfo.setIntExpCropYield(decimalFormatter.format(Double.parseDouble(cropYieldInfo.getIntExpCropYield()) + valueToChange));
                        cropTypeView.setIntExpCropYield(decimalFormatter.format(Double.parseDouble(AgricultureStandardUtils.removeAllCommas(cropTypeView.getIntExpCropYield())) + valueToChange));
//                        }
                        cropTypeView.getCropType().setCropYieldInfo(cropYieldInfo);

                        changeFlag = true;
                    }

                          /*Updating values for Variable Cost Production Global*/
                    if (globalCropProdCost != 0) {
                        InternalVariableCropProductionCosts costsCropProductionCosts = cropTypeView.getCropType().getCostsCropProductionCosts();
                        double valueToChange;

                        valueToChange = (costsCropProductionCosts.getCalculatedVariableProductionCost().doubleValue() * globalCropProdCost) / 100;
                        valueToChange = Double.parseDouble(decimalFormatter.format(valueToChange));

//                        if (globalCropProdCost < 0) {
//                            costsCropProductionCosts.setCalculatedVariableProductionCost(new BigDecimal(Double.parseDouble(decimalFormatter.format(costsCropProductionCosts.getCalculatedVariableProductionCost().doubleValue() + valueToChange))));
//                            cropTypeView.setCalculatedVariableProductionCost(new BigDecimal(decimalFormatter.format(cropTypeView.getCalculatedVariableProductionCost().doubleValue() + valueToChange)));
//                        } else {
                            costsCropProductionCosts.setCalculatedVariableProductionCost(new BigDecimal(Double.parseDouble(decimalFormatter.format(costsCropProductionCosts.getCalculatedVariableProductionCost().doubleValue() + valueToChange))));
                            cropTypeView.setCalculatedVariableProductionCost(new BigDecimal(decimalFormatter.format(cropTypeView.getCalculatedVariableProductionCost().doubleValue() + valueToChange)));
//                        }

                        cropTypeView.getCropType().setCostsCropProductionCosts(costsCropProductionCosts);

                        changeFlag = true;
                    }

//                    if (changeFlag) {
                          /*removing base CropType from list*/
//                        cropTypeViewIterator.remove();

                          /*Adding updated crop details to final list*/
//                        cropTypeViewIterator.add(new CropTypeView(cropTypeView.getCropType()));

//                    }
                }

//                updatedCropTypeViewList.add(new CropTypeView(cropTypeView.getCropType()));
            }
        }

        return cropTypeViewList;
        /*return updatedCropTypeViewList;*/

    }

    @Override
    public JSONObject getFarmOutputDetails(StrategyDataBean strategyDataBean) throws JSONException {

        boolean checkValidStrategyForFarm = false;
        List<FarmOutputDetailsView> farmOutputDetailsViewList = new ArrayList<FarmOutputDetailsView>();
        List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViews = new ArrayList<FarmOutputDetailsForFieldView>();
        Map<String, HashMap<String, String>> map = null;
        Map<String, Object> mapCompleteObject = null;
        Map<String, String> mapDifferentValues = new HashMap<String, String>();
        double totalForwardSales = 0.0;
        Map<String, HashMap<String, String>> mapForCropsForField = null;
        JSONObject jsonObjectForGraphs = null;
        String potentialProfit = null;


        OutputBeanForStrategy outputBeanForStrategy = new OutputBeanForStrategy();
        outputBeanForStrategy.setStrategyID(strategyDataBean.getFarmCustomStrategyView().getId());
        outputBeanForStrategy.setSaveFlag(false);
        outputBeanForStrategy.setBaselineFlag(false);
        outputBeanForStrategy.setSensitivityFlag(true);
        outputBeanForStrategy.setFarmInfo(strategyDataBean.getFarmInfoView().getFarmInfo());
        outputBeanForStrategy.setCropTypeList(strategyDataBean.getCropTypeList());
        outputBeanForStrategy.setResourceUsageViews(strategyDataBean.getResourceUsageViewsList());

        List<CropsGroupView> cropsGroupViews = cropGroupService.getAllCropGroupByFarm(strategyDataBean.getFarmInfoView().getId());
        if (PlanByStrategy.PLAN_BY_ACRES.equals(strategyDataBean.getFarmInfoView().getStrategy())) {
            /* Output process for Farm by Acre */
            checkValidStrategyForFarm = farmOutputCalculationService.checkIfFarmHasValidStrategy(strategyDataBean.getFarmInfoView().getId());

            /**
             *
             */
            farmOutputDetailsViewList = farmOutputCalculationService.getAllFarmOutputDetailsByFarm(outputBeanForStrategy);

			/* Calculate used and unused resources start */
            map = farmOutputCalculationDao.calculateUsedAndUnusedResourcesForAcre(strategyDataBean.getResourceUsageViewsList(), farmOutputDetailsViewList, strategyDataBean.getCropResourceUsageFieldVariancesList());

			/* Create jsonObject For Graphs */
            jsonObjectForGraphs = farmOutputCalculationDao.createJSONObjectForGraphForAcre(farmOutputDetailsViewList, map.get("cropResourceUnused").get("Land"));
            PlantingProfitLogger.info(jsonObjectForGraphs);
            potentialProfit = AgricultureStandardUtils.commaSeparaterForInteger(farmOutputCalculationDao.calculatePotentialProfitForAcre(farmOutputDetailsViewList));
            for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsViewList) {
                if (farmOutputDetailsView.getForFirm() || farmOutputDetailsView.getForProposed()) {
                    totalForwardSales += Integer.parseInt(farmOutputDetailsView.getUsedAcres().replaceAll("\\,", ""));
                }
            }
            /* Output process for Farm by Field */
        } else if (PlanByStrategy.PLAN_BY_FIELDS.equals(strategyDataBean.getFarmInfoView().getStrategy())) {

            /**
             *
             */
            farmOutputDetailsForFieldViews = farmOutputCalculationService.getAllFarmOutputDetailsForFieldByFarm(outputBeanForStrategy);


            checkValidStrategyForFarm = (farmOutputDetailsForFieldViews.size() > 0);
            map = farmOutputCalculationDao.calculateUsedAndUnusedResourcesForField(strategyDataBean.getResourceUsageViewsList(), farmOutputDetailsForFieldViews, strategyDataBean.getCropResourceUsageFieldVariancesList());

            mapCompleteObject = farmOutputCalculationDao.createJSONObjectAndMapObjectForGraphForField(strategyDataBean.getCropTypeViewList(), farmOutputDetailsForFieldViews, map.get("cropResourceUnused").get("Land"));
            jsonObjectForGraphs = (JSONObject) mapCompleteObject.get("jsonObjectForGraphs");
            mapForCropsForField = (Map<String, HashMap<String, String>>) mapCompleteObject.get("mapForCropsForField");

            for (FarmOutputDetailsForFieldView farmOutputDetailsView : farmOutputDetailsForFieldViews) {
                if (farmOutputDetailsView.isForFirm() || farmOutputDetailsView.isForProposed()) {
                    totalForwardSales += Integer.parseInt(farmOutputDetailsView.getUsedAcres().replaceAll("\\,", ""));
                }
            }
            potentialProfit = AgricultureStandardUtils.commaSeparaterForInteger(farmOutputCalculationDao.calculatePotentialProfitForField(farmOutputDetailsForFieldViews));
        }
        double forwardPercentage = (totalForwardSales * 100) / AgricultureStandardUtils.stringToLong(AgricultureStandardUtils.removeAllCommas(map.get("cropResourceUsed").get("Land") == null ? "0" : map.get("cropResourceUsed").get("Land")));
        /*mapDifferentValues.put("usedForwardAcresP", "" + AgricultureStandardUtils.doubleUptoSingleDecimalPoint(forwardPercentage));*/

        /**
         * @Changed - abhishek
         * @Date - 27-11-2015
         */
//		mapDifferentValues.put("usedForwardAcresP", ""+forwardPercentage);
        mapDifferentValues.put("usedForwardAcresP", "" + AgricultureStandardUtils.doubleUptoSingleDecimalPoint(forwardPercentage));

        JSONArray cropResourceUsed = new JSONArray();
        JSONArray cropResourceUnused = new JSONArray();

        Map<String, String> resourceUsed = map.get("cropResourceUsed");
        Map<String, String> resourceUnused = map.get("cropResourceUnused");

        JSONObject jsonObject, jsonObject1;


        List<CropResourceUsageView> resourceUsageViewsList = strategyDataBean.getResourceUsageViewsList();

        for (CropResourceUsageView cropResourceUsageView : resourceUsageViewsList) {
            if (!cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("capital")
                    && !cropResourceUsageView.getCropResourceUse().equalsIgnoreCase("land") && cropResourceUsageView.isActive()) {
                jsonObject = new JSONObject();
                jsonObject1 = new JSONObject();
                jsonObject.put("label", cropResourceUsageView.getCropResourceUse());
                jsonObject.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(resourceUsed.get(cropResourceUsageView.getCropResourceUse()))));
                jsonObject1.put("label", cropResourceUsageView.getCropResourceUse());
                jsonObject1.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(resourceUnused.get(cropResourceUsageView.getCropResourceUse()))));
                cropResourceUsed.add(jsonObject);
                cropResourceUnused.add(jsonObject1);
            }
        }
        jsonObject = new JSONObject();
        jsonObject1 = new JSONObject();
        jsonObject.put("label", "Working Capital");
        jsonObject.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(resourceUsed.get("Capital"))));
        jsonObject1.put("label", "Working Capital");
        jsonObject1.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(resourceUnused.get("Capital"))));

        cropResourceUsed.add(jsonObject);
        cropResourceUnused.add(jsonObject1);

        jsonObject = new JSONObject();
        jsonObject1 = new JSONObject();
        jsonObject.put("label", "Land");
        jsonObject.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(resourceUsed.get("Land"))));
        jsonObject1.put("label", "Land");
        jsonObject1.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(resourceUnused.get("Land"))));

        cropResourceUsed.add(jsonObject);
        cropResourceUnused.add(jsonObject1);

//        JSONArray cropResourceUsed = new JSONArray();
//        JSONArray cropResourceUnused = new JSONArray();
//        for (Map.Entry<String, String> entry : map.get("cropResourceUsed").entrySet()) {
//            JSONObject jsonObject = new JSONObject();
//            JSONObject jsonObject1 = new JSONObject();
//            if(entry.getKey().equalsIgnoreCase("capital")) {
//                jsonObject.put("label", "Working Capital");
//                jsonObject.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(map.get("cropResourceUsed").get(entry.getKey()))));
//                jsonObject1.put("label", "Working Capital");
//                jsonObject1.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(map.get("cropResourceUnused").get(entry.getKey()))));
//            } else {
//                jsonObject.put("label", entry.getKey());
//                jsonObject.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(map.get("cropResourceUsed").get(entry.getKey()))));
//                jsonObject1.put("label", entry.getKey());
//                jsonObject1.put("y", Long.parseLong(AgricultureStandardUtils.removeAllCommas(map.get("cropResourceUnused").get(entry.getKey()))));
//            }
//            cropResourceUsed.add(jsonObject);
//            cropResourceUnused.add(jsonObject1);
//
//        }


        jsonObjectForGraphs.put("cropResourceUsedForBarGraph", cropResourceUsed);
        jsonObjectForGraphs.put("cropResourceUnusedForBarGraph", cropResourceUnused);

        JSONObject jsonObjectForStrategyCompleteData = new JSONObject();
        if (strategyDataBean.getFarmInfoView() != null) {
            Map<String, ResourceDualValueView> mapResourceDualValueView = new HashMap<String, ResourceDualValueView>();

            Map<String, CropLimitDualValueView> mapCropLimitDualValueView = new HashMap<String, CropLimitDualValueView>();
            Map<String, GroupLimitDualValueViews> mapGroupLimitDualValueView = new HashMap<String, GroupLimitDualValueViews>();

            if (strategyDataBean.getFarmInfoView().getStrategy() == PlanByStrategy.PLAN_BY_ACRES) {
                FarmInfo farmInfo = farmInfoService.getFarmoldById(strategyDataBean.getFarmInfoView().getId(), null, null, null, null, "cropDualValues", "resourceDual", "groupDualValue");
                for (ResourceDualValue resourceDualValue : farmInfo.getResourceDualValues()) {
                    for (Map.Entry<String, String> entry : map.get("cropResourceUsed").entrySet()) {
                        if (entry.getKey().equals(resourceDualValue.getCropResourceUsage().getCropResourceUse())) {
                            ResourceDualValueView dualValueView = new ResourceDualValueView(resourceDualValue);
                            dualValueView.setProfitPerUnit(AgricultureStandardUtils.stringToLong(AgricultureStandardUtils.removeAllCommas(potentialProfit)) / AgricultureStandardUtils.stringToLong(AgricultureStandardUtils.removeAllCommas(entry.getValue())));
                            mapResourceDualValueView.put(entry.getKey(), dualValueView);
                        }
                    }
                }

                /**
                 * @added - Abhishek
                 * @date - 06-01-2016
                 * @desc - guidelines for Crop Acreage
                 */
                for (CropLimitDualValue cropLimitDualValue : farmInfo.getCropLimitDualValues()) {
                    for (CropTypeView crop : strategyDataBean.getCropTypeViewList()) {
                        if (crop.getSelected()) {
                            if (crop.getCropName().equalsIgnoreCase(cropLimitDualValue.getCropType().getCropName())) {
                                CropLimitDualValueView cropLimitDualValueView = new CropLimitDualValueView(cropLimitDualValue);
                                mapCropLimitDualValueView.put(crop.getCropName(), cropLimitDualValueView);
                            }
                        }
                    }
                }

                /**
                 * @added - Abhishek
                 * @date - 06-01-2016
                 * @desc - guidelines for Group Acreage
                 */
                for (GroupLimitDualValue groupLimitDualValue : farmInfo.getGroupLimitDualValues()) {
                    for (CropsGroupView cropsGroupView : cropsGroupViews) {
                        if (cropsGroupView.getCropsGroupName().equalsIgnoreCase(groupLimitDualValue.getCropsGroup().getCropsGroupName())) {
                            GroupLimitDualValueViews groupLimitDualValueViews = new GroupLimitDualValueViews(groupLimitDualValue);
                            mapGroupLimitDualValueView.put(cropsGroupView.getCropsGroupName(), groupLimitDualValueViews);
                        }
                    }
                }
                jsonObjectForStrategyCompleteData.put("mapResourceDualValueView", mapResourceDualValueView);
                /**
                 * @added - Abhishek
                 * @date - 06-01-2016
                 * @desc - guidelines for Group Acreage
                 */
                jsonObjectForStrategyCompleteData.put("mapCropLimitDualValueView", mapCropLimitDualValueView);
                jsonObjectForStrategyCompleteData.put("mapGroupLimitDualValueView", mapGroupLimitDualValueView);

                jsonObjectForStrategyCompleteData.put("checkStrategyForFarm", checkValidStrategyForFarm);
                jsonObjectForStrategyCompleteData.put("farmOutputDetails", farmOutputDetailsViewList);
                jsonObjectForStrategyCompleteData.put("cropResourceUsed", map.get("cropResourceUsed"));
                jsonObjectForStrategyCompleteData.put("cropResourceUnused", map.get("cropResourceUnused"));
                jsonObjectForStrategyCompleteData.put("jsonObjectForGraphs", jsonObjectForGraphs);
                jsonObjectForStrategyCompleteData.put("potentialProfit", potentialProfit);
            } else if (strategyDataBean.getFarmInfoView().getStrategy() == PlanByStrategy.PLAN_BY_FIELDS) {
                jsonObjectForStrategyCompleteData.put("checkStrategyForFarm", checkValidStrategyForFarm);
                jsonObjectForStrategyCompleteData.put("farmOutputDetails", farmOutputDetailsForFieldViews);
                jsonObjectForStrategyCompleteData.put("cropResourceUsed", map.get("cropResourceUsed"));
                jsonObjectForStrategyCompleteData.put("cropResourceUnused", map.get("cropResourceUnused"));
                jsonObjectForStrategyCompleteData.put("jsonObjectForGraphs", jsonObjectForGraphs);
                jsonObjectForStrategyCompleteData.put("hashMapForAcre", mapForCropsForField.get("hashMapForAcre"));
                jsonObjectForStrategyCompleteData.put("hashMapForProfit", mapForCropsForField.get("hashMapForProfit"));
                jsonObjectForStrategyCompleteData.put("hashMapForRatio", mapForCropsForField.get("hashMapForRatio"));
                jsonObjectForStrategyCompleteData.put("hashMapForProfitIndex", mapForCropsForField.get("hashMapForProfitIndex"));
                jsonObjectForStrategyCompleteData.put("hashMapForRating", mapForCropsForField.get("hashMapForRating"));
                jsonObjectForStrategyCompleteData.put("potentialProfit", potentialProfit);
            }


            jsonObjectForStrategyCompleteData.put("farmInfoView", strategyDataBean.getFarmInfoView());
            jsonObjectForStrategyCompleteData.put("fieldInfoList", strategyDataBean.getFieldInfoViewList());
            jsonObjectForStrategyCompleteData.put("resourceList", strategyDataBean.getResourceUsageViewsList());
            jsonObjectForStrategyCompleteData.put("cropTypeView", strategyDataBean.getCropTypeViewList());
            jsonObjectForStrategyCompleteData.put("cropsGroupViews", cropsGroupViews);
            jsonObjectForStrategyCompleteData.put("cropFieldsDetails", strategyDataBean.getCropFieldChoicesViewList());
            jsonObjectForStrategyCompleteData.put("mapDifferentValues", mapDifferentValues);
            /**
             * @added - Abhishek
             * @date - 11-01-2016
             * @desc - for specifying for which strategy data has been populated
             */
            jsonObjectForStrategyCompleteData.put("farmCustomStrategy", strategyDataBean.getFarmCustomStrategyView());

            jsonObjectForStrategyCompleteData.put("farm", strategyDataBean.getFarmInfoView().getFarmInfo().getFarm());

        }

        return jsonObjectForStrategyCompleteData;

    }

    @Override
    public JSONObject getScenarioComparisonDetails(FarmInfoView farmInfoView, int scenarioId, int[] strategyIdArray) {
        JSONObject jsonObject;

        if (scenarioId != 0) {
            FarmStrategyScenarioView farmStrategyScenarioView = getScenarioById(scenarioId);
            jsonObject = getScenarioOutputDetails(farmInfoView, farmStrategyScenarioView, strategyIdArray);

        } else {
            jsonObject = getStrategyDetails(farmInfoView, strategyIdArray);
        }

        return jsonObject;
    }

    private JSONObject getScenarioOutputDetails(FarmInfoView farmInfoView, FarmStrategyScenarioView farmStrategyScenarioView, int[] strategyIdArray) {

        JSONArray outputJsonArray = new JSONArray();
        JSONArray graphDetailsArray = new JSONArray();


        Map<FarmCustomStrategyView, JSONObject> scenarioDetails = farmDetailsContainerService.getScenarioDetails(farmInfoView, farmStrategyScenarioView);

        Set<FarmCustomStrategyView> farmCustomStrategyViewSet = scenarioDetails.keySet();
        for (FarmCustomStrategyView farmCustomStrategyView : farmCustomStrategyViewSet) {

            for (int strategyId : strategyIdArray) {

                if (farmCustomStrategyView.getId() == strategyId) {

                    JSONObject scenarioOutputDetails = scenarioDetails.get(farmCustomStrategyView);
                    JSONObject strategyOutputDetails = farmDetailsContainerService.getStrategyDetails(farmInfoView, farmCustomStrategyView);

//                    PlantingProfitLogger.info("strategyOutputDetails.get(\"potentialProfit\") : " + strategyOutputDetails.get("potentialProfit"));
//                    PlantingProfitLogger.info("scenarioOutputDetails.get(\"potentialProfit\") : " + scenarioOutputDetails.get("potentialProfit"));

                    int strategyProfit = Integer.parseInt(AgricultureStandardUtils.removeAllCommas(strategyOutputDetails.get("potentialProfit").toString()));
                    int scenarioProfit = Integer.parseInt(AgricultureStandardUtils.removeAllCommas(scenarioOutputDetails.get("potentialProfit").toString()));

//                    int difference = 0;
//                    if(scenarioProfit > strategyProfit){
                    int difference = scenarioProfit - strategyProfit;
//                    } else if(scenarioProfit < strategyProfit){
//                        difference = strategyProfit - scenarioProfit;
//                    }

                    double totalAcreage = 0.0;
                    if (farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)) {
                        List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>)strategyOutputDetails.get("farmOutputDetails");
                        for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {
                            totalAcreage += farmOutputDetailsForFieldView.getUsedAcresAsDouble();
                        }
                    } else if (farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {
                        List<FarmOutputDetailsView> farmOutputDetailsList = (List<FarmOutputDetailsView>) strategyOutputDetails.get("farmOutputDetails");
                        for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsList) {
                            totalAcreage += farmOutputDetailsView.getUsedAcresAsDouble();
                        }
                    }


                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("strategyName", farmCustomStrategyView.getStrategyName());
                    jsonObject.put("strategyOutput", "$" + AgricultureStandardUtils.commaSeparaterForInteger(strategyProfit));
                    jsonObject.put("scenarioOutput", "$" + AgricultureStandardUtils.commaSeparaterForInteger(scenarioProfit));
                    jsonObject.put("difference", "$" + AgricultureStandardUtils.commaSeparaterForInteger(difference));
                    jsonObject.put("differenceColor", difference < 0 ? "red" : "");
                    jsonObject.put("acreage", totalAcreage);
                    outputJsonArray.add(jsonObject);

                    JSONObject graphDetailsObject = new JSONObject();

                    graphDetailsObject.put("strategyName", farmCustomStrategyView.getStrategyName());
                    graphDetailsObject.put("potentialProfit", Long.parseLong(AgricultureStandardUtils.removeAllCommas(scenarioOutputDetails.get("potentialProfit").toString())));
                    graphDetailsArray.add(graphDetailsObject);


                }
            }
        }

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("outputDetails", outputJsonArray);
        jsonObject.put("graphDetails", graphDetailsArray);

        return jsonObject;

    }

    private JSONObject getStrategyDetails(FarmInfoView farmInfoView, int[] strategyIdArray) {

        JSONArray jsonArray = new JSONArray();

        Map<FarmCustomStrategyView, JSONObject> allStrategiesDetails = farmDetailsContainerService.getAllStrategiesDetails(farmInfoView);

        Set<Map.Entry<FarmCustomStrategyView, JSONObject>> entries = allStrategiesDetails.entrySet();

        for (Map.Entry<FarmCustomStrategyView, JSONObject> entry : entries) {

            FarmCustomStrategyView farmCustomStrategyView = entry.getKey();

            for (int strategyId : strategyIdArray) {

                if (farmCustomStrategyView.getId() == strategyId) {

                    JSONObject strategyOutputDetails = entry.getValue();

                    double totalAcreage = 0.0;
                    if (farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)) {
                        List<FarmOutputDetailsForFieldView> farmOutputDetailsForFieldViewList = (List<FarmOutputDetailsForFieldView>)strategyOutputDetails.get("farmOutputDetails");
                        for (FarmOutputDetailsForFieldView farmOutputDetailsForFieldView : farmOutputDetailsForFieldViewList) {
                            totalAcreage += farmOutputDetailsForFieldView.getUsedAcresAsDouble();
                        }
                    } else if (farmCustomStrategyView.getFarmCustomStrategy().getFarmInfo().getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {
                        List<FarmOutputDetailsView> farmOutputDetailsList = (List<FarmOutputDetailsView>) strategyOutputDetails.get("farmOutputDetails");
                        for (FarmOutputDetailsView farmOutputDetailsView : farmOutputDetailsList) {
                            totalAcreage += farmOutputDetailsView.getUsedAcresAsDouble();
                        }
                    }


                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("strategyName", farmCustomStrategyView.getStrategyName());
                    jsonObject.put("strategyOutput", "$" + strategyOutputDetails.get("potentialProfit"));
                    jsonObject.put("scenarioOutput", "");
                    jsonObject.put("difference", "");
                    jsonObject.put("acreage", totalAcreage);
                    jsonArray.add(jsonObject);

                }
            }
        }
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("outputDetails", jsonArray);

        return jsonObject;
    }


}
