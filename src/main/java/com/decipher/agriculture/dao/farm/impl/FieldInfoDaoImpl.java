package com.decipher.agriculture.dao.farm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.decipher.agriculture.dao.farm.FieldInfoDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.FieldInfo;

@Repository
@Transactional
public class FieldInfoDaoImpl implements FieldInfoDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int saveFieldInfo(FieldInfo fieldInfo) {
        PlantingProfitLogger.info("inside saveFieldInfo..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(fieldInfo);
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
    public boolean updateFieldInfo(FieldInfo fieldInfo) {
        PlantingProfitLogger.info("inside updateFieldInfo.."
                + fieldInfo.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(fieldInfo);
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
    public boolean deleteFieldInfoById(int id) {
        PlantingProfitLogger.info("inside deleteFieldInfoById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Query query = session
                    .createQuery("delete from FieldInfo where id = :id");
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
    public boolean saveList(Set<FieldInfo> fieldInfoList) {
        PlantingProfitLogger.info("inside save FieldInfo..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (FieldInfo fieldInfo : fieldInfoList)
                session.save(fieldInfo);
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
    public FieldInfo getFieldInfoById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        FieldInfo fieldInfo = null;
        try {
            Query query = session.createQuery("from FieldInfo where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof FieldInfo)
                    fieldInfo = (FieldInfo) obj;
                else
                    fieldInfo = null;
            } else {
                fieldInfo = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            fieldInfo = null;
        } finally {
            session.close();
        }
        return fieldInfo;
    }

    @Override
    public List<FieldInfo> getAllFieldsByFarmId(int farmId) {
        PlantingProfitLogger.debug("getAllFieldsByFarmId .. " + farmId);
        List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session
                    .createQuery("FROM FieldInfo where farmInfo.id=:farmId ORDER BY fieldName ASC");
            query.setParameter("farmId", farmId);
            fieldInfoList = query.list();
            if (fieldInfoList != null) {
//				PlantingProfitLogger.info("size -->>" + fieldInfoList.size());
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            fieldInfoList = null;
        } finally {
            session.close();
        }
        return fieldInfoList;
    }
}
