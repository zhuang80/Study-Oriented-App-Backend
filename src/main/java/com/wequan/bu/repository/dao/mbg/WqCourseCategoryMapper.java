package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqCourseCategory;
import java.util.List;

public interface WqCourseCategoryMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqCourseCategory record);

    WqCourseCategory selectByPrimaryKey(Short id);

    List<WqCourseCategory> selectAll();

    int updateByPrimaryKey(WqCourseCategory record);
}