package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.UserVo;
import com.wequan.bu.repository.dao.AppointmentReviewMapper;
import com.wequan.bu.repository.dao.UserFollowMapper;
import com.wequan.bu.repository.dao.UserMapper;
import com.wequan.bu.repository.dao.UserSubjectMapper;
import com.wequan.bu.repository.model.AppointmentReview;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.repository.model.UserFollow;
import com.wequan.bu.repository.model.UserSubject;
import com.wequan.bu.repository.model.extend.TutorBriefInfo;
import com.wequan.bu.repository.model.extend.UserFollowBriefInfo;
import com.wequan.bu.repository.model.extend.UserStats;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.UserService;
import com.wequan.bu.vendor.AwsEmailService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ChrisChen
 */
@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserFollowMapper userFollowMapper;
    @Autowired
    private UserSubjectMapper userSubjectMapper;
    @Autowired
    private AppointmentReviewMapper appointmentReviewMapper;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void followOtherUser(Integer userId, Integer otherUserId, Integer action) {
        if (action == 1) {
            UserFollow userFollow = new UserFollow();
            userFollow.setUserId(userId);
            userFollow.setFollowId(otherUserId);
            userFollow.setCreateTime(new Date());
            userFollowMapper.insertSelective(userFollow);
        }
        if (action == -1) {
            Map<String, Integer> params = new HashMap<>(2);
            params.put("userId", userId);
            params.put("followId", otherUserId);
            userFollowMapper.deleteByPrimaryKey(params);
        }
    }

    @Override
    public List<UserFollowBriefInfo> getUserFollowing(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return userFollowMapper.selectFollowingByUserId(userId, rowBounds);
    }

    @Override
    public List<UserFollowBriefInfo> getUserFollower(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return userFollowMapper.selectFollowerByUserId(userId, rowBounds);
    }

    @Override
    public List<AppointmentReview> getUserAppointmentReviews(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return appointmentReviewMapper.selectByUserId(userId, rowBounds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserProfile(Integer userId, UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setId(userId);
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        String subjectIds = userVo.getSubjectIds();
        if (StringUtils.isNotBlank(subjectIds)) {
            String[] sIds = subjectIds.split(",");
            List<UserSubject> userSubjects = Stream.of(sIds).map(sId -> new UserSubject(userId, Integer.valueOf(sId), new Date())).collect(Collectors.toList());
            userSubjectMapper.insertList(userSubjects);
        }
    }

    @Override
    public Integer getUserStudyPoint(Integer userId) {
        return userMapper.selectStudyPointByUserId(userId);
    }

    @Override
    public int updateUserStudyPoint(Integer userId, Short amount) {
        return userMapper.updateStudyPointByUserId(userId, amount);
    }

    @Override
    public TutorBriefInfo getUserTutorProfile(Integer userId) {
        return userMapper.selectTutorProfileByUserId(userId);
    }


}
