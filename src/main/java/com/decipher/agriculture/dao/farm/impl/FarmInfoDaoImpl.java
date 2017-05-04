package com.decipher.agriculture.dao.farm.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.decipher.agriculture.dao.farm.FarmInfoDao;
import com.decipher.agriculture.data.farm.AdditionalCropIncome;
import com.decipher.util.AgricultureStandard;
import com.decipher.util.PlantingProfitLogger;

import org.hibernate.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CropFieldChocies;
import com.decipher.agriculture.data.farm.CropForwardSales;
import com.decipher.agriculture.data.farm.CropLimit;
import com.decipher.agriculture.data.farm.CropResourceUsage;
import com.decipher.agriculture.data.farm.CropResourceUsageFieldDifferences;
import com.decipher.agriculture.data.farm.CropResourceUsageFieldVariances;
import com.decipher.agriculture.data.farm.CropType;
import com.decipher.agriculture.data.farm.CropUnitOfMeasure;
import com.decipher.agriculture.data.farm.CropYieldFieldVariances;
import com.decipher.agriculture.data.farm.CropsGroup;
import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.decipher.agriculture.data.strategy.FarmCustomStrategyForResource;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.data.farm.FieldInfo;
import com.decipher.agriculture.data.farm.InternalCropPricesInfo;
import com.decipher.agriculture.data.farm.InternalCropYieldInfo;
import com.decipher.agriculture.data.farm.InternalVariableCropProductionCosts;
import com.decipher.agriculture.data.farm.OptionalCropPlantingDates;
import com.decipher.agriculture.data.farm.OptionalCropProductionCostsDetails;
import com.decipher.agriculture.data.farm.PlanByStrategy;
import com.decipher.agriculture.data.farm.SummaryCropInfo;

@Repository
@Transactional
public class FarmInfoDaoImpl implements FarmInfoDao {

    @Autowired
    private SessionFactory sessionFactory;
    private static final DateFormat FORMATTER = AgricultureStandard.FORMATTER;

