package com.decipher.agriculture.dao.farm;

import java.util.Set;

import com.decipher.agriculture.data.farm.SummaryCropInfo;

public interface SummaryCropInfoDao {
    int save(SummaryCropInfo summaryCropInfo);

    boolean update(SummaryCropInfo summaryCropInfo);

    boolean deleteById(int id);

    boolean saveList(Set<SummaryCropInfo> summaryCropInfoList);

    SummaryCropInfo getSummaryCropInfoById(int id);
}
