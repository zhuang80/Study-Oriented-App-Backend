package com.wequan.bu.service;

import com.wequan.bu.repository.model.School;
import com.wequan.bu.repository.model.Subject;
import com.wequan.bu.repository.model.Tag;
import com.wequan.bu.repository.model.Topic;

import java.util.List;

/**
 * @author ChrisChen
 */
public interface CommonDataService {

    /**
     * 获取school列表
     * @return school列表
     */
    List<School> getSchoolData();

    /**
     * 获取tag列表
     * @return tag列表
     */
    List<Tag> getTagData();

    /**
     * 获取subject列表
     * @return subject列表
     */
    List<Subject> getSubjectData();

    /**
     * 获取topic列表
     * @return topic列表
     */
    List<Topic> getTopicData();
}
