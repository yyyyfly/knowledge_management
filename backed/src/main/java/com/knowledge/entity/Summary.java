package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统总结实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Summary extends BaseEntity {
    
    /**
     * 总结标题
     */
    private String title;
    
    /**
     * 总结类型:daily-日报,monthly-月报,quarterly-季报,yearly-年报
     */
    private String type;
    
    /**
     * 总结周期
     */
    private String period;
    
    /**
     * 长段描述
     */
    private String description;
    
    /**
     * 总结内容，JSON格式
     */
    private String content;
    
    /**
     * 满意度评分:1-10分
     */
    private Integer satisfaction;
    
    /**
     * 标签，JSON数组格式
     */
    private String tags;
}

