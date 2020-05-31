package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.Professor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface ProfessorMapper extends GeneralMapper<Professor> {

    /**get a list of Professor with its associated course rates
     * @param limit the number of reviews showed for each course
     * @param name select the professor by given name
     * @return a list of Professor associated with course rates
     */
    List<Professor> selectAllWithRateByName(@Param("limit") Integer limit, @Param("name") String name);
}
