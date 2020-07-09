package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorSubject {
    private Integer tutorId;
    private Integer subjectId;
    private LocalDateTime createTime;
}
