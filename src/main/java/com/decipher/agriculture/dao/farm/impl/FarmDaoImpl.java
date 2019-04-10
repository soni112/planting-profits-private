package com.decipher.agriculture.dao.farm.impl;

import com.decipher.agriculture.data.farm.FarmData;
import com.decipher.agriculture.dao.farm.FarmDao;
import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.data.farm.FarmInfo;
import com.decipher.agriculture.service.farm.FarmInfoService;
import com.decipher.agriculture.service.farm.impl.UploadExcelServiceImpl;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek on 30/11/16.
 */
@Repository
@Transactional
public class FarmDaoImpl implements FarmDao {
    @Autowired
    private FarmInfoService farmInfoService;
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean isFarmExitsWithNameAndUserId(String farmName, int accountId) {
        Session session = sessionFactory.openSession();
        Object object = null;
        PlantingProfitLogger.info("Trying to find farm with name : " + farmName + " for account id : " + accountId);
        try {
            Query userQuery = session.createQuery("from Farm where farmName=:farmName and account.id=:accountId and saveFlag=:saveFlag");
            userQuery.setParameter("farmName", farmName);
            userQuery.setParameter("accountId", accountId);
            userQuery.setParameter("saveFlag", true);
            object = userQuery.uniqueResult();
            PlantingProfitLogger.info("Farm with name : " + farmName + " exists");

        } catch (Exception e) {
            PlantingProfitLogger.error("Error while trying ot find farm with name : " + farmName, e);
        } finally {
            session.close();
        }

        return object != null;
    }

    @Override
    public Farm getFarmById(int farmId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Farm farm = null;
        PlantingProfitLogger.info("Trying to get farm by id : " + farmId);
        try {
            transaction.begin();
            Query query = session.createQuery("from Farm where id=:id");
            query.setParameter("id", farmId);
            farm = (Farm) query.uniqueResult();
            if (farm != null)
                initializeLazy(farm);

            transaction.commit();
        } catch (Exception e) {
            PlantingProfitLogger.error("Error while getting farm for id : " + farmId, e);
            transaction.rollback();
        } finally {
            session.close();
        }
        PlantingProfitLogger.info("Completed getting farm by id : " + farmId);
        return farm;
    }

    @Override
    public int saveFarm(Farm farm) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        PlantingProfitLogger.info("Trying to save farm named : " + farm.getFarmName());

