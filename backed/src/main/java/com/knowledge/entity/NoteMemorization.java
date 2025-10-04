package com.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 背诵笔记扩展实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteMemorization extends BaseEntity {
    
    /**
     * 笔记ID
     */
    private Long noteId;
    
    /**
     * 所属项目
     */
    private String project;
    
    /**
     * 知识点，JSON数组格式
     */
    private String knowledgePoint;
    
    /**
     * 复习次数
     */
    private Integer reviewCount;
    
    /**
     * 最后复习时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastReviewTime;
    
    /**
     * 原文
     */
    private String originalText;
    
    /**
     * 解释
     */
    private String explanation;
    
    /**
     * 提示词
     */
    private String cue;
}

