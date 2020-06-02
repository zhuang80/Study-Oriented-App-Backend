package com.wequan.bu.service;

import com.wequan.bu.repository.model.Course;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface CourseService extends Service<Course>{

    public List<Course> findByNameOrCode(String name, String code);
}
