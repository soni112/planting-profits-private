package com.decipher.agriculture.dao.farm.impl;

import java.util.Set;

import com.decipher.agriculture.dao.farm.CropResourceUsageFieldDifferencesDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CropResourceUsageFieldDifferences;
@Repository
@Transactional
public class CropResourceUsageFieldDifferencesDaoImpl implements CropResourceUsageFieldDifferencesDao
{
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public int saveCropResourceUsageFieldDifferences(Set<CropResourceUsageFieldDifferences> obj)
	{
		PlantingProfitLogger.info("inside saveCropResourceUsageFieldDifferences..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id = 0;
		try
		{
			tx = session.beginTransaction();
			for(CropResourceUsageFieldDifferences ob:obj)
			{
				id = (int) session.save(ob);
			}
			
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

}
