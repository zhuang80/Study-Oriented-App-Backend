package com.wequan.bu.service;

import com.wequan.bu.controller.vo.TutorInquiryVo;
import com.wequan.bu.repository.model.TutorInquiry;
import com.wequan.bu.repository.model.extend.TutorInquiryBriefInfo;

import java.util.List;
import java.util.Map;

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

    /**
     * 按搜索条件搜索tutor inquiry
     * @param whereCondition where
     * @param orderCondition order by
     * @param pageCondition page
     * @return Tutor inquiry列表
     */
    List<TutorInquiryBriefInfo> search(String whereCondition, String orderCondition, Map<String, Integer> pageCondition);

    void logicDeleteById(Integer id);
}
