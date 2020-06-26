package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.TutorReview;
import com.wequan.bu.repository.dao.TagMapper;
import com.wequan.bu.repository.model.Tag;
import com.wequan.bu.repository.model.TutorReviewTag;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TagService;
import com.wequan.bu.util.TagCategory;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Service
public class TagServiceImpl extends AbstractService<Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(tagMapper);
    }

    @Override
    public void saveTutorReviewAndTagRelation(List<Tag> tags, TutorReview tutorReview) {
        List<TutorReviewTag> tutorReviewTagList = new ArrayList<>();
        for(int i=0;i<tags.size();i++){
            TutorReviewTag temp = new TutorReviewTag();
            temp.setTutorReviewId(tutorReview.getId());
            temp.setTagId(tags.get(i).getId());
            temp.setCreateBy(tutorReview.getCreateBy());
            temp.setCreateTime(LocalDateTime.now());
            tutorReviewTagList.add(temp);
        }
        tagMapper.insertTutorReviewAndTagRelation(tutorReviewTagList);
    }
}
