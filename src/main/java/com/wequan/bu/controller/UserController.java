package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.Thread;
import com.wequan.bu.controller.vo.*;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.repository.model.UserFollow;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "User")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/{id}/profile")
    @ApiOperation(value = "user basic info", notes = "返回用户基本信息")
    @ApiResponses(
            @ApiResponse(code = 200, message = "name, username, school name, school id, subjects, subject id, avatar, " +
                    "brief intro, # of threads, # of following, # of followers, # of groups")
    )
    public Result<User> getUserProfile(@PathVariable("id") Integer userId) {
        User result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/appointments")
    @ApiOperation(value = "a list of user’s appointment", notes = "返回用户与Tutor的appointment列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of appointments (name, if tutor, appointment id, scheduled time) sorted by incoming time")
    )
    public Result<List<Appointment>> getAppointments(@PathVariable("id") Integer userId,
                                                     @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Appointment> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/online_events")
    @ApiOperation(value = "a list of user’s online event", notes = "返回用户的online event列表")
    public Result<List<OnlineEvent>> getOnlineEvents(@PathVariable("id") Integer userId,
                                                     @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<OnlineEvent> result = null;
        return ResultGenerator.success(result);
    }

    @PostMapping("/user/{id}/online_event")
    @ApiOperation(value = "join/quit online event", notes = "加入/退出online event成功与否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oeId", value = "online event id"),
            @ApiImplicitParam(name = "action", value = "1 -> join; -1 -> quit")
    })
    public Result doOnlineEvent(@PathVariable("id") Integer userId,
                                @RequestParam("oeId") Integer oeId,
                                @RequestParam("action") Integer action) {
        return ResultGenerator.success();
    }

    @GetMapping("/user/{id}/discussion_groups")
    @ApiOperation(value = "a list of user’s discussion group", notes = "返回用户的discussion group列表")
    public Result<List<DiscussionGroup>> getDiscussionGroups(@PathVariable("id") Integer userId,
                                                             @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<DiscussionGroup> result = null;
        return ResultGenerator.success(result);
    }

    @PostMapping("/user/{id}/discussion_group")
    @ApiOperation(value = "join/quit discussion group", notes = "加入/退出discussion group成功与否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dgId", value = "discussion group id"),
            @ApiImplicitParam(name = "action", value = "1 -> join; -1 -> quit")
    })
    public Result doDiscussionGroup(@PathVariable("id") Integer userId,
                                    @RequestParam("dgId") Integer dgId,
                                    @RequestParam("action") Integer action) {
        return ResultGenerator.success();
    }

    @GetMapping("/user/{id}/tutor_inquiries")
    @ApiOperation(value = "a list of user’s tutor inquiry", notes = "返回用户的tutor inquiry列表")
    public Result<List<TutorInquiry>> getTutorInquiries(@PathVariable("id") Integer userId,
                                                        @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<TutorInquiry> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/threads")
    @ApiOperation(value = "a list of user’s threads", notes = "返回用户的thread列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of thread card (title, thread id, user name, user id, tag, created time, first 100 words, \n" +
                    "first 3 photos, # of likes, # of dislikes, # of views, school id, study points reward, status) sorted by created time")
    )
    public Result<List<Thread>> getThreads(@PathVariable("id") Integer userId,
                                           @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Thread> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/favorites")
    @ApiOperation(value = "a list of favorites according to category for user", notes = "按类别返回用户收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "1 -> tutor; 2 -> course; 3 -> material; 4 -> thread; 5 -> professor")
    })
    public Result<List<Object>> getFavorites(@PathVariable("id") Integer userId,
                                             @RequestParam("categoryId") Integer categoryId,
                                             @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Object> result = null;
        result = Collections.singletonList(new ArrayList<Thread>());
        return ResultGenerator.success(result);
    }

    @PostMapping("/user/{id}/favorite")
    @ApiOperation(value = "favorite/unfavorite tutor/course/material/thread/professor", notes = "返回用户按类别收藏/取消收藏成功与否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "1 -> tutor; 2 -> course; 3 -> material; 4 -> thread; 5 -> professor"),
            @ApiImplicitParam(name = "favoriteId", value = "收藏资源的id"),
            @ApiImplicitParam(name = "action", value = "1 -> 收藏；-1 -> 取消收藏"),
    })
    public Result favorite(@PathVariable("id") Integer userId,
                           @RequestParam("categoryId") Integer categoryId,
                           @RequestParam("favoriteId") Integer favoriteId,
                           @RequestParam("action") Integer action) {
        return ResultGenerator.success();
    }

    @PostMapping("/user/{id}/follow")
    @ApiOperation(value = "follow/unfollow other user", notes = "返回关注/取关其他用户成功与否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "action", value = "1 -> 关注；-1 -> 取关")
    })
    public Result followOtherUser(@PathVariable("id") Integer userId,
                                  @RequestParam("otherUserId") Integer otherUserId,
                                  @RequestParam("action") Integer action) {
        return ResultGenerator.success();
    }

    @GetMapping("/user/{id}/following")
    @ApiOperation(value = "a list of following", notes = "返回所关注用户列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of user cards (name, username, user id, avatar, status)")
    )
    public Result<List<UserFollow>> getUserFollowing(@PathVariable("id") Integer userId) {
        List<UserFollow> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/followers")
    @ApiOperation(value = "a list of followers", notes = "返回被关注用户列表")
    public Result<List<UserFollow>> getUserFollowers(@PathVariable("id") Integer userId) {
        List<UserFollow> result = null;
        return ResultGenerator.success(result);
    }

}
