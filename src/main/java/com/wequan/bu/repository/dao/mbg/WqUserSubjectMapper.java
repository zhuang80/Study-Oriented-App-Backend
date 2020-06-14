package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqUserSubject;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WqUserSubjectMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("subjectId") Integer subjectId);

    int insert(WqUserSubject record);

    WqUserSubject selectByPrimaryKey(@Param("userId") Integer userId, @Param("subjectId") Integer subjectId);

    List<WqUserSubject> selectAll();

    int updateByPrimaryKey(WqUserSubject record);
}