package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.UserVo;
import com.wequan.bu.repository.dao.*;
import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.*;
import com.wequan.bu.repository.model.extend.LikeRecordBriefInfo;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ChrisChen
 */
@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

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
    @Autowired
    private LikeRecordMapper likeRecordMapper;
    @Autowired
    private ThreadMapper threadMapper;
    @Autowired
    private ThreadStreamMapper threadStreamMapper;

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
            userSubjectMapper.deleteByUserId(userId);
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

    @Override
    public Integer getFollowRelationshipWithOtherUser(Integer userId, Integer otherUserId) {
        int result;
        Map<String, Object> followResult = userMapper.selectFollowEachOther(userId, otherUserId);
        if (Objects.isNull(followResult)) {
            result = 1;
        } else {
            Object uUserId = followResult.get("u_user_id");
            Object oUserId = followResult.get("o_user_id");
            if (Objects.nonNull(uUserId)) {
                result = 2;
            } else {
                result = 1;
            }
            if (Objects.nonNull(oUserId)) {
                result += 2;
            } else {
                result += 0;
            }
        }
        return result;
    }

    @Override
    public boolean checkEmailConfirmed(String email) {
        int count = userMapper.countByEmailAndVerified(email, true);
        return count > 0;
    }

    @Override
    public User getUserProfileByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    @Override
    public List<LikeRecordBriefInfo> getUserLikedResources(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        List<LikeRecordBriefInfo> likeRecordBriefInfoList = likeRecordMapper.selectByResourceBelongId(userId, rowBounds);
        Map<Short, Map<Integer, List<LikeRecordBriefInfo>>> mapMap = new HashMap<>();
        for (LikeRecordBriefInfo info : likeRecordBriefInfoList) {
            Short resourceType = info.getResourceType();
            Integer resourceId = info.getResourceId();
            if (mapMap.containsKey(resourceType)) {
                Map<Integer, List<LikeRecordBriefInfo>> listMap = mapMap.get(resourceType);
                if (listMap.containsKey(resourceId)) {
                    listMap.get(resourceId).add(info);
                } else {
                    List<LikeRecordBriefInfo> likeRecordBriefInfoArrayList = new ArrayList<>();
                    likeRecordBriefInfoArrayList.add(info);
                    listMap.put(resourceId, likeRecordBriefInfoArrayList);
                }
            } else {
                Map<Integer, List<LikeRecordBriefInfo>> listMap = new HashMap<>();
                List<LikeRecordBriefInfo> likeRecordBriefInfoArrayList = new ArrayList<>();
                likeRecordBriefInfoArrayList.add(info);
                listMap.put(resourceId, likeRecordBriefInfoArrayList);
                mapMap.put(resourceType, listMap);
            }
        }

        FutureTask<List<Thread>> threadFutureTask = null;
        FutureTask<List<ThreadStream>> threadStreamFutureTask = null;
        for (Map.Entry<Short, Map<Integer, List<LikeRecordBriefInfo>>> mapEntry : mapMap.entrySet()) {
            int type = (int)mapEntry.getKey();
            switch (type) {
                //thread
                case 4:
                    final String threadIds = mapEntry.getValue().keySet().stream().map(String::valueOf).collect(Collectors.joining(","));
                    Callable<List<Thread>> threadCallable = () -> threadMapper.selectByIds(threadIds);
                    threadFutureTask = new FutureTask<>(threadCallable);
                    break;
                //thread reply
                case 8:
                    final String threadStreamIds = mapEntry.getValue().keySet().stream().map(String::valueOf).collect(Collectors.joining(","));
                    Callable<List<ThreadStream>> threadStreamCallable = () -> threadStreamMapper.selectByIds(threadStreamIds);
                    threadStreamFutureTask = new FutureTask<>(threadStreamCallable);
                    break;
                default:
                    break;
            }
        }

        try {
            List<Thread> threads = null;
            List<ThreadStream> threadStreams = null;
            if (threadFutureTask != null) {
                executorService.submit(threadFutureTask);
                threads = threadFutureTask.get();
            }
            if (threadStreamFutureTask != null) {
                executorService.submit(threadStreamFutureTask);
                threadStreams = threadStreamFutureTask.get();
            }
            if (threads != null) {
                for (Thread thread : threads) {
                    Integer threadId = thread.getId();
                    List<LikeRecordBriefInfo> recordBriefInfoList = mapMap.get((short) 4).get(threadId);
                    recordBriefInfoList.forEach(e -> e.setResource(thread));
                }
            }
            if (threadStreams != null) {
                for (ThreadStream threadStream : threadStreams) {
                    Integer threadStreamId = threadStream.getId();
                    List<LikeRecordBriefInfo> recordBriefInfoList = mapMap.get((short) 8).get(threadStreamId);
                    recordBriefInfoList.forEach(e -> e.setResource(threadStream));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return likeRecordBriefInfoList;
    }

    @Override
    public List<UserFollowBriefInfo> getOtherUserFollowing(Integer currentUserId, Integer otherUserId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return userFollowMapper.selectFollowingByOtherUserId(currentUserId, otherUserId, rowBounds);
    }

    @Override
    public List<UserFollowBriefInfo> getOtherUserFollower(Integer currentUserId, Integer otherUserId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return userFollowMapper.selectFollowerByOtherUserId(currentUserId, otherUserId, rowBounds);
    }

}
