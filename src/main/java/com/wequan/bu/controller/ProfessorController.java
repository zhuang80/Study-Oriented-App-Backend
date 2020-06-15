package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.ProfessorVo;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.json.JSON;
import com.wequan.bu.repository.model.Course;
import com.wequan.bu.repository.model.Professor;
import com.wequan.bu.repository.model.ProfessorCourseRate;
import com.wequan.bu.service.ProfessorCourseRateService;
import com.wequan.bu.service.ProfessorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Controller
@Api(tags = "Professor")
public class ProfessorController{

    private static final Logger log = LoggerFactory.getLogger(ProfessorController.class);

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorCourseRateService professorCourseRateService;

    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/professor")
    @ApiOperation(value="findAll", notes="return a list of professors")
    @ApiIgnore("Not in the rest api list")
    @JSON(type=Professor.class, filter = "courseRates")
    @JSON(type = Course.class, filter = {"professors","schoolId","departmentId"})
    public Result<List<Professor>> findAll(){
        List<Professor> professorList = professorService.findAll();
        Result result = ResultGenerator.success(professorList);
        return result;
    }

    @GetMapping("/professor/{id}")
    @ApiOperation(value="Professor profile", notes="返回professor信息，关联所教课程profile")
    @JSON(type = Professor.class, filter = "courseRates")
    @JSON(type = Course.class, filter = {"professors","schoolId","departmentId"})
    public Professor findById(@PathVariable("id") Integer id) {
        if(id<0){
            messageHandler.getFailResponseMessage("40005");
            return null;
        }
        return professorService.findById(id);
    }

    @GetMapping("/professor/top")
    @ResponseBody
    @ApiOperation(value = "a list of top professor", notes = "根据school id, subject id获取professor列表，按评分排名")
    public Result<List<Professor>> getTopProfessors(@RequestParam("schoolId") Integer schoolId,
                                                    @RequestParam("subjectId") Integer subjectId,
                                                    @RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize) {
        Result<List<Professor>> result = null;
        return result;
    }

    @GetMapping("/professors")
    @ResponseBody
    @ApiOperation(value="Search professor", notes="返回Professor列表, 根据评分排序，关联所教课程3个评价")
    @JSON(type = Course.class, filter = {"professors","schoolId","departmentId"})
    public List<Professor> findAllWithRateByName(@RequestParam("name") String name){
        if(name.isEmpty()) {
            messageHandler.getFailResponseMessage("40006");
            return null;
        }
        return professorService.findAllWithRateByName(3, name);
    }

    @GetMapping("/professor/{id}/course/{cId}/reviews")
    @ResponseBody
    @ApiOperation(value="Reviews for each professor, each course", notes="返回professor所教课程的评价列表")
    public List<ProfessorCourseRate> findAllReviewsByProfessorIdAndCourseId(
            @PathVariable("id") Integer p_id,
            @PathVariable("cId") Integer c_id,
            Integer pageNum,
            Integer pageSize
    ){
        if(p_id < 0 || c_id < 0) {
            messageHandler.getFailResponseMessage("40007");
            return null;
        }
        return professorCourseRateService.findAllByProfessorIdAndCourseId(p_id, c_id, pageNum, pageSize);
    }

    @PostMapping("/professor/{id}/course/{cId}/review")
    @ResponseBody
    @ApiOperation(value="Rate course for professor", notes="评价授课教师所教课程")
    public ResponseEntity<String> postReview(@PathVariable("id") Integer id,
                                             @PathVariable("cId") Integer c_id,
                                             @RequestBody ProfessorCourseRate review){
        if(id<0 || c_id<0) {
            String message = messageHandler.getFailResponseMessage("40008");
            log.info(message);
            return ResponseEntity.status(400).body(message);
        }
        log.info(professorService.checkCourseForProfessor(id,c_id).toString());
        if(professorService.checkCourseForProfessor(id, c_id)){
            review.setProfessorId(id);
            review.setCourseId(c_id);
            //hardcode use id 1, should be changed after
            review.setCreateBy(1);
            professorCourseRateService.save(review);
            return ResponseEntity.status(200).body("success");
        }
        return ResponseEntity.status(400).body("The Professor don't teach this course.");
    }

    @PostMapping("/professor")
    @ApiOperation(value="", notes="add professor")
    @ResponseBody
    public void addProfessor(@RequestBody ProfessorVo professor) {
        try {
            professorService.save(professor);
        } catch (Exception e) {
            log.info(messageHandler.getFailResponseMessage(e.getMessage()));
        }
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

    @PostMapping("/professor/associate_course")
    @ApiOperation(value="professor associate with course", notes="授课教师关联课程")
    public Result associateCourse(@RequestParam("courseId") Integer courseId,
                                  @RequestParam("professorId") Integer professorId,
                                  @RequestParam("userId") Integer userId) {
        return null;
    }
}
















