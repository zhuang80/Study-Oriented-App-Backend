package com.wequan.bu.repository.dao;

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
}
