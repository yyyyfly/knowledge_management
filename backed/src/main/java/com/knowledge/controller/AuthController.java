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

        try {
            User user = authService.register(username, password, nickname);
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
}

