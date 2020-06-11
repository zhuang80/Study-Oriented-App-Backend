package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.CourseVo;
import com.wequan.bu.json.JSON;
import com.wequan.bu.repository.model.Course;
import com.wequan.bu.repository.model.Professor;
import com.wequan.bu.service.CourseService;
import com.wequan.bu.service.ProfessorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@RestController
@RequestMapping("/study_space")
@Api(tags = "Course")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private ProfessorService professorService;

    @GetMapping("/courses")
    @ApiOperation(value="findAll", notes="return a list of courses")
    @JSON(type = Professor.class, filter = {"courses", "courseRates", "department", "school"})
    public List<Course> findAll() { return courseService.findAll(); }

    @GetMapping("/course/top")
    @ApiOperation(value = "findTopCourses", notes="a list of top course")
    public void findTopCourses(){}

    @GetMapping("/course/{id}")
    @ApiOperation(value="findById", notes="return a course by its course id")
    @JSON(type = Professor.class, filter = {"courses", "courseRates", "department", "school"})
    public Course findById(@PathVariable("id") Integer id) {
        if(id<0){
            log.info(messageHandler.getFailResponseMessage("40003"));
            return null;
        }
        return courseService.findByIdAssociatedWithProfessor(id);
    }

    @GetMapping("course/{id}/professors")
    @ApiOperation(value="", notes="a list of professors who teach required course")
    @ResponseBody
    public List<Professor> findProfessorsByCourseId(@PathVariable("id") Integer id){
        if(id < 0) {
            log.info(messageHandler.getFailResponseMessage("40003"));
            return null;
        }
        return professorService.findProfessorsByCourseId(id);
    }

    @GetMapping("/search/course")
    @ApiOperation(value = "", notes= "return a list of course with associated information by name or code")
    @JSON(type = Professor.class, filter = {"courses", "courseRates", "department", "school"})
    public List<Course> findByNameOrCode(@RequestParam("name") String name, @RequestParam("code") String code){
        if(name.isEmpty() && code.isEmpty()) {
            log.info(messageHandler.getFailResponseMessage("40004"));
            return null;
        }
        return courseService.findByNameOrCode(name, code);
    }

    @PostMapping("/course")
    @ApiOperation(value="", notes="add a course")
    @ResponseBody
    public void addCourse(@RequestBody CourseVo course){
        try{
            courseService.save(course);
        }catch(Exception e){
            log.info(messageHandler.getFailResponseMessage(e.getMessage()));
        }
    }
}
