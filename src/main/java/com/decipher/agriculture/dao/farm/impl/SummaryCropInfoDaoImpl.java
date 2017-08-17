package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.SummaryCropInfoDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.SummaryCropInfo;

@Repository
@Transactional
public class SummaryCropInfoDaoImpl implements SummaryCropInfoDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(SummaryCropInfo summaryCropInfo) {
        PlantingProfitLogger.info("inside saveSummaryCropInfo..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(summaryCropInfo);
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
    public boolean update(SummaryCropInfo summaryCropInfo) {
        PlantingProfitLogger.info("inside updateSummaryCropInfo.." + summaryCropInfo.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(summaryCropInfo);
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
                    .createQuery("delete from SummaryCropInfo where id = :id");
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
    public boolean saveList(Set<SummaryCropInfo> summaryCropInfoList) {
        PlantingProfitLogger.info("inside save Crop Production Costs..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (SummaryCropInfo summaryCropInfo : summaryCropInfoList)
                session.save(summaryCropInfo);
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
    public SummaryCropInfo getSummaryCropInfoById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        SummaryCropInfo summaryCropInfo = null;
        try {
            Query query = session
                    .createQuery("from SummaryCropInfo where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof SummaryCropInfo)
                    summaryCropInfo = (SummaryCropInfo) obj;
                else
                    summaryCropInfo = null;
            } else {
                summaryCropInfo = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            summaryCropInfo = null;
        } finally {
            session.close();
        }
        return summaryCropInfo;
    }

}
