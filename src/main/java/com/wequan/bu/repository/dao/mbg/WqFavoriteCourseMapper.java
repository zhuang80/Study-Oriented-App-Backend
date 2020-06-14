package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqFavoriteCourse;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WqFavoriteCourseMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    int insert(WqFavoriteCourse record);

    WqFavoriteCourse selectByPrimaryKey(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    List<WqFavoriteCourse> selectAll();

    int updateByPrimaryKey(WqFavoriteCourse record);
}