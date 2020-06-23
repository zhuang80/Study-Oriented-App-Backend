package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.Tag;
import com.wequan.bu.repository.model.TutorReviewTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TagMapper extends GeneralMapper<Tag> {
    public void insertTutorReviewAndTagRelation(List<TutorReviewTag> tutorReviewTagList);
}
