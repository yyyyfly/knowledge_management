package com.knowledge.service;

import com.knowledge.entity.ProjectTemplate;
import com.knowledge.mapper.ProjectTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目模板服务类
 *
 * @author system
 * @date 2025-10-06
 */
@Service
public class ProjectTemplateService {
    
    @Autowired
    private ProjectTemplateMapper projectTemplateMapper;
    
    /**
     * 根据ID查询项目模板
     */
    public ProjectTemplate getById(Long id, String recCreator) {
        return projectTemplateMapper.selectById(id, recCreator);
    }
    
    /**
     * 查询所有项目模板
     */
    public List<ProjectTemplate> getAll(String recCreator) {
        return projectTemplateMapper.selectAll(recCreator);
    }
    
    /**
     * 根据领域查询项目模板
     */
    public List<ProjectTemplate> getByDomain(String domain, String recCreator) {
        return projectTemplateMapper.selectByDomain(domain, recCreator);
    }
    
    /**
     * 创建项目模板
     */
    @Transactional(rollbackFor = Exception.class)
    public ProjectTemplate create(ProjectTemplate projectTemplate) {
        projectTemplateMapper.insert(projectTemplate);
        return projectTemplate;
    }
    
    /**
     * 更新项目模板
     */
    @Transactional(rollbackFor = Exception.class)
    public ProjectTemplate update(ProjectTemplate projectTemplate, String recCreator) {
        projectTemplateMapper.update(projectTemplate);
        return projectTemplateMapper.selectById(projectTemplate.getId(), recCreator);
    }
    
    /**
     * 删除项目模板
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return projectTemplateMapper.deleteById(id) > 0;
    }
}

