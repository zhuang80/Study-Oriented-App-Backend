package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqSubject;
import java.util.List;

public interface WqSubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqSubject record);

    WqSubject selectByPrimaryKey(Integer id);

    List<WqSubject> selectAll();

    int updateByPrimaryKey(WqSubject record);
}