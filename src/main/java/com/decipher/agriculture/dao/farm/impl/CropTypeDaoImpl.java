package com.decipher.agriculture.dao.farm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.decipher.agriculture.dao.farm.CropTypeDao;
import com.decipher.util.PlantingProfitLogger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CropType;
@Repository
@Transactional
public class CropTypeDaoImpl implements CropTypeDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public CropType getCropByName(String name) {
		PlantingProfitLogger.info("inside getCropByName name : " + name);
		CropType cropType = null;

		Session session = sessionFactory.openSession();
		try {
			Query userQuery = session.createQuery("from CropType where cropName=:name");
			userQuery.setParameter("name", name.toLowerCase().trim());
			Object object = userQuery.uniqueResult();

			if (object != null) {
				if (object instanceof CropType) {
					cropType = (CropType) object;
					PlantingProfitLogger.info("Crop exist with : " + cropType.getCropName());
				} else {
					PlantingProfitLogger.info("Crop does not exist with : " + name);
					cropType = null;
				}

			} else {
				PlantingProfitLogger.info("Crop does not exist with : " + name);
				cropType = null;
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			cropType = null;
		} finally {
			session.close();
		}
		return cropType;
	}

	@Override
	public boolean isCropExitsWithName(String name) {
		PlantingProfitLogger.info("inside isCropExitsWithName name : " + name);
		boolean toRet = true;
		CropType cropType = null;

		Session session = sessionFactory.openSession();
		try {
			Query userQuery = session.createQuery("from CropType where lower(cropName)=:name");

			userQuery.setParameter("name", name.toLowerCase());
			Object object = userQuery.uniqueResult();

			if (object != null) {
				if (object instanceof CropType) {
					cropType = (CropType) object;
					PlantingProfitLogger.info("Crop exist with : " + cropType.getCropName());
					toRet = true;
				} else {
					PlantingProfitLogger.info("Crop does not exist with : " + name);
					toRet = false;
				}

			} else {
				PlantingProfitLogger.info("Object is Null  ");
				toRet = false;
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			toRet = false;
		} finally {
			session.close();
		}

		return toRet;
	}

	@Override
	public int saveCrop(CropType cropType) {
		PlantingProfitLogger.info("inside saveUser..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id = 0;
		try {
			tx = session.beginTransaction();
			id = (int) session.save(cropType);
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
	public boolean updateCrop(CropType cropType) {
		PlantingProfitLogger.info("inside updateCrop.." + cropType.getCropName());
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(cropType);
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
	public boolean deleteCropById(int id) {
		PlantingProfitLogger.info("inside deleteFarmInfoById..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			Query query = session
					.createQuery("delete from CropType where id = :id");
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
	public CropType getCropById(int id) {
		PlantingProfitLogger.info("inside getView.." + id);
		Session session = sessionFactory.openSession();
		CropType cropType = null;
		try {
			Query query = session.createQuery("from CropType where id = :id");
			query.setParameter("id", id);
			Object obj = query.uniqueResult();
			if (obj != null) {
				if (obj instanceof CropType)
					cropType = (CropType) obj;
				else
					cropType = null;
			} else {
				cropType = null;
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			cropType = null;
		} finally {
			session.close();
		}
		return cropType;
	}

	@Override
	public boolean saveCropTypeList(Set<CropType> cropTypeList) {

		PlantingProfitLogger.info("inside save Crop Type List..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			for (CropType cropType : cropTypeList)
				session.save(cropType);
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

	@Override
	public List<CropType> getAllCropTypeByArcesId(int arcesId) {
		PlantingProfitLogger.debug("get All Crop Type By Arces Id .. " + arcesId);
		List<CropType> cropTypeView = new ArrayList<CropType>();

		Session session = sessionFactory.openSession();
		try {
			Query query = session.createQuery("FROM CropType where acres.id=:arcesId");
			query.setParameter("arcesId", arcesId);
			cropTypeView = query.list();
			if (cropTypeView != null) {
//				PlantingProfitLogger.info("size -->>" + cropTypeView.size());
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			cropTypeView = null;
		} finally {
			session.close();
		}
		return cropTypeView;
	}

	@Override
	public boolean deleteCropByFarmId(int farmId) {
		PlantingProfitLogger.info("inside deleteCropByFarmId..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			Query query = session.createQuery("delete from CropType where farmInfo.id = :farmId");
			query.setParameter("farmId", farmId);
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
	public List<CropType> getAllCropByFarm(int farmId)
	{
		PlantingProfitLogger.debug("get All Crop Type By farm Id .. " + farmId);
		List<CropType> cropList = new ArrayList<CropType>();

		Session session = sessionFactory.openSession();
		try {
			Query query = session.createQuery("FROM CropType where farmInfo.id=:farmId ORDER BY cropName ASC");
			query.setParameter("farmId", farmId);
			cropList = query.list();
			if (cropList != null) {
//				PlantingProfitLogger.info("size -->>" + cropList.size());
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			cropList = null;
		} finally {
			session.close();
		}
		return cropList;
	}

	@Override
	public List<CropType> getAllCropByFarmIds(List<Integer> farmId)
	{
		PlantingProfitLogger.debug("getAllCropByFarmIds ");
		List<CropType> cropType = new ArrayList<CropType>();

		Session session = sessionFactory.openSession();
		try {
			Query query = session.createQuery("FROM CropType where farmInfo.id in (:farmId)");
			query.setParameterList("farmId", farmId);
			cropType = query.list();
			if (cropType != null) {
//				PlantingProfitLogger.info("size -->>" + cropType.size());
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			cropType = null;
		} finally {
			session.close();
		}
		return cropType;
	}

	@Override
	public List<CropType> getAllCropByGroupIds(Integer[] groupId)
	{
		PlantingProfitLogger.debug("get All Crop By GroupIds ");
		List<CropType> cropType = new ArrayList<CropType>();

		Session session = sessionFactory.openSession();
		try {
			Query query = session.createQuery("FROM CropType where cropsGroup.id in (:groupId) ORDER BY cropsGroup.cropsGroupName ASC");
			query.setParameterList("groupId", groupId);
			cropType = query.list();
			if (cropType != null) {
//				PlantingProfitLogger.info("size -->>" + cropType.size());
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			cropType = null;
		} finally {
			session.close();
		}
		return cropType;
	}

}
