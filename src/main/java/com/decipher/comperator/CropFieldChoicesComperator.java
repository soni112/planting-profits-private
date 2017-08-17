package com.decipher.comperator;

import java.util.Comparator;

import com.decipher.agriculture.data.farm.CropFieldChocies;

public class CropFieldChoicesComperator implements Comparator<CropFieldChocies> {

    @Override
    public int compare(CropFieldChocies o1, CropFieldChocies o2) {
        return o1.getCropType().getCropName()
                .compareTo(o2.getCropType().getCropName());
    }

}
