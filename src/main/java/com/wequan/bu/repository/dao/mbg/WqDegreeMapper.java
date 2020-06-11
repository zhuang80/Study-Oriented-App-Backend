package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqDegree;
import java.util.List;

public interface WqDegreeMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqDegree record);

    WqDegree selectByPrimaryKey(Short id);

    List<WqDegree> selectAll();

    int updateByPrimaryKey(WqDegree record);
}