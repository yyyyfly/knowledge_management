package com.knowledge.mapper;

import com.knowledge.entity.ProjectIssue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目问题Mapper接口
 */
@Mapper
public interface ProjectIssueMapper {

    /**
     * 插入问题
     */
    int insert(ProjectIssue issue);

    /**
     * 根据ID查询问题
     */
    ProjectIssue selectById(@Param("id") Long id);

    /**
     * 查询所有问题（按用户过滤）
     */
    List<ProjectIssue> selectAll(@Param("username") String username);

    /**
     * 根据项目ID查询问题列表
     */
    List<ProjectIssue> selectByProjectId(@Param("projectId") Long projectId, @Param("username") String username);

    /**
     * 根据状态查询问题列表
     */
    List<ProjectIssue> selectByStatus(@Param("status") String status, @Param("username") String username);

    /**
     * 更新问题
     */
    int update(ProjectIssue issue);

    /**
     * 更新问题状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") String status, @Param("revisor") String revisor);

    /**
     * 处理问题（更新解决方案和状态）
     */
    int resolveIssue(@Param("id") Long id, @Param("solution") String solution, @Param("revisor") String revisor);

    /**
     * 逻辑删除问题
     */
    int delete(@Param("id") Long id, @Param("revisor") String revisor);

    /**
     * 重新开启问题（清空解决方案，状态改为待处理）
     */
    int reopenIssue(@Param("id") Long id, @Param("revisor") String revisor);
}

