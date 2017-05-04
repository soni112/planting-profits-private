package com.decipher.agriculture.dao.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import com.decipher.agriculture.dao.farm.FarmInfoDao;
import com.decipher.agriculture.dao.strategy.FarmCustomStrategyDao;
import com.decipher.util.PlantingProfitLogger;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.decipher.agriculture.data.farm.FarmInfo;

@Repository
@Transactional
public class FarmCustomStrategyDaoImpl implements FarmCustomStrategyDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private FarmInfoDao farmInfoDao;

    /*@Override
	public boolean saveFarmCustomStrategy(int farmId,String[] resourceArray,String strategyName, Double potentialProfit, Double totalAcreage){
		FarmInfo farmInfo=farmInfoDao.getFarmInfoOldById(farmId,null,"CropResourceUsage",null,null, null, null, null);
		FarmCustomStrategy customStrategy =new FarmCustomStrategy();
		customStrategy.setStrategyName(strategyName);
		customStrategy.setStrategyForCrop(false);
		customStrategy.setStrategyForResourse(true);
		customStrategy.setFarmInfo(farmInfo);
		customStrategy.setPotentialProfit(potentialProfit);
		customStrategy.setTotalAcreage(totalAcreage);
		farmInfo.getFarmCustomStrategy().add(customStrategy);
		for (String str : resourceArray) {
			String[] array = str.split("#-#-#");
			FarmCustomStrategyForResource farmCustomStrategyForResource=new FarmCustomStrategyForResource();
			farmCustomStrategyForResource.setResourseName(array[0]);
			farmCustomStrategyForResource.setResourseValue(Long.parseLong(array[1]));
			farmCustomStrategyForResource.setFarmCustomStrategy(customStrategy);
			for(CropResourceUsage cropResourceUsage : farmInfo.getCropResourceUsage())
			{
				if(array[0].equals(cropResourceUsage.getCropResourceUse()))
				{
				farmCustomStrategyForResource.setCropResourceUsage(cropResourceUsage);
				}
			}
			customStrategy.getCustomStrategyForResourses().add(farmCustomStrategyForResource);
		}
		return saveFarmCustomStrategy(customStrategy);
	}*/

    @Override
    public int saveFarmCustomStrategy(FarmCustomStrategy customStrategy) {
        PlantingProfitLogger.info("inside saveFarmCustomStrategy..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int strategyId = 0;
        try {
            tx = session.beginTransaction();
            strategyId = (int) session.save(customStrategy);
            session.flush();
            tx.commit();
//			return true;
        } catch (Exception e) {
            tx.rollback();
            PlantingProfitLogger.error(e);
//			return false;
        } finally {
            session.close();
        }

        return strategyId;
    }

    @Override
    public boolean isFarmStrategyExitsWithName(String strategyName, int farmId) {
        PlantingProfitLogger.info("inside isFarmStrategyExitsWithName name : " + strategyName);
        boolean toRet = true;
        FarmCustomStrategy farmCustomStrategy = null;

        Session session = sessionFactory.openSession();
        try {
            Query userQuery = session.createQuery("from FarmCustomStrategy where lower(strategyName)=:name and farm.id=:farmId");
            userQuery.setParameter("name", strategyName.toLowerCase().trim());
            userQuery.setParameter("farmId", farmId);
            Object object = userQuery.uniqueResult();

            if (object != null) {
                if (object instanceof FarmCustomStrategy) {
                    farmCustomStrategy = (FarmCustomStrategy) object;
                    initializeLazy(farmCustomStrategy);
                    PlantingProfitLogger.info("Strategy exist with : " + farmCustomStrategy.getStrategyName());
                    toRet = true;
                } else {
                    PlantingProfitLogger.info("Strategy does not exist with : " + strategyName);
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
    public boolean isFarmStrategyExitsWithId(int strategyId, int farmId) {
        boolean toRet = true;
        FarmCustomStrategy farmCustomStrategy = null;

        Session session = sessionFactory.openSession();
        try {
            Query userQuery = session.createQuery("from FarmCustomStrategy where id=:id and farm.id=:farmId");
            userQuery.setParameter("id", strategyId);
            userQuery.setParameter("farmId", farmId);
            Object object = userQuery.uniqueResult();

            if (object != null) {
                if (object instanceof FarmCustomStrategy) {
                    farmCustomStrategy = (FarmCustomStrategy) object;
                    initializeLazy(farmCustomStrategy);
                    PlantingProfitLogger.info("Strategy exist with : " + farmCustomStrategy.getStrategyName());
                    toRet = true;
                } else {
                    PlantingProfitLogger.info("Strategy does not exist with : " + strategyId);
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
    public List<FarmCustomStrategy> getDataForCustomStrategy(int farmId) {

        PlantingProfitLogger.debug("getFarmCustomStrategyViewList .. " + farmId);
        List<FarmCustomStrategy> farmCustomStrategyList = new ArrayList<FarmCustomStrategy>();

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM FarmCustomStrategy where farm.id=:farmId");
            query.setParameter("farmId", farmId);
            farmCustomStrategyList = query.list();
            if (farmCustomStrategyList != null) {
//                PlantingProfitLogger.info("size -->>" + farmCustomStrategyList.size());
                for (FarmCustomStrategy farmCustomStrategy : farmCustomStrategyList) {
                    initializeLazy(farmCustomStrategy);
                }

            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            farmCustomStrategyList = null;
        } finally {
            session.close();
        }
        return farmCustomStrategyList;

    }

    /**
     * @author - Abhishek
     * @date - 21-12-2015
     */
    @Override
    public FarmCustomStrategy getDataForCustomStrategy(int farmId, int strategyId) {
        PlantingProfitLogger.debug("getFarmCustomStrategyViewList .. " + farmId);
        FarmCustomStrategy farmCustomStrategy = new FarmCustomStrategy();

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM FarmCustomStrategy where farm.id=:farmId and id=:strategyId");
            query.setParameter("farmId", farmId);
            query.setParameter("strategyId", strategyId);
            farmCustomStrategy = (FarmCustomStrategy) query.uniqueResult();
            if (farmCustomStrategy != null) {
                PlantingProfitLogger.info("farmCustomStrategy : " + farmCustomStrategy.getStrategyName());
                initializeLazy(farmCustomStrategy);

            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            farmCustomStrategy = null;
        } finally {
            session.close();
        }
        return farmCustomStrategy;
    }

    @Override
    public int updateFarmStrategy(FarmCustomStrategy farmCustomStrategy) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.getTransaction();

        try {
            transaction.begin();

            session.update(farmCustomStrategy);

            transaction.commit();
            return farmCustomStrategy.getId();
        } catch (Exception e) {
            transaction.rollback();
            PlantingProfitLogger.error(e);
            return 0;
        } finally {
            session.close();
        }

    }

    @Override
    public boolean deleteStrategy(int strategyId) {
        PlantingProfitLogger.info("inside deleteStrategy");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("CALL removeStrategy(:strategyId)");
            query.setParameter("strategyId", strategyId);
            int result = query.executeUpdate();
            PlantingProfitLogger.info("result deleted : " + result);
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
    public FarmCustomStrategy getStrategyByNameByFarm(String strategyName, FarmInfo farmInfo) {

        PlantingProfitLogger.info("inside getStrategyByNameByFarm");
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        FarmCustomStrategy farmCustomStrategy;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from FarmCustomStrategy where strategyName=:strategyName and farmInfo.id=:farmInfoId");
            query.setParameter("strategyName", strategyName);
            query.setParameter("farmInfoId", farmInfo.getId());
            farmCustomStrategy = (FarmCustomStrategy) query.uniqueResult();
            if (farmCustomStrategy != null)
                initializeLazy(farmCustomStrategy);

            tx.commit();

        } catch (Exception e) {
            farmCustomStrategy = null;
            tx.rollback();
            PlantingProfitLogger.error(e);
        } finally {
            session.close();
        }

        return farmCustomStrategy;

    }


    private void initializeLazy(FarmCustomStrategy farmCustomStrategy) {
        Hibernate.initialize(farmCustomStrategy.getFarmInfo());
        Hibernate.initialize(farmCustomStrategy.getFarm());
        Hibernate.initialize(farmCustomStrategy.getCustomStrategyForCrops());
        Hibernate.initialize(farmCustomStrategy.getCustomStrategyForResourses());
        Hibernate.initialize(farmCustomStrategy.getCustomStrategyForGroup());
        Hibernate.initialize(farmCustomStrategy.getFarmStrategyScenarios());

    }
}
