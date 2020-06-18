package com.wequan.bu.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wequan.bu.config.WeQuanConstants;
import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.repository.model.User;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "User Login")
public class UserLoginController {

    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);

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
}
