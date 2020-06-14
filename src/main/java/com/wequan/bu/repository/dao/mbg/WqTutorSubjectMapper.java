package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqTutorSubject;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WqTutorSubjectMapper {
    int deleteByPrimaryKey(@Param("tutorId") Integer tutorId, @Param("subjectId") Integer subjectId);

    int insert(WqTutorSubject record);

    WqTutorSubject selectByPrimaryKey(@Param("tutorId") Integer tutorId, @Param("subjectId") Integer subjectId);

    List<WqTutorSubject> selectAll();

    int updateByPrimaryKey(WqTutorSubject record);
}