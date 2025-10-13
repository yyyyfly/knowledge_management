package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 刷题笔记扩展实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteExercise extends BaseEntity {
    
    /**
     * 笔记ID
     */
    private Long noteId;
    
    /**
     * 题目名称
     */
    private String problemName;
    
    /**
     * 题目来源
     */
    private String exerciseSource;
    
    /**
     * 学科类型
     */
    private String exerciseSubject;
    
    /**
     * 知识点，JSON数组格式
     */
    private String exerciseKnowledge;
    
    /**
     * 正确率
     */
    private BigDecimal accuracy;
    
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
}





