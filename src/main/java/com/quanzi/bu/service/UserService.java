package com.quanzi.bu.service;

import com.quanzi.bu.repository.model.User;

/**
 * @author ChrisChen
 */
public interface UserService extends Service<User> {

    /** 检查email是否已被注册
     * @param email email
     * @return 已被注册 -> true; 未被注册 -> false
     */
    boolean checkEmailRegistered(String email);

    /** 发送验证email
     * @param receiver email地址
     * @param nickname 用户昵称
     */
    void sendConfirmEmail(String receiver, String nickname);

}
