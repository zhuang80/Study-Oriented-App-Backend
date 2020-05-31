package com.wequan.bu.repository.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface ProfessorCourseRateMapper {

    /**
     *get a list of certain professor reviews for a certain course
     * @param p_id the professor id
     * @param c_id the course id
     * @return a list of reviews
     */
    public List<ProfessorCourseRateMapper> selectAllByProfessorIdAndCourseId(@Param("p_id") Integer p_id, @Param("c_id") Integer c_id);
}
