package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.ProjectIssue;
import com.knowledge.service.ProjectIssueService;
import com.knowledge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 项目问题Controller
 */
@RestController
@RequestMapping("/project/issue")
@CrossOrigin
public class ProjectIssueController {

    @Autowired
    private ProjectIssueService projectIssueService;

    /**
     * 创建问题
     */
    @PostMapping
    public Result<ProjectIssue> createIssue(@RequestBody ProjectIssue issue, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            ProjectIssue created = projectIssueService.createIssue(issue, username);
            return Result.success(created);
        } catch (Exception e) {
            return Result.error("创建问题失败：" + e.getMessage());
        }
    }

    /**
     * 获取所有问题
     */
    @GetMapping("/list")
    public Result<List<ProjectIssue>> getAllIssues(@RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            List<ProjectIssue> issues = projectIssueService.getAllIssues(username);
            return Result.success(issues);
        } catch (Exception e) {
            return Result.error("获取问题列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据项目ID获取问题
     */
    @GetMapping("/project/{projectId}")
    public Result<List<ProjectIssue>> getIssuesByProject(@PathVariable Long projectId, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            List<ProjectIssue> issues = projectIssueService.getIssuesByProjectId(projectId, username);
            return Result.success(issues);
        } catch (Exception e) {
            return Result.error("获取项目问题失败：" + e.getMessage());
        }
    }

    /**
     * 根据状态获取问题
     */
    @GetMapping("/status/{status}")
    public Result<List<ProjectIssue>> getIssuesByStatus(@PathVariable String status, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            List<ProjectIssue> issues = projectIssueService.getIssuesByStatus(status, username);
            return Result.success(issues);
        } catch (Exception e) {
            return Result.error("获取问题列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID获取问题
     */
    @GetMapping("/{id}")
    public Result<ProjectIssue> getIssueById(@PathVariable Long id) {
        try {
            ProjectIssue issue = projectIssueService.getById(id);
            return Result.success(issue);
        } catch (Exception e) {
            return Result.error("获取问题详情失败：" + e.getMessage());
        }
    }

    /**
     * 更新问题
     */
    @PutMapping("/{id}")
    public Result<ProjectIssue> updateIssue(@PathVariable Long id, @RequestBody ProjectIssue issue, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            issue.setId(id);
            ProjectIssue updated = projectIssueService.updateIssue(issue, username);
            return Result.success(updated);
        } catch (Exception e) {
            return Result.error("更新问题失败：" + e.getMessage());
        }
    }

    /**
     * 更新问题状态
     */
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            String status = params.get("status");
            projectIssueService.updateStatus(id, status, username);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("更新状态失败：" + e.getMessage());
        }
    }

    /**
     * 处理问题
     */
    @PutMapping("/{id}/resolve")
    public Result<Void> resolveIssue(@PathVariable Long id, @RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            String solution = params.get("solution");
            projectIssueService.resolveIssue(id, solution, username);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("处理问题失败：" + e.getMessage());
        }
    }

    /**
     * 删除问题
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteIssue(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            projectIssueService.deleteIssue(id, username);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除问题失败：" + e.getMessage());
        }
    }

    /**
     * 重新开启问题（取消解决，退回重新处理）
     */
    @PutMapping("/{id}/reopen")
    public Result<Void> reopenIssue(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            projectIssueService.reopenIssue(id, username);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("重新开启问题失败：" + e.getMessage());
        }
    }
}

