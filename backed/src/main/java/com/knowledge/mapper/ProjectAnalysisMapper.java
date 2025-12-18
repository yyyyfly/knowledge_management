package com.knowledge.mapper;

import com.knowledge.entity.ProjectAnalysis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目分析Mapper接口
 */
@Mapper
public interface ProjectAnalysisMapper {

    /**
     * 插入项目分析
     */
    int insert(ProjectAnalysis analysis);

    /**
     * 根据ID查询
     */
    ProjectAnalysis selectById(@Param("id") Long id);

    /**
     * 根据项目ID查询
     */
    ProjectAnalysis selectByProjectId(@Param("projectId") Long projectId, @Param("username") String username);

    /**
     * 查询所有项目分析
     */
    List<ProjectAnalysis> selectAll(@Param("username") String username);

    /**
     * 更新项目分析
     */
    int update(ProjectAnalysis analysis);

    /**
     * 根据项目ID更新
     */
    int updateByProjectId(ProjectAnalysis analysis);

    /**
     * 逻辑删除
     */
    int delete(@Param("id") Long id, @Param("revisor") String revisor);

    /**
     * 检查项目分析是否存在
     */
    int existsByProjectId(@Param("projectId") Long projectId, @Param("username") String username);
}

