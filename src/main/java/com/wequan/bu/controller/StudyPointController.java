package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.StudyPoint;
import com.wequan.bu.controller.vo.StudyPointHistory;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
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
@Api(tags = "Study Point")
public class StudyPointController {

    private static final Logger log = LoggerFactory.getLogger(StudyPointController.class);

    @GetMapping("/user/{id}/study_point")
    @ApiOperation(value = "get user's current study points", notes = "返回用户当前学习积分")
    public Result<StudyPoint> getUserStudyPoint(@PathVariable("id") Integer userId) {
        StudyPoint studyPoint = null;
        return ResultGenerator.success(studyPoint);
    }

    @GetMapping("/user/{id}/study_point/history")
    @ApiOperation(value = "get user's study point transactions", notes = "返回用户学习积分变动历史记录")
    public Result<List<StudyPointHistory>> getUserStudyPointHistory(@PathVariable("id") Integer userId,
                                                                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<StudyPointHistory> result = null;
        return ResultGenerator.success(result);
    }

    @PostMapping("/user/{id}/study_point")
    @ApiOperation(value = "user purchase study point", notes = "返回用户购买学习积分成功与否")
    public Result purchaseStudyPoint(@PathVariable("id") Integer userId,
                                     @RequestParam("amount") Integer amount,
                                     @RequestParam("transactionId") String transactionId){
        return ResultGenerator.success();
    }
}
