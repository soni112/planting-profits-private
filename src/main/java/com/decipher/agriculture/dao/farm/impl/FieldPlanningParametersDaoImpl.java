package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.FieldPlanningParametersDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.FieldPlanningParameters;

@Repository
@Transactional
public class FieldPlanningParametersDaoImpl implements
        FieldPlanningParametersDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(FieldPlanningParameters fieldPlanningParameters) {
        PlantingProfitLogger.info("inside saveFieldPlanningParameters..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(fieldPlanningParameters);
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
    public boolean update(FieldPlanningParameters fieldPlanningParameters) {
        PlantingProfitLogger.info("inside updateFieldPlanningParameters.."
                + fieldPlanningParameters.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(fieldPlanningParameters);
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
                    .createQuery("delete from FieldPlanningParameters where id = :id");
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
            Set<FieldPlanningParameters> fieldPlanningParametersList) {
        PlantingProfitLogger.info("inside save FieldPlanningParameters..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (FieldPlanningParameters fieldPlanningParameters : fieldPlanningParametersList)
                session.save(fieldPlanningParameters);
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
    public FieldPlanningParameters getFieldPlanningById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        FieldPlanningParameters fieldPlanning = null;
        try {
            Query query = session
                    .createQuery("from FieldPlanningParameters where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof FieldPlanningParameters)
                    fieldPlanning = (FieldPlanningParameters) obj;
                else
                    fieldPlanning = null;
            } else {
                fieldPlanning = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            fieldPlanning = null;
        } finally {
            session.close();
        }
        return fieldPlanning;
    }

}
