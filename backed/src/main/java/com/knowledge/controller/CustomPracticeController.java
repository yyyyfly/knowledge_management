package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.CustomPractice;
import com.knowledge.service.CustomPracticeService;
import com.knowledge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 定制模拟实战控制器
 *
 * @author system
 * @date 2025-01-15
 */
@RestController
@RequestMapping("/api/custom-practice")
@CrossOrigin
public class CustomPracticeController {
    
    @Autowired
    private CustomPracticeService customPracticeService;
    
    @Autowired
    private HttpServletRequest request;
    
    /**
     * 获取当前登录用户
     */
    private String getCurrentUser() {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return JwtUtil.getUsernameFromToken(token);
        }
        return null;
    }
    
    /**
     * 查询所有定制模拟实战
     */
    @GetMapping("/list")
    public Result<List<CustomPractice>> getAllCustomPractices() {
        String username = getCurrentUser();
        if (username == null) {
            return Result.error("未登录");
        }
        return customPracticeService.getAllCustomPractices(username);
    }
    
    /**
     * 根据ID查询定制模拟实战
     */
    @GetMapping("/{id}")
    public Result<CustomPractice> getCustomPracticeById(@PathVariable Long id) {
        String username = getCurrentUser();
        if (username == null) {
            return Result.error("未登录");
        }
        return customPracticeService.getCustomPracticeById(id, username);
    }
    
    /**
     * 创建定制模拟实战
     */
    @PostMapping
    public Result<Long> createCustomPractice(@RequestBody CustomPractice customPractice) {
        String username = getCurrentUser();
        if (username == null) {
            return Result.error("未登录");
        }
        customPractice.setRecCreator(username);
        return customPracticeService.createCustomPractice(customPractice);
    }
    
    /**
     * 更新定制模拟实战
     */
    @PutMapping
    public Result<String> updateCustomPractice(@RequestBody CustomPractice customPractice) {
        String username = getCurrentUser();
        if (username == null) {
            return Result.error("未登录");
        }
        customPractice.setRecRevisor(username);
        return customPracticeService.updateCustomPractice(customPractice);
    }
    
    /**
     * 删除定制模拟实战
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteCustomPractice(@PathVariable Long id) {
        String username = getCurrentUser();
        if (username == null) {
            return Result.error("未登录");
        }
        return customPracticeService.deleteCustomPractice(id);
    }
}








