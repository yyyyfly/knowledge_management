package com.knowledge.service;

import com.knowledge.entity.ProjectInsight;
import com.knowledge.mapper.ProjectInsightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目心得服务层
 */
@Service
public class ProjectInsightService {

    @Autowired
    private ProjectInsightMapper projectInsightMapper;

    /**
     * 创建项目心得
     */
    public ProjectInsight createInsight(ProjectInsight insight, String creator) {
        insight.setRecCreator(creator);
        insight.setRecRevisor(creator);
        projectInsightMapper.insert(insight);
        return insight;
    }

    /**
     * 根据ID获取项目心得
     */
    public ProjectInsight getById(Long id) {
        return projectInsightMapper.selectById(id);
    }

    /**
     * 获取所有项目心得
     */
    public List<ProjectInsight> getAllInsights(String username) {
        return projectInsightMapper.selectAll(username);
    }

    /**
     * 根据项目ID获取项目心得
     */
    public List<ProjectInsight> getInsightsByProjectId(Long projectId, String username) {
        return projectInsightMapper.selectByProjectId(projectId, username);
    }

    /**
     * 更新项目心得
     */
    public ProjectInsight updateInsight(ProjectInsight insight, String revisor) {
        insight.setRecRevisor(revisor);
        projectInsightMapper.update(insight);
        return insight;
    }

    /**
     * 删除项目心得
     */
    public void deleteInsight(Long id, String revisor) {
        projectInsightMapper.delete(id, revisor);
    }
}


