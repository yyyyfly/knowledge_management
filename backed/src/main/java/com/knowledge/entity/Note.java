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
}

