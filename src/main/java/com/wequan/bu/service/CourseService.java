package com.wequan.bu.service;

import com.wequan.bu.controller.vo.CourseVo;
import com.wequan.bu.repository.model.Course;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface CourseService extends Service<Course>{

    public List<Course> findByNameOrCode(String name, String code);

    /**
     * find a course by id with associated professors basic information
     */
    public Course findByIdAssociatedWithProfessor(Integer id);

    public List<Course> findTopViewedCourses(Integer schoolId, Integer subjectId, Integer pageNum, Integer pageSize);

    public void associateWithProfessor(Integer courseId, Integer professorId, Integer userId);
}
