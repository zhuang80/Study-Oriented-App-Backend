package com.wequan.bu.service;

import com.wequan.bu.repository.model.User;

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
}
