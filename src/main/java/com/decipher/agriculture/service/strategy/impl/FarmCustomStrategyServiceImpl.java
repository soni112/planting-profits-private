package com.decipher.agriculture.service.strategy.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.decipher.agriculture.bean.StrategyDataBean;
import com.decipher.agriculture.data.farm.CropLimit;
import com.decipher.agriculture.data.farm.CropResourceUsage;
import com.decipher.agriculture.data.farm.CropResourceUsageFieldVariances;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.data.strategy.FarmCustomStrategyForCrop;
import com.decipher.agriculture.data.strategy.FarmCustomStrategyForGroup;
import com.decipher.agriculture.data.strategy.FarmCustomStrategyForResource;
import com.decipher.agriculture.service.farm.CropFieldChociesService;
import com.decipher.agriculture.service.farm.CropResourceUsageFieldVariancesService;
import com.decipher.agriculture.service.farm.CropResourceUsageService;
import com.decipher.agriculture.service.farm.CropTypeService;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.farm.FieldInfoService;
import com.decipher.agriculture.service.strategy.FarmCustomStrategyService;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.CropFieldChociesView;
import com.decipher.view.form.farmDetails.CropResourceUsageFieldVariancesView;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FarmOutputDetailsView;
import com.decipher.view.form.farmDetails.FieldInfoView;
import com.decipher.view.form.strategy.FarmCustomStrategyForCropView;
import com.decipher.view.form.strategy.FarmCustomStrategyForResourseView;
import com.decipher.view.form.strategy.FarmCustomStrategyView;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decipher.agriculture.dao.strategy.FarmCustomStrategyDao;
import com.decipher.agriculture.data.strategy.FarmCustomStrategy;

import javax.servlet.http.HttpSession;

@Service
@SuppressWarnings("unchecked")
public class FarmCustomStrategyServiceImpl implements FarmCustomStrategyService {

    /**
     * @added - Abhishek
     * @date - 05-01-2016
     */
    /* ********************** Start ********************** */
    @Autowired
    private CropTypeService cropTypeService;
    @Autowired
    private CropResourceUsageService cropResourceUsageService;
    @Autowired
    private CropResourceUsageFieldVariancesService variancesService;
    @Autowired
    private FieldInfoService fieldInfoService;
    @Autowired
    private CropFieldChociesService cropFieldChociesService;
	/* ********************** End ********************** */

    @Autowired
    private FarmCustomStrategyDao farmCustomStrategyDao;
    @Autowired
    private FarmInfoService farmInfoService;

    @Autowired
    private HttpSession httpSession;

    @Override
    public int saveFarmCustomStrategy(int farmId, String[] resourceArray, String strategyName) {
        FarmInfo farmInfo = farmInfoService.getFarmoldById(farmId, null, "CropResourceUsage", null, null, null, null, null);
        FarmCustomStrategy customStrategy = new FarmCustomStrategy();
        customStrategy.setStrategyName(strategyName);
        customStrategy.setStrategyForCrop(false);
        customStrategy.setStrategyForResourse(true);
        customStrategy.setFarmInfo(farmInfo);
        customStrategy.setFarm(farmInfo.getFarm());

        for (String str : resourceArray) {
            String[] array = str.split("#-#-#");
            FarmCustomStrategyForResource farmCustomStrategyForResource = new FarmCustomStrategyForResource();
            farmCustomStrategyForResource.setResourseName(array[0]);
            farmCustomStrategyForResource.setResourseValue(Long.parseLong(array[1]));
            farmCustomStrategyForResource.setFarmCustomStrategy(customStrategy);
            for (CropResourceUsage cropResourceUsage : farmInfo.getCropResourceUsage()) {
                if (array[0].equals(cropResourceUsage.getCropResourceUse())) {
                    farmCustomStrategyForResource.setCropResourceUsage(cropResourceUsage);
                }
            }
            customStrategy.getCustomStrategyForResourses().add(farmCustomStrategyForResource);
        }


        return farmCustomStrategyDao.saveFarmCustomStrategy(customStrategy);
    }

    @Override
    public boolean isFarmStrategyExitsWithName(String strategyName, int farmId) {
        return farmCustomStrategyDao.isFarmStrategyExitsWithName(strategyName, farmId);
    }

