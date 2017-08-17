package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.ExternalCropYieldInfoDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.ExternalCropYieldInfo;

@Repository
@Transactional
public class ExternalCropYieldInfoDaoImpl implements ExternalCropYieldInfoDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int saveExternalCropPriceInfo(
            ExternalCropYieldInfo externalCropYieldInfo) {
        PlantingProfitLogger.info("inside saveExternalCropPriceInfo .. ");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(externalCropYieldInfo);
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
    public boolean updateExternalCropYieldInfo(
            ExternalCropYieldInfo externalCropYieldInfo) {
        PlantingProfitLogger.info("inside updateExternalCropYieldInfo.."
                + externalCropYieldInfo.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(externalCropYieldInfo);
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
    public boolean deleteExternalCropYieldInfoById(int id) {
        PlantingProfitLogger.info("inside deleteExternalCropYieldInfoById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session
                    .createQuery("delete from ExternalCropYieldInfo where id = :id");
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
    public ExternalCropYieldInfo getExternalCropYieldInfoById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        ExternalCropYieldInfo externalCropYieldInfo = null;
        try {
            Query query = session
                    .createQuery("from CropUnitOfMeasure where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof ExternalCropYieldInfo)
                    externalCropYieldInfo = (ExternalCropYieldInfo) obj;
                else
                    externalCropYieldInfo = null;
            } else {
                externalCropYieldInfo = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            externalCropYieldInfo = null;
        } finally {
            session.close();
        }
        return externalCropYieldInfo;
    }

    @Override
    public boolean saveExternalCropYieldInfoList(
            Set<ExternalCropYieldInfo> externalCropYieldInfoList) {
        PlantingProfitLogger.info("inside save External Crop Yield Info..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (ExternalCropYieldInfo externalCropYieldInfo : externalCropYieldInfoList)
                session.save(externalCropYieldInfo);
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
