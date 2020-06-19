package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.TutorApplication;
import com.wequan.bu.repository.model.TutorApplicationLog;
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
@Api(tags = "Apply Tutor")
public class TutorAdminController {

    private static final Logger log = LoggerFactory.getLogger(TutorAdminController.class);

    @GetMapping("/user/{id}/tutor_application/status")
    @ApiOperation(value = "get the status code for user's tutor application", notes = "返回用户申请tutor的当前状态")
    public Result<Integer> getTutorApplicationStatus(@PathVariable("id") Integer userId) {
        return ResultGenerator.success(0);
    }

    @GetMapping("/user/{id}/tutor_application/logs")
    @ApiOperation(value = "get the logs for user's tutor application", notes = "返回用户申请tutor的全部日志，包括历史日志，按时间倒序")
    public Result<List<TutorApplicationLog>> getTutorApplicationLogs(@PathVariable("id") Integer userId) {
        List<TutorApplicationLog> logs = null;
        return ResultGenerator.success(logs);
    }

    @PostMapping("/user/{id}/tutor_application")
    @ApiOperation(value = "raise tutor application", notes = "返回用户提交Tutor申请成功与否")
    public Result postTutorApplication(@RequestBody TutorApplication tutorApplication) {

        return ResultGenerator.success();
    }

    @PutMapping("/user/{id}/tutor_application")
    @ApiOperation(value = "modify tutor application", notes = "返回用户修改Tutor申请成功与否")
    public Result modifyTutorApplication(@RequestBody TutorApplication tutorApplication) {

        return ResultGenerator.success();
    }

    @GetMapping("/user/{id}/tutor_applications/current")
    @ApiOperation(value = "get current tutor application info for user", notes = "返回用户当前tutor申请信息")
    public Result<TutorApplication> getCurrentTutorApplicationInfo(@PathVariable("id") Integer userId) {
        TutorApplication result = null;
        return ResultGenerator.success(result);
    }

}