        int farmId = 0;
        try {
            transaction.begin();
            farmId = (int) session.save(farm);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            PlantingProfitLogger.error("Error while saving farm ", e);
            return farmId;
        } finally {
            session.close();
        }
        PlantingProfitLogger.info("Successfully saved farm named : " + farm.getFarmName());
        return farmId;
    }

    @Override
    public void deleteFarm(Farm farm) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        PlantingProfitLogger.info("Started deletion of farm : " + farm.getFarmName());
        try {
            transaction.begin();
            session.delete(farm);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            PlantingProfitLogger.error("Error deletion of farm : " + farm.getFarmName(), e);
        } finally {
            session.close();
        }
        PlantingProfitLogger.info("Completed deletion of farm : " + farm.getFarmName());
    }


    @Override
    public boolean deleteAllFarmsForUser(int accountId) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        PlantingProfitLogger.info("Started deletion of farm for user id  : " + accountId);
        boolean flag = false;
        try {
            transaction.begin();
            Query query = session.createQuery("delete from Farm where account.id=:accountId");
            query.setParameter("accountId", accountId);
            int i = query.executeUpdate();
            if (i > 0) {
                flag = true;
            }

            session.flush();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            PlantingProfitLogger.error("Error deletion of farm for user id  : " + accountId, e);
            flag = false;
        } finally {
            session.close();
        }
        PlantingProfitLogger.info("Completed deletion of farm for user id  : " + accountId);

        return flag;
    }

    @Override
    public boolean deleteFarmByIds(String farmIdsString) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        PlantingProfitLogger.info("Started deletion of farms with ids : " + farmIdsString);
        boolean flag = false;
        try {
            transaction.begin();

            Query query = session.createSQLQuery("CALL removeSelectedFarmsAndAllRecord(:farmId)");
            query.setParameter("farmId", farmIdsString);
            query.executeUpdate();

            flag = true;

            session.flush();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            PlantingProfitLogger.error("Error deletion of farms with ids : " + farmIdsString, e);
            flag = false;
        } finally {
            session.close();
        }
        PlantingProfitLogger.info("Completed deletion of farms with ids : " + farmIdsString);

        return flag;
    }

    @Override
    public boolean updateFarm(Farm farm) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        boolean flag;
        PlantingProfitLogger.info("Started updating farm : " + farm.getFarmName());
        try {
            transaction.begin();
            session.update(farm);
            session.flush();
            transaction.commit();
            flag = true;
        } catch (Exception e) {
            transaction.rollback();
            PlantingProfitLogger.error("Error while updating farm : " + farm.getFarmName(), e);
            flag = false;
        } finally {
            session.close();
        }

        PlantingProfitLogger.info("Completed updating farm : " + farm.getFarmName());

        return flag;
    }

    @Override
    public FarmInfo getBaselineFarmDetails(int farmId) {
        Session session = sessionFactory.openSession();
        PlantingProfitLogger.info("Getting baseline details for farmId : " + farmId);
        FarmInfo farmInfo = null;
        try {
            Query query = session.createQuery("from FarmInfo where farm.id=:farmId and baselineFlag= true");
//            Query query = session.createQuery("from Farm as farm inner join farm.farmInfoList as farmList on farmList.baselineFlag = true where farm.id=:farmId");
            query.setParameter("farmId", farmId);
            farmInfo = (FarmInfo) query.uniqueResult();
            if (farmInfo != null) {
                farmInfoService.initializeLazy(farmInfo);
            }
        } catch (Exception e) {
            PlantingProfitLogger.error("Error while getting baseline details for farmId : " + farmId, e);
        } finally {
            session.close();
        }
        PlantingProfitLogger.info("Completed getting baseline details for farm id : " + farmId);

        return farmInfo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Farm> getAllFarmsForUser(int userId) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
        List<Farm> farmList = new ArrayList<>();
        try {
            transaction.begin();
            Query query = session.createQuery("from Farm where account.id=:userId");
            query.setParameter("userId", userId);
            List list = query.list();
            if (list != null && !list.isEmpty()) {
                farmList.addAll(list);
                for (Farm farm : farmList) {
                    initializeLazy(farm);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            PlantingProfitLogger.error("Error while getting farms for account ", e);
            transaction.rollback();
        }
        UploadExcelServiceImpl uploadExcelService = new UploadExcelServiceImpl();
        uploadExcelService.saveStateNameAndLink();
        ArrayList stateNameList = uploadExcelService.getStateName();
        ArrayList stateLinkList = uploadExcelService.getStateLink();
        if(session.createQuery("from FarmData").list().size() > 1){
            PlantingProfitLogger.info("Excel Data already available in db");
        }else {
            for (int i = 0; i <= stateNameList.size() - 1; i++) {
                Transaction transaction1 = session.getTransaction();
                transaction1.begin();
                String stateName = (String) stateNameList.get(i);
                String stateLink = (String) stateLinkList.get(i);
                FarmData farmData = new FarmData();
                farmData.setState(stateName);
                farmData.setStateAgStatistics(stateLink);
                session.saveOrUpdate(farmData);
                PlantingProfitLogger.info("Trying to save State Name : " + farmData.getState());
                PlantingProfitLogger.info("Successfully saved state Link : " + farmData.getStateAgStatistics());
                transaction1.commit();
            }
        }
        return farmList;
    }
    private void initializeLazy(Farm farm) {
//        if(farm.getFarmInfoList().size() != 0)
//            Hibernate.initialize(farm.getFarmInfoList());
//        if(farm.getFarmCustomStrategy().size() != 0)
//            Hibernate.initialize(farm.getFarmCustomStrategy());
    }
}
