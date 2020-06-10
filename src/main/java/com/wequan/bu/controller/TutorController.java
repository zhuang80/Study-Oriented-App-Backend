package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.TutorReview;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.exception.NotImplementedException;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.service.TutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "Tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping("/tutor/{id}")
    @ApiOperation(value = "Get tutor info", notes = "返回Tutor详情")
    public Tutor getTutor(@RequestParam("id") Integer id) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @GetMapping("/tutors")
    @ApiOperation(value = "Available tutors", notes = "返回Tutor列表，按评分/加入时间倒序")
    public Result<List<Tutor>> getTutors(@RequestParam(value = "subjectId", required = false) Integer subjectId,
                                         @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Tutor> tutors = tutorService.findTutors(subjectId);
        return ResultGenerator.success(tutors);
    }

    @GetMapping("/tutors/popular")
    @ApiOperation(value = "Popular tutors", notes = "返回Tutor列表，按评分和被查看次数排序")
    public Result<List<Tutor>> getPopularTutors(@RequestParam(value = "subjectId", required = false) Integer subjectId,
                                                @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Tutor> result = null;
        return ResultGenerator.success(result);
    }

    @PostMapping("/tutor/{id}/review")
    @ApiOperation(value = "Review tutor", notes = "返回评价tutor成功与否")
    public Result reviewTutor(@PathVariable("id") Integer id,
                              @RequestBody TutorReview tutorReview) {

        return ResultGenerator.success();
    }

}
