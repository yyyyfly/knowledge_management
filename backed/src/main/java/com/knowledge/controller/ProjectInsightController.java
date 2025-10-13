package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.ProjectInsight;
import com.knowledge.service.ProjectInsightService;
import com.knowledge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目心得控制器
 */
@RestController
@RequestMapping("/project/insight")
@CrossOrigin
public class ProjectInsightController {

    @Autowired
    private ProjectInsightService projectInsightService;

    /**
     * 创建项目心得
     */
    @PostMapping
    public Result<ProjectInsight> createInsight(@RequestBody ProjectInsight insight, @RequestHeader("Authorization") String token) {
        try {
            String username = JwtUtil.getUsernameFromToken(token.substring(7));
            ProjectInsight created = projectInsightService.createInsight(insight, username);
            return Result.success(created);
        } catch (Exception e) {
            return Result.error("创建项目心得失败：" + e.getMessage());
        }
    }

    /**
     * 获取所有项目心得
     */
    @GetMapping("/list")
    public Result<List<ProjectInsight>> getAllInsights(@RequestHeader("Authorization") String token) {
        try {
            String username = JwtUtil.getUsernameFromToken(token.substring(7));
            List<ProjectInsight> insights = projectInsightService.getAllInsights(username);
            return Result.success(insights);
        } catch (Exception e) {
            return Result.error("获取项目心得列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据项目ID获取项目心得
     */
    @GetMapping("/project/{projectId}")
    public Result<List<ProjectInsight>> getInsightsByProject(@PathVariable Long projectId, @RequestHeader("Authorization") String token) {
        try {
            String username = JwtUtil.getUsernameFromToken(token.substring(7));
            List<ProjectInsight> insights = projectInsightService.getInsightsByProjectId(projectId, username);
            return Result.success(insights);
        } catch (Exception e) {
            return Result.error("获取项目心得失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID获取项目心得详情
     */
    @GetMapping("/{id}")
    public Result<ProjectInsight> getInsightById(@PathVariable Long id) {
        try {
            ProjectInsight insight = projectInsightService.getById(id);
            return Result.success(insight);
        } catch (Exception e) {
            return Result.error("获取项目心得详情失败：" + e.getMessage());
        }
    }

    /**
     * 更新项目心得
     */
    @PutMapping("/{id}")
    public Result<ProjectInsight> updateInsight(@PathVariable Long id, @RequestBody ProjectInsight insight, @RequestHeader("Authorization") String token) {
        try {
            String username = JwtUtil.getUsernameFromToken(token.substring(7));
            insight.setId(id);
            ProjectInsight updated = projectInsightService.updateInsight(insight, username);
            return Result.success(updated);
        } catch (Exception e) {
            return Result.error("更新项目心得失败：" + e.getMessage());
        }
    }

    /**
     * 删除项目心得
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteInsight(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            String username = JwtUtil.getUsernameFromToken(token.substring(7));
            projectInsightService.deleteInsight(id, username);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除项目心得失败：" + e.getMessage());
        }
    }
}



