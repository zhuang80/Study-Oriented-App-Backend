package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqCourseMaterialType;
import java.util.List;

public interface WqCourseMaterialTypeMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqCourseMaterialType record);

    WqCourseMaterialType selectByPrimaryKey(Short id);

    List<WqCourseMaterialType> selectAll();

    int updateByPrimaryKey(WqCourseMaterialType record);
}