package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.OnlineEvent;
import com.wequan.bu.exception.NotImplementedException;
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
    @ApiOperation(value = "Available online event", notes = "正在进行或即将开始的event列表(公开课，Activity)")
    public List<OnlineEvent> getAvailableOnlineEvents() {
        return null;
    }

    @GetMapping("/online_event/classes")
    @ApiOperation(value = "Public class list", notes = "返回Class page")
    public List<OnlineEvent> getPublicClassEvents() {
        return null;
    }

    @GetMapping("/online_event/activities")
    @ApiOperation(value = "Activity list", notes = "返回Activity page")
    public List<OnlineEvent> getActivityEvents() {
        return null;
    }

    @GetMapping("/online_event/{id}")
    @ApiOperation(value = "Online event detail", notes = "Online event详情")
    public OnlineEvent getAvailableOnlineEvents(@PathVariable("id") int id) {
        return null;
    }

    @PostMapping("/online_event")
    @ApiOperation(value = "Create an online event", notes = "返回创建online event信息")
    public String addOnlineEvent(@RequestBody OnlineEvent onlineEvent) throws NotImplementedException {
        throw new NotImplementedException();
    }

}
