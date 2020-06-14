package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqProfessorProfile;
import java.util.List;

public interface WqProfessorProfileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqProfessorProfile record);

    WqProfessorProfile selectByPrimaryKey(Integer id);

    List<WqProfessorProfile> selectAll();

    int updateByPrimaryKey(WqProfessorProfile record);
}