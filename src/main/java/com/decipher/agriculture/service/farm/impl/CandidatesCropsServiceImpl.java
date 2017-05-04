package com.decipher.agriculture.service.farm.impl;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.service.farm.CandidatesCropsService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.CandidatesCropsDao;
import com.decipher.agriculture.data.farm.CandidatesCrops;
import com.decipher.view.form.farmDetails.CandidatesCropsView;

@Repository
@Transactional
public class CandidatesCropsServiceImpl implements CandidatesCropsService
{
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CandidatesCropsDao candidatesCropsDao;

	@Override
	public CandidatesCrops getCandidatesCropByFarmName(String name)
	{		
		return candidatesCropsDao.getCandidatesCropByFarmName(name);
	}

	@Override
	public boolean isCandidatesCropExitsWithName(String name)
	{
		return candidatesCropsDao.isCandidatesCropExitsWithName(name);
	}

	@Override
	public int saveCandidatesCrop(CandidatesCrops candidatesCrop)
	{
		return candidatesCropsDao.saveCandidatesCrop(candidatesCrop);
	}

	@Override
	public boolean updateCandidatesCrop(CandidatesCrops candidatesCrops)
	{
		return candidatesCropsDao.updateCandidatesCrop(candidatesCrops);
	}

	@Override
	public boolean deleteCandidatesCropById(int id)
	{
		return candidatesCropsDao.deleteCandidatesCropById(id);
	}

	@Override
	public CandidatesCropsView getCandidatesCropById(int id)
	{
		return new CandidatesCropsView(candidatesCropsDao.getCandidatesCropById(id));
	}

	@Override
	public boolean saveCandidatesCropsList(Set<CandidatesCrops> candidatesCrops)
	{
		return candidatesCropsDao.saveCandidatesCropsList(candidatesCrops);
	}

	@Override
	public List<CandidatesCrops> getAllCandidatesCropsById(int arcesId)
	{
		return candidatesCropsDao.getAllCandidatesCropsById(arcesId);
	}

}
