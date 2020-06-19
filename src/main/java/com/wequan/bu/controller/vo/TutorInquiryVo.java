package com.wequan.bu.controller.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ChrisChen
 */
@Data
public class TutorInquiryVo extends BaseVo {

    private int subjectId;
    private String briefDescription;
    private boolean online;
    private short status;
    private Date requestStartTime;
    private Date requestEndTime;
    private double acceptPayRate;

    private List<Integer> topicIds;
}
