package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqProfessorCourse;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WqProfessorCourseMapper {
    int deleteByPrimaryKey(@Param("professorId") Integer professorId, @Param("courseId") Integer courseId);

    int insert(WqProfessorCourse record);

    WqProfessorCourse selectByPrimaryKey(@Param("professorId") Integer professorId, @Param("courseId") Integer courseId);

    List<WqProfessorCourse> selectAll();

    int updateByPrimaryKey(WqProfessorCourse record);
}