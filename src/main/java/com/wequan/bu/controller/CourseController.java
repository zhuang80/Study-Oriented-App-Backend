package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.repository.model.Course;
import com.wequan.bu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@RestController
@Api(value = "Operations for Course", tags = "Course Rest API")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;
    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/courses")
    @ApiOperation(value="findAll", notes="return a list of courses")
    public List<Course> findAll() { return courseService.findAll(); }

    @GetMapping("/course/{id}")
    @ApiOperation(value="findById", notes="return a course by its course id")
    public Course findById(@PathVariable("id") Integer id) {
        if(id<0){
            log.info(messageHandler.getFailResponseMessage("40003"));
            return null;
        }
        return courseService.findById(id);
    }

    @GetMapping("/search/course")
    @ApiOperation(value = "", notes= "return a list of course with associated information by name or code")
    public List<Course> findByNameOrCode(@RequestParam("name") String name, @RequestParam("code") String code){
        if(name.isEmpty() && code.isEmpty()) {
            log.info(messageHandler.getFailResponseMessage("40004"));
            return null;
        }
        return courseService.findByNameOrCode(name, code);
    }
}
