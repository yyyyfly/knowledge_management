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
     * @param email 邮箱
     * @param phone 手机号
     * @return 注册后的用户信息
     */
    User register(String username, String password, String nickname, String email, String phone);

    /**
     * 获取当前登录用户信息
     * @param username 用户名
     * @return 用户信息（不包含密码）
     */
    User getCurrentUser(String username);

    /**
     * 重置密码
     * @param username 用户名
     * @param email 邮箱
     * @param newPassword 新密码
     * @return 是否重置成功
     */
    boolean resetPassword(String username, String email, String newPassword);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 是否更新成功
     */
    boolean updateUserInfo(User user);

    /**
     * 修改密码（已登录用户）
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否修改成功
     */
    boolean changePassword(String username, String oldPassword, String newPassword);

    /**
     * 获取所有用户列表（仅管理员）
     * @return 用户列表（不包含密码）
     */
    java.util.List<User> getAllUsers();

    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean isUsernameExists(String username);
}

