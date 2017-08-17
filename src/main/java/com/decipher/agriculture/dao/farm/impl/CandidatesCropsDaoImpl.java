package com.decipher.agriculture.dao.farm.impl;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.dao.farm.CandidatesCropsDao;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.CandidatesCrops;

@Repository
@Transactional
public class CandidatesCropsDaoImpl implements CandidatesCropsDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public CandidatesCrops getCandidatesCropByFarmName(String name) {
        PlantingProfitLogger.info("inside getCropByName name : " + name);
        CandidatesCrops candidatesCrops = null;

        Session session = sessionFactory.openSession();
        try {
            Query userQuery = session
                    .createQuery("from CandidatesCrops where farm_Name=:name");
            userQuery.setParameter("name", name.toLowerCase().trim());
            Object object = userQuery.uniqueResult();

            if (object != null) {
                if (object instanceof CandidatesCrops) {
                    candidatesCrops = (CandidatesCrops) object;
                    PlantingProfitLogger.info("CandidatesCrops exist with : "
                            + candidatesCrops.getCandidate_Crops());
                } else {
                    PlantingProfitLogger.info("CandidatesCrops does not exist with : " + name);
                    candidatesCrops = null;
                }

            } else {
                PlantingProfitLogger.info("CandidatesCrops does not exist with : " + name);
                candidatesCrops = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            candidatesCrops = null;
        } finally {
            session.close();
        }
        return candidatesCrops;
    }

    @Override
    public boolean isCandidatesCropExitsWithName(String name) {
        PlantingProfitLogger.info("inside isCandidatesCropExitsWithName name : " + name);
        boolean toRet = true;
        CandidatesCrops candidatesCrops = null;

        Session session = sessionFactory.openSession();
        try {
            Query userQuery = session
                    .createQuery("from CandidatesCrops where lower(Candidate_Crops)=:name");

            userQuery.setParameter("name", name.toLowerCase());
            Object object = userQuery.uniqueResult();

            if (object != null) {
                if (object instanceof CandidatesCrops) {
                    candidatesCrops = (CandidatesCrops) object;
                    PlantingProfitLogger.info("CandidatesCrops exist with : "
                            + candidatesCrops.getCandidate_Crops());
                    toRet = true;
                } else {
                    PlantingProfitLogger.info("CandidatesCrops does not exist with : " + name);
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
    public int saveCandidatesCrop(CandidatesCrops candidatesCrop) {
        PlantingProfitLogger.info("inside saveCandidatesCrop..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            // cropType.setCropName(cropType.getCropName().toLowerCase());
            tx = session.beginTransaction();
            id = (int) session.save(candidatesCrop);
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
    public boolean updateCandidatesCrop(CandidatesCrops candidatesCrops) {
        PlantingProfitLogger.info("inside updateCandidatesCrop.."
                + candidatesCrops.getCandidate_Crops());
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(candidatesCrops);
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
    public boolean deleteCandidatesCropById(int id) {
        PlantingProfitLogger.info("inside deleteCandidatesCropById..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Query query = session
                    .createQuery("delete from CandidatesCrop where id = :id");
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
    public CandidatesCrops getCandidatesCropById(int id) {
        PlantingProfitLogger.info("inside getView.." + id);
        Session session = sessionFactory.openSession();
        CandidatesCrops candidatesCrops = null;
        try {
            Query query = session
                    .createQuery("from CandidatesCrops where id = :id");
            query.setParameter("id", id);
            Object obj = query.uniqueResult();
            if (obj != null) {
                if (obj instanceof CandidatesCrops)
                    candidatesCrops = (CandidatesCrops) obj;
                else
                    candidatesCrops = null;
            } else {
                candidatesCrops = null;
            }

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            candidatesCrops = null;
        } finally {
            session.close();
        }
        return candidatesCrops;
    }

    @Override
    public boolean saveCandidatesCropsList(Set<CandidatesCrops> candidatesCrops) {
        PlantingProfitLogger.info("inside save Crop Type List..");
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            for (CandidatesCrops candidatesCropsList : candidatesCrops)
                session.save(candidatesCropsList);
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
    public List<CandidatesCrops> getAllCandidatesCropsById(int arcesId) {
        return null;
        /*PlantingProfitLogger.debug("get All Crop Type By Arces Id .. " + arcesId);
		List<CandidatesCrops> CandidatesCropsView = new ArrayList<CandidatesCrops>();

		Session session = sessionFactory.openSession();
		try {
			Query query = session
					.createQuery("FROM CandidatesCrops where acres.id=:arcesId");
			query.setParameter("arcesId", arcesId);
			CandidatesCropsView = query.list();
			if (CandidatesCropsView != null) {
				PlantingProfitLogger.info("size -->>" + CandidatesCropsView.size());
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			CandidatesCropsView = null;
		} finally {
			session.close();
		}
		return CandidatesCropsView;*/
    }

}
