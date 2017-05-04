package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.CropConsDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CropCons;
@Repository
@Transactional
public class CropConsDaoImpl implements CropConsDao
{

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public int saveCropCons(CropCons cropCons)
	{
		PlantingProfitLogger.info("inside saveCropCons..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id = 0;
		try {
			tx = session.beginTransaction();
			id = (int) session.save(cropCons);
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
	public boolean updateCropCons(CropCons cropCons)
	{
		PlantingProfitLogger.info("inside updateCropCons.." + cropCons.getId());
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(cropCons);
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
	public boolean deleteCropConsById(int id)
	{
		PlantingProfitLogger.info("inside deleteCropConsById..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			Query query = session.createQuery("delete from CropCons where id = :id");
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
	public CropCons getCropConsById(int id)
	{
		PlantingProfitLogger.info("inside getView.." + id);
		Session session = sessionFactory.openSession();
		CropCons cropCons = null;
		try {
			Query query = session.createQuery("from CropCons where id = :id");
			query.setParameter("id", id);
			Object obj = query.uniqueResult();
			if (obj != null) {
				if (obj instanceof CropCons)
					cropCons = (CropCons) obj;
				else
					cropCons = null;
			} else {
				cropCons = null;
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			cropCons = null;
		} finally {
			session.close();
		}
		return cropCons;
	}

	@Override
	public boolean saveCropConsList(Set<CropCons> cropConsList)
	{
		PlantingProfitLogger.info("inside save Crop Cons List..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			for (CropCons cropCons : cropConsList)
				session.save(cropCons);
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
