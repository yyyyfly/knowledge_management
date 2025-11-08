package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.ProjectKeyPoint;
import com.knowledge.service.ProjectKeyPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目关键备忘 Controller
 */
@RestController
@RequestMapping("/project")
public class ProjectKeyPointController {
    
    @Autowired
    private ProjectKeyPointService keyPointService;
    
    /**
     * 获取项目的所有关键备忘
     */
    @GetMapping("/{projectId}/keypoints")
    public Result<List<ProjectKeyPoint>> getProjectKeyPoints(@PathVariable Long projectId) {
        try {
            List<ProjectKeyPoint> keyPoints = keyPointService.getKeyPointsByProjectId(projectId);
            return Result.success(keyPoints);
        } catch (Exception e) {
            return Result.error("获取关键备忘失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据类型获取项目的关键备忘
     */
    @GetMapping("/{projectId}/keypoints/type/{type}")
    public Result<List<ProjectKeyPoint>> getProjectKeyPointsByType(
            @PathVariable Long projectId,
            @PathVariable String type) {
        try {
            List<ProjectKeyPoint> keyPoints = keyPointService.getKeyPointsByProjectIdAndType(projectId, type);
            return Result.success(keyPoints);
        } catch (Exception e) {
            return Result.error("获取关键备忘失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取项目的关键备忘数量
     */
    @GetMapping("/{projectId}/keypoints/count")
    public Result<Integer> countProjectKeyPoints(@PathVariable Long projectId) {
        try {
            int count = keyPointService.countByProjectId(projectId);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("获取关键备忘数量失败：" + e.getMessage());
        }
    }
    
    /**
     * 添加关键备忘
     */
    @PostMapping("/{projectId}/keypoints")
    public Result<ProjectKeyPoint> addKeyPoint(
            @PathVariable Long projectId,
            @RequestBody ProjectKeyPoint keyPoint) {
        try {
            keyPoint.setProjectId(projectId);
            ProjectKeyPoint saved = keyPointService.addKeyPoint(keyPoint);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.error("添加关键备忘失败：" + e.getMessage());
        }
    }
    
    /**
     * 更新关键备忘
     */
    @PutMapping("/keypoints/{id}")
    public Result<Boolean> updateKeyPoint(
            @PathVariable Long id,
            @RequestBody ProjectKeyPoint keyPoint) {
        try {
            keyPoint.setId(id);
            boolean success = keyPointService.updateKeyPoint(keyPoint);
            return Result.success(success);
        } catch (Exception e) {
            return Result.error("更新关键备忘失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除关键备忘
     */
    @DeleteMapping("/keypoints/{id}")
    public Result<Boolean> deleteKeyPoint(@PathVariable Long id) {
        try {
            boolean success = keyPointService.deleteKeyPoint(id);
            return Result.success(success);
        } catch (Exception e) {
            return Result.error("删除关键备忘失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除项目的所有备忘
     */
    @DeleteMapping("/{projectId}/keypoints")
    public Result<Boolean> deleteProjectKeyPoints(@PathVariable Long projectId) {
        try {
            boolean success = keyPointService.deleteByProjectId(projectId);
            return Result.success(success);
        } catch (Exception e) {
            return Result.error("删除项目备忘失败：" + e.getMessage());
        }
    }
}


