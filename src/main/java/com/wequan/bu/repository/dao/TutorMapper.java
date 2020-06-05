package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.Tutor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhen Lin
 */
@Mapper
public interface TutorMapper extends GeneralMapper<Tutor> {
    public List<Tutor> selectTutors(@Param("subject_id") Integer subjectId);
}
