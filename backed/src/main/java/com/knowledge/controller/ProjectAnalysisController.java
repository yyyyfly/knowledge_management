package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.ProjectAnalysis;
import com.knowledge.service.ProjectAnalysisService;
import com.knowledge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目分析控制器
 */
@RestController
@RequestMapping("/project/analysis")
@CrossOrigin
public class ProjectAnalysisController {

    @Autowired
    private ProjectAnalysisService projectAnalysisService;

    /**
     * 创建或更新项目分析（upsert逻辑）
     */
    @PostMapping
    public Result<ProjectAnalysis> saveOrUpdate(@RequestBody ProjectAnalysis analysis, @RequestHeader("Authorization") String token) {
        try {
            String username = JwtUtil.getUsernameFromToken(token.substring(7));
            ProjectAnalysis saved = projectAnalysisService.saveOrUpdate(analysis, username);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.error("保存项目分析失败：" + e.getMessage());
        }
    }

    /**
     * 获取所有项目分析
     */
    @GetMapping("/list")
    public Result<List<ProjectAnalysis>> getAllAnalyses(@RequestHeader("Authorization") String token) {
        try {
            String username = JwtUtil.getUsernameFromToken(token.substring(7));
            List<ProjectAnalysis> analyses = projectAnalysisService.getAllAnalyses(username);
            return Result.success(analyses);
        } catch (Exception e) {
            return Result.error("获取项目分析列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据项目ID获取项目分析
     */
    @GetMapping("/project/{projectId}")
    public Result<ProjectAnalysis> getByProjectId(@PathVariable Long projectId, @RequestHeader("Authorization") String token) {
        try {
            String username = JwtUtil.getUsernameFromToken(token.substring(7));
            ProjectAnalysis analysis = projectAnalysisService.getByProjectId(projectId, username);
            return Result.success(analysis);
        } catch (Exception e) {
            return Result.error("获取项目分析失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID获取项目分析详情
     */
    @GetMapping("/{id}")
    public Result<ProjectAnalysis> getById(@PathVariable Long id) {
        try {
            ProjectAnalysis analysis = projectAnalysisService.getById(id);
            return Result.success(analysis);
        } catch (Exception e) {
            return Result.error("获取项目分析详情失败：" + e.getMessage());
        }
    }

    /**
     * 删除项目分析
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteAnalysis(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            String username = JwtUtil.getUsernameFromToken(token.substring(7));
            projectAnalysisService.deleteAnalysis(id, username);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除项目分析失败：" + e.getMessage());
        }
    }
}

