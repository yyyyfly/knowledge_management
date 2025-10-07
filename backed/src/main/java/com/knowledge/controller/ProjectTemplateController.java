package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.ProjectTemplate;
import com.knowledge.service.ProjectTemplateService;
import com.knowledge.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目模板控制器
 *
 * @author system
 * @date 2025-10-06
 */
@RestController
@RequestMapping("/projecttemplate")
public class ProjectTemplateController {
    
    @Autowired
    private ProjectTemplateService projectTemplateService;
    
    /**
     * 获取所有项目模板
     */
    @GetMapping("/list")
    public Result<List<ProjectTemplate>> getAll() {
        String currentUser = UserContext.getCurrentUser();
        List<ProjectTemplate> list = projectTemplateService.getAll(currentUser);
        return Result.success(list);
    }
    
    /**
     * 根据ID获取项目模板
     */
    @GetMapping("/{id}")
    public Result<ProjectTemplate> getById(@PathVariable Long id) {
        String currentUser = UserContext.getCurrentUser();
        ProjectTemplate projectTemplate = projectTemplateService.getById(id, currentUser);
        return Result.success(projectTemplate);
    }
    
    /**
     * 根据领域获取项目模板
     */
    @GetMapping("/domain/{domain}")
    public Result<List<ProjectTemplate>> getByDomain(@PathVariable String domain) {
        String currentUser = UserContext.getCurrentUser();
        List<ProjectTemplate> list = projectTemplateService.getByDomain(domain, currentUser);
        return Result.success(list);
    }
    
    /**
     * 创建项目模板
     */
    @PostMapping
    public Result<ProjectTemplate> create(@RequestBody ProjectTemplate projectTemplate) {
        String currentUser = UserContext.getCurrentUser();
        projectTemplate.setRecCreator(currentUser);
        ProjectTemplate created = projectTemplateService.create(projectTemplate);
        return Result.success(created);
    }
    
    /**
     * 更新项目模板
     */
    @PutMapping
    public Result<ProjectTemplate> update(@RequestBody ProjectTemplate projectTemplate) {
        String currentUser = UserContext.getCurrentUser();
        projectTemplate.setRecRevisor(currentUser);
        ProjectTemplate updated = projectTemplateService.update(projectTemplate, currentUser);
        return Result.success(updated);
    }
    
    /**
     * 删除项目模板
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = projectTemplateService.delete(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }
}

