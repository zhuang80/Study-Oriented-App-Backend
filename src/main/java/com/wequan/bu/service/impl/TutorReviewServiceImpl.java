package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.TutorReview;
import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.dao.TutorReviewMapper;
import com.wequan.bu.repository.model.Tag;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TagService;
import com.wequan.bu.service.TutorReviewService;
import com.wequan.bu.util.TagCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Zhaochao Huang
 */
@Service
public class TutorReviewServiceImpl extends AbstractService<TutorReview> implements TutorReviewService {

    @Autowired
    private TutorReviewMapper tutorReviewMapper;

    @Autowired
    private TagService tagService;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(tutorReviewMapper);
    }
    @Override
    public void save(TutorReview tutorReview){
        //insert tutor review
        tutorReviewMapper.insertSelective(tutorReview);
        //insert tags
        List<Tag> tags = parseTags(tutorReview);
        tagService.save(tags);
        //insert tutor_review_tag
        tagService.saveTutorReviewAndTagRelation(tags, tutorReview);

    }

    private List<Tag> parseTags(TutorReview tutorReview){
       String[] names = tutorReview.getTags().split(",");
       List<Tag> ret = new ArrayList<>();
       for(int i=0;i<names.length;i++){
           System.out.println("+================= name: " + names[i]);
           Tag tag = new Tag();
           tag.setCategory( (short) TagCategory.TUTOR_REVIEW.getValue());
           tag.setCreateBy(tutorReview.getCreateBy());
           tag.setCreateTime(LocalDateTime.now());
           tag.setName(names[i]);
            ret.add(tag);
       }
        return ret;
    }

    @Override
    public List<TutorReview> findByTutorId(Integer tutorId) {
        return tutorReviewMapper.selectByTutorId(tutorId);
    }
}
