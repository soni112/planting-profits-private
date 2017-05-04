package com.decipher.agriculture.dao.scenario.impl;

import com.decipher.agriculture.dao.scenario.ScenarioDao;
import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.decipher.agriculture.data.scenario.FarmStrategyScenario;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.scenario.FarmStrategyScenarioView;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raja on 12/25/15.
 */

@Repository
@Transactional
public class ScenarioDaoImpl implements ScenarioDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public FarmStrategyScenarioView getScenarioById(int scenarioId) {
        final Session session = sessionFactory.openSession();
        FarmStrategyScenario obj = null;
        try {
            obj = (FarmStrategyScenario) session.createQuery("from FarmStrategyScenario where id = :scenarioId").setParameter("scenarioId", scenarioId).uniqueResult();

            Hibernate.initialize(obj.getFarmCustomStrategy());
            Hibernate.initialize(obj.getFarmCustomStrategy().getFarm());

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }
        if (obj != null) {
            return new FarmStrategyScenarioView(obj);
        } else {
            return null;
        }
    }

    @Override
    public FarmStrategyScenarioView getScenarioByNameFarmStrategy(String scenarioName, int strategyId, int farmId) {
        final Session session = sessionFactory.openSession();
        FarmStrategyScenario obj = null;
        try {
            Query query = session.createQuery("from FarmStrategyScenario where scenarioName = :scenarioName and farmCustomStrategy.id = :strategyId and farmCustomStrategy.farm.id = :farmId");
            query.setParameter("scenarioName", scenarioName).setParameter("strategyId", strategyId).setParameter("farmId", farmId);
            obj = (FarmStrategyScenario)query.uniqueResult();
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }
        if (obj != null) {
            return new FarmStrategyScenarioView(obj);
        } else {
            return null;
        }
    }

    @Override
    public List<FarmStrategyScenarioView> getAllScenarioByStrategy(int strategyId) {

        final Session session = sessionFactory.openSession();
        /*final Transaction tx = session.beginTransaction();*/
        List<FarmStrategyScenarioView> scenarioData = new ArrayList<FarmStrategyScenarioView>();
        try {
            List<FarmStrategyScenario> list = session.createQuery("from FarmStrategyScenario where farmCustomStrategy.id = :strategyId").setParameter("strategyId", strategyId).list();
            if (list != null) {
                for (FarmStrategyScenario scenario : list) {
                    scenarioData.add(new FarmStrategyScenarioView(scenario));
                }
            }
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        } finally {
            session.close();
        }

        return scenarioData;
    }

    @Override
    public long saveScenario(FarmStrategyScenario scenario, int strategyId) {
        final Session session = sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();

        try {
            scenario.setFarmCustomStrategy((FarmCustomStrategy) session.load(FarmCustomStrategy.class, strategyId));
            Serializable save = session.save(scenario);
            PlantingProfitLogger.info("Saved scenario serializable : " + save);
            tx.commit();
            return Long.parseLong(save.toString());
        } catch (Exception e) {
            tx.rollback();
            PlantingProfitLogger.error(e);
        } finally {
            session.close();
        }
        return 0;
    }

    @Override
    public boolean updateScenario(int scenarioId, FarmStrategyScenario farmStrategyScenario) {
        final Session session = sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();

        try{
            session.update(farmStrategyScenario);
            tx.commit();
            return true;
        }catch(Exception e){
            tx.rollback();
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean deleteScenarioCropCpecificData(FarmStrategyScenario farmStrategyScenario) {
        final Session session = sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();

        try{
            session.createQuery("delete from FarmStrategyScenarioCropSpecific where farmStrategyScenario.id = :scenarioId").setParameter("scenarioId", farmStrategyScenario.getScenarioId()).executeUpdate();
            tx.commit();
            return true;
        }catch(Exception e){
            tx.rollback();
        }finally {
            session.close();
        }
        return false;
    }
}