    @Override
    public boolean isFarmStrategyExitsWithId(int strategyId, int farmId) {
        return farmCustomStrategyDao.isFarmStrategyExitsWithId(strategyId, farmId);
    }

    @Override
    public int saveFarmCustomStrategyForMultipalCrop(int farmId, String[] cropsArray, String[] cropContractArray,
                                                     String[] cropProposedArray, String[] cropsGroupArray, String strategyName) {

        PlantingProfitLogger.info("for saved saveFarmCustomStrategyForMultipleCrop in dao : ");
        FarmInfo farmInfo = farmInfoService.getFarmoldById(farmId, "CropTypes", null, null, null, null, null, null);

        FarmCustomStrategy customStrategy = new FarmCustomStrategy();
        customStrategy.setStrategyName(strategyName);
        customStrategy.setStrategyForCrop(true);
        customStrategy.setStrategyForResourse(false);
        customStrategy.setFarmInfo(farmInfo);
        customStrategy.setFarm(farmInfo.getFarm());
        Set<CropType> cropTypeSet = farmInfo.getCropTypes();

        if (cropsArray != null) {
            for (String str : cropsArray) {
                FarmCustomStrategyForCrop customStrategyForCrop = new FarmCustomStrategyForCrop();
                customStrategyForCrop.setCropname(str.split("#-#-#")[0]);
                if (str.split("#-#-#")[1].equals("max")) {
                    customStrategyForCrop.setMaximum(str.split("#-#-#")[2]);
                } else if (str.split("#-#-#")[1].equals("min")) {
                    customStrategyForCrop.setMinimum(str.split("#-#-#")[2]);
                }
                for (CropType cropType : cropTypeSet) {
                    if (cropType.getCropName().equals(str.split("#-#-#")[0])) {
                        customStrategyForCrop.setCropType(cropType);
                        break;
                    }
                }
                customStrategyForCrop.setProposedchecked(false);
                customStrategyForCrop.setContractchecked(false);
                customStrategy.getCustomStrategyForCrops().add(customStrategyForCrop);
            }
        }
        if (cropContractArray != null) {
            for (String str : cropContractArray) {
                FarmCustomStrategyForCrop customStrategyForCrop = new FarmCustomStrategyForCrop();
                customStrategyForCrop.setCropname(str.split("#-#-#")[0]);
                if (str.split("#-#-#")[1].equals("min")) {
                    customStrategyForCrop.setMinimum(str.split("#-#-#")[2]);
                }
                for (CropType cropType : cropTypeSet) {
                    if (cropType.getCropName().equals(str.split("#-#-#")[0])) {
                        customStrategyForCrop.setCropType(cropType);
                        break;
                    }
                }
                customStrategyForCrop.setProposedchecked(false);
                customStrategyForCrop.setContractchecked(true);
                customStrategy.getCustomStrategyForCrops().add(customStrategyForCrop);
            }
        }
        if (cropProposedArray != null) {
            for (String str : cropProposedArray) {
                FarmCustomStrategyForCrop customStrategyForCrop = new FarmCustomStrategyForCrop();
                customStrategyForCrop.setCropname(str.split("#-#-#")[0]);
                if (str.split("#-#-#")[1].equals("min")) {
                    customStrategyForCrop.setMinimum(str.split("#-#-#")[2]);
                }
                for (CropType cropType : cropTypeSet) {
                    if (cropType.getCropName().equals(str.split("#-#-#")[0])) {
                        customStrategyForCrop.setCropType(cropType);
                        break;
                    }
                }
                customStrategyForCrop.setProposedchecked(true);
                customStrategyForCrop.setContractchecked(false);
                customStrategy.getCustomStrategyForCrops().add(customStrategyForCrop);
            }
        }
        if (cropsGroupArray != null) {
            for (String str : cropsGroupArray) {
                FarmCustomStrategyForGroup customStrategyForGroup = new FarmCustomStrategyForGroup();
                customStrategyForGroup.setGroupname(str.split("#-#-#")[0]);
                if (str.split("#-#-#")[1].equals("max")) {
                    customStrategyForGroup.setMaximum(str.split("#-#-#")[2]);
                } else if (str.split("#-#-#")[1].equals("min")) {
                    customStrategyForGroup.setMinimum(str.split("#-#-#")[2]);
                }
                Set<CropsGroup> cropsGroupSet = farmInfo.getCropsGroup();
                for (CropsGroup cropsGroup : cropsGroupSet) {
                    if (cropsGroup.getCropsGroupName().equals(str.split("#-#-#")[0])) {
                        customStrategyForGroup.setCropsGroup(cropsGroup);
                        break;
                    }
                }
                customStrategy.getCustomStrategyForGroup().add(customStrategyForGroup);

            }
        }

        return farmCustomStrategyDao.saveFarmCustomStrategy(customStrategy);
    }

