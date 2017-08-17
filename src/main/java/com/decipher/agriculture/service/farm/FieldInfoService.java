package com.decipher.agriculture.service.farm;

import java.util.List;
import java.util.Set;

import com.decipher.agriculture.data.farm.FieldInfo;
import com.decipher.view.form.farmDetails.FieldInfoView;
//import com.decipher.view.form.farmDetails.FieldInfoView;

public interface FieldInfoService {
    int saveFieldInfo(FieldInfo fieldInfo);

    boolean updateFieldInfo(FieldInfo fieldInfo);

    boolean deleteFieldInfoById(int id);

    boolean saveList(Set<FieldInfo> fieldInfoList);

    //FieldInfoView getFieldInfoById(int id);
    List<FieldInfoView> getAllFieldsByFarmId(int farmId);

    List<FieldInfo> getAllFieldsByFarm(int farmId);
}
