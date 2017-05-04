package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.VariableCropProductionCostsComponentsDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.VariableCropProductionCostsComponents;

@Repository
@Transactional
public class VariableCropProductionCostsComponentsDaoImpl implements
        VariableCropProductionCostsComponentsDao
{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int save(VariableCropProductionCostsComponents costsComponents)
	{
		PlantingProfitLogger.info("inside saveVariableCropProductionCostsComponents..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id = 0;
		try
		{
			tx = session.beginTransaction();
			id = (int) session.save(costsComponents);
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
	public boolean update(VariableCropProductionCostsComponents costsComponents)
	{
		PlantingProfitLogger.info("inside updateVariableCropProductionCostsComponents.."
				+ costsComponents.getId());
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.update(costsComponents);
			tx.commit();
			return true;
		} catch (Exception e)
		{
			tx.rollback();
			PlantingProfitLogger.error(e);
			return false;
		} finally
		{
			session.close();
		}
	}

	@Override
	public boolean deleteById(int id)
	{
		PlantingProfitLogger.info("inside deleteById..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			Query query = session
					.createQuery("delete from VariableCropProductionCostsComponents where id = :id");
			query.setParameter("id", id);
			int result = query.executeUpdate();
			PlantingProfitLogger.info("result deleted : " + result);
			tx.commit();
			return true;
		} catch (Exception e)
		{
			tx.rollback();
			PlantingProfitLogger.info("Exception Occurs -->>" + e.toString());
			return false;
		} finally
		{
			session.close();
		}
	}

	@Override
	public boolean saveList(
			Set<VariableCropProductionCostsComponents> costsComponentsList)
	{
		PlantingProfitLogger.info("inside save Component Unit Prices..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{

			tx = session.beginTransaction();
			for (VariableCropProductionCostsComponents costsComponents : costsComponentsList)
				session.save(costsComponents);
			tx.commit();

			return true;
		} catch (Exception e)
		{
			if (tx != null && tx.isActive())
				tx.rollback();
			PlantingProfitLogger.error(e);
			return false;
		} finally
		{
			session.close();
		}
	}

	@Override
	public VariableCropProductionCostsComponents getCostsComponentsById(
			int id)
	{
		PlantingProfitLogger.info("inside getView.." + id);
		Session session = sessionFactory.openSession();
		VariableCropProductionCostsComponents costsComponent = null;
		try
		{
			Query query = session
					.createQuery("from VariableCropProductionCostsComponents where id = :id");
			query.setParameter("id", id);
			Object obj = query.uniqueResult();
			if (obj != null)
			{
				if (obj instanceof VariableCropProductionCostsComponents)
					costsComponent = (VariableCropProductionCostsComponents) obj;
				else
					costsComponent = null;
			} else
			{
				costsComponent = null;
			}

		} catch (Exception e)
		{
			PlantingProfitLogger.error(e);
			costsComponent = null;
		} finally
		{
			session.close();
		}
		return costsComponent;
	}

}
