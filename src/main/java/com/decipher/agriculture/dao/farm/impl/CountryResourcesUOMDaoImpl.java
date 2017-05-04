package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.CountryResourcesUOMDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CountryResourcesUOM;

@Repository
@Transactional
public class CountryResourcesUOMDaoImpl implements CountryResourcesUOMDao
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int saveCountryResourcesUOM(CountryResourcesUOM countryResourcesUOM)
	{
		PlantingProfitLogger.info("inside saveCountryResourcesUOM..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id = 0;
		try
		{
			tx = session.beginTransaction();
			id = (int) session.save(countryResourcesUOM);
			tx.commit();
			return id;
		} catch (Exception e)
		{
			id = 0;
			tx.rollback();
			PlantingProfitLogger.error(e);
			return id;
		} finally
		{
			session.close();
		}
	}

	@Override
	public boolean updateCountryResourcesUOM(
			CountryResourcesUOM countryResourcesUOM)
	{
		PlantingProfitLogger.info("inside updateCountryResourcesUOM.." + countryResourcesUOM.getId());
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(countryResourcesUOM);
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
	public boolean deleteCountryResourcesUOMById(int id)
	{
		PlantingProfitLogger.info("inside deleteCountryResourcesUOMById..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			Query query = session.createQuery("delete from CountryResourcesUOM where id = :id");
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
	public CountryResourcesUOM getCountryResourcesUOMById(int id)
	{
		PlantingProfitLogger.info("inside getView.." + id);
		Session session = sessionFactory.openSession();
		CountryResourcesUOM countryResourcesUOM = null;
		try {
			Query query = session.createQuery("from CountryResourcesUOM where id = :id");
			query.setParameter("id", id);
			Object obj = query.uniqueResult();
			if (obj != null) {
				if (obj instanceof CountryResourcesUOM)
					countryResourcesUOM = (CountryResourcesUOM) obj;
				else
					countryResourcesUOM = null;
			} else {
				countryResourcesUOM = null;
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			countryResourcesUOM = null;
		} finally {
			session.close();
		}
		return countryResourcesUOM;
	}

	@Override
	public boolean saveCountryResourcesUOMList(
			Set<CountryResourcesUOM> countryResourcesUOMList)
	{
		PlantingProfitLogger.info("inside save Country ResourcesUOM List..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			for (CountryResourcesUOM countryResourcesUOM : countryResourcesUOMList)
				session.save(countryResourcesUOM);
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
