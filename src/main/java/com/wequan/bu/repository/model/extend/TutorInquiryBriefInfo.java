package com.wequan.bu.repository.model.extend;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ChrisChen
 */
@Data
public class TutorInquiryBriefInfo {

    private Integer id;
    private UserBriefInfo createBy;
    private SubjectBriefInfo subject;
    private String briefDescription;
    private Boolean online;
    private Date createTime;
    private Date updateTime;
    private Short status;
    private Date requestStartTime;
    private Date requestEndTime;
    private Float acceptablePayRate;
    private List<TopicBriefInfo> topicList;

}
