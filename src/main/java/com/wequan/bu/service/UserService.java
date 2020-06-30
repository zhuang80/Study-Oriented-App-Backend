package com.wequan.bu.service;

import com.wequan.bu.repository.model.AppointmentReview;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.repository.model.extend.UserFollowBriefInfo;
import com.wequan.bu.repository.model.extend.UserStats;

import java.util.List;

/**
 * @author ChrisChen
 */
public interface UserService extends Service<User> {

    /** 检查email是否已被注册
     * @param email email
     * @return 已被注册 -> true; 未被注册 -> false
     */
    boolean checkEmailRegistered(String email);

    /**
     * 检查用户名是否已被注册
     * @param userName 用户名
     * @return 是否已被注册
     */
    boolean checkUerNameRegistered(String userName);

    /**
     * 注册用户
     * @param user 用户实例
     * @return 注册消息
     */
    boolean registerUser(User user);

    /** 发送验证email
     * @param receiver email地址
     * @param useName 用户昵称
     */
    void sendConfirmEmail(String receiver, String useName);

    /**
     * 邮箱得到认证
     * @param email email
     * @return 更新数据库成功与否
     */
    boolean confirmEmail(String email);

    /**
     * 获取用户profile
     * @param userId 用户id
     * @return 用户实体
     */
    UserStats getUserProfile(Integer userId);

    /**
     * 关注/取关其他用户
     * @param userId 用户id
     * @param otherUserId 其他用户id
     * @param action 关注/取关
     */
    void followOtherUser(Integer userId, Integer otherUserId, Integer action);

    /**
     * 获取用户所关注的用户
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 用户所关注的用户列表
     */
    List<UserFollowBriefInfo> getUserFollowing(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 获取用户粉丝
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 用户粉丝列表
     */
    List<UserFollowBriefInfo> getUserFollower(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 获取用户appointment评价列表
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 用户appointment评价列表
     */
    List<AppointmentReview> getUserAppointmentReviews(Integer userId, Integer pageNum, Integer pageSize);
}
