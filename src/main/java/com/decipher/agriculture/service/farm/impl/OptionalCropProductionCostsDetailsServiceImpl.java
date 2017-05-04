package com.decipher.agriculture.service.farm.impl;

import java.util.ArrayList;
import java.util.List;

import com.decipher.agriculture.service.farm.OptionalCropProductionCostsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.farm.OptionalCropProductionCostsDetails;
import com.decipher.view.form.farmDetails.OptionalCropProductionCostsDetailsView;
import com.decipher.agriculture.dao.farm.OptionalCropProductionCostsDetailsDao;

@Repository
@Transactional
public class OptionalCropProductionCostsDetailsServiceImpl implements OptionalCropProductionCostsDetailsService {
    @Autowired
    private OptionalCropProductionCostsDetailsDao OptionalCropProductionCostsDetailsDao;

    @Override
    public List<OptionalCropProductionCostsDetailsView> getAllCropProductionCostsDetailsByCrop(
            int cropId) {
        List<OptionalCropProductionCostsDetailsView> CropProductionCostsDetailsView = new ArrayList<OptionalCropProductionCostsDetailsView>();
        List<OptionalCropProductionCostsDetails> cropProductionCostsDetailsList = OptionalCropProductionCostsDetailsDao.getAllCropProductionCostsDetailsByCrop(cropId);
        if (cropProductionCostsDetailsList != null) {
            for (OptionalCropProductionCostsDetails detailsList : cropProductionCostsDetailsList)
                CropProductionCostsDetailsView.add(new OptionalCropProductionCostsDetailsView(detailsList));
        }
        return CropProductionCostsDetailsView;
    }

    @Override
    public List<OptionalCropProductionCostsDetailsView> getAllCropProductionCostsDetailsByCropIds(
            Integer[] cropId) {
        List<OptionalCropProductionCostsDetailsView> CropProductionCostsDetailsView = new ArrayList<OptionalCropProductionCostsDetailsView>();
        List<OptionalCropProductionCostsDetails> cropProductionCostsDetailsList = OptionalCropProductionCostsDetailsDao.getAllCropProductionCostsDetailsByCropIds(cropId);
        if (cropProductionCostsDetailsList != null) {
            for (OptionalCropProductionCostsDetails detailsList : cropProductionCostsDetailsList)
                CropProductionCostsDetailsView.add(new OptionalCropProductionCostsDetailsView(detailsList));
        }
        return CropProductionCostsDetailsView;
    }


}
