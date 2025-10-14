package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 笔记基础实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Note extends BaseEntity {
    
    /**
     * 笔记类型:framework-框架笔记,study-求学笔记,memorization-背诵笔记,exercise-刷题笔记,practical-实战笔记,fragment-碎片笔记
     */
    private String type;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 摘要
     */
    private String summary;
    
    /**
     * 内容
     */
    private String content;
    
    /**
     * 标签，JSON数组格式
     */
    private String tags;
    
    // ===== 框架笔记字段 =====
    /**
     * 学科类型（逗号分隔）
     */
    private String subjectType;
    
    /**
     * 知识点（逗号分隔）
     */
    private String knowledgePoint;
    
    // ===== 求学笔记字段 =====
    /**
     * 求学学科（逗号分隔）
     */
    private String studySubject;
    
    /**
     * 核心概念（富文本）
     */
    private String coreConcept;
    
    /**
     * 机制原理（富文本）
     */
    private String mechanism;
    
    /**
     * 应用案例（富文本）
     */
    private String applicationCase;
    
    /**
     * 延伸/对比（富文本）
     */
    private String extension;
    
    /**
     * 常见误区（富文本）
     */
    private String commonMistake;
    
    /**
     * 思考理解（富文本）
     */
    private String reflection;
    
    // ===== 背诵笔记字段 =====
    /**
     * 学科项目
     */
    private String project;
    
    /**
     * 原文内容
     */
    private String originalText;
    
    /**
     * 解释说明
     */
    private String explanation;
    
    /**
     * 记忆提示词
     */
    private String cue;
    
    // ===== 刷题笔记字段 =====
    /**
     * 题目来源
     */
    private String exerciseSource;
    
    /**
     * 刷题学科类型
     */
    private String exerciseSubject;
    
    /**
     * 刷题知识点（逗号分隔）
     */
    private String exerciseKnowledge;
    
    /**
     * 难度等级:easy-简单,medium-中等,hard-困难
     */
    private String exerciseDifficulty;
    
    /**
     * 题目描述（富文本）
     */
    private String questionDescription;
    
    /**
     * 分析理解（富文本）
     */
    private String analysis;
    
    /**
     * 参考答案（富文本）
     */
    private String referenceAnswer;
    
    // ===== 实战笔记字段 =====
    /**
     * 技术栈标签（逗号分隔）
     */
    private String techTags;
    
    /**
     * 项目类型（逗号分隔）
     */
    private String projectType;
    
    /**
     * 需求描述（富文本）
     */
    private String requirementDescription;
    
    /**
     * 设计思路（富文本）
     */
    private String designThinking;
    
    /**
     * 参考设计（富文本）
     */
    private String referenceDesign;
    
    // ===== 碎片笔记字段 =====
    /**
     * 碎片分类（逗号分隔）
     */
    private String fragmentCategory;
    
    /**
     * 碎片主题（逗号分隔）
     */
    private String fragmentTheme;
    
    /**
     * 重要程度:low-一般,medium-重要,high-非常重要
     */
    private String importance;
    
    // ===== 巩固复习字段 =====
    /**
     * 巩固次数
     */
    private Integer reviewCount;
    
    /**
     * 最后巩固时间
     */
    private String lastReviewTime;
}





