package com.decipher.agriculture.service.farm;

import java.util.List;

import com.decipher.view.form.farmDetails.OptionalCropProductionCostsDetailsView;

public interface OptionalCropProductionCostsDetailsService {
    List<OptionalCropProductionCostsDetailsView> getAllCropProductionCostsDetailsByCropIds(Integer[] cropId);

    List<OptionalCropProductionCostsDetailsView> getAllCropProductionCostsDetailsByCrop(int cropId);
}
