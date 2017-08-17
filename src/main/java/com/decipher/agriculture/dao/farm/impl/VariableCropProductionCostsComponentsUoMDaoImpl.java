package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.VariableCropProductionCostsComponentsUoMDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponentsUoM;

@Repository
@Transactional
public class VariableCropProductionCostsComponentsUoMDaoImpl implements
        VariableCropProductionCostsComponentsUoMDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(VariableCropProductionCostsComponentsUoM costsComponents) {
        PlantingProfitLogger.info("inside saveVariableCropProductionCostsComponentsUoM..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(costsComponents);
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
            VariableCropProductionCostsComponentsUoM costsComponents) {
        PlantingProfitLogger.info("inside updateVariableCropProductionCostsComponentsUoM.."
                + costsComponents.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(costsComponents);
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
                    .createQuery("delete from VariableCropProductionCostsComponentsUoM where id = :id");
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
            Set<VariableCropProductionCostsComponentsUoM> costsComponentsList) {
        PlantingProfitLogger.info("inside save Variable CropProduction Costs ComponentsUoM..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (VariableCropProductionCostsComponentsUoM costsComponents : costsComponentsList)
                session.save(costsComponents);
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
    public VariableCropProductionCostsComponentsUoM getCostsComponentsUoMById(
            int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        VariableCropProductionCostsComponentsUoM costsComponent = null;
        try {
            Query query = session
                    .createQuery("from VariableCropProductionCostsComponentsUoM where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof VariableCropProductionCostsComponentsUoM)
                    costsComponent = (VariableCropProductionCostsComponentsUoM) obj;
                else
                    costsComponent = null;
            } else {
                costsComponent = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            costsComponent = null;
        } finally {
            session.close();
        }
        return costsComponent;
    }

}
