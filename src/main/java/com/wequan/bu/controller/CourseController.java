package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.CourseVo;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultCode;
import com.wequan.bu.controller.vo.result.ResultGenerator;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@RestController
@Api(tags = "Course")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private ProfessorService professorService;

    @PostMapping("/course")
    @ApiOperation(value = "add course", notes = "添加课程")
    public Result addCourse(@RequestBody CourseVo course) {
        Result result;
        try{
            courseService.save(course);
        }catch(Exception e){
            String message = messageHandler.getFailResponseMessage(e.getMessage());
            result = ResultGenerator.fail(message);
            return result;
        }
        result = ResultGenerator.success();
        return result;
    }

    @GetMapping("/search/courses")
    @ApiOperation(value="Search course", notes="根据课程名或者课程代号返回课程列表，关联授课professor的 name, rating")
    @JSON(type = Professor.class, filter = {"courses", "courseRates", "department", "school"})
    public Result<List<Course>> findCourses(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "code", required = false) String code) {
        List<Course> courseList = null;
        if (StringUtils.hasText(name) || StringUtils.hasText(code)) {
            courseList = courseService.findByNameOrCode(name, code);
        } else {
            courseList = courseService.findAll();
        }
        Result result = ResultGenerator.success(courseList);
        return result;
    }

    @GetMapping("/course/top")
    @ApiOperation(value = "a list of top course", notes = "根据school id, subject id获取course列表，按查看记录排名")
    public Result<List<Course>> getTopCourses(@RequestParam("schoolId") Integer schoolId,
                                              @RequestParam("subjectId") Integer subjectId,
                                              @RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize) {
        Result<List<Course>> result = null;
        return result;
    }

    @GetMapping("/course/{id}")
    @ApiOperation(value="Course profile", notes="获取course信息，关联授课professor信息以及可用课程资料类型")
    @JSON(type = Professor.class, filter = {"courses", "courseRates", "department", "school"})
    public Result<Course> findById(@PathVariable("id") Integer id) {
        Result result;
        if(id<0){
            String message = messageHandler.getFailResponseMessage("40003");
            result = ResultGenerator.fail(message);
            return result;
        }
        Course course = courseService.findByIdAssociatedWithProfessor(id);
        result = ResultGenerator.success(course);
        return result;
    }

    @GetMapping("/course/{id}/professors")
    @ApiOperation(value="a list of professors who teach required course", notes="根据course id获取授课教师列表")
    @ResponseBody
    public Result<List<Professor>> findProfessorsByCourseId(@PathVariable("id") Integer id){
        Result result;
        if(id < 0) {
            String message = messageHandler.getFailResponseMessage("40003");
            result = ResultGenerator.fail(ResultCode.FAIL.code(), message);
            return result;
        }
        List<Professor> professorList = professorService.findProfessorsByCourseId(id);
        result = ResultGenerator.success(professorList);
        return result;
    }

}
