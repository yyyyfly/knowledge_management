package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 指导大纲实体类
 *
 * @author system
 * @date 2025-11-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GuidanceOutline extends BaseEntity {
    
    /**
     * 学科名称
     */
    private String subjectName;
    
    /**
     * 学科分类
     */
    private String subjectCategory;
    
    /**
     * 知识体系树形结构（JSON格式）
     */
    private String knowledgeTree;
    
    /**
     * 标签（逗号分隔）
     */
    private String tags;
}

