package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.DiscussionGroup;
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
@Api(tags = "Discussion Group")
public class DiscussionGroupController {

    private static final Logger log = LoggerFactory.getLogger(DiscussionGroupController.class);

    @GetMapping("/discussion_groups")
    @ApiOperation(value = "Available discussion group", notes = "Discussion group列表，按临近时间倒序")
    public Result<List<DiscussionGroup>> getAvailableDiscussionGroups(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<DiscussionGroup> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/discussion_groups/school/{id}")
    @ApiOperation(value = "Show a list of discussion group in user’s school", notes = "返回与school对应的discussion group列表")
    public Result<List<DiscussionGroup>> getDiscussionGroupsBySchool(@PathVariable("id") Integer id,
                                                                     @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<DiscussionGroup> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/discussion_group/{id}")
    @ApiOperation(value = "Discussion group detail", notes = "返回discussion group详情")
    public Result<DiscussionGroup> getDiscussionGroup(@PathVariable("id") Integer id) {
        DiscussionGroup discussionGroup = null;
        return ResultGenerator.success(discussionGroup);
    }

    @PostMapping("/discussion_group")
    @ApiOperation(value = "Create discussion group", notes = "返回创建discussion group成功与否")
    public Result addDiscussionGroup(@RequestBody DiscussionGroup discussionGroup) {
        return ResultGenerator.success();
    }

}
