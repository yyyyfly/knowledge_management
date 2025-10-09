package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * 时光轨迹事件实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TimelineEvent extends BaseEntity {
    
    /**
     * 事件类型：global-全局历史事件, milestone-专项时间轴里程碑
     */
    private String eventType;
    
    /**
     * 事件标题
     */
    private String title;
    
    /**
     * 事件描述
     */
    private String description;
    
    /**
     * 事件日期
     */
    private Date eventDate;
    
    /**
     * 主题ID（仅milestone类型使用，关联km_topic表）
     */
    private String topicId;
}

