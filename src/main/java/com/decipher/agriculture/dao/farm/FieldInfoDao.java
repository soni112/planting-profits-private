package com.decipher.agriculture.dao.farm;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.data.farm.FieldInfo;

public interface FieldInfoDao {
    int saveFieldInfo(FieldInfo fieldInfo);

    boolean updateFieldInfo(FieldInfo fieldInfo);

    boolean deleteFieldInfoById(int id);

    boolean saveList(Set<FieldInfo> fieldInfoList);

    FieldInfo getFieldInfoById(int id);

    List<FieldInfo> getAllFieldsByFarmId(int farmId);

}
