package com.wequan.bu.repository.model.extend;

import lombok.Data;

import java.util.List;

/**
 * @author ChrisChen
 */
@Data
public class SubjectBriefInfo {
    private Integer id;
    private String name;
    private List<TopicBriefInfo> topicList;
}
