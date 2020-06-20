package com.wequan.bu.repository.model;

import java.util.Date;
import lombok.Data;

/**
 * Database Table Remarks:
 *   申请Tutor的历史记录，同时用于审核，当approve后，会insert到tutor_profile表中
 */
@Data
public class TutorApplication {
    private Integer id;
    private Integer userId;
    private String briefIntroduction;
    private Date createTime;
    private Date updateTime;
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