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

    /**
     * 根据用户id获取tutor inquiry列表
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return tutor inquiry列表
     */
    List<TutorInquiry> getUserTutorInquiries(Integer userId, Integer pageNum, Integer pageSize);
}
