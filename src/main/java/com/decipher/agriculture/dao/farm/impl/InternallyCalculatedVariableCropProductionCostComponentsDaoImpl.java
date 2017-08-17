package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.InternallyCalculatedVariableCropProductionCostComponentsDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.InternallyCalculatedVariableCropProductionCostComponents;

@Repository
@Transactional
public class InternallyCalculatedVariableCropProductionCostComponentsDaoImpl
        implements InternallyCalculatedVariableCropProductionCostComponentsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(
            InternallyCalculatedVariableCropProductionCostComponents costComponents) {
        PlantingProfitLogger.info("inside saveInternallyCalculatedVariableCropProductionCostComponents..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(costComponents);
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
            InternallyCalculatedVariableCropProductionCostComponents costComponents) {
        PlantingProfitLogger.info("inside updateInternallyCalculatedVariableCropProductionCostComponents.."
                + costComponents.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(costComponents);
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
                    .createQuery("delete from InternallyCalculatedVariableCropProductionCostComponents where id = :id");
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
            Set<InternallyCalculatedVariableCropProductionCostComponents> costComponentsList) {
        PlantingProfitLogger.info("inside save CropProductionCostComponents..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (InternallyCalculatedVariableCropProductionCostComponents costComponents : costComponentsList)
                session.save(costComponents);
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
    public InternallyCalculatedVariableCropProductionCostComponents getCostComponentsById(
            int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        InternallyCalculatedVariableCropProductionCostComponents costComponent = null;
        try {
            Query query = session
                    .createQuery("from InternallyCalculatedVariableCropProductionCostComponents where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof InternallyCalculatedVariableCropProductionCostComponents)
                    costComponent = (InternallyCalculatedVariableCropProductionCostComponents) obj;
                else
                    costComponent = null;
            } else {
                costComponent = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            costComponent = null;
        } finally {
            session.close();
        }
        return costComponent;
    }

}
