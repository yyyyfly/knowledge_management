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
    public User register(String username, String password, String nickname) {
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
}

