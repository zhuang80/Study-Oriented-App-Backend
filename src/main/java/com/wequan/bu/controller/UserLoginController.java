package com.wequan.bu.controller;

import com.wequan.bu.config.WeQuanConstants;
import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.config.properties.AppProperties;
import com.wequan.bu.controller.vo.LoginSignUp;
import com.wequan.bu.controller.vo.StudyPointRule;
import com.wequan.bu.controller.vo.Token;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.event.StudyPointEvent;
import com.wequan.bu.exception.NotImplementedException;
import com.wequan.bu.repository.dao.UserMapper;
import com.wequan.bu.repository.model.StudyPointHistory;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.security.authentication.token.EmailPasswordAuthenticationToken;
import com.wequan.bu.security.authentication.token.UserNamePasswordAuthenticationToken;
import com.wequan.bu.security.component.TokenProvider;
import com.wequan.bu.security.oauth2.user.AuthProvider;
import com.wequan.bu.service.UserService;
import com.wequan.bu.util.GeneralTool;
import com.wequan.bu.util.redis.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "User Login")
public class UserLoginController {

    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);
    private static final Object lock = new Object();

    @Autowired
    private UserService userService;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/user/register")
    @ApiOperation(value = "user register", notes = "返回注册信息")
    @ApiModelProperty(value = "user", notes = "用户信息的json串")
    public Result register(@RequestBody LoginSignUp loginSignUp) {
        String userName = loginSignUp.getUserName();
        String email = loginSignUp.getEmail();
        String password = loginSignUp.getPassword();
        //check parameters
        String msg;
        if (!GeneralTool.checkUsername(userName)) {
            msg = messageHandler.getMessage("40001", "User Name");
            return ResultGenerator.fail(msg);
        }
        if (!GeneralTool.checkEmail(email)) {
            msg = messageHandler.getMessage("40001", "Email");
            return ResultGenerator.fail(msg);
        }
        if (!GeneralTool.checkPassword(password)) {
            msg = messageHandler.getMessage("40001", "Password");
            return ResultGenerator.fail(msg);
        }
        //check email registered
        if (userService.checkEmailRegistered(email)) {
            msg = messageHandler.getMessage("40002", email);
            return ResultGenerator.fail(msg);
        }
        //check username registered
        if (userService.checkUerNameRegistered(userName)) {
            msg = messageHandler.getMessage("40002", email);
            return ResultGenerator.fail(msg);
        }
        //add user info
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setProvider(AuthProvider.LOCAL.toString());
        user.setCredential(passwordEncoder.encode(password));
        user.setCreateTime(new Date());
        boolean success = userService.registerUser(user);
        if (success) {
            //send confirm email
            userService.sendConfirmEmail(email, userName);
            return ResultGenerator.success();
        } else {
            return ResultGenerator.fail(500, messageHandler.getMessage("500"));
        }
    }

    @PostMapping("/user/login")
    @ApiOperation(value = "user login", notes = "返回登录信息")
    public Result login(@RequestBody LoginSignUp loginSignUp) {
        String userName = loginSignUp.getUserName();
        String email = loginSignUp.getEmail();
        String password = loginSignUp.getPassword();
        Authentication authentication = null;
        if (StringUtils.hasText(userName) && StringUtils.hasText(password)) {
            try {
                authentication = authenticationManager.authenticate(
                        new UserNamePasswordAuthenticationToken(userName, password)
                );
            } catch (Exception e) {
                log.error(String.format("Authentication fail with username = %s", userName), e);
            }
        }
        if (authentication == null && StringUtils.hasText(email) && StringUtils.hasText(password)) {
            try {
                authentication = authenticationManager.authenticate(
                        new EmailPasswordAuthenticationToken(email, password)
                );
            } catch (Exception e) {
                log.error(String.format("Authentication fail with email = %s", email), e);
            }
        }
        if (Objects.isNull(authentication)) {
            return ResultGenerator.fail("认证失败");
        } else {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Token accessToken = tokenProvider.createToken(authentication);
            Token refreshToken = tokenProvider.createRefreshToken(authentication);
            //cache in redis in case unnecessary token create
            redisUtil.set(WeQuanConstants.ACCESS_TOKEN_PREFIX_IN_REDIS + accessToken.getSubject(), accessToken.getToken(),
                    (accessToken.getExpiryDate().getTime() - System.currentTimeMillis()) / 1000);
            redisUtil.set(WeQuanConstants.REFRESH_TOKEN_PREFIX_IN_REDIS + accessToken.getSubject(), refreshToken.getToken(),
                    (refreshToken.getExpiryDate().getTime() - System.currentTimeMillis()) / 1000);
            Map<String, String> tokens = new HashMap<>(2);
            tokens.put("access_token", accessToken.getToken());
            tokens.put("refresh_token", refreshToken.getToken());
            return ResultGenerator.success(tokens);
        }
    }

    @GetMapping("/user/e-confirm")
    @ApiOperation(value = "verify user", notes = "返回验证注册用户信息")
    public Result emailConfirm(@RequestParam("token") String token) {
        Result result;
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            Claims claims = Jwts.parser()
                    .setSigningKey(appProperties.getAuth().getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody();
            String subject = claims.getSubject();
            String email = subject.split("\\|\\|")[0];
            userService.confirmEmail(email);
            result = ResultGenerator.success();
            //add study point
            StudyPointHistory studyPoint = StudyPointHistory.builder().
                    userId(0).actionLog(StudyPointRule.REGISTER_SUCCESS.name().toLowerCase())
                    .changeAmount((short)StudyPointRule.REGISTER_SUCCESS.getStudyPoint()).remainingAmount((short)0).actionTime(new Date()).build();
            applicationContext.publishEvent(new StudyPointEvent(studyPoint));
        } else {
            result = ResultGenerator.fail(messageHandler.getMessage("40099"));
        }
        return result;
    }

    @PostMapping("/user/reset-password")
    @ApiOperation(value = "reset password", notes = "返回重置用户密码信息")
    public String resetPassword() {
        throw new NotImplementedException();
    }

    @GetMapping("/access_token/refresh")
    @ApiOperation(value = "refresh access/refresh token", notes = "返回新的access token/refresh token")
    public Result refreshToken(@RequestParam("refreshToken") String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(appProperties.getAuth().getRefreshTokenSecret()).parseClaimsJws(refreshToken);
            Claims claims = claimsJws.getBody();
            //refresh access token
            String userId = claims.getSubject();
            Object accessTokenObj = redisUtil.get(WeQuanConstants.ACCESS_TOKEN_PREFIX_IN_REDIS + userId);
            if (Objects.isNull(accessTokenObj)) {
                synchronized (lock) {
                    accessTokenObj = redisUtil.get(WeQuanConstants.ACCESS_TOKEN_PREFIX_IN_REDIS + userId);
                    if (Objects.isNull(accessTokenObj)) {
                        Authentication authentication = new UserNamePasswordAuthenticationToken(userId, null);
                        Token accessToken = tokenProvider.createToken(authentication);
                        redisUtil.set(WeQuanConstants.ACCESS_TOKEN_PREFIX_IN_REDIS + userId, accessToken.getToken(),
                                (accessToken.getExpiryDate().getTime() - System.currentTimeMillis()) / 1000);
                        accessTokenObj = accessToken.getToken();
                        log.info("new access token = " + accessTokenObj);
                        //update refresh token if almost expiry
                        Date expiration = claims.getExpiration();
                        long tokenExpirationMse = appProperties.getAuth().getTokenExpirationMsec();
                        if ((expiration.getTime() - System.currentTimeMillis()) <= 2 * tokenExpirationMse) {
                            Token newRefreshToken = tokenProvider.createRefreshToken(authentication);
                            redisUtil.set(WeQuanConstants.REFRESH_TOKEN_PREFIX_IN_REDIS + userId, newRefreshToken.getToken(),
                                    (newRefreshToken.getExpiryDate().getTime() - System.currentTimeMillis()) / 1000);
                            log.info("new refresh token = " + newRefreshToken.getToken());
                        }
                    }
                }
            }
            Object refreshTokenObj = redisUtil.get(WeQuanConstants.REFRESH_TOKEN_PREFIX_IN_REDIS + userId);
            if (Objects.nonNull(refreshTokenObj) && !refreshToken.equals(refreshTokenObj)) {
                refreshToken = refreshTokenObj.toString();
            }
            Map<String, String> tokens = new HashMap<>(2);
            tokens.put("access_token", accessTokenObj.toString());
            tokens.put("refresh_token", refreshToken);
            return ResultGenerator.success(tokens);
        } catch (ExpiredJwtException e) {
            //refreshToken过期，客户端跳转登录界面
            log.error("Expired JWT refresh token");
            return new Result().setCode(40095).setMessage(messageHandler.getMessage("40095"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultGenerator.fail(messageHandler.getMessage("40099"));
        }
    }

}
