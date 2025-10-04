package com.knowledge.service;

import com.knowledge.entity.Project;
import com.knowledge.mapper.ProjectMapper;
import com.knowledge.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}

