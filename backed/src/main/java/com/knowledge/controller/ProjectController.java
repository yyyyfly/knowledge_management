package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.Project;
import com.knowledge.entity.Note;
import com.knowledge.service.ProjectService;
import com.knowledge.service.ProjectNoteRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    
    @Autowired
    private ProjectNoteRelationService projectNoteRelationService;
    
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
    
    // ==================== 项目笔记关联接口 ====================
    
    /**
     * 获取项目关联的笔记ID列表
     */
    @GetMapping("/{projectId}/notes")
    public Result<List<Integer>> getProjectNotes(@PathVariable Integer projectId) {
        try {
            List<Integer> noteIds = projectNoteRelationService.getNoteIdsByProjectId(projectId);
            return Result.success(noteIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取项目笔记失败: " + e.getMessage());
        }
    }
    
    /**
     * 添加笔记到项目
     */
    @PostMapping("/{projectId}/notes")
    public Result<String> addNoteToProject(@PathVariable Integer projectId, @RequestBody Map<String, Object> params) {
        try {
            Integer noteId = (Integer) params.get("noteId");
            String noteType = (String) params.getOrDefault("noteType", "unknown");
            String currentUser = com.knowledge.util.UserContext.getCurrentUser();
            
            if (noteId == null) {
                return Result.error("笔记ID不能为空");
            }
            
            boolean success = projectNoteRelationService.addProjectNoteRelation(projectId, noteId, noteType, currentUser);
            if (success) {
                return Result.success("笔记已关联到项目");
            } else {
                return Result.error("笔记已经关联到该项目");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加笔记失败: " + e.getMessage());
        }
    }
    
    /**
     * 从项目移除笔记
     */
    @DeleteMapping("/{projectId}/notes/{noteId}")
    public Result<String> removeNoteFromProject(@PathVariable Integer projectId, @PathVariable Integer noteId) {
        try {
            boolean success = projectNoteRelationService.removeProjectNoteRelation(projectId, noteId);
            if (success) {
                return Result.success("已移除笔记关联");
            } else {
                return Result.error("未找到该关联记录");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("移除笔记失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取项目的笔记数量
     */
    @GetMapping("/{projectId}/notes/count")
    public Result<Integer> getProjectNoteCount(@PathVariable Integer projectId) {
        try {
            int count = projectNoteRelationService.countNotesByProjectId(projectId);
            return Result.success(count);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取笔记数量失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取笔记关联的项目ID列表
     */
    @GetMapping("/notes/{noteId}/projects")
    public Result<List<Integer>> getNoteProjects(@PathVariable Integer noteId) {
        try {
            List<Integer> projectIds = projectNoteRelationService.getProjectIdsByNoteId(noteId);
            return Result.success(projectIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取笔记关联的项目失败: " + e.getMessage());
        }
    }
    
    // ==================== 冲刺记忆笔记接口 ====================
    
    /**
     * 获取项目的冲刺记忆笔记列表
     */
    @GetMapping("/{projectId}/sprint-notes")
    public Result<List<Note>> getProjectSprintNotes(@PathVariable Integer projectId) {
        try {
            String currentUser = com.knowledge.util.UserContext.getCurrentUser();
            List<Note> sprintNotes = projectService.getSprintNotes(projectId, currentUser);
            return Result.success(sprintNotes);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取冲刺记忆失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取项目的冲刺记忆笔记数量
     */
    @GetMapping("/{projectId}/sprint-notes/count")
    public Result<Integer> getProjectSprintNotesCount(@PathVariable Integer projectId) {
        try {
            String currentUser = com.knowledge.util.UserContext.getCurrentUser();
            int count = projectService.getSprintNotesCount(projectId, currentUser);
            return Result.success(count);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取冲刺记忆数量失败: " + e.getMessage());
        }
    }
    
    /**
     * 添加冲刺记忆笔记到项目
     */
    @PostMapping("/{projectId}/sprint-notes")
    public Result<Note> addSprintNoteToProject(@PathVariable Integer projectId, @RequestBody Note note) {
        try {
            String currentUser = com.knowledge.util.UserContext.getCurrentUser();
            Note created = projectService.createSprintNote(projectId, note, currentUser);
            return Result.success("添加成功", created);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加冲刺记忆失败: " + e.getMessage());
        }
    }
    
    /**
     * 编辑冲刺记忆笔记
     */
    @PutMapping("/sprint-notes/{noteId}")
    public Result<Note> updateSprintNote(@PathVariable Long noteId, @RequestBody Note note) {
        try {
            String currentUser = com.knowledge.util.UserContext.getCurrentUser();
            note.setId(noteId);
            Note updated = projectService.updateSprintNote(note, currentUser);
            return Result.success("更新成功", updated);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新冲刺记忆失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除冲刺记忆笔记
     */
    @DeleteMapping("/sprint-notes/{noteId}")
    public Result<String> deleteSprintNote(@PathVariable Integer noteId) {
        try {
            boolean success = projectService.deleteSprintNote(noteId);
            if (success) {
                return Result.success("删除成功");
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除冲刺记忆失败: " + e.getMessage());
        }
    }
}

