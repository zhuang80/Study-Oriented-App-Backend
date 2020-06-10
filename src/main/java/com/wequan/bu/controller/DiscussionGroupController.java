package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.DiscussionGroup;
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
@Api(tags = "Discussion Group")
public class DiscussionGroupController {

    private static final Logger log = LoggerFactory.getLogger(DiscussionGroupController.class);

    @GetMapping("/discussion_groups")
    @ApiOperation(value = "Available discussion group", notes = "正在进行或即将开始的discussion group列表")
    public List<DiscussionGroup> getAvailableDiscussionGroups() {
        return null;
    }

    @GetMapping("/discussion_groups/school/{id}")
    @ApiOperation(value = "Show a list of discussion group in user’s school", notes = "返回与school对应的discussion group列表")
    public List<DiscussionGroup> getDiscussionGroupsBySchool(@PathVariable("id") int id) {
        return null;
    }

    @GetMapping("/discussion_group/{id}")
    @ApiOperation(value = "Discussion group detail", notes = "返回discussion group详情")
    public DiscussionGroup getDiscussionGroup(@PathVariable("id") int id) {
        return null;
    }

    @PostMapping("/discussion_group")
    @ApiOperation(value = "Create discussion group", notes = "返回创建discussion group信息")
    public String addDiscussionGroup(@RequestBody DiscussionGroup discussionGroup) {
        return null;
    }

}
