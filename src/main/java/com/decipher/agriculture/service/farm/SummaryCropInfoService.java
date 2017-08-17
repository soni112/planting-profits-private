package com.decipher.agriculture.service.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.SummaryCropInfo;
import com.decipher.view.form.farmDetails.SummaryCropInfoView;

public interface SummaryCropInfoService {
    int save(SummaryCropInfo summaryCropInfo);

    boolean update(SummaryCropInfo summaryCropInfo);

    boolean deleteById(int id);

    boolean saveList(Set<SummaryCropInfo> summaryCropInfoList);

    SummaryCropInfoView getSummaryCropInfoById(int id);
}
