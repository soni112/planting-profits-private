package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.InternallyCalculatedVariableCropProductionDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.InternallyCalculatedVariableCropProduction;

@Repository
@Transactional
public class InternallyCalculatedVariableCropProductionDaoImpl implements
        InternallyCalculatedVariableCropProductionDao
{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int save(InternallyCalculatedVariableCropProduction cropProduction)
	{
		PlantingProfitLogger.info("inside saveInternallyCalculatedVariableCropProduction..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id = 0;
		try
		{
			tx = session.beginTransaction();
			id = (int) session.save(cropProduction);
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
	public boolean update(
			InternallyCalculatedVariableCropProduction cropProduction)
	{
		PlantingProfitLogger.info("inside updateInternallyCalculatedVariableCropProduction.."
				+ cropProduction.getId());
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.update(cropProduction);
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
					.createQuery("delete from InternallyCalculatedVariableCropProduction where id = :id");
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
			Set<InternallyCalculatedVariableCropProduction> cropProductionList)
	{
		PlantingProfitLogger.info("inside save FieldInfo..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{

			tx = session.beginTransaction();
			for (InternallyCalculatedVariableCropProduction cropProduction : cropProductionList)
				session.save(cropProduction);
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
	public InternallyCalculatedVariableCropProduction getCropProductionById(
			int id)
	{
		PlantingProfitLogger.info("inside getView.." + id);
		Session session = sessionFactory.openSession();
		InternallyCalculatedVariableCropProduction internalCrop = null;
		try
		{
			Query query = session
					.createQuery("from InternallyCalculatedVariableCropProduction where id = :id");
			query.setParameter("id", id);
			Object obj = query.uniqueResult();
			if (obj != null)
			{
				if (obj instanceof InternallyCalculatedVariableCropProduction)
					internalCrop = (InternallyCalculatedVariableCropProduction) obj;
				else
					internalCrop = null;
			} else
			{
				internalCrop = null;
			}

		} catch (Exception e)
		{
			PlantingProfitLogger.error(e);
			internalCrop = null;
		} finally
		{
			session.close();
		}
		return internalCrop;
	}

}
