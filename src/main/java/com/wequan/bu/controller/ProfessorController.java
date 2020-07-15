package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.ProfessorVo;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.json.JSON;
import com.wequan.bu.repository.model.*;
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
//@ApiIgnore
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
    @JSON(type = School.class, include = {"id", "name"})
    @JSON(type = Course.class, filter = {"professors","schoolId","departmentId"})
    public Result<Professor> findById(@PathVariable("id") Integer id) {
        if(id<0){
            String message = messageHandler.getFailResponseMessage("40005");
            return ResultGenerator.fail(message);
        }
        return ResultGenerator.success(professorService.findById(id));
    }

    @GetMapping("/professor/top")
    @ResponseBody
    @ApiOperation(value = "a list of top professor", notes = "根据school id, subject id获取professor列表，按评分排名")
    public Result<List<ProfessorVo>> getTopProfessors(@RequestParam("schoolId") Integer schoolId,
                                                    @RequestParam(value = "subjectId", required = false) Integer subjectId,
                                                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
       if(schoolId < 0 ) {
           String message = messageHandler.getFailResponseMessage("40008");
           return ResultGenerator.fail(message);
       }
        if(subjectId != null && subjectId < 0 ) {
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        List<ProfessorVo> professorVoList = professorService.findTopProfessors(schoolId, subjectId, pageNum, pageSize);
        return ResultGenerator.success(professorVoList);
    }

    @GetMapping("/professors")
    @ResponseBody
    @ApiOperation(value="Search professor", notes="返回Professor列表, 根据评分排序，关联所教课程3个评价")
    @JSON(type = Course.class, filter = {"professors","schoolId","departmentId"})
    public Result<List<Professor>> findAllWithRateByName(@RequestParam("name") String name,
                                                         @RequestParam(value="pageNum", required = false) Integer pageNum,
                                                         @RequestParam(value="pageSize", required = false) Integer pageSize){
        if(name == null || name.isEmpty()) {
            String message = messageHandler.getFailResponseMessage("40006");
            return ResultGenerator.fail(message);
        }
        return ResultGenerator.success(professorService.findAllWithRateByName(3, name, pageNum, pageSize));
    }

    @GetMapping("/professor/{id}/course/{cId}/reviews")
    @JSON(type = User.class, include = {"id", "username", "first_name", "last_name", "avatar"})
    @ApiOperation(value="Reviews for each professor, each course", notes="返回professor所教课程的评价列表")
    public Result<List<ProfessorCourseRate>> findAllReviewsByProfessorIdAndCourseId(
            @PathVariable("id") Integer p_id,
            @PathVariable("cId") Integer c_id,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ){
        if(p_id < 0 || c_id < 0) {
            String message = messageHandler.getFailResponseMessage("40007");
            return ResultGenerator.fail(message);
        }
        List<ProfessorCourseRate> professorCourseRateList= professorCourseRateService.findAllByProfessorIdAndCourseId(p_id, c_id, pageNum, pageSize);
        return ResultGenerator.success(professorCourseRateList);
    }

    @PostMapping("/professor/{id}/course/{cId}/review")
    @ResponseBody
    @ApiOperation(value="Rate course for professor", notes="评价授课教师所教课程")
    public Result postReview(@PathVariable("id") Integer id,
                                             @PathVariable("cId") Integer c_id,
                                             @RequestBody ProfessorCourseRate review){
        if(id<0 || c_id<0) {
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }

        if(professorService.checkCourseForProfessor(id, c_id)){
            review.setProfessorId(id);
            review.setCourseId(c_id);
            review.setCreateBy(review.getCreateBy());
            professorCourseRateService.save(review);
            return ResultGenerator.success();
        }
        return ResultGenerator.fail("No match.");
    }

    @PostMapping("/professor")
    @ApiOperation(value="", notes="add professor")
    @ResponseBody
    public Result addProfessor(@RequestBody ProfessorVo professor) {
        try {
            professorService.save(professor);
        } catch (Exception e) {
            return ResultGenerator.fail(messageHandler.getFailResponseMessage(e.getMessage()));
        }
        return ResultGenerator.success();
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
















