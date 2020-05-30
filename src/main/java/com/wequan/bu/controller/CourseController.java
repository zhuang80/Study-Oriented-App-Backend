package com.wequan.bu.controller;

import com.wequan.bu.repository.model.Course;
import com.wequan.bu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@RestController
@Api(value = "Operations for Course", tags = "Course Rest API")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    @ApiOperation(value="findAll", notes="return a list of courses")
    public List<Course> findAll() { return courseService.findAll(); }

    @GetMapping("/course/{id}")
    @ApiOperation(value="findById", notes="return a course by its course id")
    public Course findById(@PathVariable("id") Integer id) { return courseService.findById(id); }
}
