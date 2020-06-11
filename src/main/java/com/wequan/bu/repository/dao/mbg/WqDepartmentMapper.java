package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqDepartment;
import java.util.List;

public interface WqDepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqDepartment record);

    WqDepartment selectByPrimaryKey(Integer id);

    List<WqDepartment> selectAll();

    int updateByPrimaryKey(WqDepartment record);
}