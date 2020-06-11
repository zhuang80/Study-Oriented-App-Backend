package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface DepartmentMapper extends GeneralMapper<Department> {

    /**
     * select departments by school id
     * @param id the id of school id
     * @return a list of departments which belonged to a required school
     */
    public List<Department> selectDepartmentsBySchoolId(Integer id);
}
