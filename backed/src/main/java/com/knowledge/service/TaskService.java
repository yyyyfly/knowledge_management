package com.knowledge.service;

import com.knowledge.entity.Task;
import com.knowledge.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务服务类
 *
 * @author system
 * @date 2025-10-04
 */
@Service
public class TaskService {
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private ProjectService projectService;
    
    /**
     * 根据ID查询任务
     */
    public Task getById(Long id, String recCreator) {
        return taskMapper.selectById(id, recCreator);
    }
    
    /**
     * 查询所有任务
     */
    public List<Task> getAll(String recCreator) {
        return taskMapper.selectAll(recCreator);
    }
    
    /**
     * 根据项目ID查询任务
     */
    public List<Task> getByProjectId(Long projectId, String recCreator) {
        return taskMapper.selectByProjectId(projectId, recCreator);
    }
    
    /**
     * 根据状态查询任务
     */
    public List<Task> getByStatus(String status, String recCreator) {
        return taskMapper.selectByStatus(status, recCreator);
    }
    
    /**
     * 创建任务
     */
    @Transactional(rollbackFor = Exception.class)
    public Task create(Task task) {
        taskMapper.insert(task);
        // 更新项目任务数量
        projectService.updateTaskCount(task.getProjectId());
        return task;
    }
    
    /**
     * 更新任务
     */
    @Transactional(rollbackFor = Exception.class)
    public Task update(Task task, String recCreator) {
        taskMapper.update(task);
        return taskMapper.selectById(task.getId(), recCreator);
    }
    
    /**
     * 删除任务
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id, String recCreator) {
        Task task = taskMapper.selectById(id, recCreator);
        if (task != null) {
            taskMapper.deleteById(id);
            // 更新项目任务数量
            projectService.updateTaskCount(task.getProjectId());
            return true;
        }
        return false;
    }
}

