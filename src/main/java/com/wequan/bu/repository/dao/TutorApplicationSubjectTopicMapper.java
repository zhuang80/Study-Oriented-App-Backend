package com.wequan.bu.repository.dao;

import com.wequan.bu.controller.vo.TutorApplicationSubjectTopic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorApplicationSubjectTopicMapper extends GeneralMapper<TutorApplicationSubjectTopic> {
    List<TutorApplicationSubjectTopic> selectByIds(@Param("ids") String ids);
}
