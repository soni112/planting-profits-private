package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.FarmProductionPlanningModelParametersDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.FarmProductionPlanningModelParameters;

@Repository
@Transactional
public class FarmProductionPlanningModelParametersDaoImpl implements
        FarmProductionPlanningModelParametersDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(FarmProductionPlanningModelParameters farmProduction) {
        PlantingProfitLogger.info("inside saveFarmProduction..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(farmProduction);
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
    public boolean update(FarmProductionPlanningModelParameters farmProduction) {
        PlantingProfitLogger.info("inside updateFarmLocation.." + farmProduction.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(farmProduction);
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
        PlantingProfitLogger.info("inside deleteFarmProductionById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Query query = session
                    .createQuery("delete from FarmProductionPlanningModelParameters where id = :id");
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
            Set<FarmProductionPlanningModelParameters> farmProductionList) {
        PlantingProfitLogger.info("inside save FarmLocation..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (FarmProductionPlanningModelParameters farmProduction : farmProductionList)
                session.save(farmProduction);
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
    public FarmProductionPlanningModelParameters getFarmProductionById(
            int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        FarmProductionPlanningModelParameters farmProduction = null;
        try {
            Query query = session
                    .createQuery("from FarmProductionPlanningModelParameters where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof FarmProductionPlanningModelParameters)
                    farmProduction = (FarmProductionPlanningModelParameters) obj;
                else
                    farmProduction = null;
            } else {
                farmProduction = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            farmProduction = null;
        } finally {
            session.close();
        }
        return farmProduction;
    }

}
