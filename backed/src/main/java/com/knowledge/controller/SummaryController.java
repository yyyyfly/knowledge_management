package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.Summary;
import com.knowledge.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统总结控制器
 *
 * @author system
 * @date 2025-10-04
 */
@RestController
@RequestMapping("/summary")
public class SummaryController {
    
    @Autowired
    private SummaryService summaryService;
    
    /**
     * 根据ID查询总结
     */
    @GetMapping("/{id}")
    public Result<Summary> getById(@PathVariable Long id) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        Summary summary = summaryService.getById(id, currentUser);
        if (summary != null) {
            return Result.success(summary);
        }
        return Result.error("总结不存在");
    }
    
    /**
     * 查询所有总结
     */
    @GetMapping("/list")
    public Result<List<Summary>> getAll() {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Summary> list = summaryService.getAll(currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据类型查询总结
     */
    @GetMapping("/type/{type}")
    public Result<List<Summary>> getByType(@PathVariable String type) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Summary> list = summaryService.getByType(type, currentUser);
        return Result.success(list);
    }
    
    /**
     * 创建总结
     */
    @PostMapping
    public Result<Summary> create(@RequestBody Summary summary) {
        // 设置创建人为当前登录用户
        summary.setRecCreator(com.knowledge.util.UserContext.getCurrentUser());
        Summary created = summaryService.create(summary);
        return Result.success("创建成功", created);
    }
    
    /**
     * 更新总结
     */
    @PutMapping
    public Result<Summary> update(@RequestBody Summary summary) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        Summary updated = summaryService.update(summary, currentUser);
        return Result.success("更新成功", updated);
    }
    
    /**
     * 删除总结
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = summaryService.delete(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}

