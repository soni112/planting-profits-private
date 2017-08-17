package com.decipher.agriculture.dao.farm.impl;

import java.util.ArrayList;
import java.util.List;

import com.decipher.agriculture.dao.farm.OptionalCropProductionCostsDetailsDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.OptionalCropProductionCostsDetails;

@Repository
@Transactional
public class OptionalCropProductionCostsDetailsDaoImpl implements OptionalCropProductionCostsDetailsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<OptionalCropProductionCostsDetails> getAllCropProductionCostsDetailsByCrop(
            int cropId) {
        PlantingProfitLogger.debug("get Optional Crop Production Costs Details .. " + cropId);
        List<OptionalCropProductionCostsDetails> cropProductionCostsDetailsList = new ArrayList<OptionalCropProductionCostsDetails>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session
                    .createQuery("FROM OptionalCropProductionCostsDetails where cropType.id=:cropId");
            query.setParameter("cropId", cropId);
            cropProductionCostsDetailsList = query.list();
            if (cropProductionCostsDetailsList != null) {
//				PlantingProfitLogger.info("size -->>" + cropProductionCostsDetailsList.size());
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropProductionCostsDetailsList = null;
        } finally {
            session.close();
        }
        return cropProductionCostsDetailsList;
    }

    @Override
    public List<OptionalCropProductionCostsDetails> getAllCropProductionCostsDetailsByCropIds(
            Integer[] cropId) {
        PlantingProfitLogger.debug("getAllCropProductionCostsDetailsByCropIds ");
        List<OptionalCropProductionCostsDetails> cropProductionCostsDetailsList = new ArrayList<OptionalCropProductionCostsDetails>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session
                    .createQuery("FROM OptionalCropProductionCostsDetails where cropType.id in (:cropId) order by CostComponentsName");
            query.setParameterList("cropId", cropId);
            cropProductionCostsDetailsList = query.list();
            if (cropProductionCostsDetailsList != null) {
//				PlantingProfitLogger.info("size -->>" + cropProductionCostsDetailsList.size());
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            cropProductionCostsDetailsList = null;
        } finally {
            session.close();
        }
        return cropProductionCostsDetailsList;
    }

}
