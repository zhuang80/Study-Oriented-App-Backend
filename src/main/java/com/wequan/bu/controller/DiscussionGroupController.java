package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.DiscussionGroupMemberIdsWrapper;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.DiscussionGroup;
import com.wequan.bu.service.DiscussionGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "Discussion Group")
public class DiscussionGroupController {

    private static final Logger log = LoggerFactory.getLogger(DiscussionGroupController.class);

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private DiscussionGroupService discussionGroupService;

    @GetMapping("/discussion_groups")
    @ApiOperation(value = "Available discussion group", notes = "Discussion group列表，按临近时间倒序")
    public Result<List<DiscussionGroup>> getAvailableDiscussionGroups(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<DiscussionGroup> result = discussionGroupService.findAll(pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @GetMapping("/discussion_groups/school/{id}")
    @ApiOperation(value = "Show a list of discussion group in user’s school", notes = "返回与school对应的discussion group列表")
    public Result<List<DiscussionGroup>> getDiscussionGroupsBySchool(@PathVariable("id") Integer id,
                                                                     @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(id < 0){
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        List<DiscussionGroup> result = discussionGroupService.findBySchoolId(id, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @GetMapping("/discussion_group/{id}")
    @ApiOperation(value = "Discussion group detail", notes = "返回discussion group详情，包括成员列表")
    public Result<DiscussionGroup> getDiscussionGroup(@PathVariable("id") Integer id) {
        if (id < 0) {
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        DiscussionGroup discussionGroup = discussionGroupService.findDetailById(id);
        return ResultGenerator.success(discussionGroup);
    }

    @PostMapping("/discussion_group")
    @ApiOperation(value = "Create discussion group", notes = "返回创建discussion group成功与否")
    public Result addDiscussionGroup(@RequestBody DiscussionGroup discussionGroup) {
        discussionGroupService.save(discussionGroup);
        return ResultGenerator.success();
    }

    @PutMapping("/discussion_group/{id}/availability")
    @ApiOperation(value = "group admin changes discussion group availability", notes = "讨论组管理员改变群的转态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "action", value = "1 -> open; -1 -> close")
    })
    public Result changeAvailability(@PathVariable("id") Integer id,
                                     @RequestParam("action") Short action){
        if(id < 0){
            String message = messageHandler.getFailResponseMessage("40080");
            return ResultGenerator.fail(message);
        }
        try{
            discussionGroupService.updateAvailability(id, action);
        }catch(Exception e){
            return ResultGenerator.fail(e.getMessage());
        }

        return ResultGenerator.success();
    }

    @PutMapping("/discussion_group/{id}/name")
    @ApiOperation(value = "update the discussion group name", notes = "更新群组的名称")
    public Result changeDiscussionGroupName(@PathVariable("id") Integer id,
                                            @RequestParam("name") String name){
        if(id < 0){
            String message = messageHandler.getFailResponseMessage("40080");
            return ResultGenerator.fail(message);
        }
        try{
            discussionGroupService.updateName(id, name);
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }

    @PutMapping("/discussion_group/{id}/visibility")
    @ApiOperation(value = "group admin changes the discussion group visibility", notes = "更新discussion group的对校外可视性")
    public Result changeVisibility(@PathVariable("id") Integer id,
                                   @RequestParam("visibility") boolean visibility){
        if(id < 0){
            String message = messageHandler.getFailResponseMessage("40080");
            return ResultGenerator.fail(message);
        }

        try{
            discussionGroupService.updateVisibility(id, visibility);
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }

    @PutMapping("/discussion_group/{id}/brief_description")
    @ApiOperation(value = "group admin changes the discussion group brief description", notes = "更新discussion group的简介")
    public Result changeBriefDescription(@PathVariable("id") Integer id,
                                         @RequestBody String briefDescription){
        if(id < 0){
            String message = messageHandler.getFailResponseMessage("40080");
            return ResultGenerator.fail(message);
        }

        try{
            discussionGroupService.updateBriefDescription(id, briefDescription);
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }

    @PutMapping("/discussion_group/{id}/group_message")
    @ApiOperation(value = "group admin changes the group message", notes = "群管理员，更新群通知")
    public Result changeGroupMessage(@PathVariable("id") Integer id,
                                     @RequestBody String groupMessage){
        if(id < 0){
            String message = messageHandler.getFailResponseMessage("40080");
            return ResultGenerator.fail(message);
        }

        try{
            discussionGroupService.updateGroupMessage(id, groupMessage);
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }

    @PutMapping("/discussion_group/{id}/tag")
    @ApiOperation(value = "group admin changes tag", notes = "群管理员，更新群标签")
    public Result changeTag(@PathVariable("id") Integer id,
                            @RequestParam("tag_id") Short tagId){
        if(id < 0 || tagId < 0){
            String message = messageHandler.getFailResponseMessage("40080");
            return ResultGenerator.fail(message);
        }

        try{
            discussionGroupService.updateTag(id, tagId);
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }

    @PutMapping("/discussion_group/{id}/logo")
    @ApiOperation(value = "group admin changes logo", notes = "群管理员，更新群logo")
    public Result changeLogo(@PathVariable("id") Integer id,
                            @RequestParam("logo_url") String logoUrl){
        if(id < 0){
            String message = messageHandler.getFailResponseMessage("40080");
            return ResultGenerator.fail(message);
        }

        try{
            discussionGroupService.updateLogo(id, logoUrl);
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }

    @GetMapping("/discussion_group/{id}/members")
    @ApiOperation(value = "get the member list of a given discussion group", notes = "返回某个讨论组的所有成员id, 服务于IM系统")
    public Result<DiscussionGroupMemberIdsWrapper> getMemberIds(@PathVariable("id") Integer id){
        if(id < 0){
            String message = messageHandler.getFailResponseMessage("40080");
            return ResultGenerator.fail(message);
        }

        DiscussionGroupMemberIdsWrapper memberIdsWrapper = new DiscussionGroupMemberIdsWrapper();
        DiscussionGroup discussionGroup = discussionGroupService.findById(id);
        memberIdsWrapper.setId(discussionGroup.getId());
        memberIdsWrapper.setMemberIds(discussionGroupService.findMemberIdsByDiscussionGroupId(id));
       return ResultGenerator.success(memberIdsWrapper);
    }

    @GetMapping("/discussion_group/members")
    @ApiOperation(value = "get the member list of all discussion group", notes = "返回每个discussion group的成员列表")
    public Result<List<DiscussionGroupMemberIdsWrapper>> getMemberIdsForAllDiscussionGroup(){
        return ResultGenerator.success(discussionGroupService.findMemberIdsForAllDiscussionGroup());
    }

}
