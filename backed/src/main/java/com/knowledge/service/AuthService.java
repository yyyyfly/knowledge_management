package com.knowledge.service;

import com.knowledge.entity.User;

/**
 * 认证服务接口
 */
public interface AuthService {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return Token
     */
    String login(String username, String password);

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 注册后的用户信息
     */
    User register(String username, String password, String nickname);

    /**
     * 获取当前登录用户信息
     * @param username 用户名
     * @return 用户信息（不包含密码）
     */
    User getCurrentUser(String username);
}

