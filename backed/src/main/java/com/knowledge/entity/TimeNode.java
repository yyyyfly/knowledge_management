package com.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 时间节点实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TimeNode extends BaseEntity {
    
    /**
     * 节点标题
     */
    private String title;
    
    /**
     * 节点描述
     */
    private String description;
    
    /**
     * 节点时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
    
    /**
     * 节点类型:war-战争行动,diplomatic-外交活动
     */
    private String type;
    
    /**
     * 节点状态:not-started-未开始,upcoming-即将开始,ongoing-进行中,completed-已完成,overdue-已逾期,closed-已关闭
     */
    private String status;
}

