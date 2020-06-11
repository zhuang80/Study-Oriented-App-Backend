package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqCourseMaterial;
import java.util.List;

public interface WqCourseMaterialMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqCourseMaterial record);

    WqCourseMaterial selectByPrimaryKey(Integer id);

    List<WqCourseMaterial> selectAll();

    int updateByPrimaryKey(WqCourseMaterial record);
}