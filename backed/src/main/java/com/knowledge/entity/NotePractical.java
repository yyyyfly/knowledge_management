package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实战笔记扩展实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NotePractical extends BaseEntity {
    
    /**
     * 笔记ID
     */
    private Long noteId;
    
    /**
     * 技术栈，JSON数组格式
     */
    private String techStack;
    
    /**
     * 项目类型，JSON数组格式
     */
    private String projectType;
    
    /**
     * 项目名称
     */
    private String projectName;
}

