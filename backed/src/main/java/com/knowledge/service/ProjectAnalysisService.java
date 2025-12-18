package com.knowledge.service;

import com.knowledge.entity.ProjectAnalysis;
import com.knowledge.mapper.ProjectAnalysisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目分析服务层
 */
@Service
public class ProjectAnalysisService {

    @Autowired
    private ProjectAnalysisMapper projectAnalysisMapper;

    /**
     * 创建或更新项目分析（upsert逻辑）
     * 每个项目只有一条分析记录
     */
    public ProjectAnalysis saveOrUpdate(ProjectAnalysis analysis, String username) {
        // 检查是否已存在该项目的分析记录
        int exists = projectAnalysisMapper.existsByProjectId(analysis.getProjectId(), username);
        
        if (exists > 0) {
            // 存在则更新
            analysis.setRecCreator(username);
            analysis.setRecRevisor(username);
            projectAnalysisMapper.updateByProjectId(analysis);
            return projectAnalysisMapper.selectByProjectId(analysis.getProjectId(), username);
        } else {
            // 不存在则创建
            analysis.setRecCreator(username);
            analysis.setRecRevisor(username);
            projectAnalysisMapper.insert(analysis);
            return analysis;
        }
    }

    /**
     * 根据ID获取项目分析
     */
    public ProjectAnalysis getById(Long id) {
        return projectAnalysisMapper.selectById(id);
    }

    /**
     * 根据项目ID获取项目分析
     */
    public ProjectAnalysis getByProjectId(Long projectId, String username) {
        return projectAnalysisMapper.selectByProjectId(projectId, username);
    }

    /**
     * 获取所有项目分析
     */
    public List<ProjectAnalysis> getAllAnalyses(String username) {
        return projectAnalysisMapper.selectAll(username);
    }

    /**
     * 删除项目分析
     */
    public void deleteAnalysis(Long id, String revisor) {
        projectAnalysisMapper.delete(id, revisor);
    }
}

