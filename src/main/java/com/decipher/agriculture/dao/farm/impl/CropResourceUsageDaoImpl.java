package com.decipher.agriculture.dao.farm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.decipher.agriculture.dao.farm.CropResourceUsageDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CropResourceUsage;

@Repository
@Transactional
public class CropResourceUsageDaoImpl implements CropResourceUsageDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int saveCropResourceUsage(CropResourceUsage cropResourceUsage) {
        PlantingProfitLogger.info("inside saveCropResourceUsage .. ");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(cropResourceUsage);
            tx.commit();
            return id;
        } catch (Exception e) {
            id = 0;
            tx.rollback();
            PlantingProfitLogger.error(e);
            return id;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateCropResourceUsage(CropResourceUsage cropResourceUsage) {
        PlantingProfitLogger.info("inside updateCropResourceUsage.."
                + cropResourceUsage.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(cropResourceUsage);
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

    @Override
    public boolean deleteCropResourceUsageById(int id) {
        PlantingProfitLogger.info("inside deleteCropResourceUsageById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Query query = session
                    .createQuery("delete from CropResourceUsage where id = :id");
            query.setParameter("id", id);
            int result = query.executeUpdate();
            PlantingProfitLogger.info("result deleted : " + result);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            PlantingProfitLogger.info("Exception Occurs -->>" + e.toString());
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public CropResourceUsage getCropResourceUsageById(int id) {
        PlantingProfitLogger.info("inside get Crop Resource Usage By Id.." + id);
        Session session = sessionFactory.openSession();
        CropResourceUsage cropResourceUsage = null;
        try {
            Query query = session
                    .createQuery("from CropResourceUsage where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof CropResourceUsage)
                    cropResourceUsage = (CropResourceUsage) obj;
                else
                    cropResourceUsage = null;
            } else {
                cropResourceUsage = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropResourceUsage = null;
        } finally {
            session.close();
        }
        return cropResourceUsage;
    }

    @Override
    public boolean saveCropResourceUsageList(
            Set<CropResourceUsage> cropResourceUsageList) {
        PlantingProfitLogger.info("inside save Crop Resource Usage List..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (CropResourceUsage cropResourceUsage : cropResourceUsageList)
                session.save(cropResourceUsage);
            tx.commit();

            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive())
                tx.rollback();
            PlantingProfitLogger.error(e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<CropResourceUsage> getAllCropResourceUsageByFarmId(int farmId) {
        PlantingProfitLogger.debug("get All Crop Resource Usage By Farm Id .. " + farmId);
        List<CropResourceUsage> cropResourceUsageList = new ArrayList<CropResourceUsage>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM CropResourceUsage where farmInfo.id=:farmId");
            query.setParameter("farmId", farmId);
            cropResourceUsageList = query.list();
            if (cropResourceUsageList != null) {
//				PlantingProfitLogger.info("size -->>" + cropResourceUsageList.size());
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropResourceUsageList = null;
        } finally {
            session.close();
        }
        return cropResourceUsageList;
    }

    @Override
    public List<CropResourceUsage> getAllCropResourceUsageByFarmIds(
            List<Integer> farmId) {
        PlantingProfitLogger.debug("getAllCropResourceUsageByFarmIds ");
        List<CropResourceUsage> cropResourceUsageList = new ArrayList<CropResourceUsage>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session
                    .createQuery("FROM CropResourceUsage where farmInfo.id in (:farmId)");
            query.setParameterList("farmId", farmId);
            cropResourceUsageList = query.list();
            if (cropResourceUsageList != null) {
//				PlantingProfitLogger.info("size -->>" + cropResourceUsageList.size());
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropResourceUsageList = null;
        } finally {
            session.close();
        }
        return cropResourceUsageList;
    }

}