    @Override
    public List<FarmCustomStrategyView> getDataForCustomStrategy(int farmId) {
        List<FarmCustomStrategyView> farmCustomStrategyViews = new ArrayList<FarmCustomStrategyView>();
        List<FarmCustomStrategy> farmCustomStrategyList = farmCustomStrategyDao.getDataForCustomStrategy(farmId);
        if (farmCustomStrategyList != null) {
            for (FarmCustomStrategy farCustomStrategy : farmCustomStrategyList) {
                farmCustomStrategyViews.add(new FarmCustomStrategyView(farCustomStrategy));
            }
        }
        return farmCustomStrategyViews;
    }

    /**
     * @return - FarmCustomStrategyView Object
     * @author - Abhishek
     * @date - 21-12-2015
     */
    @Override
    public FarmCustomStrategyView getDataForCustomStrategy(int farmId, int strategyID) {
        FarmCustomStrategyView farmCustomStrategyViews = new FarmCustomStrategyView();
        FarmCustomStrategy farmCustomStrategy = farmCustomStrategyDao.getDataForCustomStrategy(farmId, strategyID);
        if (farmCustomStrategy != null) {
            farmCustomStrategyViews = new FarmCustomStrategyView(farmCustomStrategy);
        }
        return farmCustomStrategyViews;
    }

