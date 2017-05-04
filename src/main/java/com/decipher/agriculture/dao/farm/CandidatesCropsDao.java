package com.decipher.agriculture.dao.farm;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.data.farm.CandidatesCrops;

public interface CandidatesCropsDao
{
	CandidatesCrops getCandidatesCropByFarmName(String name);
	boolean isCandidatesCropExitsWithName(String name);
	int saveCandidatesCrop(CandidatesCrops candidatesCrop);
	boolean updateCandidatesCrop(CandidatesCrops candidatesCrops);
	boolean deleteCandidatesCropById(int id);
	CandidatesCrops getCandidatesCropById(int id);
	boolean saveCandidatesCropsList(Set<CandidatesCrops> candidatesCrops);
	List<CandidatesCrops> getAllCandidatesCropsById(int arcesId);
}
