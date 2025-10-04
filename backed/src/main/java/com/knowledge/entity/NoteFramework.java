package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 框架笔记扩展实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteFramework extends BaseEntity {
    
    /**
     * 笔记ID
     */
    private Long noteId;
    
    /**
     * 学科类型，JSON数组格式
     */
    private String subjectType;
    
    /**
     * 知识点，JSON数组格式
     */
    private String knowledgePoint;
}

