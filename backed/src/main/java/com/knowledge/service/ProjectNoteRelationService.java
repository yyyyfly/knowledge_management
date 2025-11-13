package com.knowledge.service;

import com.knowledge.entity.ProjectNoteRelation;
import com.knowledge.mapper.ProjectNoteRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 项目笔记关联服务类
 */
@Service
public class ProjectNoteRelationService {

    @Autowired
    private ProjectNoteRelationMapper projectNoteRelationMapper;

    /**
     * 获取项目关联的笔记ID列表
     *
     * @param projectId 项目ID
     * @return 笔记ID列表
     */
    public List<Integer> getNoteIdsByProjectId(Integer projectId) {
        return projectNoteRelationMapper.selectNoteIdsByProjectId(projectId);
    }

    /**
     * 获取笔记关联的项目ID列表
     *
     * @param noteId 笔记ID
     * @return 项目ID列表
     */
    public List<Integer> getProjectIdsByNoteId(Integer noteId) {
        return projectNoteRelationMapper.selectProjectIdsByNoteId(noteId);
    }

    /**
     * 添加项目笔记关联
     *
     * @param projectId 项目ID
     * @param noteId 笔记ID
     * @param noteType 笔记类型
     * @param username 用户名
     * @return 是否成功
     */
    @Transactional
    public boolean addProjectNoteRelation(Integer projectId, Integer noteId, String noteType, String username) {
        // 检查是否已存在关联
        ProjectNoteRelation existing = projectNoteRelationMapper.selectByProjectIdAndNoteId(projectId, noteId);
        if (existing != null) {
            return false; // 已存在关联
        }

        // 创建新关联
        ProjectNoteRelation relation = new ProjectNoteRelation();
        relation.setProjectId(projectId);
        relation.setNoteId(noteId);
        relation.setNoteType(noteType);
        relation.setRecCreator(username);
        relation.setRecCreateTime(new Date());
        relation.setArchFlag("1");

        return projectNoteRelationMapper.insertSelective(relation) > 0;
    }

    /**
     * 删除项目笔记关联
     *
     * @param projectId 项目ID
     * @param noteId 笔记ID
     * @return 是否成功
     */
    @Transactional
    public boolean removeProjectNoteRelation(Integer projectId, Integer noteId) {
        return projectNoteRelationMapper.deleteByProjectIdAndNoteId(projectId, noteId) > 0;
    }

    /**
     * 删除项目的所有笔记关联
     *
     * @param projectId 项目ID
     * @return 删除数量
     */
    @Transactional
    public int removeAllNotesByProjectId(Integer projectId) {
        return projectNoteRelationMapper.deleteByProjectId(projectId);
    }

    /**
     * 删除笔记的所有项目关联
     *
     * @param noteId 笔记ID
     * @return 删除数量
     */
    @Transactional
    public int removeAllProjectsByNoteId(Integer noteId) {
        return projectNoteRelationMapper.deleteByNoteId(noteId);
    }

    /**
     * 获取项目的笔记数量
     *
     * @param projectId 项目ID
     * @return 笔记数量
     */
    public int countNotesByProjectId(Integer projectId) {
        return projectNoteRelationMapper.countByProjectId(projectId);
    }

    /**
     * 获取笔记关联的项目数量
     *
     * @param noteId 笔记ID
     * @return 项目数量
     */
    public int countProjectsByNoteId(Integer noteId) {
        return projectNoteRelationMapper.countByNoteId(noteId);
    }

    /**
     * 检查项目和笔记是否已关联
     *
     * @param projectId 项目ID
     * @param noteId 笔记ID
     * @return 是否已关联
     */
    public boolean isRelated(Integer projectId, Integer noteId) {
        return projectNoteRelationMapper.selectByProjectIdAndNoteId(projectId, noteId) != null;
    }

    /**
     * 获取项目的所有关联记录
     *
     * @param projectId 项目ID
     * @return 关联记录列表
     */
    public List<ProjectNoteRelation> getRelationsByProjectId(Integer projectId) {
        return projectNoteRelationMapper.selectByProjectId(projectId);
    }
}




















