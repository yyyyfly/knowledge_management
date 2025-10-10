package com.knowledge.service;

import com.knowledge.entity.ProjectIssue;
import com.knowledge.mapper.ProjectIssueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目问题服务类
 */
@Service
public class ProjectIssueService {

    @Autowired
    private ProjectIssueMapper projectIssueMapper;

    /**
     * 创建问题
     */
    public ProjectIssue createIssue(ProjectIssue issue, String creator) {
        issue.setRecCreator(creator);
        issue.setRecRevisor(creator);
        if (issue.getStatus() == null || issue.getStatus().isEmpty()) {
            issue.setStatus("open");
        }
        if (issue.getPriority() == null || issue.getPriority().isEmpty()) {
            issue.setPriority("medium");
        }
        projectIssueMapper.insert(issue);
        return issue;
    }

    /**
     * 根据ID查询问题
     */
    public ProjectIssue getById(Long id) {
        return projectIssueMapper.selectById(id);
    }

    /**
     * 查询所有问题
     */
    public List<ProjectIssue> getAllIssues(String username) {
        return projectIssueMapper.selectAll(username);
    }

    /**
     * 根据项目ID查询问题
     */
    public List<ProjectIssue> getIssuesByProjectId(Long projectId, String username) {
        return projectIssueMapper.selectByProjectId(projectId, username);
    }

    /**
     * 根据状态查询问题
     */
    public List<ProjectIssue> getIssuesByStatus(String status, String username) {
        return projectIssueMapper.selectByStatus(status, username);
    }

    /**
     * 更新问题
     */
    public ProjectIssue updateIssue(ProjectIssue issue, String revisor) {
        issue.setRecRevisor(revisor);
        projectIssueMapper.update(issue);
        return issue;
    }

    /**
     * 更新问题状态
     */
    public void updateStatus(Long id, String status, String revisor) {
        projectIssueMapper.updateStatus(id, status, revisor);
    }

    /**
     * 处理问题
     */
    public void resolveIssue(Long id, String solution, String revisor) {
        projectIssueMapper.resolveIssue(id, solution, revisor);
    }

    /**
     * 删除问题
     */
    public void deleteIssue(Long id, String revisor) {
        projectIssueMapper.delete(id, revisor);
    }

    /**
     * 重新开启问题（取消解决，退回重新处理）
     */
    public void reopenIssue(Long id, String revisor) {
        projectIssueMapper.reopenIssue(id, revisor);
    }
}

