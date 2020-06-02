package com.wequan.bu.repository.model;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public class CourseRate {
    private Integer id;
    private List<ProfessorCourseRate> reviews;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ProfessorCourseRate> getReviews() {
        return reviews;
    }

    public void setReviews(List<ProfessorCourseRate> reviews) {
        this.reviews = reviews;
    }
}
