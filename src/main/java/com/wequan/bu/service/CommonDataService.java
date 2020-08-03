package com.wequan.bu.service;

import com.wequan.bu.repository.model.*;

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

    /**
     * 获取degree列表
     * @return degree列表
     */
    List<Degree> getDegreeData();

    /**
     * 获取cancellation policy 列表
     * @return cancellation policy列表
     */
    List<CancellationPolicy> getCancellationPolicyData();

    /**
     * 获取late policy列表
     * @return late policy列表
     */
    List<LatePolicy> getLatePolicyData();

}
