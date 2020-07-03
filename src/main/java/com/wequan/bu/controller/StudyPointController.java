package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.StudyPointHistory;
import com.wequan.bu.service.StudyPointService;
import com.wequan.bu.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "Study Point")
public class StudyPointController {

    private static final Logger log = LoggerFactory.getLogger(StudyPointController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private StudyPointService studyPointService;
    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/user/{id}/study_point")
    @ApiOperation(value = "get user's current study points", notes = "返回用户当前学习积分")
    public Result<Integer> getUserStudyPoint(@PathVariable("id") Integer userId) {
        Integer studyPoint = null;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        studyPoint = userService.getUserStudyPoint(userId);
        return ResultGenerator.success(studyPoint);
    }

    @GetMapping("/user/{id}/study_point/history")
    @ApiOperation(value = "get user's study point transactions", notes = "返回用户学习积分变动历史记录")
    public Result<List<StudyPointHistory>> getUserStudyPointHistory(@PathVariable("id") Integer userId,
                                                                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<StudyPointHistory> studyPointHistories = null;
        if (userId <= 0) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        studyPointHistories = studyPointService.getUserStudyPointTransactions(userId, pageNum,pageSize);
        return ResultGenerator.success(studyPointHistories);
    }

    @PostMapping("/user/{id}/study_point")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "user's study point change", notes = "变动用户学习积分成功与否")
    public Result changeStudyPoint(@PathVariable("id") Integer userId,
                                   @RequestParam("amount") Short amount,
                                   @RequestParam("actionLog") String actionLog,
                                   @RequestParam(value = "transactionId", required = false) String transactionId){
        if (userId <= 0 || StringUtils.isBlank(actionLog)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        int result = userService.updateUserStudyPoint(userId, amount);
        if (result > 0) {
            StudyPointHistory studyPointHistory = StudyPointHistory.builder().userId(userId).changeAmount(amount)
                    .actionLog(actionLog).actionTime(new Date()).transactionId(transactionId).build();
            studyPointService.save(studyPointHistory);
        }
        return ResultGenerator.success();
    }
}
