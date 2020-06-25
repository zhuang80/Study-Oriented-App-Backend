package com.wequan.bu.service;

import com.wequan.bu.controller.vo.TutorReview;
import com.wequan.bu.repository.model.Tag;
import com.wequan.bu.util.TagCategory;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface TagService extends Service<Tag>{
    public void saveTutorReviewAndTagRelation(List<Tag> tags, TutorReview tutorReview);
}
