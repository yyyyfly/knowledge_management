package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.NoteConfig;
import com.knowledge.service.NoteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 笔记配置控制器（通用）
 *
 * @author system
 * @date 2025-10-08
 */
@RestController
@RequestMapping("/note-config")
public class NoteConfigController {
    
    @Autowired
    private NoteConfigService noteConfigService;
    
    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public Result<NoteConfig> getById(@PathVariable Long id) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        NoteConfig config = noteConfigService.getById(id, currentUser);
        if (config != null) {
            return Result.success(config);
        }
        return Result.error("配置不存在");
    }
    
    /**
     * 查询所有配置
     */
    @GetMapping("/list")
    public Result<List<NoteConfig>> getAll() {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<NoteConfig> list = noteConfigService.getAll(currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据笔记类型查询
     */
    @GetMapping("/note-type/{noteType}")
    public Result<List<NoteConfig>> getByNoteType(@PathVariable String noteType) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<NoteConfig> list = noteConfigService.getByNoteType(noteType, currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据笔记类型和配置类型查询
     */
    @GetMapping("/note-type/{noteType}/config-type/{configType}")
    public Result<List<NoteConfig>> getByNoteAndConfigType(@PathVariable String noteType, @PathVariable String configType) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<NoteConfig> list = noteConfigService.getByNoteAndConfigType(noteType, configType, currentUser);
        return Result.success(list);
    }
    
    /**
     * 创建配置
     */
    @PostMapping
    public Result<NoteConfig> create(@RequestBody NoteConfig config) {
        try {
            // 设置创建人为当前登录用户
            config.setRecCreator(com.knowledge.util.UserContext.getCurrentUser());
            NoteConfig created = noteConfigService.create(config);
            return Result.success("创建成功", created);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 更新配置
     */
    @PutMapping
    public Result<NoteConfig> update(@RequestBody NoteConfig config) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        NoteConfig updated = noteConfigService.update(config, currentUser);
        return Result.success("更新成功", updated);
    }
    
    /**
     * 删除配置
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = noteConfigService.delete(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}


