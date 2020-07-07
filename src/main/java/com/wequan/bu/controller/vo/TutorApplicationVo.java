package com.wequan.bu.controller.vo;

import com.wequan.bu.repository.model.TutorApplicationEducationBackground;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TutorApplicationVo {
    private Integer id;
    private Integer userId;
    private String briefIntroduction;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String currentCity;
    private String currentState;
    private String teachMethod;
    private Integer hourlyRate;
    private Short latePolicyId;
    private Short cancellationPolicyId;

    private MultipartFile[] resumes;
    private MultipartFile[] transcripts;
    private MultipartFile[] others;
    /**
     * Database Column Remarks:
     *   关联education_background(id)，多个id用逗号连接，如1,2,3
     */
    private String educationBackgroundIds;
    /**
     * Database Column Remarks:
     *   关联subject_topics(id)，多个id用逗号连接，如1,2,3
     */
    private List<TutorApplicationSubjectTopic> subjectTopics;
    private String supportMaterialIds;
    private List<TutorApplicationEducationBackground> educationBackgrounds;
}
