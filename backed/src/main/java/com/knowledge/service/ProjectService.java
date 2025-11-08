package com.knowledge.service;

import com.knowledge.entity.Project;
import com.knowledge.entity.Note;
import com.knowledge.mapper.ProjectMapper;
import com.knowledge.mapper.TaskMapper;
import com.knowledge.mapper.ProjectNoteRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

/**
 * 项目服务类
 *
 * @author system
 * @date 2025-10-04
 */
@Service
public class ProjectService {
    
    @Autowired
    private ProjectMapper projectMapper;
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private NoteService noteService;
    
    @Autowired
    private ProjectNoteRelationService projectNoteRelationService;
    
    /**
     * 根据ID查询项目
     */
    public Project getById(Long id, String recCreator) {
        return projectMapper.selectById(id, recCreator);
    }
    
    /**
     * 查询所有项目
     */
    public List<Project> getAll(String recCreator) {
        return projectMapper.selectAll(recCreator);
    }
    
    /**
     * 根据类别查询项目
     */
    public List<Project> getByCategory(String category, String recCreator) {
        return projectMapper.selectByCategory(category, recCreator);
    }
    
    /**
     * 根据状态查询项目
     */
    public List<Project> getByStatus(String status, String recCreator) {
        return projectMapper.selectByStatus(status, recCreator);
    }
    
    /**
     * 创建项目
     */
    @Transactional(rollbackFor = Exception.class)
    public Project create(Project project) {
        project.setTaskCount(0);
        projectMapper.insert(project);
        return project;
    }
    
    /**
     * 更新项目
     */
    @Transactional(rollbackFor = Exception.class)
    public Project update(Project project, String recCreator) {
        projectMapper.update(project);
        return projectMapper.selectById(project.getId(), recCreator);
    }
    
    /**
     * 删除项目
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return projectMapper.deleteById(id) > 0;
    }
    
    /**
     * 更新项目任务数量
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskCount(Long projectId) {
        int taskCount = taskMapper.countByProjectId(projectId);
        projectMapper.updateTaskCount(projectId, taskCount);
    }
    
    // ==================== 冲刺记忆笔记相关方法 ====================
    
    /**
     * 获取项目的冲刺记忆笔记列表
     */
    public List<Note> getSprintNotes(Integer projectId, String recCreator) {
        List<Integer> noteIds = projectNoteRelationService.getNoteIdsByProjectId(projectId);
        List<Note> sprintNotes = new ArrayList<>();
        
        for (Integer noteId : noteIds) {
            Note note = noteService.getById(noteId.longValue(), recCreator);
            // 只返回memorization类型的笔记（冲刺记忆）
            if (note != null && "memorization".equals(note.getType())) {
                sprintNotes.add(note);
            }
        }
        
        return sprintNotes;
    }
    
    /**
     * 获取项目的冲刺记忆笔记数量
     */
    public int getSprintNotesCount(Integer projectId, String recCreator) {
        return getSprintNotes(projectId, recCreator).size();
    }
    
    /**
     * 创建冲刺记忆笔记并关联到项目
     */
    @Transactional(rollbackFor = Exception.class)
    public Note createSprintNote(Integer projectId, Note note, String currentUser) {
        // 设置笔记类型为memorization
        note.setType("memorization");
        note.setRecCreator(currentUser);
        
        // 创建笔记
        Note created = noteService.create(note);
        
        // 关联到项目
        projectNoteRelationService.addProjectNoteRelation(
            projectId, 
            created.getId().intValue(), 
            "memorization", 
            currentUser
        );
        
        return created;
    }
    
    /**
     * 更新冲刺记忆笔记
     */
    @Transactional(rollbackFor = Exception.class)
    public Note updateSprintNote(Note note, String currentUser) {
        return noteService.update(note, currentUser);
    }
    
    /**
     * 删除冲刺记忆笔记及其项目关联
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSprintNote(Integer noteId) {
        // 删除所有项目关联
        projectNoteRelationService.removeAllProjectsByNoteId(noteId);
        
        // 删除笔记本身
        return noteService.delete(noteId.longValue());
    }
}

