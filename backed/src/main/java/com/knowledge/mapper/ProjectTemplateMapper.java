package com.knowledge.mapper;

import com.knowledge.entity.ProjectTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目模板Mapper接口
 *
 * @author system
 * @date 2025-10-06
 */
@Mapper
public interface ProjectTemplateMapper {
    
    /**
     * 根据ID查询项目模板
     */
    ProjectTemplate selectById(@Param("id") Long id, @Param("recCreator") String recCreator);
    
    /**
     * 查询所有项目模板
     */
    List<ProjectTemplate> selectAll(@Param("recCreator") String recCreator);
    
    /**
     * 根据领域查询项目模板
     */
    List<ProjectTemplate> selectByDomain(@Param("domain") String domain, @Param("recCreator") String recCreator);
    
    /**
     * 插入项目模板
     */
    int insert(ProjectTemplate projectTemplate);
    
    /**
     * 更新项目模板
     */
    int update(ProjectTemplate projectTemplate);
    
    /**
     * 删除项目模板（软删除）
     */
    int deleteById(@Param("id") Long id);
}

