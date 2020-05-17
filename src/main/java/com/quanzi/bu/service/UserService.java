package com.quanzi.bu.service;

import com.quanzi.bu.repository.dao.UserMapper;
import com.quanzi.bu.repository.model.User;
import com.quanzi.bu.vendor.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChrisChen
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailService emailService;

    public boolean checkEmailRegistered(String email) {
        User user = userMapper.selectByEmail(email);
        return user != null;
    }

    public void sendConfirmEmail(String receiver, String nickname) {
        emailService.sendTemplate(receiver, nickname);
    }


}
