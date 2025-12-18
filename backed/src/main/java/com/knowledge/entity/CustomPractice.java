package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 专项实战实体类
 * 针对特定考试、项目、竞赛的定制化训练
 *
 * @author system
 * @date 2025-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomPractice extends BaseEntity {
    
    /**
     * 实战名称
     */
    private String practiceName;
    
    /**
     * 场景类型（考试/项目/竞赛）
     */
    private String scenarioType;
    
    /**
     * 场景详情（具体考试名称、项目类型、竞赛名称等）
     */
    private String scenarioDetail;
    
    /**
     * 难度等级（简单/中等/困难）
     */
    private String difficulty;
    
    /**
     * 背景说明（考试大纲、项目背景、竞赛规则等）
     */
    private String background;
    
    /**
     * 详细描述
     */
    private String description;
    
    /**
     * 目标技能点（JSON格式）
     */
    private String targetSkills;
    
    /**
     * 指定技术栈（JSON格式）
     */
    private String techStack;
    
    /**
     * 限制条件（JSON格式：时间、资源、技术限制等）
     */
    private String constraints;
    
    /**
     * 实战内容（JSON格式）
     */
    private String practiceContent;
    
    /**
     * 评估标准（JSON格式）
     */
    private String evaluationCriteria;
    
    /**
     * 参考资料（JSON格式）
     */
    private String referenceMaterials;
    
    /**
     * 建议完成时间（分钟）
     */
    private Integer timeLimit;
    
    /**
     * 模板兼容性说明
     */
    private String compatibilityNotes;
    
    /**
     * 标签（逗号分隔）
     */
    private String tags;
}