    @Override
    public boolean updateFarmInfo(FarmInfo farmInfo) {

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            session.saveOrUpdate(farmInfo);
            session.flush();

            session.load(FarmInfo.class, farmInfo.getId());

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            PlantingProfitLogger.error(e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public FarmInfo getFarmInfoById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        FarmInfo farmInfo = null;
        try {
            Query query = session.createQuery("from FarmInfo where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {

                farmInfo = (FarmInfo) obj;
                initializeLazy(farmInfo);

            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            farmInfo = null;
        } finally {
            session.close();
        }
        return farmInfo;
    }

    @Override
    public FarmInfo getFarmInfoOldById(int id, String CropTypes,
                                       String CropResourceUsage, String FieldInfos, String CropsGroup,
                                       String cropDualValues, String resourceDual, String groupDualValue) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        FarmInfo info = null;
        try {
            Query query = session.createQuery("from FarmInfo where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof FarmInfo) {
                    info = (FarmInfo) obj;
                    if (CropTypes != null)
                        Hibernate.initialize(info.getCropTypes());
                    if (CropResourceUsage != null)
                        Hibernate.initialize(info.getCropResourceUsage());
                    if (FieldInfos != null)
                        Hibernate.initialize(info.getFieldInfos());
                    if (cropDualValues != null)
                        Hibernate.initialize(info.getCropLimitDualValues());
                    if (resourceDual != null)
                        Hibernate.initialize(info.getResourceDualValues());
                    if (groupDualValue != null)
                        Hibernate.initialize(info.getGroupLimitDualValues());
                    if (CropsGroup != null)
                        Hibernate.initialize(info.getCropsGroup());

                } else
                    info = null;
            } else {
                info = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            info = null;
        } finally {
            session.close();
        }
        return info;
    }

    @Override
    public boolean deleteAllFarmRecords(int farmId) {
        PlantingProfitLogger.info("inside deleteAllFarmRecords..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("CALL removeFirmAllRecord(:farmId)");
            query.setParameter("farmId", farmId);
            int result = query.executeUpdate();
            session.flush();
            PlantingProfitLogger.info("result deleted : " + result);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            PlantingProfitLogger.error(e);
            return false;
        } finally {
            session.close();
        }
    }

//    /*@Override
//    public List<Integer> getAllFarmIds(int userId, int[] farmIds) {
//        PlantingProfitLogger.info("inside getAllFarmIds farmIds : " + farmIds);
//        List<FarmInfo> farmInfos = null;
//        List<Integer> farmId = new ArrayList<Integer>();
//        Session session = sessionFactory.openSession();
//        try {
//            Query userQuery = session
//                    .createQuery("from FarmInfo where account.id=:userId and id in (:farmId)");
//            userQuery.setParameter("userId", userId);
//            userQuery.setParameter("farmId", farmIds);
//            farmInfos = userQuery.list();
//            if (farmInfos.size() > 0) {
//                for (FarmInfo farmInfo : farmInfos)
//                    farmId.add(farmInfo.getId());
//            }
//        } catch (Exception e) {
//            PlantingProfitLogger.error(e);
//            farmInfos = null;
//        } finally {
//            session.close();
//        }
//        return farmId;
//    }*/

    @Override
    public List<Integer> getAllFarmIds(int userId) {
        PlantingProfitLogger.info("inside getAllFarmIds farmIds : ");
        List<FarmInfo> farmInfos = null;
        List<Integer> farmId = new ArrayList<Integer>();
//        Session session = sessionFactory.openSession();
//        try {
//            Query userQuery = session.createQuery("from FarmInfo where account.id=:userId");
//            userQuery.setParameter("userId", userId);
//            farmInfos = userQuery.list();
//            if (farmInfos.size() > 0) {
//                for (FarmInfo farmInfo : farmInfos)
//                    farmId.add(farmInfo.getId());
//            }
//        } catch (Exception e) {
//            PlantingProfitLogger.error(e);
//            farmInfos = null;
//        } finally {
//            session.close();
//        }
        return farmId;
    }

    @Override
    public boolean deleteAllSelectedFarms(String farmIdsString) {
        PlantingProfitLogger.info("inside deleteAllSelectedFarms..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("CALL removeSelectedFarmsAndAllRecord(:farmId)");
            query.setParameter("farmId", farmIdsString);
            query.executeUpdate();

            tx.commit();
            return true;
        } catch (Exception e) {
            assert tx != null;
            tx.rollback();
            PlantingProfitLogger.error(e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public FarmInfo getFarmByFarmIdAndUserId(int farmId, int userId) {
        PlantingProfitLogger.info("inside getFarmByFarmIdAndUserId name : "
                + farmId);
        FarmInfo farmInfo = null;

        Session session = sessionFactory.openSession();
        try {
//            Query userQuery = session.createQuery("from FarmInfo where id=:farmId and account.id=:userId and saveFlag=:saveflag");
//
//            userQuery.setParameter("farmId", farmId);
//            userQuery.setParameter("userId", userId);
//            userQuery.setParameter("saveflag", true);
//            Object object = userQuery.uniqueResult();
//
//            if (object != null && object instanceof FarmInfo) {
//                farmInfo = (FarmInfo) object;
//                PlantingProfitLogger.info("Got Farm with name : " + farmInfo.getFarmName());
//                initializeLazy(farmInfo);
//                return farmInfo;
//            } else {
//                PlantingProfitLogger.info("FarmInfo Object is Null  ");
                return null;
//            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateFarmDetails(int userId, int farmId, String farmName) {
        PlantingProfitLogger.info("inside updateFarmDetails name : " + farmName);
        FarmInfo farmInfo = null;

        Session session = sessionFactory.openSession();
        try {
//            Query userQuery = session.createQuery("from FarmInfo where id=:farmId and account.id=:userId and saveFlag=:saveflag");
//            userQuery.setParameter("farmId", farmId);
//            userQuery.setParameter("userId", userId);
//            userQuery.setParameter("saveflag", true);
//            Object object = userQuery.uniqueResult();
//
//            if (object != null && object instanceof FarmInfo) {
//                farmInfo = (FarmInfo) object;
//                PlantingProfitLogger.info("Got Farm with name : "
//                        + farmInfo.getFarmName() + " New name :" + farmName);
//                try {
//                    farmInfo.setFarmName(farmName);
//                    session.saveOrUpdate(farmInfo);
//                    session.flush();
//                    PlantingProfitLogger.info("Farm name updated successfully");
//                    return true;
//                } catch (Exception e) {
                    return false;
//                }
//            } else {
//                PlantingProfitLogger.info("FarmInfo Object is Null");
//                return false;
//            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public FarmInfo getFarmByIdAfterCheckForAccount(int farmId, int accountId) {
        PlantingProfitLogger.info("inside getView.." + farmId);
        Session session = sessionFactory.openSession();
        FarmInfo info = null;
        try {
            /*Query query = session.createQuery("from FarmInfo where id = :farmId and account.id = :accountId");
            query.setParameter("farmId", farmId);
            query.setParameter("accountId", accountId);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof FarmInfo) {
                    info = (FarmInfo) obj;
                    initializeLazy(info);
                } else
                    info = null;
            } else {
                info = null;
            }*/

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            info = null;
        } finally {
            session.close();
        }
        return info;
    }

    @Override
    public FarmInfo updateFarmInfo(FarmInfo farmInfo, int farmId,
                                   String irrigate_val, boolean evaluate_forward_sales_val,
                                   boolean evaluate_storage_sales_val,
                                   boolean evaluate_crop_insurance_val, String strategy,
                                   String total_land, String[] crop_str,
                                   String[] crop_information_detail, String[] cal_var_cost_crops,
                                   String[] option_crop_info_array,
                                   String[] optional_planting_date_array,
                                   String[] manage_resource_tbody_array,
                                   String[] crop_resource_usage_tbody_array,
                                   String[] crop_limits_table_tbody_array,
                                   String[] forward_sales_information_tbody_array,
                                   String[] plan_by_field_tbody_array,
                                   String[] field_choice_crop_tbody_row_array,
                                   String[] crop_resources_usages_difference_tbody_array,
                                   String field_difference_str, String[] crop_group_array, String additionalCropCostObj) {
        PlantingProfitLogger.info("welcome for updated farm");
        if (!(farmInfo.getStrategy().equals(strategy))) {
            if (strategy.equals("fields")) {
                farmInfo.setStrategy(PlanByStrategy.PLAN_BY_FIELDS);
                farmInfo.setLand(total_land);
            } else if (strategy.equals("acres")) {
                farmInfo.setStrategy(PlanByStrategy.PLAN_BY_ACRES);
                farmInfo.setLand(total_land);
            } else {
                farmInfo.setStrategy(PlanByStrategy.BOTH);
            }
        }


        /**
         * @added - abhishek
         * @date - 13-09-2016
         * @desc - for additional income details for crops
         */
        JSONParser jsonParser = new JSONParser();
        JSONObject additionalCropCostJsonObj = new JSONObject();
        try {
            additionalCropCostJsonObj = (JSONObject) jsonParser.parse(additionalCropCostObj);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }


        if (!(farmInfo.getIrrigate().equals(irrigate_val))) {
            farmInfo.setIrrigate(irrigate_val);
        }
        if (!(farmInfo.getEvaluate_storage() == (evaluate_storage_sales_val))) {
            farmInfo.setEvaluate_storage(evaluate_storage_sales_val);
        }
        if (!(farmInfo.getEvaluate_forward_sales() == (evaluate_forward_sales_val))) {
            farmInfo.setEvaluate_forward_sales(evaluate_forward_sales_val);
        }
        if (!(farmInfo.getEvaluate_crop_insurance() == (evaluate_crop_insurance_val))) {
            farmInfo.setEvaluate_crop_insurance(evaluate_crop_insurance_val);
        }

        List<CropResourceUsage> cropResourceUsages = farmInfo.getCropResourceUsage();
        List<FieldInfo> fieldInfos = farmInfo.getFieldInfos();
        Set<CropType> cropTypes = farmInfo.getCropTypes();
        Set<CropsGroup> cropsGroups = farmInfo.getCropsGroup();
//		Set<FarmCustomStrategy> farmCustomStrategySet = farmInfo.getFarmCustomStrategy();
        Set<Object> removeObject = new HashSet<Object>();
        for (String resourceString : manage_resource_tbody_array) {
            String[] resourceStringArray = resourceString.split("#-#-#");
            boolean flag = false;
            for (CropResourceUsage cropUsage : farmInfo.getCropResourceUsage()) {
                if (resourceStringArray[0].equals(cropUsage.getCropResourceUse())) {
                    cropUsage.setUoMResource(resourceStringArray[1]);
                    cropUsage.setCropResourceUseAmount(resourceStringArray[2]);
                    cropUsage.setResourceActive(resourceStringArray[3].equalsIgnoreCase("active"));
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                CropResourceUsage cropResourceUsage = new CropResourceUsage();
                cropResourceUsage.setCropResourceUse(resourceStringArray[0]);
                cropResourceUsage.setUoMResource(resourceStringArray[1]);
                cropResourceUsage.setCropResourceUseAmount(resourceStringArray[2]);
                cropResourceUsage.setResourceActive(resourceStringArray[3].equalsIgnoreCase("active"));
                cropResourceUsage.setFarmInfo(farmInfo);
                cropResourceUsages.add(cropResourceUsage);
            }
        }
        int size = cropResourceUsages.size();
        for (int i = 0; i < size; i++) {
            boolean flag = false;
            for (String resourceString : manage_resource_tbody_array) {
                if (cropResourceUsages.get(i).getCropResourceUse().equals(resourceString.split("#-#-#")[0])) {
                    flag = true;
                }
            }
            if (!flag) {
//				for (FarmCustomStrategy customStrategy : farmCustomStrategySet) {
//					for (FarmCustomStrategyForResource customStrategyForResourse : customStrategy.getCustomStrategyForResourses()) {
//						if (customStrategyForResourse.getCropResourceUsage().equals(cropResourceUsages.get(i))) {
//							customStrategyForResourse.setCropResourceUsage(null);
//						}
//					}
//				}
                cropResourceUsages.remove(i);
                i--;
                size--;
            }
        }

        if (crop_resources_usages_difference_tbody_array != null) {
            for (String str : crop_resources_usages_difference_tbody_array) {
                String[] array = str.split("#-#-#");
                for (CropResourceUsage cropResourceUsage : cropResourceUsages) {

                    if (cropResourceUsage.getCropResourceUse().equals(array[0]) && cropResourceUsage.getDifferences() != null) {
                        CropResourceUsageFieldDifferences cropResourceUsageFieldDifferences = cropResourceUsage.getDifferences();
                        cropResourceUsageFieldDifferences.setResourseOverrideAmount(array[1]);
                        cropResourceUsageFieldDifferences.setCropResourceUsage(cropResourceUsage);
                        cropResourceUsage.setDifferences(cropResourceUsageFieldDifferences);
                    } else if (cropResourceUsage.getCropResourceUse().equals(array[0]) && cropResourceUsage.getDifferences() == null) {
                        CropResourceUsageFieldDifferences cropResourceUsageFieldDifferences = new CropResourceUsageFieldDifferences();
                        cropResourceUsageFieldDifferences.setResourseOverrideAmount(array[1]);
                        cropResourceUsageFieldDifferences.setCropResourceUsage(cropResourceUsage);
                        cropResourceUsage.setDifferences(cropResourceUsageFieldDifferences);
                    }
                }
            }
        }
        if (plan_by_field_tbody_array != null && plan_by_field_tbody_array.length > 0) {
            for (String palne_fields : plan_by_field_tbody_array) {
                String[] resourse_array = palne_fields.split("#-#-#");
                boolean flag = false;
                for (FieldInfo fieldInfo : fieldInfos) {
                    if (resourse_array[0].equals(fieldInfo.getFieldName())) {
                        fieldInfo.setFieldName(resourse_array[0]);
                        fieldInfo.setFieldSize(resourse_array[1]);
                        fieldInfo.setLastCrop(resourse_array[2]);
                        fieldInfo.setFallow(resourse_array[3]);
                        fieldInfo.setDivide(resourse_array[4]);
                        fieldInfo.setIrrigate(resourse_array[5]);
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfo.setFieldName(resourse_array[0]);
                    fieldInfo.setFieldSize(resourse_array[1]);
                    fieldInfo.setLastCrop(resourse_array[2]);
                    fieldInfo.setFallow(resourse_array[3]);
                    fieldInfo.setDivide(resourse_array[4]);
                    fieldInfo.setIrrigate(resourse_array[5]);
                    fieldInfos.add(fieldInfo);
                }
            }
        }
        int fieldSize = fieldInfos.size();
        for (int i = 0; i < fieldSize; i++) {
            boolean flag = false;
            for (String palne_fields : plan_by_field_tbody_array) {
                if (palne_fields.split("#-#-#")[0].equals(fieldInfos.get(i).getFieldName())) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                for (CropFieldChocies cropFieldChocies : fieldInfos.get(i).getChocies()) {
                    cropFieldChocies.getCropType().getChocies().remove(cropFieldChocies);
                    cropFieldChocies.setCropType(null);
                    cropFieldChocies.setName(null);
                }
                fieldInfos.remove(i);
                fieldSize--;
                i--;
            }
        }

        for (String crop : crop_str) {
            String[] cropArray = crop.split("#-#-#");
            boolean flag = false;
            for (CropType cropType : cropTypes) {
                if (cropType.getCropName().equals(cropArray[0])) {
                    if (cropType.getSelected() && cropArray[1].equals("true")) {
                        PlantingProfitLogger.info("Previous crop name : " + cropArray[0]);
                        cropType.setCropName(cropArray[0]);
                        cropType.setCropTypeName((cropArray[2].equals("vegitable")) ? "Vegitable_Crop" : "Field_Crop");
                        cropType.setSelected(cropArray[1].equals("true"));
                        Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariances = cropType.getCropResourceUsageFieldVariances();
                        for (String str : crop_information_detail) {
                            String[] array = str.split("#-#-#");
                            if (array[0].equals(cropType.getCropName())) {
                                CropUnitOfMeasure cropUnitOfMeasure = cropType.getCropUnitOfMeasure();
                                cropUnitOfMeasure.setCropType(cropType);
                                cropUnitOfMeasure.setUnitOfMeasure(array[1]);
                                InternalCropYieldInfo cropYieldInfo = cropType.getCropYieldInfo();
                                cropYieldInfo.setCropType(cropType);
                                cropYieldInfo.setIntExpCropYield((array[2].equals("")) ? "" : array[2]);
                                cropYieldInfo.setIntMinCropYield((array[3].equals("")) ? "" : array[3]);
                                cropYieldInfo.setIntMaxCropYield((array[4].equals("")) ? "" : array[4]);
                                cropYieldInfo.setCropType(cropType);
                                InternalCropPricesInfo cropPricesInfo = cropType.getCropPricesInfo();
                                cropPricesInfo.setIntExpCropPrice(new BigDecimal((array[5].equals("")) ? "0" : array[5]));
                                cropPricesInfo.setIntMinCropPrice(new BigDecimal((array[6].equals("")) ? "0" : array[6]));
                                cropPricesInfo.setIntMaxCropPrice(new BigDecimal((array[7].equals("")) ? "0" : array[7]));
                                InternalVariableCropProductionCosts costsCropProductionCosts = cropType.getCostsCropProductionCosts();
                                costsCropProductionCosts.setCropType(cropType);
                                costsCropProductionCosts.setCalculatedVariableProductionCost(new BigDecimal((array[8].equals("")) ? "0" : array[8]));
                            }
                        }
                        if (crop_limits_table_tbody_array != null) {
                            for (String str : crop_limits_table_tbody_array) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    CropLimit cropLimit = cropType.getCropLimit();
                                    cropLimit.setCropType(cropType);
                                    cropLimit.setMinimumAcres((array[1].equals("0")) ? "0" : array[1]);
                                    cropLimit.setMaximumAcres((array[2].equals("0")) ? "0" : array[2]);
                                }
                            }
                        }
                        if (crop_resource_usage_tbody_array != null) {
                            for (String str : crop_resource_usage_tbody_array) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    boolean flag1 = false;
                                    for (CropResourceUsageFieldVariances cropResourceUsageFieldVariance : cropResourceUsageFieldVariances) {
                                        if (cropResourceUsageFieldVariance.getCropFieldResourceUse().equals(array[2])) {
                                            cropResourceUsageFieldVariance.setCropType(cropType);
                                            cropResourceUsageFieldVariance.setCropFieldResourceUse(array[2]);
                                            cropResourceUsageFieldVariance.setCropResourceAmount(array[3]);
                                            cropResourceUsageFieldVariances.add(cropResourceUsageFieldVariance);
                                            flag1 = true;
                                            break;
                                        }
                                    }
                                    if (flag1 == false) {
                                        CropResourceUsageFieldVariances cropResourceUsageFieldVariance = new CropResourceUsageFieldVariances();
                                        cropResourceUsageFieldVariance.setCropType(cropType);
                                        cropResourceUsageFieldVariance.setCropFieldResourceUse(array[2]);
                                        cropResourceUsageFieldVariance.setCropResourceAmount(array[3]);
                                        cropResourceUsageFieldVariances.add(cropResourceUsageFieldVariance);
                                    }
                                }
                            }
                        }

                        Set<CropFieldChocies> chocies = cropType.getChocies();
                        if (field_choice_crop_tbody_row_array != null && field_choice_crop_tbody_row_array.length > 0) {
                            for (String str : field_choice_crop_tbody_row_array) {
                                String[] array = str.split("#-#-#");
                                if (array[1].equals(cropType.getCropName())) {
                                    boolean flag1 = false;

                                    for (CropFieldChocies cropFieldChocies : chocies) {
                                        if (cropFieldChocies.getName().getFieldName().equals(array[0])) {
                                            cropFieldChocies.setCropType(cropType);
                                            cropFieldChocies.setCropFieldChoice(array[2]);
                                            for (FieldInfo fieldInfo : fieldInfos) {
                                                if (fieldInfo.getFieldName().equals(array[0])) {
                                                    cropFieldChocies.setName(fieldInfo);
                                                    break;
                                                }
                                            }
                                            flag1 = true;
                                            break;
                                        }
                                    }
                                    if (flag1 == false) {
                                        CropFieldChocies cropFieldChocies = new CropFieldChocies();
                                        cropFieldChocies.setCropType(cropType);
                                        cropFieldChocies.setCropFieldChoice(array[2]);
                                        for (FieldInfo fieldInfo : fieldInfos) {
                                            if (fieldInfo.getFieldName().equals(array[0])) {
                                                cropFieldChocies.setName(fieldInfo);
                                                break;
                                            }
                                        }
                                        chocies.add(cropFieldChocies);
                                    }
                                }
                            }
                        }
                        if (forward_sales_information_tbody_array != null) {
                            for (String str : forward_sales_information_tbody_array) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    CropForwardSales cropForwardSales = cropType.getCropForwardSales();
                                    if (cropForwardSales == null){
                                        cropForwardSales = new CropForwardSales();
                                    }
                                    cropForwardSales.setPrice(array[1]);
                                    cropForwardSales.setQuantity(array[2]);
                                    cropForwardSales.setFirmchecked(array[3]);
                                    cropForwardSales.setProposedchecked(array[7].equalsIgnoreCase("true") ? true : false);
                                    cropForwardSales.setAcres(new Double(array[4]));
                                    cropForwardSales.setUpperLimit(new Double(array[5]));
                                    cropForwardSales.setContactIdentifier(array[6]);
                                    cropForwardSales.setCropType(cropType);
                                    break;
                                }
                            }
                        }

                        if (option_crop_info_array != null) {
                            for (String str : option_crop_info_array) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    SummaryCropInfo cropInfo = cropType.getCropInfo();
                                    cropInfo.setIrrigated(array[1]);
                                    cropInfo.setConservation_Crop(array[2]);
                                    cropInfo.setHiRiskCrop(array[3]);
                                    cropInfo.setCropType(cropType);
                                    break;
                                }
                            }
                        }
                        if (optional_planting_date_array != null) {
                            for (String str : optional_planting_date_array) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    OptionalCropPlantingDates cropPlantingDates = cropType.getCropPlantingDates();
                                    try {
                                        cropPlantingDates.setPreferredPlantingDate(FORMATTER.parse(array[1]));
                                        cropPlantingDates.setEarlyPlantingDate(FORMATTER.parse(array[2]));
                                        cropPlantingDates.setLatePlantingDate(FORMATTER.parse(array[3]));
                                    } catch (ParseException e) {
                                        PlantingProfitLogger.error(e);
                                    }
                                    cropPlantingDates.setLengthofSeason(Integer.parseInt(array[4]));
                                    cropPlantingDates.setCropType(cropType);
                                    break;
                                }
                            }
                        }
                        Set<OptionalCropProductionCostsDetails> optionalCropProductionCostsDetailsSet = cropType.getOptCropCost();
                        if (cal_var_cost_crops != null) {
                            for (String str : cal_var_cost_crops) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    boolean flag1 = false;
                                    for (OptionalCropProductionCostsDetails optionalCropProductionCostsDetails : optionalCropProductionCostsDetailsSet) {
                                        if (optionalCropProductionCostsDetails.getCostComponentsName().equals(array[1])) {
                                            optionalCropProductionCostsDetails.setCropType(cropType);
                                            optionalCropProductionCostsDetails.setCostComponentsName(array[1]);
                                            optionalCropProductionCostsDetails.setUnitsPerAcre(new BigDecimal(array[2]));
                                            optionalCropProductionCostsDetails.setUnitPrices(new BigDecimal(array[3]));
                                            optionalCropProductionCostsDetails.setUnitTotal(new BigDecimal(array[4]));
                                            optionalCropProductionCostsDetailsSet.add(optionalCropProductionCostsDetails);
                                            flag1 = true;
                                        }
                                    }
                                    if (flag1 == false) {
                                        OptionalCropProductionCostsDetails optionalCropProductionCostsDetails = new OptionalCropProductionCostsDetails();
                                        optionalCropProductionCostsDetails.setCropType(cropType);
                                        optionalCropProductionCostsDetails.setCostComponentsName(array[1]);
                                        optionalCropProductionCostsDetails.setUnitsPerAcre(new BigDecimal(array[2]));
                                        optionalCropProductionCostsDetails.setUnitPrices(new BigDecimal(array[3]));
                                        optionalCropProductionCostsDetails.setUnitTotal(new BigDecimal(array[4]));
                                        optionalCropProductionCostsDetailsSet.add(optionalCropProductionCostsDetails);
                                    }
                                }
                            }
                        }
                        if (optionalCropProductionCostsDetailsSet != null) {
                            Set<OptionalCropProductionCostsDetails> costsDetails = new HashSet<OptionalCropProductionCostsDetails>();
                            for (OptionalCropProductionCostsDetails optionalCropProductionCostsDetails : optionalCropProductionCostsDetailsSet) {
                                boolean flagcheck = false;
                                for (String str : cal_var_cost_crops) {
                                    String[] array = str.split("#-#-#");
                                    if (array[0].equals(cropType.getCropName())) {
                                        if (optionalCropProductionCostsDetails.getCostComponentsName().equals(array[1])) {
                                            flagcheck = true;
                                            break;
                                        }
                                    }
                                }
                                if (flagcheck == false) {
                                    optionalCropProductionCostsDetails.setCropType(null);
                                    costsDetails.add(optionalCropProductionCostsDetails);

                                }
                            }
                            optionalCropProductionCostsDetailsSet.removeAll(costsDetails);
                        }

                        if (field_difference_str != null && !field_difference_str.equals("")) {
                            String[] array = field_difference_str.split("#-#-#");
                            if (array[1].equals(cropType.getCropName())) {
                                CropYieldFieldVariances cropYieldFieldVariances = cropType.getCropYieldFieldVariances();
                                if (cropType.getCropYieldFieldVariances() == null) {
                                    cropYieldFieldVariances = new CropYieldFieldVariances();
                                    for (FieldInfo fieldInfo : fieldInfos) {
                                        if (fieldInfo.getFieldName().equals(array[0])) {
                                            cropYieldFieldVariances.setFieldInfo(fieldInfo);
                                            break;
                                        }
                                    }
                                    cropYieldFieldVariances.setCropType(cropType);
                                    cropYieldFieldVariances.setExpCropYieldField(new Double(array[2]));
                                    cropYieldFieldVariances.setMinCropYieldField(new Double(array[3]));
                                    cropYieldFieldVariances.setMaxCropYieldField(new Double(array[4]));
                                    cropYieldFieldVariances.setVarProductionCost(new Double(array[5]));
                                    cropType.setCropYieldFieldVariances(cropYieldFieldVariances);
                                } else {
                                    for (FieldInfo fieldInfo : fieldInfos) {
                                        if (fieldInfo.getFieldName().equals(array[0])) {
                                            cropYieldFieldVariances.setFieldInfo(fieldInfo);
                                            break;
                                        }
                                    }
                                    cropYieldFieldVariances.setCropType(cropType);
                                    cropYieldFieldVariances.setExpCropYieldField(new Double(array[2]));
                                    cropYieldFieldVariances.setMinCropYieldField(new Double(array[3]));
                                    cropYieldFieldVariances.setMaxCropYieldField(new Double(array[4]));
                                    cropYieldFieldVariances.setVarProductionCost(new Double(array[5]));
                                }
                            }
                        }
                        if (cropType.getCropYieldFieldVariances() != null) {
                            CropYieldFieldVariances cropYieldFieldVariances = cropType.getCropYieldFieldVariances();

                            String[] array = field_difference_str
                                    .split("#-#-#");
                            if (field_difference_str == null || field_difference_str.equals("")) {
                                cropYieldFieldVariances.getCropType().setCropYieldFieldVariances(null);
                                cropYieldFieldVariances.getFieldInfo().setCropYieldFieldVariances(null);
                                cropYieldFieldVariances.setCropType(null);
                                cropYieldFieldVariances.setFieldInfo(null);
                                removeObject.add(cropYieldFieldVariances);
                            } else {
                                if (array[1].equals(cropYieldFieldVariances.getCropType().getCropName()) && array[0].equals(cropYieldFieldVariances.getFieldInfo().getFieldName())) {
                                } else {
                                    cropYieldFieldVariances.getCropType().setCropYieldFieldVariances(null);
                                    cropYieldFieldVariances.getFieldInfo().setCropYieldFieldVariances(null);
                                    cropYieldFieldVariances.setCropType(null);
                                    cropYieldFieldVariances.setFieldInfo(null);
                                    removeObject.add(cropYieldFieldVariances);
                                }
                            }
                        }
                    } else if (cropType.getSelected() && !cropArray[1].equals("true")) {
                        PlantingProfitLogger.info("remove crop" + cropArray[0]);
                        cropType.setCropName(cropArray[0]);
                        cropType.setCropTypeName((cropArray[2].equals("vegitable")) ? "Vegitable_Crop" : "Field_Crop");
                        cropType.setSelected((cropArray[1].equals("true")) ? true : false);

                        CropLimit cropLimit = cropType.getCropLimit();
                        cropType.setCropLimit(null);
                        cropLimit.setCropType(null);
                        removeObject.add(cropLimit);
                        CropUnitOfMeasure cropUnitOfMeasure = cropType.getCropUnitOfMeasure();
                        cropUnitOfMeasure.setCropType(null);
                        cropType.setCropUnitOfMeasure(null);
                        removeObject.add(cropUnitOfMeasure);

                        InternalCropYieldInfo internalCropYieldInfo = cropType.getCropYieldInfo();
                        internalCropYieldInfo.setCropType(null);
                        cropType.setCropYieldInfo(null);
                        removeObject.add(internalCropYieldInfo);

                        InternalCropPricesInfo internalCropPricesInfo = cropType.getCropPricesInfo();
                        internalCropPricesInfo.setCropType(null);
                        cropType.setCropPricesInfo(null);
                        removeObject.add(internalCropPricesInfo);

                        InternalVariableCropProductionCosts inCropProductionCosts = cropType.getCostsCropProductionCosts();
                        inCropProductionCosts.setCropType(null);
                        cropType.setCostsCropProductionCosts(null);
                        removeObject.add(inCropProductionCosts);

                        for (CropResourceUsageFieldVariances cropResourceUsageFieldVariance : cropType.getCropResourceUsageFieldVariances()) {
                            cropResourceUsageFieldVariance.setCropType(null);
                            cropType.setCropResourceUsageFieldVariances(null);
                            removeObject.add(cropResourceUsageFieldVariance);
                        }

                        Set<CropFieldChocies> chocies1 = new HashSet<CropFieldChocies>();
                        for (CropFieldChocies cropFieldChocies : cropType.getChocies()) {
                            cropFieldChocies.setCropType(null);
                            cropFieldChocies.getName().getChocies().remove(cropFieldChocies);
                            cropFieldChocies.setName(null);
                            chocies1.add(cropFieldChocies);
                        }
                        cropType.getChocies().removeAll(chocies1);

                        CropForwardSales cropForwardSales = cropType.getCropForwardSales();
                        cropForwardSales.setCropType(null);
                        cropType.setCropForwardSales(null);
                        removeObject.add(cropForwardSales);

                        SummaryCropInfo summaryCropInfo = cropType.getCropInfo();
                        summaryCropInfo.setCropType(null);
                        cropType.setCropInfo(null);
                        removeObject.add(summaryCropInfo);

                        OptionalCropPlantingDates optionalCropPlantingDates = cropType.getCropPlantingDates();
                        optionalCropPlantingDates.setCropType(null);
                        cropType.setCropPlantingDates(null);
                        removeObject.add(optionalCropPlantingDates);

                        for (OptionalCropProductionCostsDetails optionalCropProductionCostsDetails : cropType.getOptCropCost()) {
                            optionalCropProductionCostsDetails.setCropType(null);
                            cropType.setOptCropCost(null);
                            removeObject.add(optionalCropProductionCostsDetails);
                        }

                        CropYieldFieldVariances cropYieldFieldVariances = cropType.getCropYieldFieldVariances();
                        if (cropYieldFieldVariances != null) {
                            cropYieldFieldVariances.setCropType(null);
                            cropYieldFieldVariances.getFieldInfo().setCropYieldFieldVariances(null);
                            cropYieldFieldVariances.setFieldInfo(null);
                            cropType.setCropYieldFieldVariances(null);
                            removeObject.add(cropYieldFieldVariances);
                        }
                    } else if (!cropType.getSelected() && cropArray[1].equals("true")) {
                        PlantingProfitLogger.info("new crop selected " + cropArray[0]);
                        cropType.setCropName(cropArray[0]);
                        cropType.setCropTypeName((cropArray[2].equals("vegitable")) ? "Vegitable_Crop" : "Field_Crop");
                        cropType.setSelected((cropArray[1].equals("true")) ? true : false);
                        Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariances = new HashSet<CropResourceUsageFieldVariances>();
                        for (String str : crop_information_detail) {
                            String[] array = str.split("#-#-#");
                            if (array[0].equals(cropType.getCropName())) {
                                CropUnitOfMeasure cropUnitOfMeasure = new CropUnitOfMeasure();
                                InternalCropYieldInfo internalCropYieldInfo = new InternalCropYieldInfo();
                                InternalVariableCropProductionCosts internalVariableCropProductionCosts = new InternalVariableCropProductionCosts();
                                InternalCropPricesInfo internalCropPricesInfo = new InternalCropPricesInfo();
                                cropUnitOfMeasure.setCropType(cropType);
                                cropUnitOfMeasure.setUnitOfMeasure(array[1]);
                                internalCropYieldInfo.setCropType(cropType);
                                internalCropYieldInfo.setIntExpCropYield((array[2].equals("")) ? "" : array[2]);
                                internalCropYieldInfo.setIntMinCropYield((array[3].equals("")) ? "" : array[3]);
                                internalCropYieldInfo.setIntMaxCropYield((array[4].equals("")) ? "" : array[4]);
                                internalCropPricesInfo.setCropType(cropType);
                                internalCropPricesInfo.setIntExpCropPrice(new BigDecimal((array[5].equals("")) ? "0" : array[5]));
                                internalCropPricesInfo.setIntMinCropPrice(new BigDecimal((array[6].equals("")) ? "0" : array[6]));
                                internalCropPricesInfo.setIntMaxCropPrice(new BigDecimal((array[7].equals("")) ? "0" : array[7]));
                                internalVariableCropProductionCosts.setCropType(cropType);
                                internalVariableCropProductionCosts.setCalculatedVariableProductionCost(new BigDecimal((array[8].equals("")) ? "0" : array[8]));
                                cropType.setCropUnitOfMeasure(cropUnitOfMeasure);
                                cropType.setCropYieldInfo(internalCropYieldInfo);
                                cropType.setCropPricesInfo(internalCropPricesInfo);
                                cropType.setCostsCropProductionCosts(internalVariableCropProductionCosts);
                            }
                        }

                        if (crop_limits_table_tbody_array != null) {
                            for (String str : crop_limits_table_tbody_array) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    CropLimit cropLimit = new CropLimit();
                                    cropLimit.setCropType(cropType);
                                    cropLimit.setMinimumAcres((array[1].equals("0")) ? "0" : array[1]);
                                    cropLimit.setMaximumAcres((array[2].equals("0")) ? "0" : array[2]);
                                    cropType.setCropLimit(cropLimit);
                                }
                            }
                        }
                        if (crop_resource_usage_tbody_array != null) {
                            for (String str : crop_resource_usage_tbody_array) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {

                                    CropResourceUsageFieldVariances cropResourceUsageFieldVariance = new CropResourceUsageFieldVariances();
                                    cropResourceUsageFieldVariance.setCropType(cropType);
                                    cropResourceUsageFieldVariance.setCropFieldResourceUse(array[2]);
                                    cropResourceUsageFieldVariance.setCropResourceAmount(array[3]);
                                    cropResourceUsageFieldVariances.add(cropResourceUsageFieldVariance);
                                    cropType.setCropResourceUsageFieldVariances(cropResourceUsageFieldVariances);
                                }
                            }
                        }
                        if (field_choice_crop_tbody_row_array != null
                                && field_choice_crop_tbody_row_array.length > 0) {
                            for (String str : field_choice_crop_tbody_row_array) {
                                String[] array = str.split("#-#-#");
                                if (array[1].equals(cropType.getCropName())) {
                                    CropFieldChocies cropFieldChocies = new CropFieldChocies();
                                    cropFieldChocies.setCropType(cropType);
                                    cropFieldChocies.setCropFieldChoice(array[2]);
                                    for (FieldInfo fieldInfo : farmInfo.getFieldInfos()) {
                                        if (fieldInfo.getFieldName().equals(array[0])) {
                                            cropFieldChocies.setName(fieldInfo);
                                            fieldInfo.getChocies().add(cropFieldChocies);
                                            break;
                                        }
                                    }
                                    cropType.getChocies().add(cropFieldChocies);
                                }
                            }
                        }
                        if (forward_sales_information_tbody_array != null) {
                            for (String str : forward_sales_information_tbody_array) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    CropForwardSales cropForwardSales = new CropForwardSales();
                                    cropForwardSales.setPrice(array[1]);
                                    cropForwardSales.setQuantity(array[2]);
                                    cropForwardSales.setFirmchecked(array[3]);
                                    cropForwardSales.setProposedchecked(array[7].equals("true") ? true : false);
                                    cropForwardSales.setAcres(new Double(array[4]));
                                    cropForwardSales.setUpperLimit(new Double(array[5]));
                                    cropForwardSales.setContactIdentifier(array[6]);
                                    cropForwardSales.setCropType(cropType);
                                    cropType.setCropForwardSales(cropForwardSales);
                                    break;
                                }
                            }
                        }

                        if (option_crop_info_array != null) {
                            for (String str : option_crop_info_array) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    SummaryCropInfo summaryCropInfo = new SummaryCropInfo();
                                    summaryCropInfo.setIrrigated(array[1]);
                                    summaryCropInfo.setConservation_Crop(array[2]);
                                    summaryCropInfo.setHiRiskCrop(array[3]);
                                    summaryCropInfo.setCropType(cropType);
                                    cropType.setCropInfo(summaryCropInfo);
                                    break;
                                }
                            }
                        }
                        if (optional_planting_date_array != null) {
                            for (String str : optional_planting_date_array) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    OptionalCropPlantingDates optionalCropPlantingDates = new OptionalCropPlantingDates();
                                    try {

                                        optionalCropPlantingDates.setPreferredPlantingDate(FORMATTER.parse(array[1]));
                                        optionalCropPlantingDates.setEarlyPlantingDate(FORMATTER.parse(array[2]));
                                        optionalCropPlantingDates.setLatePlantingDate(FORMATTER.parse(array[3]));
                                    } catch (ParseException e) {
                                        PlantingProfitLogger.error(e);
                                    }
                                    optionalCropPlantingDates.setLengthofSeason(Integer.parseInt(array[4]));
                                    optionalCropPlantingDates.setCropType(cropType);
                                    cropType.setCropPlantingDates(optionalCropPlantingDates);
                                    break;
                                }
                            }
                        }
                        if (cal_var_cost_crops != null) {
                            for (String str : cal_var_cost_crops) {
                                String[] array = str.split("#-#-#");
                                if (array[0].equals(cropType.getCropName())) {
                                    OptionalCropProductionCostsDetails optionalCropProductionCostsDetails = new OptionalCropProductionCostsDetails();
                                    optionalCropProductionCostsDetails.setCropType(cropType);
                                    optionalCropProductionCostsDetails.setCostComponentsName(array[1]);
                                    optionalCropProductionCostsDetails.setUnitsPerAcre(new BigDecimal(array[2]));
                                    optionalCropProductionCostsDetails.setUnitPrices(new BigDecimal(array[3]));
                                    optionalCropProductionCostsDetails.setUnitTotal(new BigDecimal(array[4]));
                                    cropType.getOptCropCost().add(optionalCropProductionCostsDetails);
                                }
                            }
                        }
                        if (field_difference_str != null && !field_difference_str.equals("")) {
                            String[] array = field_difference_str.split("#-#-#");
                            if (array[1].equals(cropType.getCropName())) {
                                CropYieldFieldVariances cropYieldFieldVariances = new CropYieldFieldVariances();
                                for (FieldInfo fieldInfo : fieldInfos) {
                                    if (fieldInfo.getFieldName().equals(array[0])) {
                                        cropYieldFieldVariances.setFieldInfo(fieldInfo);
                                        break;
                                    }
                                }
                                cropYieldFieldVariances.setCropType(cropType);
                                cropYieldFieldVariances.setExpCropYieldField(new Double(array[2]));
                                cropYieldFieldVariances.setMinCropYieldField(new Double(array[3]));
                                cropYieldFieldVariances.setMaxCropYieldField(new Double(array[4]));
                                cropYieldFieldVariances.setVarProductionCost(new Double(array[5]));
                                cropType.setCropYieldFieldVariances(cropYieldFieldVariances);
                            }
                        }
                    }
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                PlantingProfitLogger.info("new crop Added" + cropArray[0]);
                CropType cropType = new CropType();
                cropType.setCropName(cropArray[0]);
                cropType.setCropTypeName((cropArray[2].equals("vegitable")) ? "Vegitable_Crop" : "Field_Crop");
                cropType.setSelected((cropArray[1].equals("true")) ? true : false);
                Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariances = new HashSet<CropResourceUsageFieldVariances>();
                if (cropType.getSelected()) {
                    for (String str : crop_information_detail) {
                        String[] array = str.split("#-#-#");
                        if (array[0].equals(cropType.getCropName())) {
                            CropUnitOfMeasure cropUnitOfMeasure = new CropUnitOfMeasure();
                            InternalCropYieldInfo internalCropYieldInfo = new InternalCropYieldInfo();
                            InternalVariableCropProductionCosts internalVariableCropProductionCosts = new InternalVariableCropProductionCosts();
                            InternalCropPricesInfo internalCropPricesInfo = new InternalCropPricesInfo();
                            cropUnitOfMeasure.setCropType(cropType);
                            cropUnitOfMeasure.setUnitOfMeasure(array[1]);
                            internalCropYieldInfo.setCropType(cropType);
                            internalCropYieldInfo.setIntExpCropYield((array[2].equals("")) ? "" : array[2]);
                            internalCropYieldInfo.setIntMinCropYield((array[3].equals("")) ? "" : array[3]);
                            internalCropYieldInfo.setIntMaxCropYield((array[4].equals("")) ? "" : array[4]);
                            internalCropPricesInfo.setCropType(cropType);
                            internalCropPricesInfo.setIntExpCropPrice(new BigDecimal((array[5].equals("")) ? "0" : array[5]));
                            internalCropPricesInfo.setIntMinCropPrice(new BigDecimal((array[6].equals("")) ? "0" : array[6]));
                            internalCropPricesInfo.setIntMaxCropPrice(new BigDecimal((array[7].equals("")) ? "0" : array[7]));
                            internalVariableCropProductionCosts.setCropType(cropType);
                            internalVariableCropProductionCosts.setCalculatedVariableProductionCost(new BigDecimal((array[8].equals("")) ? "0" : array[8]));
                            cropType.setCropUnitOfMeasure(cropUnitOfMeasure);
                            cropType.setCropYieldInfo(internalCropYieldInfo);
                            cropType.setCropPricesInfo(internalCropPricesInfo);
                            cropType.setCostsCropProductionCosts(internalVariableCropProductionCosts);
                        }
                    }
                    if (crop_limits_table_tbody_array != null) {
                        for (String str : crop_limits_table_tbody_array) {
                            String[] array = str.split("#-#-#");
                            if (array[0].equals(cropType.getCropName())) {
                                CropLimit cropLimit = new CropLimit();
                                cropLimit.setCropType(cropType);
                                cropLimit.setMinimumAcres((array[1].equals("0")) ? "0" : array[1]);
                                cropLimit.setMaximumAcres((array[2].equals("0")) ? "0" : array[2]);
                                cropType.setCropLimit(cropLimit);
                            }
                        }
                    }
                    if (crop_resource_usage_tbody_array != null) {

                        for (String str : crop_resource_usage_tbody_array) {
                            String[] array = str.split("#-#-#");
                            if (array[0].equals(cropType.getCropName())) {

                                CropResourceUsageFieldVariances cropResourceUsageFieldVariance = new CropResourceUsageFieldVariances();
                                cropResourceUsageFieldVariance.setCropType(cropType);
                                cropResourceUsageFieldVariance.setCropFieldResourceUse(array[2]);
                                cropResourceUsageFieldVariance.setCropResourceAmount(array[3]);
                                cropResourceUsageFieldVariances.add(cropResourceUsageFieldVariance);
                                cropType.setCropResourceUsageFieldVariances(cropResourceUsageFieldVariances);
                            }
                        }
                    }
                    Set<CropFieldChocies> chocies = new HashSet<CropFieldChocies>();
                    if (field_choice_crop_tbody_row_array != null && field_choice_crop_tbody_row_array.length > 0) {
                        for (String str : field_choice_crop_tbody_row_array) {
                            String[] array = str.split("#-#-#");
                            if (array[1].equals(cropType.getCropName())) {
                                CropFieldChocies cropFieldChocies = new CropFieldChocies();
                                cropFieldChocies.setCropType(cropType);
                                cropFieldChocies.setCropFieldChoice(array[2]);
                                for (FieldInfo fieldInfo : fieldInfos) {
                                    if (fieldInfo.getFieldName().equals(array[0])) {
                                        cropFieldChocies.setName(fieldInfo);
                                        fieldInfo.getChocies().add(cropFieldChocies);
                                        break;
                                    }
                                }
                                chocies.add(cropFieldChocies);
                                cropType.setChocies(chocies);
                            }
                        }
                    }

                    if (forward_sales_information_tbody_array != null) {
                        for (String str : forward_sales_information_tbody_array) {
                            String[] array = str.split("#-#-#");
                            if (array[0].equals(cropType.getCropName())) {
                                CropForwardSales cropForwardSales = new CropForwardSales();
                                cropForwardSales.setPrice(array[1]);
                                cropForwardSales.setQuantity(array[2]);
                                cropForwardSales.setFirmchecked(array[3]);
                                cropForwardSales.setProposedchecked(array[7].equals("true") ? true : false);
                                cropForwardSales.setAcres(new Double(array[4]));
                                cropForwardSales.setUpperLimit(new Double(array[5]));
                                cropForwardSales.setContactIdentifier(array[6]);
                                cropForwardSales.setCropType(cropType);
                                cropType.setCropForwardSales(cropForwardSales);
                                break;
                            }
                        }
                    }
                    if (option_crop_info_array != null) {
                        for (String str : option_crop_info_array) {
                            String[] array = str.split("#-#-#");
                            if (array[0].equals(cropType.getCropName())) {
                                SummaryCropInfo summaryCropInfo = new SummaryCropInfo();
                                summaryCropInfo.setIrrigated(array[1]);
                                summaryCropInfo.setConservation_Crop(array[2]);
                                summaryCropInfo.setHiRiskCrop(array[3]);
                                summaryCropInfo.setCropType(cropType);
                                cropType.setCropInfo(summaryCropInfo);
                                break;
                            }
                        }
                    }
                    if (optional_planting_date_array != null) {
                        for (String str : optional_planting_date_array) {
                            String[] array = str.split("#-#-#");
                            if (array[0].equals(cropType.getCropName())) {
                                OptionalCropPlantingDates optionalCropPlantingDates = new OptionalCropPlantingDates();
                                try {
                                    optionalCropPlantingDates.setPreferredPlantingDate(FORMATTER.parse(array[1]));
                                    optionalCropPlantingDates.setEarlyPlantingDate(FORMATTER.parse(array[2]));
                                    optionalCropPlantingDates.setLatePlantingDate(FORMATTER.parse(array[3]));
                                } catch (ParseException e) {
                                    PlantingProfitLogger.error(e);
                                }
                                optionalCropPlantingDates.setLengthofSeason(Integer.parseInt(array[4]));
                                optionalCropPlantingDates.setCropType(cropType);
                                cropType.setCropPlantingDates(optionalCropPlantingDates);
                                break;
                            }
                        }
                    }
                    if (cal_var_cost_crops != null) {
                        for (String str : cal_var_cost_crops) {
                            String[] array = str.split("#-#-#");
                            if (array[0].equals(cropType.getCropName())) {
                                OptionalCropProductionCostsDetails optionalCropProductionCostsDetails = new OptionalCropProductionCostsDetails();
                                optionalCropProductionCostsDetails.setCropType(cropType);
                                optionalCropProductionCostsDetails.setCostComponentsName(array[1]);
                                optionalCropProductionCostsDetails.setUnitsPerAcre(new BigDecimal(array[2]));
                                optionalCropProductionCostsDetails.setUnitPrices(new BigDecimal(array[3]));
                                optionalCropProductionCostsDetails.setUnitTotal(new BigDecimal(array[4]));
                                cropType.getOptCropCost().add(optionalCropProductionCostsDetails);
                            }
                        }
                    }
                    if (field_difference_str != null && !field_difference_str.equals("")) {
                        String[] array = field_difference_str.split("#-#-#");
                        if (array[1].equals(cropType.getCropName())) {
                            CropYieldFieldVariances cropYieldFieldVariances = new CropYieldFieldVariances();
                            for (FieldInfo fieldInfo : fieldInfos) {
                                if (fieldInfo.getFieldName().equals(array[0])) {
                                    cropYieldFieldVariances.setFieldInfo(fieldInfo);
                                    break;
                                }
                            }
                            cropYieldFieldVariances.setCropType(cropType);
                            cropYieldFieldVariances.setExpCropYieldField(new Double(array[2]));
                            cropYieldFieldVariances.setMinCropYieldField(new Double(array[3]));
                            cropYieldFieldVariances.setMaxCropYieldField(new Double(array[4]));
                            cropYieldFieldVariances.setVarProductionCost(new Double(array[5]));
                            cropType.setCropYieldFieldVariances(cropYieldFieldVariances);
                        }
                    }
                }
                cropTypes.add(cropType);
            }
        }

        for (CropsGroup cropsGroup : farmInfo.getCropsGroup()) {
            boolean flag = false;
            if (crop_group_array != null) {
                for (String str : crop_group_array) {
                    String[] array = str.split("#-#-#");
                    if (cropsGroup.getCropsGroupName().equals(array[0])) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag == false) {
                cropsGroup.setFarmInfo(null);
                cropsGroup.setCropTypes(null);
                farmInfo.getCropsGroup().remove(cropsGroup);
                removeObject.add(cropsGroup);
            }
        }
        List<CropType> cropTypes2 = new ArrayList<>();
        for (CropType cropType : farmInfo.getCropTypes()) {
            boolean flag = false;
            for (String cropString : crop_str) {
                if (cropString.split("#-#-#")[0].equals(cropType.getCropName())) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {

                if (cropType.getSelected()) {
                    // PlantingProfitLogger.info("cropType deleted parmanently");
                    CropLimit cropLimit = cropType.getCropLimit();
                    cropType.setCropLimit(null);
                    cropLimit.setCropType(null);
                    removeObject.add(cropLimit);

                    CropUnitOfMeasure cropUnitOfMeasure = cropType.getCropUnitOfMeasure();
                    cropUnitOfMeasure.setCropType(null);
                    cropType.setCropUnitOfMeasure(null);
                    removeObject.add(cropUnitOfMeasure);

                    InternalCropYieldInfo internalCropYieldInfo = cropType.getCropYieldInfo();
                    internalCropYieldInfo.setCropType(null);
                    cropType.setCropYieldInfo(null);
                    removeObject.add(internalCropYieldInfo);

                    InternalCropPricesInfo internalCropPricesInfo = cropType.getCropPricesInfo();
                    internalCropPricesInfo.setCropType(null);
                    cropType.setCropPricesInfo(null);
                    removeObject.add(internalCropPricesInfo);

                    InternalVariableCropProductionCosts inCropProductionCosts = cropType.getCostsCropProductionCosts();
                    inCropProductionCosts.setCropType(null);
                    cropType.setCostsCropProductionCosts(null);
                    removeObject.add(inCropProductionCosts);

                    for (CropResourceUsageFieldVariances cropResourceUsageFieldVariance : cropType.getCropResourceUsageFieldVariances()) {
                        cropResourceUsageFieldVariance.setCropType(null);
                        cropType.setCropResourceUsageFieldVariances(null);
                        removeObject.add(cropResourceUsageFieldVariance);
                    }

                    // Set<CropFieldChocies> chocies = cropType.getChocies();
                    Set<CropFieldChocies> chocies1 = new HashSet<CropFieldChocies>();
                    for (CropFieldChocies cropFieldChocies : cropType.getChocies()) {
                        cropFieldChocies.setCropType(null);
                        cropFieldChocies.getName().getChocies().remove(cropFieldChocies);
                        cropFieldChocies.setName(null);
                        chocies1.add(cropFieldChocies);
                    }
                    cropType.getChocies().removeAll(chocies1);
                    CropForwardSales cropForwardSales = cropType.getCropForwardSales();
                    cropForwardSales.setCropType(null);
                    cropType.setCropForwardSales(null);
                    removeObject.add(cropForwardSales);

                    SummaryCropInfo summaryCropInfo = cropType.getCropInfo();
                    summaryCropInfo.setCropType(null);
                    cropType.setCropInfo(null);
                    removeObject.add(summaryCropInfo);

                    OptionalCropPlantingDates optionalCropPlantingDates = cropType.getCropPlantingDates();
                    optionalCropPlantingDates.setCropType(null);
                    cropType.setCropPlantingDates(null);
                    removeObject.add(optionalCropPlantingDates);

                    for (OptionalCropProductionCostsDetails optionalCropProductionCostsDetails : cropType.getOptCropCost()) {
                        optionalCropProductionCostsDetails.setCropType(null);
                        cropType.setOptCropCost(null);
                        removeObject.add(optionalCropProductionCostsDetails);
                    }

                    CropYieldFieldVariances cropYieldFieldVariances = cropType.getCropYieldFieldVariances();
                    if (cropYieldFieldVariances != null) {
                        cropYieldFieldVariances.setCropType(null);
                        cropYieldFieldVariances.getFieldInfo().setCropYieldFieldVariances(null);
                        cropYieldFieldVariances.setFieldInfo(null);
                        cropType.setCropYieldFieldVariances(null);
                        removeObject.add(cropYieldFieldVariances);
                    }

                    // deleteFarmInfo(cropType);
                    // PlantingProfitLogger.info("remove successfully"+cropArray[0]);

                }
                // farmInfo.setCropTypes(null);
                cropType.setFarmInfo(null);

                // farmInfo.getCropTypes().remove(cropType);
                cropTypes2.add(cropType);
                // deleteFarmInfo(cropType);
            }
        }
        farmInfo.getCropTypes().removeAll(cropTypes2);
        if (crop_group_array != null) {
            for (String str : crop_group_array) {
                String[] array = str.split("#-#-#");
                boolean flag = false;
                for (CropsGroup cropsGroup : farmInfo.getCropsGroup()) {
                    if (cropsGroup.getCropsGroupName().equals(array[0])) {
                        cropsGroup.setCropsGroupName(array[0]);
                        cropsGroup.setMaximumAcres(array[1]);
                        cropsGroup.setMinimumAcres(array[2]);
                        cropsGroup.setFarmInfo(farmInfo);
                        Set<CropType> types = new HashSet<CropType>();
                        for (CropType cropType : cropsGroup.getCropTypes()) {
                            boolean checkflag = false;
                            for (int i = 4; i < array.length; i++) {
                                if (cropType.getCropName().equals(array[i])) {
                                    checkflag = true;
                                    flag = true;
                                    break;
                                }
                            }
                            if (checkflag == false) {
                                cropType.getCropsGroup().remove(cropsGroup);
                                types.add(cropType);
                            }
                        }
                        cropsGroup.getCropTypes().removeAll(types);
                        for (int i = 4; i < array.length; i++) {
                            boolean checkflag = false;
                            for (CropType cropType : cropsGroup.getCropTypes()) {
                                if (cropType.getCropName().equals(array[i])) {
                                    checkflag = true;
                                    break;
                                }
                            }
                            if (checkflag == false) {
                                for (CropType cropType : farmInfo.getCropTypes()) {
                                    if (cropType.getSelected() && cropType.getCropName().equals(array[i])) {
                                        cropsGroup.getCropTypes().add(cropType);
                                        cropType.getCropsGroup().add(cropsGroup);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (flag == false) {
                    CropsGroup cropsGroup = new CropsGroup();
                    cropsGroup.setCropsGroupName(array[0]);
                    cropsGroup.setMaximumAcres(array[1]);
                    cropsGroup.setMinimumAcres(array[2]);
                    cropsGroup.setFarmInfo(farmInfo);
                    for (int i = 4; i < array.length; ) {
                        for (CropType cropType : cropTypes) {
                            if (cropType.getSelected() && cropType.getCropName().equals(array[i])) {
                                cropsGroup.getCropTypes().add(cropType);
                                cropType.getCropsGroup().add(cropsGroup);
                                i++;
                                break;
                            }
                        }
                    }
                    cropsGroups.add(cropsGroup);
                }
            }
        }
//		for (Object removeObj : removeObject) {
//			PlantingProfitLogger.info("delete cropLimit object : " + removeObj);
//			deleteFarmInfo(removeObj);
//		}
        /**
         * @added - abhishek
         * @date - 13-09-2016
         * @desc - for additional income details for crops
         */
        Set<CropType> cropTypeList = farmInfo.getCropTypes();
        for (CropType cropType : cropTypeList) {

            if (additionalCropCostJsonObj.size() != 0) {
                Object o = additionalCropCostJsonObj.get(cropType.getCropName());
                if (!Objects.equals(o, null)) {
                    JSONObject jsonObject = (JSONObject) o;
                    AdditionalCropIncome additionalCropIncome = cropType.getAdditionalCropIncome();
                    if (additionalCropIncome == null) {
                        additionalCropIncome = new AdditionalCropIncome();
                    }

                    additionalCropIncome.setGovernmentPayments(Double.parseDouble(jsonObject.get("governmentPayments").toString()));
                    additionalCropIncome.setCoProductsPrice(Double.parseDouble(jsonObject.get("coProducts").toString()));
                    additionalCropIncome.setAdditionalVariableProductionCost(Double.parseDouble(jsonObject.get("additionalVariableCost").toString()));
                    additionalCropIncome.setAdditionalIncome(Double.parseDouble(jsonObject.get("additionalIncome").toString()));
                    additionalCropIncome.setCropType(cropType);
                    cropType.setAdditionalCropIncome(additionalCropIncome);
                }
            }

        }


        return farmInfo;
    }

    @Override
    public FarmInfo saveFarm(int farmId, String irrigate_val,
                             boolean evaluate_forward_sales_val,
                             boolean evaluate_storage_sales_val,
                             boolean evaluate_crop_insurance_val, String strategy,
                             String total_land, FarmInfo farmInfo, String[] crop_str,
                             String[] crop_information_detail, String[] cal_var_cost_crops,
                             String[] option_crop_info_array,
                             String[] optional_planting_date_array,
                             String[] manage_resource_tbody_array,
                             String[] crop_resource_usage_tbody_array,
                             String[] crop_limits_table_tbody_array,
                             String[] forward_sales_information_tbody_array,
                             String[] plan_by_field_tbody_array,
                             String[] field_choice_crop_tbody_row_array,
                             String[] crop_resources_usages_difference_tbody_array,
                             String field_difference_str, String[] crop_group_array, String additionalCropCostObj) {
        PlantingProfitLogger.info("welcome inserted farm");
        List<FieldInfo> fieldInfos = farmInfo.getFieldInfos();
        Set<CropType> cropTypes = farmInfo.getCropTypes();
        List<CropResourceUsage> cropResourceUsages = farmInfo.getCropResourceUsage();
        Set<CropsGroup> cropsGroups = farmInfo.getCropsGroup();
        /* All Variables End */

        farmInfo.setIrrigate(irrigate_val);
        farmInfo.setEvaluate_forward_sales(evaluate_forward_sales_val);
        farmInfo.setEvaluate_storage(evaluate_storage_sales_val);
        farmInfo.setEvaluate_crop_insurance(evaluate_crop_insurance_val);
        farmInfo.setCurrencyUOM("$");
        farmInfo.setLandUOM("Acre");

        /**
         * @added - abhishek
         * @date - 13-09-2016
         * @desc - for additional income details for crops
         */
        JSONParser jsonParser = new JSONParser();
        JSONObject additionalCropCostJsonObj = new JSONObject();
        try {
            additionalCropCostJsonObj = (JSONObject) jsonParser.parse(additionalCropCostObj);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }


        if (strategy.equals("fields")) {
            farmInfo.setStrategy(PlanByStrategy.PLAN_BY_FIELDS);
            farmInfo.setLand(total_land);
        } else if (strategy.equals("acres")) {
            farmInfo.setStrategy(PlanByStrategy.PLAN_BY_ACRES);
            farmInfo.setLand(total_land);
        } else {
            farmInfo.setStrategy(PlanByStrategy.BOTH);
        }

        if (plan_by_field_tbody_array != null && plan_by_field_tbody_array.length > 0) {
            for (String palne_fields : plan_by_field_tbody_array) {
                String[] resourse_array = palne_fields.split("#-#-#");
                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setFieldName(resourse_array[0]);
                fieldInfo.setFieldSize(resourse_array[1]);
                fieldInfo.setLastCrop(resourse_array[2]);
                fieldInfo.setFallow(resourse_array[3]);
                fieldInfo.setDivide(resourse_array[4]);
                fieldInfo.setIrrigate(resourse_array[5]);
                fieldInfo.setFarmInfo(farmInfo);
                fieldInfos.add(fieldInfo);
            }
        }

        for (String resourceString : manage_resource_tbody_array) {
            String[] resourceStringArray = resourceString.split("#-#-#");
            CropResourceUsage cropResourceUsage = new CropResourceUsage();
            cropResourceUsage.setCropResourceUse(resourceStringArray[0]);
            cropResourceUsage.setUoMResource(resourceStringArray[1]);
            cropResourceUsage.setCropResourceUseAmount(resourceStringArray[2]);
            cropResourceUsage.setResourceActive(resourceStringArray[3].equalsIgnoreCase("active"));
            cropResourceUsage.setFarmInfo(farmInfo);
            cropResourceUsages.add(cropResourceUsage);
        }
        if (crop_resources_usages_difference_tbody_array != null) {
            for (String str : crop_resources_usages_difference_tbody_array) {
                String[] array = str.split("#-#-#");
                CropResourceUsageFieldDifferences cropResourceUsageFieldDifferences = new CropResourceUsageFieldDifferences();
                cropResourceUsageFieldDifferences.setResourseOverrideAmount(array[1]);
                for (CropResourceUsage cropResourceUsage : cropResourceUsages) {
                    if (cropResourceUsage.getCropResourceUse().equals(array[0])) {
                        cropResourceUsageFieldDifferences.setCropResourceUsage(cropResourceUsage);
                        cropResourceUsage.setDifferences(cropResourceUsageFieldDifferences);
                        break;
                    }
                }
            }
        }

        for (String crop : crop_str) {
            String[] cropArray = crop.split("#-#-#");
            CropType cropType = new CropType();
            cropType.setFarmInfo(farmInfo);
            cropType.setCropName(cropArray[0]);
            cropType.setCropTypeName((cropArray[2].equals("vegitable")) ? "Vegitable_Crop" : "Field_Crop");
            cropType.setSelected((cropArray[1].equals("true")) ? true : false);
            if (cropType.getSelected()) {
                CropUnitOfMeasure cropUnitOfMeasure = new CropUnitOfMeasure();
                InternalCropYieldInfo internalCropYieldInfo = new InternalCropYieldInfo();
                InternalVariableCropProductionCosts internalVariableCropProductionCosts = new InternalVariableCropProductionCosts();
                SummaryCropInfo summaryCropInfo = new SummaryCropInfo();
                OptionalCropPlantingDates optionalCropPlantingDates = new OptionalCropPlantingDates();
                Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariances = new HashSet<CropResourceUsageFieldVariances>();
                InternalCropPricesInfo internalCropPricesInfo = new InternalCropPricesInfo();
                CropLimit cropLimit = new CropLimit();
                CropForwardSales cropForwardSales = new CropForwardSales();
                Set<CropFieldChocies> chocies = null;
                for (String str : crop_information_detail) {
                    String[] array = str.split("#-#-#");
                    if (array[0].equals(cropType.getCropName())) {
                        cropUnitOfMeasure.setCropType(cropType);
                        cropUnitOfMeasure.setUnitOfMeasure(array[1]);
                        internalCropYieldInfo.setCropType(cropType);
                        internalCropYieldInfo.setIntExpCropYield((array[2].equals("")) ? "" : array[2]);
                        internalCropYieldInfo.setIntMinCropYield((array[3].equals("")) ? "" : array[3]);
                        internalCropYieldInfo.setIntMaxCropYield((array[4].equals("")) ? "" : array[4]);
                        internalCropPricesInfo.setCropType(cropType);
                        internalCropPricesInfo.setIntExpCropPrice(new BigDecimal((array[5].equals("")) ? "0" : array[5]));
                        internalCropPricesInfo.setIntMinCropPrice(new BigDecimal((array[6].equals("")) ? "0" : array[6]));
                        internalCropPricesInfo.setIntMaxCropPrice(new BigDecimal((array[7].equals("")) ? "0" : array[7]));
                        internalVariableCropProductionCosts.setCropType(cropType);
                        internalVariableCropProductionCosts.setCalculatedVariableProductionCost(new BigDecimal((array[8].equals("")) ? "0" : array[8]));
                    }
                }
                if (crop_limits_table_tbody_array != null) {
                    for (String str : crop_limits_table_tbody_array) {
                        String[] array = str.split("#-#-#");
                        if (array[0].equals(cropType.getCropName())) {
                            cropLimit.setCropType(cropType);
                            cropLimit.setMinimumAcres((array[1].equals("0")) ? "0" : array[1]);
                            cropLimit.setMaximumAcres((array[2].equals("0")) ? "0" : array[2]);
                        }
                    }
                }
                if (crop_resource_usage_tbody_array != null) {
                    for (String str : crop_resource_usage_tbody_array) {
                        String[] array = str.split("#-#-#");
                        if (array[0].equals(cropType.getCropName())) {
                            CropResourceUsageFieldVariances cropResourceUsageFieldVariance = new CropResourceUsageFieldVariances();
                            cropResourceUsageFieldVariance.setCropType(cropType);
                            cropResourceUsageFieldVariance.setCropFieldResourceUse(array[2]);
                            cropResourceUsageFieldVariance.setCropResourceAmount(array[3]);
                            cropResourceUsageFieldVariances.add(cropResourceUsageFieldVariance);
                        }
                    }
                }
                // if (cropType.getFarmInfo().getStrategy() ==
                // PlanByStrategy.PLAN_BY_FIELDS) {
                chocies = new HashSet<CropFieldChocies>();
                if (field_choice_crop_tbody_row_array != null && field_choice_crop_tbody_row_array.length > 0) {
                    for (String str : field_choice_crop_tbody_row_array) {
                        String[] array = str.split("#-#-#");
                        if (array[1].equals(cropType.getCropName())) {
                            CropFieldChocies cropFieldChocies = new CropFieldChocies();
                            cropFieldChocies.setCropType(cropType);
                            cropFieldChocies.setCropFieldChoice(array[2]);
                            for (FieldInfo fieldInfo : fieldInfos) {
                                if (fieldInfo.getFieldName().equals(array[0])) {
                                    cropFieldChocies.setName(fieldInfo);
                                    break;
                                }
                            }
                            chocies.add(cropFieldChocies);
                        }
                    }
                }
                // }
                if (forward_sales_information_tbody_array != null) {
                    for (String str : forward_sales_information_tbody_array) {
                        String[] array = str.split("#-#-#");
                        if (array[0].equals(cropType.getCropName())) {
                            cropForwardSales.setPrice(array[1]);
                            cropForwardSales.setQuantity(array[2]);
                            cropForwardSales.setFirmchecked(array[3]);
                            cropForwardSales.setProposedchecked(array[7].equals("true") ? true : false);
                            cropForwardSales.setAcres(new Double(array[4]));
                            cropForwardSales.setUpperLimit(new Double(array[5]));
                            cropForwardSales.setContactIdentifier(array[6]);
                            cropForwardSales.setCropType(cropType);
                            break;
                        }
                    }
                }
                if (option_crop_info_array != null) {
                    for (String str : option_crop_info_array) {
                        String[] array = str.split("#-#-#");
                        if (array[0].equals(cropType.getCropName())) {
                            summaryCropInfo.setIrrigated(array[1]);
                            summaryCropInfo.setConservation_Crop(array[2]);
                            summaryCropInfo.setHiRiskCrop(array[3]);
                            summaryCropInfo.setCropType(cropType);
                            break;
                        }
                    }
                }
                if (optional_planting_date_array != null) {
                    for (String str : optional_planting_date_array) {
                        String[] array = str.split("#-#-#");
                        if (array[0].equals(cropType.getCropName())) {
                            try {
                                optionalCropPlantingDates.setPreferredPlantingDate(FORMATTER.parse(array[1]));
                                optionalCropPlantingDates.setEarlyPlantingDate(FORMATTER.parse(array[2]));
                                optionalCropPlantingDates.setLatePlantingDate(FORMATTER.parse(array[3]));
                            } catch (ParseException e) {
                                PlantingProfitLogger.error(e);
                            }
                            optionalCropPlantingDates.setLengthofSeason(Integer.parseInt(array[4]));
                            optionalCropPlantingDates.setCropType(cropType);
                            break;
                        }
                    }
                }
                if (cal_var_cost_crops != null) {
                    for (String str : cal_var_cost_crops) {
                        String[] array = str.split("#-#-#");
                        if (array[0].equals(cropType.getCropName())) {
                            OptionalCropProductionCostsDetails optionalCropProductionCostsDetails = new OptionalCropProductionCostsDetails();
                            optionalCropProductionCostsDetails.setCropType(cropType);
                            optionalCropProductionCostsDetails.setCostComponentsName(array[1]);
                            optionalCropProductionCostsDetails.setUnitsPerAcre(new BigDecimal(array[2]));
                            optionalCropProductionCostsDetails.setUnitPrices(new BigDecimal(array[3]));
                            optionalCropProductionCostsDetails.setUnitTotal(new BigDecimal(array[4]));
                            cropType.getOptCropCost().add(optionalCropProductionCostsDetails);
                        }
                    }
                }
                if (field_difference_str != null && !field_difference_str.equals("")) {
                    String[] array = field_difference_str.split("#-#-#");
                    if (array[1].equals(cropType.getCropName())) {
                        CropYieldFieldVariances cropYieldFieldVariances = new CropYieldFieldVariances();
                        for (FieldInfo fieldInfo : fieldInfos) {
                            if (fieldInfo.getFieldName().equals(array[0])) {
                                cropYieldFieldVariances.setFieldInfo(fieldInfo);
                                break;
                            }
                        }
                        cropYieldFieldVariances.setCropType(cropType);
                        cropYieldFieldVariances.setExpCropYieldField(new Double(array[2]));
                        cropYieldFieldVariances.setMinCropYieldField(new Double(array[3]));
                        cropYieldFieldVariances.setMaxCropYieldField(new Double(array[4]));
                        cropYieldFieldVariances.setVarProductionCost(new Double(array[5]));
                        cropType.setCropYieldFieldVariances(cropYieldFieldVariances);
                    }
                }


                /**
                 * @added - abhishek
                 * @date - 13-09-2016
                 * @desc - for additional income details for crops
                 */
                if (additionalCropCostJsonObj.size() != 0) {
                    Object o = additionalCropCostJsonObj.get(cropType.getCropName());
                    if (!Objects.equals(o, null)) {
                        JSONObject jsonObject = (JSONObject) o;
                        AdditionalCropIncome additionalCropIncome = new AdditionalCropIncome();
                        additionalCropIncome.setGovernmentPayments(Double.parseDouble(jsonObject.get("governmentPayments").toString()));
                        additionalCropIncome.setCoProductsPrice(Double.parseDouble(jsonObject.get("coProducts").toString()));
                        additionalCropIncome.setAdditionalVariableProductionCost(Double.parseDouble(jsonObject.get("additionalVariableCost").toString()));
                        additionalCropIncome.setAdditionalIncome(Double.parseDouble(jsonObject.get("additionalIncome").toString()));
                        additionalCropIncome.setCropType(cropType);
                        cropType.setAdditionalCropIncome(additionalCropIncome);
                    }
                }


                cropType.setCropUnitOfMeasure(cropUnitOfMeasure);
                cropType.setCropYieldInfo(internalCropYieldInfo);
                cropType.setCostsCropProductionCosts(internalVariableCropProductionCosts);
                cropType.setCropInfo(summaryCropInfo);
                cropType.setCropPlantingDates(optionalCropPlantingDates);
                cropType.setCropResourceUsageFieldVariances(cropResourceUsageFieldVariances);
                cropType.setCropPricesInfo(internalCropPricesInfo);
                cropType.setCropLimit(cropLimit);
                cropType.setCropForwardSales(cropForwardSales);
                cropType.setChocies(chocies);


            }
            cropTypes.add(cropType);
        }

        if (crop_group_array != null) {
            for (String str : crop_group_array) {
                String[] array = str.split("#-#-#");
                CropsGroup cropsGroup = new CropsGroup();
                cropsGroup.setCropsGroupName(array[0]);
                cropsGroup.setMaximumAcres(array[1]);
                cropsGroup.setMinimumAcres(array[2]);
                cropsGroup.setFarmInfo(farmInfo);
                for (int i = 4; i < array.length; ) {
                    for (CropType cropType : cropTypes) {
                        if (cropType.getSelected() && cropType.getCropName().equals(array[i])) {
                            cropsGroup.getCropTypes().add(cropType);
                            cropType.getCropsGroup().add(cropsGroup);
                            i++;
                            break;
                        }
                    }
                }
                cropsGroups.add(cropsGroup);
            }
        }


        // farmInfo.setCropTypes(cropTypes);
        // farmInfo.setCropResourceUsage(cropResourceUsages);
        // farmInfo.setFieldInfos(fieldInfos);
        // farmInfo.setCropsGroup(cropsGroups);
        farmInfo.setSaveFlag(true);
        return farmInfo;
    }

    @Override
    public void initializeLazy(FarmInfo farmInfo) {
        Hibernate.initialize(farmInfo.getFarm());
        Hibernate.initialize(farmInfo.getCropTypes());
        Hibernate.initialize(farmInfo.getCropResourceUsage());
        Hibernate.initialize(farmInfo.getCropsGroup());
        Hibernate.initialize(farmInfo.getFieldInfos());
		Hibernate.initialize(farmInfo.getFarmCustomStrategySet());
        Hibernate.initialize(farmInfo.getCropLimitDualValues());
        Hibernate.initialize(farmInfo.getResourceDualValues());
        Hibernate.initialize(farmInfo.getGroupLimitDualValues());
    }


}