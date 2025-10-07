package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.User;
import com.knowledge.service.AuthService;
import com.knowledge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        try {
            String token = authService.login(username, password);
            User user = authService.getCurrentUser(username);
            
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", user);
            
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody Map<String, String> registerRequest) {
        String username = registerRequest.get("username");
        String password = registerRequest.get("password");
        String nickname = registerRequest.get("nickname");
        String email = registerRequest.get("email");
        String phone = registerRequest.get("phone");

        try {
            User user = authService.register(username, password, nickname, email, phone);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/current")
    public Result<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            // 去掉 "Bearer " 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            String username = JwtUtil.getUsernameFromToken(token);
            User user = authService.getCurrentUser(username);
            
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("获取用户信息失败");
        }
    }

    /**
     * 退出登录（前端清除Token即可，这里仅作示例）
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success("退出成功");
    }

    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    public Result<String> resetPassword(@RequestBody Map<String, String> resetRequest) {
        String username = resetRequest.get("username");
        String email = resetRequest.get("email");
        String newPassword = resetRequest.get("newPassword");

        try {
            boolean success = authService.resetPassword(username, email, newPassword);
            if (success) {
                return Result.success("密码重置成功，请使用新密码登录");
            } else {
                return Result.error("密码重置失败，请重试");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update-info")
    public Result<User> updateUserInfo(@RequestBody User user, @RequestHeader("Authorization") String token) {
        try {
            // 去掉 "Bearer " 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            String username = JwtUtil.getUsernameFromToken(token);
            User currentUser = authService.getCurrentUser(username);
            
            // 设置用户ID（防止前端传入错误ID）
            user.setId(currentUser.getId());
            
            boolean success = authService.updateUserInfo(user);
            if (success) {
                // 返回更新后的用户信息
                User updatedUser = authService.getCurrentUser(username);
                return Result.success(updatedUser);
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改密码（已登录用户）
     */
    @PostMapping("/change-password")
    public Result<String> changePassword(@RequestBody Map<String, String> passwordRequest, @RequestHeader("Authorization") String token) {
        try {
            // 去掉 "Bearer " 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            String username = JwtUtil.getUsernameFromToken(token);
            String oldPassword = passwordRequest.get("oldPassword");
            String newPassword = passwordRequest.get("newPassword");

            boolean success = authService.changePassword(username, oldPassword, newPassword);
            if (success) {
                return Result.success("密码修改成功");
            } else {
                return Result.error("密码修改失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

