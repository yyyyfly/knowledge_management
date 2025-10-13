package com.knowledge.mapper;

import com.knowledge.entity.ProjectInsight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目心得数据访问层
 */
@Mapper
public interface ProjectInsightMapper {
    
    /**
     * 插入项目心得
     */
    int insert(ProjectInsight insight);

    /**
     * 根据ID查询
     */
    ProjectInsight selectById(@Param("id") Long id);

    /**
     * 查询所有项目心得
     */
    List<ProjectInsight> selectAll(@Param("username") String username);

    /**
     * 根据项目ID查询
     */
    List<ProjectInsight> selectByProjectId(@Param("projectId") Long projectId, @Param("username") String username);

    /**
     * 更新项目心得
     */
    int update(ProjectInsight insight);

    /**
     * 逻辑删除项目心得
     */
    int delete(@Param("id") Long id, @Param("revisor") String revisor);
}



