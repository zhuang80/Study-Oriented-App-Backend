package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.TutorSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorSubjectMapper extends GeneralMapper<TutorSubject> {
    int upsertList(List<TutorSubject> tutorSubjectList);
}
