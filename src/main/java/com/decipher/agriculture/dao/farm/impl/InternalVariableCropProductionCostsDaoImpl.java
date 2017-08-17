package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.InternalVariableCropProductionCostsDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.InternalVariableCropProductionCosts;

@Repository
@Transactional
public class InternalVariableCropProductionCostsDaoImpl implements
        InternalVariableCropProductionCostsDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(InternalVariableCropProductionCosts productionCosts) {
        PlantingProfitLogger.info("inside saveInternalVariableCropProductionCosts..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(productionCosts);
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
    public boolean update(InternalVariableCropProductionCosts productionCosts) {
        PlantingProfitLogger.info("inside updateInternalVariableCropProductionCosts.."
                + productionCosts.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(productionCosts);
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
                    .createQuery("delete from InternalVariableCropProductionCosts where id = :id");
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
            Set<InternalVariableCropProductionCosts> productionCostsList) {
        PlantingProfitLogger.info("inside save Crop Production Costs..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (InternalVariableCropProductionCosts productionCosts : productionCostsList)
                session.save(productionCosts);
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
    public InternalVariableCropProductionCosts getCropProductionCostById(
            int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        InternalVariableCropProductionCosts productionCosts = null;
        try {
            Query query = session
                    .createQuery("from InternalVariableCropProductionCosts where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof InternalVariableCropProductionCosts)
                    productionCosts = (InternalVariableCropProductionCosts) obj;
                else
                    productionCosts = null;
            } else {
                productionCosts = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            productionCosts = null;
        } finally {
            session.close();
        }
        return productionCosts;
    }

}
