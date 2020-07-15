package com.wequan.bu.controller.vo;

import com.wequan.bu.repository.model.ThreadResource;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author ChrisChen
 */
@Data
@Builder
public class ThreadVo {

    /**
     *   创建帖子用户id，关联user_profile(id)
     */
    private Integer createBy;

    /**
     *   帖子标题
     */
    private String title;

    /**
     *   帖子所属类别，两个（学习，非学习），关联category(id)，一一对应
     */
    private Short category;

    /**
     *   帖子所属标签，关联tag(id) ，一一对应
     */
    private Short tagId;

    /**
     *   帖子内容
     */
    private String content;

    /**
     *   悬赏贴的学习积分
     */
    private Short studyPointsBonus;

    /**
     *   学校id
     */
    private Integer schoolId;

    private List<Integer> subjectIds;

    private List<ThreadResource> threadResources;
}
