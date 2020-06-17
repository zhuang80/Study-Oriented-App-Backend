package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhen Lin
 */
@Data
public class Tutor {

    private Integer id;
    private User user;
    private String briefIntroduction;
    private String resumePath;
    private String transcriptPath;
    private String otherProofPath;
    private Integer currentSchoolId;
    private Integer currentDegreeId;
    private String currentLocation;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Double payRate;
    private String status;
    private Integer latePolicyId;
    private Integer cancellationPolicyId;
    private Boolean tutorAvailable;
}
