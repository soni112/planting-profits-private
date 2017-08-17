package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.ExternalCropPriceInfoDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.ExternalCropPriceInfo;

@Repository
@Transactional
public class ExternalCropPriceInfoDaoImpl implements ExternalCropPriceInfoDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int saveExternalCropPriceInfo(
            ExternalCropPriceInfo externalCropPriceInfo) {
        PlantingProfitLogger.info("inside saveExternalCropPriceInfo .. ");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(externalCropPriceInfo);
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
    public boolean updateExternalCropPriceInfo(
            ExternalCropPriceInfo externalCropPriceInfo) {
        PlantingProfitLogger.info("inside updateExternalCropPriceInfo.."
                + externalCropPriceInfo.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(externalCropPriceInfo);
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
    public boolean deleteExternalCropPriceInfoById(int id) {
        PlantingProfitLogger.info("inside deleteExternalCropPriceInfoById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session
                    .createQuery("delete from ExternalCropPriceInfo where id = :id");
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
    public ExternalCropPriceInfo getExternalCropPriceInfoById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        ExternalCropPriceInfo externalCropPriceInfo = null;
        try {
            Query query = session
                    .createQuery("from CropUnitOfMeasure where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof ExternalCropPriceInfo)
                    externalCropPriceInfo = (ExternalCropPriceInfo) obj;
                else
                    externalCropPriceInfo = null;
            } else {
                externalCropPriceInfo = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            externalCropPriceInfo = null;
        } finally {
            session.close();
        }
        return externalCropPriceInfo;
    }

    @Override
    public boolean saveExternalCropPriceInfoList(
            Set<ExternalCropPriceInfo> externalCropPriceInfoList) {
        PlantingProfitLogger.info("inside save External Crop Price Info..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (ExternalCropPriceInfo externalCropPriceInfo : externalCropPriceInfoList)
                session.save(externalCropPriceInfo);
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
