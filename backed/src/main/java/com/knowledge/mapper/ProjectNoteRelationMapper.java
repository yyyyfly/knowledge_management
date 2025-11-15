package com.knowledge.mapper;

import com.knowledge.entity.ProjectNoteRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目笔记关联Mapper接口
 */
@Mapper
public interface ProjectNoteRelationMapper {
    /**
     * 插入项目笔记关联
     */
    int insertSelective(ProjectNoteRelation record);

    /**
     * 根据ID查询
     */
    ProjectNoteRelation selectByPrimaryKey(Integer id);

    /**
     * 根据项目ID查询关联的笔记ID列表
     */
    List<Integer> selectNoteIdsByProjectId(@Param("projectId") Integer projectId);

    /**
     * 根据笔记ID查询关联的项目ID列表
     */
    List<Integer> selectProjectIdsByNoteId(@Param("noteId") Integer noteId);

    /**
     * 根据项目ID和笔记ID查询关联记录
     */
    ProjectNoteRelation selectByProjectIdAndNoteId(@Param("projectId") Integer projectId, @Param("noteId") Integer noteId);

    /**
     * 根据项目ID删除所有关联
     */
    int deleteByProjectId(@Param("projectId") Integer projectId);

    /**
     * 根据笔记ID删除所有关联
     */
    int deleteByNoteId(@Param("noteId") Integer noteId);

    /**
     * 删除指定的项目笔记关联
     */
    int deleteByProjectIdAndNoteId(@Param("projectId") Integer projectId, @Param("noteId") Integer noteId);

    /**
     * 查询项目的笔记数量
     */
    int countByProjectId(@Param("projectId") Integer projectId);

    /**
     * 查询笔记关联的项目数量
     */
    int countByNoteId(@Param("noteId") Integer noteId);

    /**
     * 根据项目ID查询所有关联记录
     */
    List<ProjectNoteRelation> selectByProjectId(@Param("projectId") Integer projectId);
}





















