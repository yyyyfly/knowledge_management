package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 笔记配置实体类（通用）
 *
 * @author system
 * @date 2025-10-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteConfig extends BaseEntity {
    
    /**
     * 笔记类型：fragment-碎片,study-学习,memorization-背诵,exercise-刷题,practical-实战,framework-框架
     */
    private String noteType;
    
    /**
     * 配置类型：根据笔记类型不同而不同
     * fragment: category-分类, theme-主题
     * study: subject-学科, knowledgePoint-知识点
     * memorization: subject-科目, knowledgePoint-知识点
     * exercise: source-题目来源, subject-学科类型
     * practical: domain-领域, techStack-技术栈
     * framework: subjectType-学科类型, knowledgePoint-知识点类型
     */
    private String configType;
    
    /**
     * 配置名称
     */
    private String configName;
    
    /**
     * 排序序号
     */
    private Integer sortOrder;
}

