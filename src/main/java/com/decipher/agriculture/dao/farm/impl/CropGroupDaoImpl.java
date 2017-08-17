package com.decipher.agriculture.dao.farm.impl;

import java.util.ArrayList;
import java.util.List;

import com.decipher.agriculture.dao.farm.CropGroupDao;
import com.decipher.util.PlantingProfitLogger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CropsGroup;

@Repository
@Transactional
public class CropGroupDaoImpl implements CropGroupDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int saveCropGroup(CropsGroup cropGroup) {
        PlantingProfitLogger.info("inside saveCropGroup .. ");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(cropGroup);
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
    public List<CropsGroup> getAllCropGroupByFarm(int farmId) {
        PlantingProfitLogger.debug("get All Crop Group By Farm Id .. " + farmId);
        List<CropsGroup> cropGroupList = new ArrayList<CropsGroup>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM CropsGroup where farmInfo.id=:farmId ORDER BY cropsGroupName ASC");
            query.setParameter("farmId", farmId);
            cropGroupList = query.list();
            if (cropGroupList != null) {
//				PlantingProfitLogger.info("size -->>" + cropGroupList.size());
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropGroupList = null;
        } finally {
            session.close();
        }
        return cropGroupList;
    }

}
