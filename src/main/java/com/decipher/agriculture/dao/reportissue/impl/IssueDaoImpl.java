package com.decipher.agriculture.dao.reportissue.impl;

import com.decipher.agriculture.dao.reportissue.IssueDao;
import com.decipher.agriculture.data.reportissue.Issue;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 9/9/17 3:39 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@Repository
@Transactional
public class IssueDaoImpl implements IssueDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createIssue(Issue issue) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(issue);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
            PlantingProfitLogger.error(e.getMessage(), e);
            PlantingProfitLogger.debug(e);
        } finally {
            session.close();
        }

    }

}
