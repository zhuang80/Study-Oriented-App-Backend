package com.wequan.bu.controller.vo;

import com.wequan.bu.repository.model.Course;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Data
public class ProfessorVo {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer schoolId;
    private Double overallScore;
    private Integer createBy;
    private Integer departmentId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private List<CoursesGroupedBySubject> courseList;
}
