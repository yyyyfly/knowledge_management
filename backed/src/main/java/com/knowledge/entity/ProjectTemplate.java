package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目模板实体类
 *
 * @author system
 * @date 2025-10-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectTemplate extends BaseEntity {
    
    /**
     * 项目分类/领域
     */
    private String domain;
    
    /**
     * 模板名称
     */
    private String name;
    
    /**
     * 模板描述
     */
    private String description;
    
    /**
     * 功能性需求（JSON格式）
     */
    private String requirements;
    
    /**
     * 非功能性需求/通用特性（JSON格式）
     */
    private String commonFeatures;
}

