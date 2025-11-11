package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.GuidanceOutline;
import com.knowledge.service.GuidanceOutlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 指导大纲控制器
 *
 * @author system
 * @date 2025-11-11
 */
@RestController
@RequestMapping("/guidance-outline")
public class GuidanceOutlineController {
    
    @Autowired
    private GuidanceOutlineService guidanceOutlineService;
    
    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public Result<GuidanceOutline> getById(@PathVariable Long id) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        GuidanceOutline outline = guidanceOutlineService.getById(id, currentUser);
        if (outline != null) {
            return Result.success(outline);
        }
        return Result.error("指导大纲不存在");
    }
    
    /**
     * 查询所有
     */
    @GetMapping("/list")
    public Result<List<GuidanceOutline>> getAll() {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<GuidanceOutline> list = guidanceOutlineService.getAll(currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据分类查询
     */
    @GetMapping("/category/{category}")
    public Result<List<GuidanceOutline>> getByCategory(@PathVariable String category) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<GuidanceOutline> list = guidanceOutlineService.getByCategory(category, currentUser);
        return Result.success(list);
    }
    
    /**
     * 搜索
     */
    @GetMapping("/search")
    public Result<List<GuidanceOutline>> search(@RequestParam String keyword) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<GuidanceOutline> list = guidanceOutlineService.search(keyword, currentUser);
        return Result.success(list);
    }
    
    /**
     * 创建
     */
    @PostMapping
    public Result<GuidanceOutline> create(@RequestBody GuidanceOutline guidanceOutline) {
        guidanceOutline.setRecCreator(com.knowledge.util.UserContext.getCurrentUser());
        GuidanceOutline created = guidanceOutlineService.create(guidanceOutline);
        return Result.success("创建成功", created);
    }
    
    /**
     * 更新
     */
    @PutMapping
    public Result<GuidanceOutline> update(@RequestBody GuidanceOutline guidanceOutline) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        GuidanceOutline updated = guidanceOutlineService.update(guidanceOutline, currentUser);
        return Result.success("更新成功", updated);
    }
    
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = guidanceOutlineService.delete(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}

