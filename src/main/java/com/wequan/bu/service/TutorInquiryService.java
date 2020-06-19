package com.wequan.bu.service;

import com.wequan.bu.controller.vo.TutorInquiryVo;
import com.wequan.bu.repository.model.TutorInquiry;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface TutorInquiryService extends Service<TutorInquiry> {

    List<TutorInquiry> findBySubject(Integer subjectId, Integer pageNum, Integer pageSize);

    void save(TutorInquiryVo tutorInquiry);
}
