package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.CourseSubject;
import com.wequan.bu.controller.vo.Material;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Course;
import com.wequan.bu.repository.model.Department;
import com.wequan.bu.repository.model.Professor;
import com.wequan.bu.repository.model.ProfessorCourseRate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@RequestMapping("/study_space")
@Api(tags = "Study Space")
public class StudySpaceController {

    private static final Logger log = LoggerFactory.getLogger(StudySpaceController.class);

    @GetMapping("/subjects")
    @ApiOperation(value = "a list of subjects", notes = "根据school id返回学科列表")
    public Result<List<CourseSubject>> getCourseSubjects(@RequestParam("schoolId") Integer schoolId) {
        Result<List<CourseSubject>> result = null;

        return result;
    }

    @PostMapping("/subject/change")
    @ApiOperation(value = "change subject", notes = "用于保存当前用户选择的subject，以便页面恢复上次浏览状态")
    public Result changeSubject(@RequestParam("schoolId") Integer schoolId,
                                @RequestParam("subjectId") Integer subjectId,
                                @RequestParam("userId") String userId) {
        Result result = ResultGenerator.success();

        return result;
    }

    @GetMapping("/subject/restore")
    @ApiOperation(value = "restore user's subject", notes = "获取用户上次选择的subject")
    public Result<Integer> restoreSubject(@RequestParam("userId") String userId) {
        Result<Integer> result = null;

        return result;
    }

    @GetMapping("/professor/top")
    @ApiOperation(value = "a list of top professor", notes = "根据school id, subject id获取professor列表，按评分排名")
    public Result<List<Professor>> getTopProfessors(@RequestParam("schoolId") Integer schoolId,
                                                    @RequestParam("subjectId") Integer subjectId,
                                                    @RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize) {
        Result<List<Professor>> result = null;
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

    @GetMapping("/professor/{id}")
    @ApiOperation(value = "professor detail", notes = "根据professor id, 获取professor详情，包括所教课程及评价")
    public Result<Professor> getProfessor(@PathVariable("id") Integer id) {
        Result<Professor> result = null;
        return result;
    }

    @PostMapping("/professor/review/report")
    @ApiOperation(value = "report professor review", notes = "对授课教师的评价进行举报")
    public Result reportProfessorReview(@RequestParam("reviewId") Integer reviewId,
                                        @RequestParam("userId") Integer userId,
                                        @RequestParam("reason") String reason) {
        Result result = ResultGenerator.success();
        return result;
    }

    @PostMapping("/professor/review/like")
    @ApiOperation(value = "like professor review", notes = "对授课教师的评价点赞")
    public Result likeProfessorReview(@RequestParam("reviewId") Integer reviewId,
                                      @RequestParam("userId") Integer userId) {
        Result result = ResultGenerator.success();
        return result;
    }

    @PostMapping("/professor/review/dislike")
    @ApiOperation(value = "dislike professor review", notes = "对授课教师的评价拍砖")
    public Result dislikeProfessorReview(@RequestParam("reviewId") Integer reviewId,
                                         @RequestParam("userId") Integer userId) {
        Result result = ResultGenerator.success();
        return result;
    }

    @PostMapping("/professor/rate")
    @ApiOperation(value = "rate professor", notes = "对授课教师所教课程进行评价")
    public Result rateProfessor(@RequestBody ProfessorCourseRate professorCourseRate) {
        Result result = ResultGenerator.success();
        return result;
    }

    @PostMapping("/professor")
    @ApiOperation(value = "add professor", notes = "添加授课教师")
    public Result addProfessor(@RequestBody Professor professor) {
        Result result = ResultGenerator.success();
        return result;
    }

    @GetMapping("/departments")
    @ApiOperation(value = "a list of departments", notes = "根据school id获取departments")
    public Result<List<Department>> getDepartments(@RequestParam("schoolId") Integer schoolId) {
        Result<List<Department>> result = null;
        return result;
    }

    @GetMapping("/course/{id}")
    @ApiOperation(value = "course detail", notes = "根据course id获取课程详情")
    public Result<Course> getCourse(@PathVariable("id") Integer id) {
        Result<Course> result = null;
        return result;
    }

    @PostMapping("/course")
    @ApiOperation(value = "add course", notes = "添加课程")
    public Result rateProfessor(@RequestBody Course course) {
        Result result = ResultGenerator.success();
        return result;
    }

    @GetMapping("/course/{id}/professors")
    @ApiOperation(value = "a list of professors who teach required course", notes = "根据course id获取授课教师列表")
    public Result<List<Professor>> getProfessorsByCourse(@PathVariable("id") Integer id) {
        Result<List<Professor>> result = null;
        return result;
    }

    @GetMapping("/materials")
    @ApiOperation(value = "a list of materials", notes = "根据professor id, course id获取解基本课程资料列表")
    public Result<List<Material>> getMaterials(@RequestParam("professorId") Integer professorId,
                                               @RequestParam("courseId") Integer courseId,
                                               @RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize) {
        Result<List<Material>> result = null;
        return result;
    }

    @GetMapping("/material/{id}")
    @ApiOperation(value = "material detail", notes = "根据material id获取课程资料详情")
    public Result<Material> getMaterial(@PathVariable("id") Integer id) {
        Result<Material> result = null;
        return result;
    }

    @PostMapping("/material/{id}/unlock")
    @ApiOperation(value = "unlock material", notes = "根据material id解锁课程资料")
    public Result<Material> unlockMaterial(@PathVariable("id") Integer id) {
        Result<Material> result = null;
        return result;
    }
}
