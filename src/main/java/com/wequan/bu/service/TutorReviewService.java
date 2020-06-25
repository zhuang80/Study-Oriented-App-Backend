package com.wequan.bu.service;

import com.wequan.bu.controller.vo.TutorReview;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface TutorReviewService extends Service<TutorReview>{

    public List<TutorReview> findByTutorId(Integer tutorId);
}
