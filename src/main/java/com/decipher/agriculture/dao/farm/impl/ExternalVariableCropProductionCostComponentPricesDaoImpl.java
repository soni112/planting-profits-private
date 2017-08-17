package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.ExternalVariableCropProductionCostComponentPricesDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.ExternalVariableCropProductionCostComponentPrices;

@Repository
@Transactional
public class ExternalVariableCropProductionCostComponentPricesDaoImpl implements
        ExternalVariableCropProductionCostComponentPricesDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(
            ExternalVariableCropProductionCostComponentPrices componentPrices) {
        PlantingProfitLogger.info("inside saveComponentPrices..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(componentPrices);
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
            ExternalVariableCropProductionCostComponentPrices componentPrices) {
        PlantingProfitLogger.info("inside updateCostComponentPrices.."
                + componentPrices.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(componentPrices);
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
    public boolean delete(int id) {
        PlantingProfitLogger.info("inside deleteComponentPricesById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Query query = session
                    .createQuery("delete from ExternalVariableCropProductionCostComponentPrices where id = :id");
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
    public ExternalVariableCropProductionCostComponentPrices getProductionCostComponentPricesById(
            int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        ExternalVariableCropProductionCostComponentPrices componentPrices = null;
        try {
            Query query = session
                    .createQuery("from ExternalVariableCropProductionCostComponentPrices where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof ExternalVariableCropProductionCostComponentPrices)
                    componentPrices = (ExternalVariableCropProductionCostComponentPrices) obj;
                else
                    componentPrices = null;
            } else {
                componentPrices = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            componentPrices = null;
        } finally {
            session.close();
        }
        return componentPrices;
    }

    @Override
    public boolean saveList(
            Set<ExternalVariableCropProductionCostComponentPrices> componentPricesList) {
        PlantingProfitLogger.info("inside save Crop Production Cost Component Prices..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (ExternalVariableCropProductionCostComponentPrices componentPrices : componentPricesList)
                session.save(componentPrices);
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
