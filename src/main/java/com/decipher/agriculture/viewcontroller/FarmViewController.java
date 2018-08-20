package com.decipher.agriculture.viewcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.scenario.FarmStrategyScenario;
import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.decipher.agriculture.service.account.impl.SessionService;
import com.decipher.agriculture.service.farm.CropFieldChociesService;
import com.decipher.agriculture.service.farm.CropGroupService;
import com.decipher.agriculture.service.farm.CropResourceUsageFieldVariancesService;
import com.decipher.agriculture.service.farm.CropResourceUsageService;
import com.decipher.agriculture.service.farm.CropTypeService;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.agriculture.service.farm.FieldInfoService;
import com.decipher.agriculture.service.farm.OptionalCropProductionCostsDetailsService;
import com.decipher.agriculture.service.farmDetails.FarmDetailsContainerService;
import com.decipher.agriculture.service.strategy.FarmCustomStrategyService;
import com.decipher.util.PlantingProfitLogger;

import com.decipher.view.form.farmDetails.CropFieldChociesView;
import com.decipher.view.form.farmDetails.CropResourceUsageFieldVariancesView;
import com.decipher.view.form.farmDetails.CropResourceUsageView;
import com.decipher.view.form.farmDetails.CropTypeView;
import com.decipher.view.form.farmDetails.CropsGroupView;
import com.decipher.view.form.farmDetails.FarmInfoView;
import com.decipher.view.form.farmDetails.FieldInfoView;
import com.decipher.view.form.farmDetails.OptionalCropProductionCostsDetailsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.decipher.agriculture.data.account.Account;

import javax.servlet.http.HttpSession;


@Controller
@SessionAttributes({"userFlag", "growerId"})
public class FarmViewController {

