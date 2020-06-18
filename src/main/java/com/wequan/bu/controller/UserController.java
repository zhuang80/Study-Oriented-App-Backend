package com.wequan.bu.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wequan.bu.config.WeQuanConstants;
import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.*;
import com.wequan.bu.controller.vo.Thread;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.UserFollow;
import com.wequan.bu.security.component.AppUserDetails;
import com.wequan.bu.service.UserService;
import com.wequan.bu.util.GeneralTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "User")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user/register")
    @ApiOperation(value = "user register", notes = "返回注册信息")
    @ApiModelProperty(value = "user", notes = "用户信息的json串")
    public String register(User user) {
        String nickname = user.getUserName();
        String email = user.getEmail();
        String password = user.getCredential();
        //check parameters
        if (!GeneralTool.checkNickname(nickname)) {
            return messageHandler.getFailResponseMessage("40001", "Nickname");
        }
        if (!GeneralTool.checkEmail(email)) {
            return messageHandler.getFailResponseMessage("40001", "Email");
        }
        if (!GeneralTool.checkPassword(password)) {
            return messageHandler.getFailResponseMessage("40001", "Password");
        }
        //check email registered
        if (userService.checkEmailRegistered(email)) {
            return messageHandler.getFailResponseMessage("40002", email);
        }
        userService.sendConfirmEmail("ccyzhope@gmail.com", "Chris");
        return "";
    }

    @PostMapping("/user/login")
    @ApiOperation(value = "user login", notes = "返回登录信息")
    public ResponseEntity<String> login(User user) {
        String email = user.getEmail();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getCredential()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        String jwt = JWT.create()
                .withSubject(String.valueOf(userDetails.getId()))
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC512(WeQuanConstants.SECRET_KEY.getBytes()));
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setBearerAuth(jwt);
        return ResponseEntity.ok().headers(respHeaders).body("success");
    }

    @GetMapping("/user/e-confirm")
    @ApiOperation(value = "verify user", notes = "返回验证注册用户信息")
    public String emailConfirm(@RequestParam("tokenKey") String tokenKey) {

        return "";
    }

    @PostMapping("/user/reset-password")
    @ApiOperation(value = "reset password", notes = "返回重置用户密码信息")
    public String resetPassword() {
        return "";
    }

    @GetMapping("/users")
    @ApiOperation(value = "a list of users", notes = "返回用户列表")
    public String getAll() {
        StringBuilder result = new StringBuilder();
        userService.findAll().stream().map(user -> user.getEmail()).forEach(e -> result.append(e));
        return result.toString();
    }

    @GetMapping("/user/{id}/appointments")
    @ApiOperation(value = "a list of user’s appointment", notes = "返回用户与Tutor的appointment列表")
    public Result<List<Appointment>> getAppointments(@PathVariable("id") Integer id,
                                                     @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Appointment> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/online_events")
    @ApiOperation(value = "a list of user’s online event", notes = "返回用户的online event列表")
    public Result<List<OnlineEvent>> getOnlineEvents(@PathVariable("id") Integer id,
                                                     @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<OnlineEvent> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/tutor_inquiries")
    @ApiOperation(value = "a list of user’s tutor inquiry", notes = "返回用户的tutor inquiry列表")
    public Result<List<TutorInquiryVo>> getTutorInquiries(@PathVariable("id") Integer id,
                                                          @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<TutorInquiryVo> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/threads")
    @ApiOperation(value = "a list of user’s threads", notes = "返回用户的thread列表")
    public Result<List<Thread>> getThreads(@PathVariable("id") Integer id,
                                           @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Thread> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/profile")
    @ApiOperation(value = "user basic info", notes = "返回用户基本信息")
    public Result<List<TutorInquiryVo>> getUserProfile(@PathVariable("id") Integer id) {
        List<TutorInquiryVo> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/favorites")
    @ApiOperation(value = "a list of favorites according to category for user", notes = "按类别(1->tutor; 2->course; 3->material; 4->thread; 5->professor)返回用户收藏列表")
    public Result<List<Object>> getFavorites(@PathVariable("id") Integer id,
                                             @RequestParam("categoryId") Integer categoryId,
                                             @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Object> result = null;
        result = Collections.singletonList(new ArrayList<Thread>());
        return ResultGenerator.success(result);
    }

    @PostMapping("/user/{id}/favorite")
    @ApiOperation(value = "favorite tutor/course/material/thread/professor", notes = "返回用户按类别(1->tutor; 2->course; 3->material; 4->thread; 5->professor)收藏成功与否")
    public Result favorite(@PathVariable("id") Integer id,
                           @RequestParam("categoryId") Integer categoryId,
                           @RequestParam("favoriteId") Integer favoriteId) {
        return ResultGenerator.success();
    }

    @PostMapping("/user/{id}/unfavorite")
    @ApiOperation(value = "unfavorite tutor/course/material/thread/professor", notes = "返回用户按类别(1->tutor; 2->course; 3->material; 4->thread; 5->professor)取消收藏成功与否")
    public Result unFavorite(@PathVariable("id") Integer id,
                             @RequestParam("categoryId") Integer categoryId,
                             @RequestParam("favoriteId") Integer favoriteId) {
        return ResultGenerator.success();
    }

    @PostMapping("/user/{id}/follow/other_user/{otherId}")
    @ApiOperation(value = "follow other user", notes = "返回关注其他用户成功与否")
    public Result followOtherUser(@PathVariable("id") Integer userId,
                                     @PathVariable("otherId") Integer otherUserId) {
        return ResultGenerator.success();
    }

    @PostMapping("/user/{id}/unfollow/other_user/{otherId}")
    @ApiOperation(value = "unfollow other user", notes = "返回取关其他用户成功与否")
    public Result unFollowOtherUser(@PathVariable("id") Integer userId,
                                    @PathVariable("otherId") Integer otherUserId) {
        return ResultGenerator.success();
    }

    @GetMapping("/user/{id}/follow")
    @ApiOperation(value = "a list of follow", notes = "返回所关注用户列表")
    public Result<List<UserFollow>> getUserFollows(@PathVariable("id") Integer userId) {
        List<UserFollow> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/user/{id}/followed")
    @ApiOperation(value = "a list of followed", notes = "返回被关注用户列表")
    public Result<List<UserFollow>> getUserFollowed(@PathVariable("id") Integer userId) {
        List<UserFollow> result = null;
        return ResultGenerator.success(result);
    }

}