    /**
     * @return - Updated vales for strategy
     * @added - Abhishek
     * @date - 05-01-2016
     */
    @Override
    public StrategyDataBean getStrategyBaseValuesForStrategy(int strategyId, FarmInfoView farmInfoView) {

        StrategyDataBean strategyDataBean = new StrategyDataBean();

        Farm farm = farmInfoView.getFarmInfo().getFarm();


        /**
         * Crop min and max values to be changed here
         */
        List<CropTypeView> cropTypeViewList = cropTypeService.getAllCropByFarm(farmInfoView.getId());

        Integer[] cropIdArray = new Integer[cropTypeViewList.size()];
        List<CropType> cropTypeList = new ArrayList<CropType>();
        int i = 0;
        for (CropTypeView cropTypeView : cropTypeViewList) {
            cropTypeList.add(cropTypeView.getCropType());
            cropIdArray[i] = cropTypeView.getId();
            i++;
        }

        /**
         * resource values to be changed here
         */
        List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(farmInfoView.getId());
        /**
         * resource usage values to be changed here
         */
        List<CropResourceUsageFieldVariancesView> resourceUsageVariances = variancesService.getAllResourceByCropIds(cropIdArray);


//		Updating values in baseline according to the strategy updated values
        List<FarmCustomStrategyView> dataForCustomStrategy = this.getDataForCustomStrategy(farm.getFarmId());
        for (FarmCustomStrategyView farmCustomStrategyView : dataForCustomStrategy) {
            if (farmCustomStrategyView.getId().equals(strategyId)) {

                /**
                 * @chanegd - Abhishek
                 * @date - 11-01-2016
                 * @desc - using FarmCustomStrategyView object instead of strategyID
                 */
                strategyDataBean.setFarmCustomStrategyView(farmCustomStrategyView);


                if (farmCustomStrategyView.isStrategyForCrop() && farmCustomStrategyView.getCustomStrategyForCropsViews() != null) {
                    for (FarmCustomStrategyForCropView farmCustomStrategyForCropView : farmCustomStrategyView.getCustomStrategyForCropsViews()) {
                        for (CropTypeView cropDetails : cropTypeViewList) {
                            if (cropDetails.getSelected() && cropDetails.getCropName().equalsIgnoreCase(farmCustomStrategyForCropView.getCropname())) {
                                cropDetails.setMaximumAcres(farmCustomStrategyForCropView.getMaximum());
                                cropDetails.setMinimumAcres(farmCustomStrategyForCropView.getMinimum());

                                CropLimit cropLimit = cropDetails.getCropType().getCropLimit();
                                cropLimit.setMaximumAcres(farmCustomStrategyForCropView.getMaximum());
                                cropLimit.setMinimumAcres(farmCustomStrategyForCropView.getMinimum());
                            }
                        }
                    }
                }
                if (farmCustomStrategyView.isStrategyForResource() && farmCustomStrategyView.getCustomStrategyForResourcesView() != null) {
                    for (FarmCustomStrategyForResourseView farmCustomStrategyForResourseView : farmCustomStrategyView.getCustomStrategyForResourcesView()) {
                        for (CropResourceUsageView resourceUsageView : resourceUsageViews) {
                            if (resourceUsageView.getCropResourceUse().equalsIgnoreCase(farmCustomStrategyForResourseView.getResourseName())) {
                                resourceUsageView.setCropResourceUseAmount(Long.toString(farmCustomStrategyForResourseView.getResourseValue()));

                                CropResourceUsage cropResourceUsage = resourceUsageView.getCropResourceUsage();
                                cropResourceUsage.setCropResourceUseAmount(resourceUsageView.getCropResourceUseAmount());
                            }
                        }
                        for (CropResourceUsageFieldVariancesView otherResourcesUsed : resourceUsageVariances) {
                            if (otherResourcesUsed.getCropFieldResourceUse().equalsIgnoreCase(farmCustomStrategyForResourseView.getResourseName())) {
                                otherResourcesUsed.setCropResourceAmount(Long.toString(farmCustomStrategyForResourseView.getResourseValue()));

                                CropResourceUsageFieldVariances cropResourceUsageFieldVariances = otherResourcesUsed.getCropResourceUsageFieldVariances();
                                cropResourceUsageFieldVariances.setCropResourceAmount(otherResourcesUsed.getCropResourceAmount());

                            }
                        }
                    }
                }
            }
        }

        List<FieldInfoView> fieldInfoViews = fieldInfoService.getAllFieldsByFarmId(farmInfoView.getId());

        List<CropFieldChociesView> cropFieldsDetails = cropFieldChociesService.getAllCropFiledsCropIds(cropIdArray);

//		Setting updated values in bean(FarmCustomStrategyView setted above)



        strategyDataBean.setCropTypeList(cropTypeList);
        strategyDataBean.setCropTypeViewList(cropTypeViewList);
        strategyDataBean.setResourceUsageViewsList(resourceUsageViews);
        strategyDataBean.setCropResourceUsageFieldVariancesList(resourceUsageVariances);
        strategyDataBean.setFieldInfoViewList(fieldInfoViews);
        strategyDataBean.setCropIdArray(cropIdArray);
        strategyDataBean.setFarmCustomStrategyViewList(dataForCustomStrategy);
        strategyDataBean.setCropFieldChoicesViewList(cropFieldsDetails);
        strategyDataBean.setFarmInfoView(farmInfoView);

        return strategyDataBean;
    }

    @Override
    public int saveAsBaseLineStrategy(FarmInfo farmInfo) {
        boolean flag = true;
        Farm farm = farmInfo.getFarm();
        FarmCustomStrategy strategyForBaseline = new FarmCustomStrategy();

        Set<FarmCustomStrategy> farmCustomStrategySet = farm.getFarmCustomStrategy();
        if (farmCustomStrategySet.size() > 0) {
            for (FarmCustomStrategy farmCustomStrategy1 : farmCustomStrategySet) {
                if (farmCustomStrategy1.getStrategyName().equalsIgnoreCase("Baseline Strategy")) {
                    strategyForBaseline = farmCustomStrategy1;
                    flag = false;
                }
            }
        }

        strategyForBaseline.setStrategyName("Baseline Strategy");
        strategyForBaseline.setStrategyForCrop(false);
        strategyForBaseline.setStrategyForResourse(false);
        strategyForBaseline.setFarmInfo(farmInfo);
        strategyForBaseline.setFarm(farmInfo.getFarm());

        if (flag) {
            PlantingProfitLogger.info("Saving baseline strategy for " + farm.getFarmName());
            return farmCustomStrategyDao.saveFarmCustomStrategy(strategyForBaseline);
        } else {
            PlantingProfitLogger.info("Updating baseline strategy for " + farm.getFarmName());
            return updateFarmStrategy(strategyForBaseline);
        }

    }

