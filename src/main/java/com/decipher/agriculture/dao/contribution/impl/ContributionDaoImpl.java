package com.decipher.agriculture.dao.contribution.impl;

import com.decipher.agriculture.dao.contribution.ContributionDao;
import com.decipher.agriculture.data.Contribution;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Raja Dushyant Vashishtha
 */
@Repository
public class ContributionDaoImpl implements ContributionDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Contribution contribution) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(contribution);
            transaction.commit();
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public Contribution update(Contribution contribution) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(contribution);
            transaction.commit();
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            transaction.rollback();
        } finally {
            session.close();
        }
        return contribution;
    }

    @Override
    public boolean delete(Contribution contribution) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        boolean status;
        try {
            transaction.begin();
            session.delete(contribution);
            transaction.commit();
            status = true;
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            transaction.rollback();
            status = false;
        } finally {
            session.close();
        }
        return status;
    }

    @Override
    public Contribution getById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Contribution contribution = null;
        try {
            transaction.begin();
            Query query = session.createQuery("from Contribution where id = :id");
            query.setParameter("id", id);
            contribution = (Contribution) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            transaction.rollback();
        } finally {
            session.close();
        }
        return contribution;
    }
}
