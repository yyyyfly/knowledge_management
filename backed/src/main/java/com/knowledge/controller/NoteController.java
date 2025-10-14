package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.Note;
import com.knowledge.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 笔记控制器
 *
 * @author system
 * @date 2025-10-04
 */
@RestController
@RequestMapping("/note")
public class NoteController {
    
    @Autowired
    private NoteService noteService;
    
    /**
     * 根据ID查询笔记
     */
    @GetMapping("/{id}")
    public Result<Note> getById(@PathVariable Long id) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        Note note = noteService.getById(id, currentUser);
        if (note != null) {
            return Result.success(note);
        }
        return Result.error("笔记不存在");
    }
    
    /**
     * 查询所有笔记
     */
    @GetMapping("/list")
    public Result<List<Note>> getAll() {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Note> list = noteService.getAll(currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据类型查询笔记
     */
    @GetMapping("/type/{type}")
    public Result<List<Note>> getByType(@PathVariable String type) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Note> list = noteService.getByType(type, currentUser);
        return Result.success(list);
    }
    
    /**
     * 搜索笔记
     */
    @GetMapping("/search")
    public Result<List<Note>> search(@RequestParam String keyword) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Note> list = noteService.search(keyword, currentUser);
        return Result.success(list);
    }
    
    /**
     * 创建笔记
     */
    @PostMapping
    public Result<Note> create(@RequestBody Note note) {
        // 设置创建人为当前登录用户
        note.setRecCreator(com.knowledge.util.UserContext.getCurrentUser());
        Note created = noteService.create(note);
        return Result.success("创建成功", created);
    }
    
    /**
     * 更新笔记
     */
    @PutMapping
    public Result<Note> update(@RequestBody Note note) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        Note updated = noteService.update(note, currentUser);
        return Result.success("更新成功", updated);
    }
    
    /**
     * 删除笔记
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = noteService.delete(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
    
    /**
     * 根据类型获取推荐巩固的笔记（限5条）
     */
    @GetMapping("/type/{type}/recommended")
    public Result<List<Note>> getRecommended(@PathVariable String type) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        List<Note> list = noteService.getByTypeRecommended(type, currentUser);
        return Result.success(list);
    }
    
    /**
     * 完成巩固
     */
    @PostMapping("/{id}/review")
    public Result<String> completeReview(@PathVariable Long id) {
        String currentUser = com.knowledge.util.UserContext.getCurrentUser();
        noteService.completeReview(id, currentUser);
        return Result.success("巩固完成");
    }
}

