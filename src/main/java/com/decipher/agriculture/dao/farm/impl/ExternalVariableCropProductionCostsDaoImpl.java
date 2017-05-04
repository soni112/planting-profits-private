package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.ExternalVariableCropProductionCostsDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.ExternalVariableCropProductionCosts;

@Repository
@Transactional
public class ExternalVariableCropProductionCostsDaoImpl implements
        ExternalVariableCropProductionCostsDao
{
	

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int save(ExternalVariableCropProductionCosts productionCosts)
	{
		PlantingProfitLogger.info("inside saveProductionCosts..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id = 0;
		try
		{
			tx = session.beginTransaction();
			id = (int) session.save(productionCosts);
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
	public boolean update(ExternalVariableCropProductionCosts productionCosts)
	{
		PlantingProfitLogger.info("inside updateProductionCosts.." + productionCosts.getId());
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.update(productionCosts);
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
	public boolean delete(int id)
	{
		PlantingProfitLogger.info("inside deleteProductionCostsById..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{

			tx = session.beginTransaction();
			Query query = session
					.createQuery("delete from ExternalVariableCropProductionCosts where id = :id");
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
	public ExternalVariableCropProductionCosts getProductionCostsById(int id)
	{
		PlantingProfitLogger.info("inside getView.." + id);
		Session session = sessionFactory.openSession();
		ExternalVariableCropProductionCosts productionCosts = null;
		try
		{
			Query query = session
					.createQuery("from ExternalVariableCropProductionCosts where id = :id");
			query.setParameter("id", id);
			Object obj = query.uniqueResult();
			if (obj != null)
			{
				if (obj instanceof ExternalVariableCropProductionCosts)
					productionCosts = (ExternalVariableCropProductionCosts) obj;
				else
					productionCosts = null;
			} else
			{
				productionCosts = null;
			}

		} catch (Exception e)
		{
			PlantingProfitLogger.error(e);
			productionCosts = null;
		} finally
		{
			session.close();
		}
		return productionCosts;
	}

	@Override
	public boolean saveList(Set<ExternalVariableCropProductionCosts> productionCostsList)
	{
		PlantingProfitLogger.info("inside save External Variable Crop Production Costs..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{

			tx = session.beginTransaction();
			for (ExternalVariableCropProductionCosts productionCosts : productionCostsList)
				session.save(productionCosts);
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

}
