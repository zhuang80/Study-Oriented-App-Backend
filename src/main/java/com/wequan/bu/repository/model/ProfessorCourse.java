package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class ProfessorCourse {
    private Integer professorId;
    private Integer courseId;
    private Integer createBy;
    private LocalDateTime createTime;
}
