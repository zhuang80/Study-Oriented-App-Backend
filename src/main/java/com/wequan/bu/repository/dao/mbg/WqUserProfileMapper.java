package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqUserProfile;
import java.util.List;

public interface WqUserProfileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqUserProfile record);

    WqUserProfile selectByPrimaryKey(Integer id);

    List<WqUserProfile> selectAll();

    int updateByPrimaryKey(WqUserProfile record);
}