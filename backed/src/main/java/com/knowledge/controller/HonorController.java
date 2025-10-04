package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.Honor;
import com.knowledge.service.HonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 荣誉战绩控制器
 *
 * @author system
 * @date 2025-10-04
 */
@RestController
@RequestMapping("/honor")
public class HonorController {
    
    @Autowired
    private HonorService honorService;
    
    /**
     * 根据ID查询荣誉
     */
    @GetMapping("/{id}")
    public Result<Honor> getById(@PathVariable Long id) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        Honor honor = honorService.getById(id, currentUser);
        if (honor != null) {
            return Result.success(honor);
        }
        return Result.error("荣誉不存在");
    }
    
    /**
     * 查询所有荣誉
     */
    @GetMapping("/list")
    public Result<List<Honor>> getAll() {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Honor> list = honorService.getAll(currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据类别查询荣誉
     */
    @GetMapping("/category/{category}")
    public Result<List<Honor>> getByCategory(@PathVariable String category) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Honor> list = honorService.getByCategory(category, currentUser);
        return Result.success(list);
    }
    
    /**
     * 创建荣誉
     */
    @PostMapping
    public Result<Honor> create(@RequestBody Honor honor) {
        // 设置创建人为当前登录用户
        honor.setRecCreator(com.knowledge.util.UserContext.getCurrentUser());
        Honor created = honorService.create(honor);
        return Result.success("创建成功", created);
    }
    
    /**
     * 更新荣誉
     */
    @PutMapping
    public Result<Honor> update(@RequestBody Honor honor) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        Honor updated = honorService.update(honor, currentUser);
        return Result.success("更新成功", updated);
    }
    
    /**
     * 删除荣誉
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = honorService.delete(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}

