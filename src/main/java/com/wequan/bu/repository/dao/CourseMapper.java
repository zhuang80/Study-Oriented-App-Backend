package com.wequan.bu.repository.dao;

import com.wequan.bu.controller.vo.CourseVo;
import com.wequan.bu.repository.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface CourseMapper extends GeneralMapper<Course> {

    public List<Course> selectByNameOrCode(@Param("name") String name, @Param("code") String code);

    /**
     * select a course by id associated with professors basic information
     */
    public Course selectByIdAssociatedWithProfessor(@Param("id") Integer id);

}
