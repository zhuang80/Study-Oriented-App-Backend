package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqCourse;
import java.util.List;

public interface WqCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqCourse record);

    WqCourse selectByPrimaryKey(Integer id);

    List<WqCourse> selectAll();

    int updateByPrimaryKey(WqCourse record);
}