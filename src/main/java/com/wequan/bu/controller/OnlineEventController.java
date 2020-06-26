package com.wequan.bu.controller;

import com.wequan.bu.repository.model.OnlineEvent;
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
@Api(tags = "Online Event")
public class OnlineEventController {

    private static final Logger log = LoggerFactory.getLogger(OnlineEventController.class);

    @GetMapping("/online_events")
    @ApiOperation(value = "Available online event", notes = "返回Online event列表，按临近时间倒序(公开课，Activity)")
    public Result<List<OnlineEvent>> getAvailableOnlineEvents(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return null;
    }

    @GetMapping("/online_events/school/{id}")
    @ApiOperation(value = "Show a list of online event in user’s school", notes = "返回与school对应的online event列表")
    public Result<List<OnlineEvent>> getOnlineEventBySchool(@PathVariable("id") Integer id,
                                                                @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<OnlineEvent> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/online_event/classes")
    @ApiOperation(value = "Public class list", notes = "返回公开课列表")
    public Result<List<OnlineEvent>> getPublicClassEvents(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<OnlineEvent> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/online_event/activities")
    @ApiOperation(value = "Activity list", notes = "返回Activity列表")
    public Result<List<OnlineEvent>> getActivityEvents(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<OnlineEvent> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/online_event/{id}")
    @ApiOperation(value = "Online event detail", notes = "Online event详情")
    public Result<OnlineEvent> getOnlineEvent(@PathVariable("id") Integer id) {
        OnlineEvent result = null;
        return ResultGenerator.success(result);
    }

    @PostMapping("/online_event")
    @ApiOperation(value = "Create an online event", notes = "返回创建online event成功与否")
    public Result addOnlineEvent(@RequestBody OnlineEvent onlineEvent) {
        // only tutor can create public class
        // user can create activities
        return ResultGenerator.success();
    }

}
