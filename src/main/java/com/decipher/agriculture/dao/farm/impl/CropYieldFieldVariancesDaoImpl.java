package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.CropYieldFieldVariancesDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CropYieldFieldVariances;

@Repository
@Transactional
public class CropYieldFieldVariancesDaoImpl implements
        CropYieldFieldVariancesDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int saveCropYieldFieldVariances(
            CropYieldFieldVariances cropYieldFieldVariances) {
        PlantingProfitLogger.info("inside saveCropYieldFieldVariances .. ");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(cropYieldFieldVariances);
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
    public boolean updateCropYieldFieldVariances(
            CropYieldFieldVariances cropYieldFieldVariances) {
        PlantingProfitLogger.info("inside updateCropYieldFieldVariances.."
                + cropYieldFieldVariances.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(cropYieldFieldVariances);
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
    public boolean deleteCropYieldFieldVariancesById(int id) {
        PlantingProfitLogger.info("inside deleteCropYieldFieldVariancesById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session
                    .createQuery("delete from CropYieldFieldVariances where id = :id");
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
    public CropYieldFieldVariances getCropYieldFieldVariancesById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        CropYieldFieldVariances cropYieldFieldVariances = null;
        try {
            Query query = session
                    .createQuery("from CropUnitOfMeasure where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof CropYieldFieldVariances)
                    cropYieldFieldVariances = (CropYieldFieldVariances) obj;
                else
                    cropYieldFieldVariances = null;
            } else {
                cropYieldFieldVariances = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropYieldFieldVariances = null;
        } finally {
            session.close();
        }
        return cropYieldFieldVariances;
    }

    @Override
    public boolean saveCropYieldFieldVariancesList(
            Set<CropYieldFieldVariances> cropYieldFieldVariancesList) {
        PlantingProfitLogger.info("inside save Crop UnitOfMeasure List..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (CropYieldFieldVariances cropYieldFieldVariances : cropYieldFieldVariancesList)
                session.save(cropYieldFieldVariances);
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

}
