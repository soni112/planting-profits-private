package com.decipher.agriculture.dao.util.impl;

import com.decipher.agriculture.dao.util.HibernateUtils;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by raja on 12/26/15.
 */

@Repository
@Transactional
public class HibernateUtilsImpl implements HibernateUtils {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Object getPersistedObject(Class<?> clazz, Serializable primaryKey) {

        Session session = sessionFactory.openSession();

        Object o = null;
        try{
            o = session.get(clazz, primaryKey);
        }catch(Exception e){
            PlantingProfitLogger.error(e);
            e.printStackTrace();
        }
        return o;
    }


}
