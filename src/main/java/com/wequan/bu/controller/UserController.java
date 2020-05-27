package com.wequan.bu.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wequan.bu.config.WeQuanConstants;
import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.User;
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

import java.util.Date;

/**
 * @author ChrisChen
 */
@RestController
@RequestMapping("/user")
@Api(value = "对用户的操作", tags = "用户相关Rest Api")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "返回注册信息")
    @ApiModelProperty(value = "user", notes = "用户信息的json串")
    public String register(User user) {
        String nickname = user.getNickname();
        String email = user.getEmail();
        String password = user.getPassword();
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

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "返回注册信息")
    public ResponseEntity<String> login(User user) {
        String email = user.getEmail();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
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

    @GetMapping("/e-confirm")
    public String emailConfirm(@RequestParam("tokenKey") String tokenKey) {

        return "";
    }

    @PostMapping("/reset-password")
    public String resetPassword() {
        return "";
    }

    @GetMapping("/all")
    public String getAll() {
        StringBuilder result = new StringBuilder();
        userService.findAll().stream().map(user -> user.getEmail()).forEach(e -> result.append(e));
        return result.toString();
    }


}
