package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.InternalCropYieldInfoDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.InternalCropYieldInfo;

@Repository
@Transactional
public class InternalCropYieldInfoDaoImpl implements InternalCropYieldInfoDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(InternalCropYieldInfo cropYieldInfo) {
        PlantingProfitLogger.info("inside saveInternalCropYieldInfo..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(cropYieldInfo);
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
    public boolean update(InternalCropYieldInfo cropYieldInfo) {
        PlantingProfitLogger.info("inside updateInternalCropYieldInfo.."
                + cropYieldInfo.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(cropYieldInfo);
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
                    .createQuery("delete from InternalCropYieldInfo where id = :id");
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
    public boolean saveList(Set<InternalCropYieldInfo> cropYieldInfoList) {
        PlantingProfitLogger.info("inside save FieldInfo..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (InternalCropYieldInfo cropYieldInfo : cropYieldInfoList)
                session.save(cropYieldInfo);
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
    public InternalCropYieldInfo getInternalCropYieldInfoById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        InternalCropYieldInfo internalCrop = null;
        try {
            Query query = session
                    .createQuery("from InternalCropYieldInfo where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof InternalCropYieldInfo)
                    internalCrop = (InternalCropYieldInfo) obj;
                else
                    internalCrop = null;
            } else {
                internalCrop = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            internalCrop = null;
        } finally {
            session.close();
        }
        return internalCrop;
    }

}
