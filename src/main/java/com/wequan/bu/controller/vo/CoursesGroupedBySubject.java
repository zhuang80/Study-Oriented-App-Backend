package com.wequan.bu.controller.vo;

import com.wequan.bu.repository.model.Course;
import lombok.Data;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Data
public class CoursesGroupedBySubject {
    private Integer subjectId;
    private List<Course> courseList;
}
