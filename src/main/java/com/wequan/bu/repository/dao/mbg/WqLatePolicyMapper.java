package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqLatePolicy;
import java.util.List;

public interface WqLatePolicyMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqLatePolicy record);

    WqLatePolicy selectByPrimaryKey(Short id);

    List<WqLatePolicy> selectAll();

    int updateByPrimaryKey(WqLatePolicy record);
}