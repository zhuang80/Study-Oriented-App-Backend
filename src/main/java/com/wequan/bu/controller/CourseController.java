package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultCode;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.json.JSON;
import com.wequan.bu.repository.model.Course;
import com.wequan.bu.repository.model.CourseViewHistory;
import com.wequan.bu.repository.model.Professor;
import com.wequan.bu.service.CourseService;
import com.wequan.bu.service.CourseViewHistoryService;
import com.wequan.bu.service.ProfessorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Controller
@Api(tags = "Course")
@ApiIgnore
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private CourseViewHistoryService courseViewHistoryService;

    @PostMapping("/course")
    @ResponseBody
    @ApiOperation(value = "add course", notes = "添加课程")
    public Result addCourse(@RequestBody Course course) {
        courseService.save(course);
        return ResultGenerator.success();
    }

    @GetMapping("/courses")
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
    @ResponseBody
    @ApiOperation(value = "a list of top course", notes = "根据school id, subject id获取course列表，按查看记录排名")
    public Result<List<Course>> getTopCourses(@RequestParam("schoolId") Integer schoolId,
                                              @RequestParam("subjectId") Integer subjectId,
                                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Course> courses = courseService.findTopViewedCourses(schoolId, subjectId, pageNum, pageSize);
        return ResultGenerator.success(courses);
    }

    @GetMapping("/course/{id}")
    @ApiOperation(value="Course profile", notes="获取course信息，关联授课professor信息以及可用课程资料类型")
    @JSON(type = Professor.class, filter = {"courses", "courseRates", "department", "school"})
    public Result<Course> findById(@PathVariable("id") Integer id,
                                   @RequestParam("user_id") Integer userId) {
        if(id<0){
            String message = messageHandler.getFailResponseMessage("40003");
            return ResultGenerator.fail(message);
        }
        Course course = courseService.findByIdAssociatedWithProfessor(id);
        courseViewHistoryService.recordHistory(id, userId);
        return  ResultGenerator.success(course);
    }

    @GetMapping("/course/{id}/professors")
    @ResponseBody
    @ApiOperation(value="a list of professors who teach required course", notes="根据course id获取授课教师列表")
    public Result<List<Professor>> findProfessorsByCourseId(@PathVariable("id") Integer id) {
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

    @PostMapping("/course/associate_professor")
    @ResponseBody
    @ApiOperation(value="course associate with professor", notes="课程关联授课教师")
    public Result associateProfessor(@RequestParam("courseId") Integer courseId,
                                     @RequestParam("professorId") Integer professorId,
                                     @RequestParam("userId") Integer userId) {

        if(courseId < 0 || professorId < 0 || userId < 0){
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        courseService.associateWithProfessor(courseId, professorId, userId);
        return ResultGenerator.success();
    }

}
