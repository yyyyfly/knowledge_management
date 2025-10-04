package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.Project;
import com.knowledge.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目控制器
 *
 * @author system
 * @date 2025-10-04
 */
@RestController
@RequestMapping("/project")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    /**
     * 根据ID查询项目
     */
    @GetMapping("/{id}")
    public Result<Project> getById(@PathVariable Long id) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        Project project = projectService.getById(id, currentUser);
        if (project != null) {
            return Result.success(project);
        }
        return Result.error("项目不存在");
    }
    
    /**
     * 查询所有项目
     */
    @GetMapping("/list")
    public Result<List<Project>> getAll() {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        System.out.println("【ProjectController】查询所有项目，当前用户: " + currentUser);
        List<Project> list = projectService.getAll(currentUser);
        System.out.println("【ProjectController】返回项目数量: " + list.size());
        return Result.success(list);
    }
    
    /**
     * 根据类别查询项目
     */
    @GetMapping("/category/{category}")
    public Result<List<Project>> getByCategory(@PathVariable String category) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        System.out.println("====== 查询类别项目 ======");
        System.out.println("类别: " + category);
        System.out.println("当前用户: " + currentUser);
        List<Project> list = projectService.getByCategory(category, currentUser);
        System.out.println("返回项目数量: " + list.size());
        System.out.println("=======================");
        return Result.success(list);
    }
    
    /**
     * 根据状态查询项目
     */
    @GetMapping("/status/{status}")
    public Result<List<Project>> getByStatus(@PathVariable String status) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Project> list = projectService.getByStatus(status, currentUser);
        return Result.success(list);
    }
    
    /**
     * 创建项目
     */
    @PostMapping
    public Result<Project> create(@RequestBody Project project) {
        // 设置创建人为当前登录用户
        project.setRecCreator(com.knowledge.util.UserContext.getCurrentUser());
        Project created = projectService.create(project);
        return Result.success("创建成功", created);
    }
    
    /**
     * 更新项目
     */
    @PutMapping
    public Result<Project> update(@RequestBody Project project) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        Project updated = projectService.update(project, currentUser);
        return Result.success("更新成功", updated);
    }
    
    /**
     * 删除项目
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = projectService.delete(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}

