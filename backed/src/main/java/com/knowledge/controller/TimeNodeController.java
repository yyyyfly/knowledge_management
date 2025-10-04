package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.TimeNode;
import com.knowledge.service.TimeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 时间节点控制器
 *
 * @author system
 * @date 2025-10-04
 */
@RestController
@RequestMapping("/timenode")
public class TimeNodeController {
    
    @Autowired
    private TimeNodeService timeNodeService;
    
    /**
     * 根据ID查询时间节点
     */
    @GetMapping("/{id}")
    public Result<TimeNode> getById(@PathVariable Long id) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        TimeNode timeNode = timeNodeService.getById(id, currentUser);
        if (timeNode != null) {
            return Result.success(timeNode);
        }
        return Result.error("时间节点不存在");
    }
    
    /**
     * 查询所有时间节点
     */
    @GetMapping("/list")
    public Result<List<TimeNode>> getAll() {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<TimeNode> list = timeNodeService.getAll(currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据类型查询时间节点
     */
    @GetMapping("/type/{type}")
    public Result<List<TimeNode>> getByType(@PathVariable String type) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<TimeNode> list = timeNodeService.getByType(type, currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据状态查询时间节点
     */
    @GetMapping("/status/{status}")
    public Result<List<TimeNode>> getByStatus(@PathVariable String status) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<TimeNode> list = timeNodeService.getByStatus(status, currentUser);
        return Result.success(list);
    }
    
    /**
     * 创建时间节点
     */
    @PostMapping
    public Result<TimeNode> create(@RequestBody TimeNode timeNode) {
        // 设置创建人为当前登录用户
        timeNode.setRecCreator(com.knowledge.util.UserContext.getCurrentUser());
        TimeNode created = timeNodeService.create(timeNode);
        return Result.success("创建成功", created);
    }
    
    /**
     * 更新时间节点
     */
    @PutMapping
    public Result<TimeNode> update(@RequestBody TimeNode timeNode) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        TimeNode updated = timeNodeService.update(timeNode, currentUser);
        return Result.success("更新成功", updated);
    }
    
    /**
     * 删除时间节点
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = timeNodeService.delete(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}

