package com.decipher.agriculture.dao.farm.impl;

import java.util.ArrayList;
import java.util.List;

import com.decipher.agriculture.dao.farm.CropFieldChociesDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CropFieldChocies;

@Repository
@Transactional
public class CropFieldChociesDaoImpl implements CropFieldChociesDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int saveCropFieldChocies(CropFieldChocies cropFieldChocies) {
        PlantingProfitLogger.info("inside saveCropFieldChocies .. ");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            id = (int) session.save(cropFieldChocies);
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
    public boolean updateCropFieldChocies(CropFieldChocies cropFieldChocies) {
        PlantingProfitLogger.info("inside updateCropFieldChocies.."
                + cropFieldChocies.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(cropFieldChocies);
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
    public boolean deleteCropFieldChociesById(int id) {
        PlantingProfitLogger.info("inside deleteCropFieldChociesById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Query query = session
                    .createQuery("delete from CropFieldChocies where id = :id");
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
    public CropFieldChocies getCropFieldChociesById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        CropFieldChocies cropFieldChocies = null;
        try {
            Query query = session.createQuery("from CropFieldChocies where id = :id");
//			Query query = session.createQuery("from CropFieldChocies where id = :id order by CropFieldChocies.cropType.cropName");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof CropFieldChocies)
                    cropFieldChocies = (CropFieldChocies) obj;
                else
                    cropFieldChocies = null;
            } else {
                cropFieldChocies = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropFieldChocies = null;
        } finally {
            session.close();
        }
        return cropFieldChocies;
    }

    @Override
    public List<CropFieldChocies> getAllCropFiledsCrop(int cropId) {
        PlantingProfitLogger.debug("get All Crop Fileds Crop .. " + cropId);
        List<CropFieldChocies> cropFieldChociesList = new ArrayList<CropFieldChocies>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session
                    .createQuery("FROM CropFieldChocies where cropType.id=:cropId");
            query.setParameter("cropId", cropId);
            cropFieldChociesList = query.list();
            if (cropFieldChociesList != null) {
//				PlantingProfitLogger.info("size -->>" + cropFieldChociesList.size());
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropFieldChociesList = null;
        } finally {
            session.close();
        }
        return cropFieldChociesList;
    }

    @Override
    public List<CropFieldChocies> getAllCropFiledsCropIds(Integer[] cropId) {
        PlantingProfitLogger.debug("get All Crop Fileds Crop Ids ");
        List<CropFieldChocies> cropFieldChociesList = new ArrayList<CropFieldChocies>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM CropFieldChocies where cropType.id in (:cropId) order by cropType.cropName");
            query.setParameterList("cropId", cropId);
            cropFieldChociesList = query.list();
            if (cropFieldChociesList != null) {
//				PlantingProfitLogger.info("size -->>" + cropFieldChociesList.size());
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropFieldChociesList = null;
        } finally {
            session.close();
        }
        return cropFieldChociesList;
    }

}
