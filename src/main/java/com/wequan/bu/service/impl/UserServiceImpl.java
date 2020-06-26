package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.UserMapper;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.repository.model.extend.UserStats;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.UserService;
import com.wequan.bu.vendor.AwsEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author ChrisChen
 */
@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AwsEmailService emailService;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(userMapper);
    }

    @Override
    public boolean checkEmailRegistered(String email) {
        User user = userMapper.selectByEmail(email);
        return user != null;
    }

    @Override
    public boolean checkUerNameRegistered(String userName) {
        // to do if need
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerUser(User user) {
        int result = userMapper.insertSelective(user);
        return result > 0;
    }

    @Override
    public void sendConfirmEmail(String receiver, String userName) {
        emailService.sendRegisterEmail(receiver, userName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmEmail(String email) {
        int result = userMapper.updateEmailVerifiedByEmail(email, true);
        return result > 0;
    }

    @Override
    public UserStats getUserProfile(Integer userId) {
        return userMapper.selectById(userId);
    }


}
