package com.decipher.agriculture.service.farm;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.data.farm.CandidatesCrops;
import com.decipher.view.form.farmDetails.CandidatesCropsView;

public interface CandidatesCropsService
{
	CandidatesCrops getCandidatesCropByFarmName(String name);
	boolean isCandidatesCropExitsWithName(String name);
	int saveCandidatesCrop(CandidatesCrops candidatesCrop);
	boolean updateCandidatesCrop(CandidatesCrops candidatesCrops);
	boolean deleteCandidatesCropById(int id);
	CandidatesCropsView getCandidatesCropById(int id);
	boolean saveCandidatesCropsList(Set<CandidatesCrops> candidatesCrops);
	List<CandidatesCrops> getAllCandidatesCropsById(int arcesId);
}
