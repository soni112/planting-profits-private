package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.CropUnitOfMeasureDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CropUnitOfMeasure;

@Repository
@Transactional
public class CropUnitOfMeasureDaoImpl implements CropUnitOfMeasureDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int saveCropUnitOfMeasure(CropUnitOfMeasure cropUnitOfMeasure) {
		PlantingProfitLogger.info("inside saveCropUnitOfMeasure .. ");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id = 0;
		try {
			tx = session.beginTransaction();
			id = (int) session.save(cropUnitOfMeasure);
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
	public boolean updateCropUnitOfMeasure(CropUnitOfMeasure cropUnitOfMeasure) {
		PlantingProfitLogger.info("inside updateCropUnitOfMeasure.." + cropUnitOfMeasure.getId());
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(cropUnitOfMeasure);
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
	public boolean deleteCropUnitOfMeasureById(int id) {
		PlantingProfitLogger.info("inside deleteCropUnitOfMeasureById..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("delete from CropUnitOfMeasure where id = :id");
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
	public CropUnitOfMeasure getCropUnitOfMeasureById(int id) {
		PlantingProfitLogger.info("inside getView.." + id);
		Session session = sessionFactory.openSession();
		CropUnitOfMeasure cropUnitOfMeasure = null;
		try {
			Query query = session
					.createQuery("from CropUnitOfMeasure where id = :id");
			query.setParameter("id", id);
			Object obj = query.uniqueResult();
			if (obj != null) {
				if (obj instanceof CropUnitOfMeasure)
					cropUnitOfMeasure = (CropUnitOfMeasure) obj;
				else
					cropUnitOfMeasure = null;
			} else {
				cropUnitOfMeasure = null;
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			cropUnitOfMeasure = null;
		} finally {
			session.close();
		}
		return cropUnitOfMeasure;
	}

	@Override
	public boolean saveCropUnitOfMeasureList(Set<CropUnitOfMeasure> cropUnitOfMeasureList) {
		PlantingProfitLogger.info("inside save Crop UnitOfMeasure List..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (CropUnitOfMeasure cropUnitOfMeasure : cropUnitOfMeasureList)
				session.save(cropUnitOfMeasure);
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
}