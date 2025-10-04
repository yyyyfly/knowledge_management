package com.knowledge.mapper;

import com.knowledge.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务Mapper接口
 *
 * @author system
 * @date 2025-10-04
 */
@Mapper
public interface TaskMapper {
    
    /**
     * 根据ID查询任务
     */
    Task selectById(@Param("id") Long id, @Param("recCreator") String recCreator);
    
    /**
     * 查询所有任务
     */
    List<Task> selectAll(@Param("recCreator") String recCreator);
    
    /**
     * 根据项目ID查询任务
     */
    List<Task> selectByProjectId(@Param("projectId") Long projectId, @Param("recCreator") String recCreator);
    
    /**
     * 根据状态查询任务
     */
    List<Task> selectByStatus(@Param("status") String status, @Param("recCreator") String recCreator);
    
    /**
     * 插入任务
     */
    int insert(Task task);
    
    /**
     * 更新任务
     */
    int update(Task task);
    
    /**
     * 删除任务
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 统计项目任务数
     */
    int countByProjectId(@Param("projectId") Long projectId);
}

