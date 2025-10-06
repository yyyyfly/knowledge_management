package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Project extends BaseEntity {
    
    /**
     * 项目名称
     */
    private String name;
    
    /**
     * 项目描述
     */
    private String description;
    
    /**
     * 项目状态:planning-规划中,active-进行中,completed-已完成,suspended-已暂停
     */
    private String status;
    
    /**
     * 项目类别:construction-工程建设,defense-战争行动,diplomatic-外交活动
     */
    private String category;
    
    /**
     * 任务数量
     */
    private Integer taskCount;
}





