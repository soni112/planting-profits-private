package com.decipher.agriculture.dao.farm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.decipher.agriculture.dao.farm.CropResourceUsageFieldVariancesDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CropResourceUsageFieldVariances;

@Repository
@Transactional
public class CropResourceUsageFieldVariancesDaoImpl implements CropResourceUsageFieldVariancesDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int saveCropResourceUsageFieldVariances(
            CropResourceUsageFieldVariances cropResourceUsageFieldVariances) {
        PlantingProfitLogger.info("inside saveCropResourceUsageFieldVariances..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(cropResourceUsageFieldVariances);
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
    public boolean updateCropResourceUsageFieldVariances(
            CropResourceUsageFieldVariances cropResourceUsageFieldVariances) {
        PlantingProfitLogger.info("inside updateCropResourceUsageFieldVariances.." + cropResourceUsageFieldVariances.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(cropResourceUsageFieldVariances);
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
    public boolean deleteCropResourceUsageFieldVariancesById(int id) {
        PlantingProfitLogger.info("inside deleteCropResourceUsageFieldVariancesById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Query query = session.createQuery("delete from CropResourceUsageFieldVariances where id = :id");
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
    public CropResourceUsageFieldVariances getCropResourceUsageFieldVariancesById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        CropResourceUsageFieldVariances cropResourceUsageFieldVariances = null;
        try {
            Query query = session.createQuery("from CropResourceUsageFieldVariances where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof CropResourceUsageFieldVariances)
                    cropResourceUsageFieldVariances = (CropResourceUsageFieldVariances) obj;
                else
                    cropResourceUsageFieldVariances = null;
            } else {
                cropResourceUsageFieldVariances = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropResourceUsageFieldVariances = null;
        } finally {
            session.close();
        }
        return cropResourceUsageFieldVariances;
    }

    @Override
    public boolean saveCropResourceUsageFieldVariancesList(
            Set<CropResourceUsageFieldVariances> cropResourceUsageFieldVariancesList) {
        PlantingProfitLogger.info("inside save CropResourceUsage Field Variances List..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (CropResourceUsageFieldVariances cropResourceUsageFieldVariances : cropResourceUsageFieldVariancesList)
                session.save(cropResourceUsageFieldVariances);
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
    public List<CropResourceUsageFieldVariances> getAllResourceByCropIds(Integer[] cropId) {
        PlantingProfitLogger.debug("getAllResourceByCropIds ");
        List<CropResourceUsageFieldVariances> cropResourceList = new ArrayList<CropResourceUsageFieldVariances>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM CropResourceUsageFieldVariances where cropType.id in (:cropId)");
            query.setParameterList("cropId", cropId);
            cropResourceList = query.list();
            if (cropResourceList != null) {
//                PlantingProfitLogger.info("size -->>" + cropResourceList.size());
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropResourceList = null;
        } finally {
            session.close();
        }
        return cropResourceList;
    }

    @Override
    public List<CropResourceUsageFieldVariances> getAllResourceByCrop(
            int cropId) {
        PlantingProfitLogger.debug("getAllResourceByCrop .. " + cropId);
        List<CropResourceUsageFieldVariances> cropResourceList = new ArrayList<CropResourceUsageFieldVariances>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session
                    .createQuery("FROM CropResourceUsageFieldVariances where cropType.id=:cropId");
            query.setParameter("cropId", cropId);
            cropResourceList = query.list();
            if (cropResourceList != null) {
//                PlantingProfitLogger.info("size -->>" + cropResourceList.size());
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropResourceList = null;
        } finally {
            session.close();
        }
        return cropResourceList;
    }
}
