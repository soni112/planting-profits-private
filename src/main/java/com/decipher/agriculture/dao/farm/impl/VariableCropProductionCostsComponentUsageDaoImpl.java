package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.VariableCropProductionCostsComponentUsageDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponentUsage;

@Repository
@Transactional
public class VariableCropProductionCostsComponentUsageDaoImpl implements
        VariableCropProductionCostsComponentUsageDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(VariableCropProductionCostsComponentUsage componentUsage) {
        PlantingProfitLogger.info("inside saveVariableCropProductionCostsComponentUsage..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(componentUsage);
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
    public boolean update(
            VariableCropProductionCostsComponentUsage componentUsage) {
        PlantingProfitLogger.info("inside updateVariableCropProductionCostsComponentUsage.."
                + componentUsage.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(componentUsage);
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
                    .createQuery("delete from VariableCropProductionCostsComponentUsage where id = :id");
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
    public boolean saveList(
            Set<VariableCropProductionCostsComponentUsage> componentUsageList) {
        PlantingProfitLogger.info("inside save Variable Crop Production Costs Component Usage..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (VariableCropProductionCostsComponentUsage componentUsage : componentUsageList)
                session.save(componentUsage);
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
    public VariableCropProductionCostsComponentUsage getcomponentUsageById(
            int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        VariableCropProductionCostsComponentUsage costsComponentUsage = null;
        try {
            Query query = session
                    .createQuery("from VariableCropProductionCostsComponentUsage where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof VariableCropProductionCostsComponentUsage)
                    costsComponentUsage = (VariableCropProductionCostsComponentUsage) obj;
                else
                    costsComponentUsage = null;
            } else {
                costsComponentUsage = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            costsComponentUsage = null;
        } finally {
            session.close();
        }
        return costsComponentUsage;
    }

}
