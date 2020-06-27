package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface SubjectsMapper extends GeneralMapper<Subject> {
    List<Subject> selectByTutorId(@Param("tutor_id") Integer tutorId);
}