package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.repository.model.OnlineEvent;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.service.OnlineEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "Online Event")
//@ApiIgnore
public class OnlineEventController {

    private static final Logger log = LoggerFactory.getLogger(OnlineEventController.class);

    @Autowired
    private OnlineEventService onlineEventService;

    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/online_events")
    @ApiOperation(value = "Available online event", notes = "返回Online event列表，按临近时间倒序(公开课，Activity)")
    public Result<List<OnlineEvent>> getAvailableOnlineEvents(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return ResultGenerator.success(onlineEventService.findAll(pageNum, pageSize));
    }

    @GetMapping("/online_events/school/{id}")
    @ApiOperation(value = "Show a list of online event in user’s school", notes = "返回与school对应的online event列表")
    public Result<List<OnlineEvent>> getOnlineEventBySchool(@PathVariable("id") Integer id,
                                                                @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<OnlineEvent> result = onlineEventService.findAllBySchoolId(id, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @GetMapping("/online_event/classes")
    @ApiOperation(value = "Public class list", notes = "返回公开课列表")
    public Result<List<OnlineEvent>> getPublicClassEvents(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
       List<OnlineEvent> result = onlineEventService.findAllPublicClasses(pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @GetMapping("/online_event/activities")
    @ApiOperation(value = "Activity list", notes = "返回Activity列表")
    public Result<List<OnlineEvent>> getActivityEvents(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<OnlineEvent> result = onlineEventService.findAllActivities(pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @GetMapping("/online_event/{id}")
    @ApiOperation(value = "Online event detail", notes = "Online event详情")
    public Result<OnlineEvent> getOnlineEvent(@PathVariable("id") Integer id) {
        if(id < 0){
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        return ResultGenerator.success(onlineEventService.findById(id));
    }

    @PostMapping("/online_event")
    @ApiOperation(value = "Create an online event", notes = "返回创建online event成功与否")
    public Result addOnlineEvent(@RequestBody OnlineEvent onlineEvent) {
        // only tutor can create public class
        // user can create activities
        try{
            onlineEventService.saveOnlineEvent(onlineEvent);
        } catch (Exception e) {
            return ResultGenerator.fail(e.getMessage());
        }

        return ResultGenerator.success();
    }

    @PutMapping("/online_event/{id}/name")
    @ApiOperation(value = "update the online event name", notes = "更新online event的名字")
    public Result updateName(@PathVariable("id") Integer id,
                             @RequestParam("name") String name){
        onlineEventService.updateName(id, name);
        return ResultGenerator.success();
    }

    @PutMapping("/online_event/{id}/brief_description")
    @ApiOperation(value = "update the online event brief description", notes = "更新online event的简介")
    public Result updateDescription(@PathVariable("id") Integer id,
                                    @RequestBody String description){
        onlineEventService.updateDescription(id, description);
        return ResultGenerator.success();
    }

    @PutMapping("/online_event/{id}/fee")
    @ApiOperation(value = "update the online event fee", notes = "更新online event的费用")
    public Result updateFee(@PathVariable("id") Integer id,
                            @RequestParam("fee") Integer fee){
        onlineEventService.updateFee(id, fee);
        return ResultGenerator.success();
    }

    @PutMapping("/online_event/{id}/method")
    @ApiOperation(value = "update the online event method", notes = "更新online event的方式")
    public Result updateMethod(@PathVariable("id") Integer id,
                               @RequestBody String method){
        onlineEventService.updateMethod(id, method);
        return ResultGenerator.success();
    }

    @PutMapping("/online_event/{id}/start_time")
    @ApiOperation(value = "update the online event start time", notes = "更新online event的开始时间")
    public Result updateStartTime(@PathVariable("id") Integer id,
                                  @RequestBody LocalDateTime startTime){
        onlineEventService.updateStartTime(id, startTime);
        return ResultGenerator.success();
    }

    @PutMapping("/online_event/{id}/end_time")
    @ApiOperation(value = "update the online event end time", notes = "更新online event的结束时间")
    public Result updateEndTime(@PathVariable("id") Integer id,
                                @RequestBody LocalDateTime endTime){
        onlineEventService.updateEndTime(id, endTime);
        return ResultGenerator.success();
    }

    @PutMapping("/online_event/{id}/visibility")
    @ApiOperation(value = "update the online event visibility ", notes = "更新online event的可见性")
    public Result updateVisibility(@PathVariable("id") Integer id,
                                    @RequestParam("visibility") boolean visibility){
        onlineEventService.updateVisibility(id, visibility);
        return ResultGenerator.success();
    }

    @PutMapping("/online_event/{id}/method_detail")
    @ApiOperation(value = "update the online event method detail ", notes = "更新online event的method detail")
    public Result updateMethodDetail(@PathVariable("id") Integer id,
                                     @RequestBody String methodDetail){
        onlineEventService.updateMethodDetail(id, methodDetail);
        return ResultGenerator.success();
    }

    @PutMapping("/online_event/{id}/logo")
    @ApiOperation(value = "update the online event logo", notes = "更新online event的logo")
    public Result updateLogo(@PathVariable("id") Integer id,
                            @RequestParam("logo") String logo){
        onlineEventService.updateLogo(id, logo);
        return ResultGenerator.success();
    }

    @PutMapping("/online_event/{id}/tag")
    @ApiOperation(value = "update the online event tag", notes = "更新online event的tag")
    public Result updateTag(@PathVariable("id") Integer id,
                            @RequestParam("tagId") Short tagId){
        onlineEventService.updateTag(id, tagId);
        return ResultGenerator.success();
    }
}