    @Autowired
    private FarmService farmService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private FarmInfoService farmInfoService;
    @Autowired
    private CropTypeService cropTypeService;
    @Autowired
    private OptionalCropProductionCostsDetailsService cropProductionCostsDetailsService;
    @Autowired
    private CropResourceUsageService cropResourceUsageService;
    @Autowired
    private CropResourceUsageFieldVariancesService variancesService;
    @Autowired
    private FieldInfoService fieldInfoService;
    @Autowired
    private CropFieldChociesService cropFieldChociesService;
    @Autowired
    private CropGroupService cropGroupService;
    @Autowired
    private FarmCustomStrategyService farmCustomStrategyService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private FarmDetailsContainerService farmDetailsContainerService;


    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/farm.htm")
    public ModelAndView farmPage(@RequestParam(value = "growerId", required = false) String growerId) {

        Map<String, Object> model = new HashMap<String, Object>();
        PlantingProfitLogger.info("User requesting for farm.htm page .... ");

        /**
         * @chnaged - Abhishek
         * @date - 29-03-2016
         * @desc - changed for getting account from saved session
         */
        if (growerId != null) {
            httpSession.removeAttribute("growerId");
            httpSession.setAttribute("growerId", growerId);
        }

        Account account = sessionService.getLoggedInUser();
        String page = "";
        if (account != null) {
            List<Farm> allFarmsForUser = farmService.getAllFarmsForUser(account.getId());

            model.put("farmList", allFarmsForUser);

            UserCountry physical_address_country = account.getPhysical_Address_Country();
            if (physical_address_country != null) {
                model.put("country", physical_address_country.getCountryCode().toUpperCase());
            }

            page = "farm";
            PlantingProfitLogger.info("User requesting for farm.htm page .... userId " + account.getId());
        } else {
            PlantingProfitLogger.info("User requesting for farm.htm page .... userId " + account.getId());
            page = "home";
        }
        return new ModelAndView(page, "model", model);

    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/farm-info.htm")
    public ModelAndView FarmInfoPage(@RequestParam(value = "farmId", required = false) int farmId) {
        PlantingProfitLogger.info("request for farm-info.htm ");
        Map<String, Object> myModel = new HashMap<String, Object>();

        Farm farm = farmService.getFarmById(farmId);
        if (farm != null) {
            myModel.put("farm", farm);
        }
        return new ModelAndView("farm-info", "model", myModel);
    }


    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/output-edit-farm-info.htm")
    public ModelAndView OutputEditInfoPage(@RequestParam(value = "farmId", required = false) int farmId) {
        PlantingProfitLogger.info("request for output-edit-farm-info.htm ");
        Map<String, Object> myModel = new HashMap<String, Object>();
//        FarmInfoView farmInfoView = farmInfoService.getFarmByIdAfterCheckForAccount(farmId, account.getId());

        Farm farm = farmService.getFarmById(farmId);
//        Set<FarmCustomStrategy> farmCustomStrategies = farm.getFarmCustomStrategy();
//        for (FarmCustomStrategy farmCustomStrategy:farmCustomStrategies){
//            if (farmCustomStrategy.getStrategyName().equals("Baseline Strategy")){
//                Set<FarmStrategyScenario> farmStrategyScenarios = farmCustomStrategy.getFarmStrategyScenarios();
//                for (FarmStrategyScenario farmStrategyScenario:farmStrategyScenarios){
//                    int scenarioId = farmStrategyScenario.getScenarioId();
//                }
//            }
//        }


        if (farm != null && !farm.getSaveFlag()) {
            myModel.put("farm", farm);
            return new ModelAndView("farm-info", "model", myModel);
        } else {
            myModel.put("farm", farm);
            try {
                myModel.put("strategies", farmDetailsContainerService.getAllStrategiesForFarm(farm));
            } catch (Exception e) {
                PlantingProfitLogger.debug(e);
                PlantingProfitLogger.error(e.getMessage());
            }
            return new ModelAndView("output-edit-farm-info", "model", myModel);
        }


    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(value = "/view-farm-info.htm")
    public ModelAndView viewFarmInfoPage(@RequestParam(value = "farmId", required = true) int farmId) {
        PlantingProfitLogger.info("request for view-farm-info.htm ");
        Map<String, Object> myModel = new HashMap<String, Object>();
        FarmInfoView farmInfoView = farmService.getBaselineFarmDetails(farmId);

        List<CropTypeView> cropTypeView = cropTypeService.getAllCropByFarm(farmInfoView.getId());
        Integer[] cropIdArray = new Integer[cropTypeView.size()];
        int i = 0;
        for (CropTypeView view : cropTypeView) {
            cropIdArray[i] = view.getId();
//			PlantingProfitLogger.info("crop name : "+view.getCropName()+"Production Cost : "+view.getCalculatedVariableProductionCostFloat());
            i++;
        }

        List<OptionalCropProductionCostsDetailsView> cropProductionCostsDetailsList = cropProductionCostsDetailsService.getAllCropProductionCostsDetailsByCropIds(cropIdArray);
        List<CropResourceUsageView> resourceUsageViews = cropResourceUsageService.getAllCropResourceUsageByFarmId(farmInfoView.getId());
        List<CropResourceUsageFieldVariancesView> resourceUsageVariances = variancesService.getAllResourceByCropIds(cropIdArray);
        List<FieldInfoView> fieldInfoViews = fieldInfoService.getAllFieldsByFarmId(farmInfoView.getId());
        List<CropFieldChociesView> cropFieldsDetails = cropFieldChociesService.getAllCropFiledsCropIds(cropIdArray);
//		List<CropListForGroupView> cropListForGroup= new ArrayList<CropListForGroupView>();
        List<CropsGroupView> cropsGroupView = cropGroupService.getAllCropGroupByFarm(farmInfoView.getId());

        if (farmInfoView != null) {
            myModel.put("farm", farmInfoView.getFarmInfo().getFarm());
            myModel.put("farmInfoView", farmInfoView);
            myModel.put("cropTypeView", cropTypeView);
            myModel.put("CropCostsDetailsList", sortOptionalCropInfo(cropProductionCostsDetailsList));
            myModel.put("resourceList", resourceUsageViews);
            myModel.put("resourceVariancesList", resourceUsageVariances);
            myModel.put("fieldInfoList", fieldInfoViews);
            myModel.put("cropFieldsDetails", cropFieldsDetails);
            myModel.put("cropsGroupList", cropsGroupView);
//			myModel.put("cropListForGroup", cropListForGroup);		
            return new ModelAndView("view-farm-info", "model", myModel);
        } else {
            return new ModelAndView("redirect:farm.htm", "model", myModel);
        }
    }


    private List<OptionalCropProductionCostsDetailsView> sortOptionalCropInfo(List<OptionalCropProductionCostsDetailsView> cropProductionCostsDetailsList){
        /**
         * @added - Abhishek
         * @date - 02-11-2016
         * @desc - for sorting of optional crop info in the given format
         * @status - pending(needs approval for sorting and changes to be made in the previous values into database)
         */
        String[] optionalCropInfoHeadsArr = {
                "Seed",
                "Fertilizer and soil amendments",
                "Herbicide",
                "Insecticide",
                "Fungicide",
                "Defoliant",
                "Other chemicals",
                "Crop insurance",
                "Crop consulting",
                "Other professional services",
                "Machinery operating costs",
                "Cost of operator and hired labor",
                "Custom hire and rental",
                "Irrigation",
                "Drying",
                "Storage",
                "Marketing",
                "Rent",
                "Operating interest (e.g. 6% x ½ year)",
                "Others"
        };

        List<OptionalCropProductionCostsDetailsView> optionalCropProductionCostsDetailsViewList = new ArrayList<>();

        for (String head : optionalCropInfoHeadsArr) {
            for (OptionalCropProductionCostsDetailsView optionalCropProductionCostsDetailsView : cropProductionCostsDetailsList) {
                if(optionalCropProductionCostsDetailsView.getCostComponentsName().equalsIgnoreCase("Crop supplies")){
                    optionalCropProductionCostsDetailsView.setCostComponentsName("Others");
                }

                if(head.equalsIgnoreCase(optionalCropProductionCostsDetailsView.getCostComponentsName())){
                    optionalCropProductionCostsDetailsViewList.add(optionalCropProductionCostsDetailsView);
                }

            }
        }

        return optionalCropProductionCostsDetailsViewList;
    }
}
