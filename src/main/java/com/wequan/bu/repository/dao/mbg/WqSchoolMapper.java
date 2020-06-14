package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqSchool;
import java.util.List;

public interface WqSchoolMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqSchool record);

    WqSchool selectByPrimaryKey(Short id);

    List<WqSchool> selectAll();

    int updateByPrimaryKey(WqSchool record);
}