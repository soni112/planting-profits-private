package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.ResourceTypeDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.ResourceType;

@Repository
@Transactional
public class ResourceTypeDaoImpl implements ResourceTypeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(ResourceType resourceType) {
        PlantingProfitLogger.info("inside saveResourceType..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(resourceType);
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
    public boolean update(ResourceType resourceType) {
        PlantingProfitLogger.info("inside updateResourceType.." + resourceType.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(resourceType);
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
                    .createQuery("delete from ResourceType where id = :id");
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
    public boolean saveList(Set<ResourceType> resourceTypeList) {
        PlantingProfitLogger.info("inside save Crop Production Costs..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (ResourceType resourceType : resourceTypeList)
                session.save(resourceType);
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
    public ResourceType getCropProductionCostById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        ResourceType resourceType = null;
        try {
            Query query = session
                    .createQuery("from ResourceType where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof ResourceType)
                    resourceType = (ResourceType) obj;
                else
                    resourceType = null;
            } else {
                resourceType = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            resourceType = null;
        } finally {
            session.close();
        }
        return resourceType;
    }

}
