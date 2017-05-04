package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.VariableCropProductionComponentUnitPricesDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.VariableCropProductionComponentUnitPrices;

@Repository
@Transactional
public class VariableCropProductionComponentUnitPricesDaoImpl implements
        VariableCropProductionComponentUnitPricesDao
{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int save(VariableCropProductionComponentUnitPrices unitPrices)
	{
		PlantingProfitLogger.info("inside saveComponentUnitPrices..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id = 0;
		try
		{
			tx = session.beginTransaction();
			id = (int) session.save(unitPrices);
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
	public boolean update(VariableCropProductionComponentUnitPrices unitPrices)
	{
		PlantingProfitLogger.info("inside updateComponentUnitPrices.." + unitPrices.getId());
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.update(unitPrices);
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
					.createQuery("delete from VariableCropProductionComponentUnitPrices where id = :id");
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
			Set<VariableCropProductionComponentUnitPrices> unitPricesList)
	{
		PlantingProfitLogger.info("inside save Component Unit Prices..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{

			tx = session.beginTransaction();
			for (VariableCropProductionComponentUnitPrices unitPrices : unitPricesList)
				session.save(unitPrices);
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
	public VariableCropProductionComponentUnitPrices getComponentUnitPricesById(
			int id)
	{
		PlantingProfitLogger.info("inside getView.." + id);
		Session session = sessionFactory.openSession();
		VariableCropProductionComponentUnitPrices unitPrices = null;
		try
		{
			Query query = session
					.createQuery("from VariableCropProductionComponentUnitPrices where id = :id");
			query.setParameter("id", id);
			Object obj = query.uniqueResult();
			if (obj != null)
			{
				if (obj instanceof VariableCropProductionComponentUnitPrices)
					unitPrices = (VariableCropProductionComponentUnitPrices) obj;
				else
					unitPrices = null;
			} else
			{
				unitPrices = null;
			}

		} catch (Exception e)
		{
			PlantingProfitLogger.error(e);
			unitPrices = null;
		} finally
		{
			session.close();
		}
		return unitPrices;
	}

}
