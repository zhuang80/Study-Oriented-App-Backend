package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqTutorProfile;
import java.util.List;

public interface WqTutorProfileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqTutorProfile record);

    WqTutorProfile selectByPrimaryKey(Integer id);

    List<WqTutorProfile> selectAll();

    int updateByPrimaryKey(WqTutorProfile record);
}