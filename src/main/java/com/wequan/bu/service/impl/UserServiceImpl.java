package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.UserMapper;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.UserService;
import com.wequan.bu.vendor.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author ChrisChen
 */
@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailService emailService;

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
    public void sendConfirmEmail(String receiver, String nickname) {
        emailService.sendTemplate(receiver, nickname);
    }


}
