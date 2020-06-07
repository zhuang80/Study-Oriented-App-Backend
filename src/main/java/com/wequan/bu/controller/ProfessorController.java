package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@RestController
@Api(value = "Operations for Professor", tags="Professor Rest API")
public class ProfessorController{

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorCourseRateService professorCourseRateService;

    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/professor")
    @ApiOperation(value="findAll", notes="return a list of professors")
    public List<Professor> findAll(){
        return professorService.findAll();
    }

    @GetMapping("/professor/{id}")
    @ApiOperation(value="findById", notes="find a specific professor by its id")
    public Professor findById(@PathVariable("id") Integer id) {
        if(id<0){
            messageHandler.getFailResponseMessage("40005");
            return null;
        }
        return professorService.findById(id);
    }

    @GetMapping("/search/professor")
    @ApiOperation(value="", notes="")
    public List<Professor> findAllWithRateByName(@RequestParam("name") String name){
        if(name.isEmpty()) {
            messageHandler.getFailResponseMessage("40006");
            return null;
        }
        return professorService.findAllWithRateByName(3, name);
    }

    @GetMapping("/professor/{id}/course/{c_id}/evaluations")
    @ApiOperation(value="", notes="return all reviews for each professor, each course")
    public List<ProfessorCourseRate> findAllReviewsByProfessorIdAndCourseId(
            @PathVariable("id") Integer p_id,
            @PathVariable("c_id") Integer c_id,
            Integer pageNum,
            Integer pageSize
    ){
        if(p_id < 0 || c_id < 0) {
            messageHandler.getFailResponseMessage("40007");
            return null;
        }
        return professorCourseRateService.findAllByProfessorIdAndCourseId(p_id, c_id, pageNum, pageSize);
    }

    @PostMapping("/professor/{id}/course/{c_id}/evaluations")
    @ApiOperation(value="postReview", notes="post a new reviews")
    public ResponseEntity<String> postReview(@PathVariable("id") Integer id,
                                             @PathVariable("c_id") Integer c_id,
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
            review.setUserId(1); //hardcode use id 1, should be changed after
            professorCourseRateService.save(review);
            return ResponseEntity.status(200).body("success");
        }
        return ResponseEntity.status(400).body("The Professor don't teach this course.");
    }
}
