    @Override
    public int updateFarmStrategy(FarmCustomStrategy farmCustomStrategy) {
        return farmCustomStrategyDao.updateFarmStrategy(farmCustomStrategy);
    }

    @Override
    public boolean deleteStrategy(int strategyId) {
        return farmCustomStrategyDao.deleteStrategy(strategyId);
    }

    @Override
    public FarmCustomStrategyView getBaseLineStrategyForFarm(FarmInfo farminfo) {
        FarmCustomStrategyView farmCustomStrategyViews = new FarmCustomStrategyView();
        FarmCustomStrategy farmCustomeStrategy = farmCustomStrategyDao.getStrategyByNameByFarm("Baseline Strategy", farminfo);
        if (farmCustomeStrategy != null) {
            farmCustomStrategyViews = new FarmCustomStrategyView(farmCustomeStrategy);
        }
        return farmCustomStrategyViews;
    }

    @Override
    public int saveFarmCustomStrategy(FarmCustomStrategy farmCustomStrategy) {
        return farmCustomStrategyDao.saveFarmCustomStrategy(farmCustomStrategy);
    }

    @Override
    public Map<Integer, String> getCropDetailsForSelection(FarmInfoView farmInfoView, JSONObject baseLineOutputDetails){

        if (httpSession.getAttribute("cropDescription") == null) {

            Map<Integer, String> cropDescription = new TreeMap<>();

            if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_ACRES)) {

                List<FarmOutputDetailsView> outputDetailsForFarm = (List<FarmOutputDetailsView>)baseLineOutputDetails.get("farmOutputDetails");

                int keyIndex = 12;
                for (FarmOutputDetailsView farmOutputDetails : outputDetailsForFarm) {
//                    Map<String, Object> cropDetails = new TreeMap<>();
                    String cropNameStr;
                    if (farmOutputDetails.getForFirm())
                        cropNameStr = farmOutputDetails.getCropTypeView().getCropName() + " (Firm)";
                    else if (farmOutputDetails.getForProposed())
                        cropNameStr = farmOutputDetails.getCropTypeView().getCropName() + " (Proposed)";
                    else
                        cropNameStr = farmOutputDetails.getCropTypeView().getCropName();

//                    cropDetails.put("text", "Acreage of " + cropNameStr);
//                    cropDetails.put("cropType", farmOutputDetails.getCropTypeView());

                    cropDescription.put(keyIndex, "Acreage of " + cropNameStr);
                    keyIndex++;
                }

            } else if (farmInfoView.getStrategy().equals(PlanByStrategy.PLAN_BY_FIELDS)) {
                Map<String, String> hashMapForAcre = (Map<String, String>) baseLineOutputDetails.get("hashMapForAcre");

                int keyIndex = 12;

                Set<String> keySet = hashMapForAcre.keySet();

                for (String cropName : keySet) {
//                    Map<String, Object> cropDetails = new TreeMap<>();
//                    cropDetails.put("text", "Acreage of " + cropName);
//                    cropDetails.put("cropType", cropTypeView);

                    cropDescription.put(keyIndex, "Acreage of " + cropName);
                    keyIndex++;
                }

                /*for (CropTypeView cropTypeView : cropTypeViewList) {
                    if (cropTypeView.getSelected()) {
                        Map<String, Object> cropDetails = new TreeMap<>();

                        String cropNameStr;
                        if(cropTypeView.getFirmchecked().equalsIgnoreCase("true"))
                            cropNameStr = cropTypeView.getCropName() + " (Firm)";
                        else if(cropTypeView.getProposedchecked())
                            cropNameStr = cropTypeView.getCropName() + " (Proposed)";
                        else
                            cropNameStr = cropTypeView.getCropName();

                        cropDetails.put("text", "Acreage of " + cropNameStr);
                        cropDetails.put("cropType", cropTypeView);

                        cropDescription.put(keyIndex, cropDetails);
                        keyIndex++;
                    }
                }*/
            }



            httpSession.setAttribute("cropDescription", cropDescription);

            return cropDescription;
        } else {

            return (Map<Integer, String>) httpSession.getAttribute("cropDescription");
        }

    }

}
