package com.wequan.bu.repository.dao;

import com.wequan.bu.controller.vo.TutorInquiryVo;
import com.wequan.bu.repository.model.Topic;
import com.wequan.bu.repository.model.TutorInquiry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorInquiryMapper extends GeneralMapper<TutorInquiry> {
    List<TutorInquiry> selectBySubject(@Param("subject_id") Integer subjectId);

    void save(TutorInquiryVo tutorInquiry);

    List<TutorInquiry> selectPartPerSubject(@Param("limit") Integer limit);

    List<Topic> selectTopic();
}
