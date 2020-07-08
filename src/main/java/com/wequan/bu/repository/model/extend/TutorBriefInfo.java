package com.wequan.bu.repository.model.extend;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ChrisChen
 */
@Data
public class TutorBriefInfo {

    private Integer id;
    private UserBriefInfo user;
    private String briefIntroduction;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Short status;
    private Short latePolicyId;
    private Short cancellationPolicyId;
    private Boolean tutorAvailable;
    private Integer tutorApplicationId;
    private String currentCity;
    private String currentState;
    private String teachMethod;
    private Integer hourlyRate;
    private Double score;
    private List<SubjectBriefInfo> subjectList;


}
