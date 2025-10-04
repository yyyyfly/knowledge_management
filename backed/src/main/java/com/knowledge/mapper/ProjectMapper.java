package com.knowledge.mapper;

import com.knowledge.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目Mapper接口
 *
 * @author system
 * @date 2025-10-04
 */
@Mapper
public interface ProjectMapper {
    
    /**
     * 根据ID查询项目
     */
    Project selectById(@Param("id") Long id, @Param("recCreator") String recCreator);
    
    /**
     * 查询所有项目
     */
    List<Project> selectAll(@Param("recCreator") String recCreator);
    
    /**
     * 根据类别查询项目
     */
    List<Project> selectByCategory(@Param("category") String category, @Param("recCreator") String recCreator);
    
    /**
     * 根据状态查询项目
     */
    List<Project> selectByStatus(@Param("status") String status, @Param("recCreator") String recCreator);
    
    /**
     * 插入项目
     */
    int insert(Project project);
    
    /**
     * 更新项目
     */
    int update(Project project);
    
    /**
     * 删除项目
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 更新项目任务数量
     */
    int updateTaskCount(@Param("id") Long id, @Param("taskCount") Integer taskCount);
}

