package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqCancellationPolicy;
import java.util.List;

public interface WqCancellationPolicyMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqCancellationPolicy record);

    WqCancellationPolicy selectByPrimaryKey(Short id);

    List<WqCancellationPolicy> selectAll();

    int updateByPrimaryKey(WqCancellationPolicy record);
}