package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.Task;
import com.knowledge.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务控制器
 *
 * @author system
 * @date 2025-10-04
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    /**
     * 根据ID查询任务
     */
    @GetMapping("/{id}")
    public Result<Task> getById(@PathVariable Long id) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        Task task = taskService.getById(id, currentUser);
        if (task != null) {
            return Result.success(task);
        }
        return Result.error("任务不存在");
    }
    
    /**
     * 查询所有任务
     */
    @GetMapping("/list")
    public Result<List<Task>> getAll() {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Task> list = taskService.getAll(currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据项目ID查询任务
     */
    @GetMapping("/project/{projectId}")
    public Result<List<Task>> getByProjectId(@PathVariable Long projectId) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Task> list = taskService.getByProjectId(projectId, currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据状态查询任务
     */
    @GetMapping("/status/{status}")
    public Result<List<Task>> getByStatus(@PathVariable String status) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Task> list = taskService.getByStatus(status, currentUser);
        return Result.success(list);
    }
    
    /**
     * 创建任务
     */
    @PostMapping
    public Result<Task> create(@RequestBody Task task) {
        // 设置创建人为当前登录用户
        task.setRecCreator(com.knowledge.util.UserContext.getCurrentUser());
        Task created = taskService.create(task);
        return Result.success("创建成功", created);
    }
    
    /**
     * 更新任务
     */
    @PutMapping
    public Result<Task> update(@RequestBody Task task) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        Task updated = taskService.update(task, currentUser);
        return Result.success("更新成功", updated);
    }
    
    /**
     * 删除任务
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        boolean success = taskService.delete(id, currentUser);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}

