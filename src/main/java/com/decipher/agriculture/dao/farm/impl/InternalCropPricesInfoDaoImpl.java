package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.InternalCropPricesInfoDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.InternalCropPricesInfo;

@Repository
@Transactional
public class InternalCropPricesInfoDaoImpl implements InternalCropPricesInfoDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(InternalCropPricesInfo cropPricesInfo) {
        PlantingProfitLogger.info("inside save InternalCropPricesInfo..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(cropPricesInfo);
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
    public boolean update(InternalCropPricesInfo cropPricesInfo) {
        PlantingProfitLogger.info("inside updateInternalCropPricesInfo.."
                + cropPricesInfo.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(cropPricesInfo);
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
    public boolean deleteById(int id) {
        PlantingProfitLogger.info("inside deleteById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Query query = session
                    .createQuery("delete from InternalCropPricesInfo where id = :id");
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
    public boolean saveList(Set<InternalCropPricesInfo> cropPricesInfoList) {
        PlantingProfitLogger.info("inside save InternalCropPricesInfo..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (InternalCropPricesInfo cropPricesInfo : cropPricesInfoList)
                session.save(cropPricesInfo);
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
    public InternalCropPricesInfo getInternalCropPricesInfoById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        InternalCropPricesInfo pricesInfo = null;
        try {
            Query query = session
                    .createQuery("from InternalCropPricesInfo where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof InternalCropPricesInfo)
                    pricesInfo = (InternalCropPricesInfo) obj;
                else
                    pricesInfo = null;
            } else {
                pricesInfo = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            pricesInfo = null;
        } finally {
            session.close();
        }
        return pricesInfo;
    }

}
