package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqUserFollower;
import java.util.List;

public interface WqUserFollowerMapper {
    int insert(WqUserFollower record);

    List<WqUserFollower> selectAll();
}