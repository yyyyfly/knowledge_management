package com.knowledge.service.impl;

import com.knowledge.entity.User;
import com.knowledge.mapper.UserMapper;
import com.knowledge.service.AuthService;
import com.knowledge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * 认证服务实现
 */
@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(String username, String password) {
        // 查询用户
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 验证密码（使用MD5加密）
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 更新最后登录时间
        userMapper.updateLastLoginTime(user.getId());

        // 生成Token
        return JwtUtil.generateToken(username);
    }

    @Override
    public User register(String username, String password, String nickname, String email, String phone) {
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(username);
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        // MD5加密密码
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setStatus(1); // 默认启用
        user.setRecCreator("system");

        // 插入数据库
        userMapper.insert(user);

        // 返回用户信息（不包含密码）
        user.setPassword(null);
        return user;
    }

    @Override
    public User getCurrentUser(String username) {
        User user = userMapper.findByUsername(username);
        if (user != null) {
            // 清除密码信息
            user.setPassword(null);
        }
        return user;
    }

    @Override
    public boolean resetPassword(String username, String email, String newPassword) {
        // 验证用户名和邮箱是否匹配
        User user = userMapper.findByUsernameAndEmail(username, email);
        if (user == null) {
            throw new RuntimeException("用户名或邮箱不正确，请核实后重试");
        }

        // 加密新密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());

        // 更新密码
        int result = userMapper.updatePassword(user.getId(), encryptedPassword);
        
        return result > 0;
    }

    @Override
    public boolean updateUserInfo(User user) {
        if (user.getId() == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        
        int result = userMapper.updateUserInfo(user);
        return result > 0;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        // 查询用户
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证旧密码
        String encryptedOldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        if (!encryptedOldPassword.equals(user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }

        // 加密新密码
        String encryptedNewPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());

        // 更新密码
        int result = userMapper.updatePassword(user.getId(), encryptedNewPassword);
        
        return result > 0;
    }

    @Override
    public java.util.List<User> getAllUsers() {
        java.util.List<User> users = userMapper.findAllUsers();
        // 清除所有用户的密码信息
        for (User user : users) {
            user.setPassword(null);
        }
        return users;
    }

    @Override
    public boolean isUsernameExists(String username) {
        int count = userMapper.countByUsername(username);
        return count > 0;
    }
}

