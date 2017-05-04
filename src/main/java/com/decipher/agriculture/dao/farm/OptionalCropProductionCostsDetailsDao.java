package com.decipher.agriculture.dao.farm;

import java.util.List;

import com.decipher.agriculture.data.farm.OptionalCropProductionCostsDetails;

public interface OptionalCropProductionCostsDetailsDao {
    List<OptionalCropProductionCostsDetails> getAllCropProductionCostsDetailsByCrop(int cropId);

    List<OptionalCropProductionCostsDetails> getAllCropProductionCostsDetailsByCropIds(Integer[] cropId);
}
