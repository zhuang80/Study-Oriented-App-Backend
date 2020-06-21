package com.wequan.bu.repository.model;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorApplication {
    private Integer id;
    private Integer userId;
    private String briefIntroduction;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    /**
     * Database Column Remarks:
     *   0, default with apply; 1, checker pass; -1,  checker reject; -2, user delete
     */
    private Short status;
    private String currentCity;
    private String currentState;
    private String teachMethod;
    private Float hourlyRate;
    private Short latePolicyId;
    private Short cancellationPolicyId;
    /**
     * Database Column Remarks:
     *   关联education_background(id)，多个id用逗号连接，如1,2,3
     */
    private String educationBackgroundIds;

    /**
     * Database Column Remarks:
     *   关联subject_topics(id)，多个id用逗号连接，如1,2,3
     */
    private String subjectTopicsIds;

    /**
     * Database Column Remarks:
     *   resumes, transcripts, etc文件id，关联support_material(id)，多个id用逗号连接，如1,2,3
     */
    private String supportMaterialIds;
}