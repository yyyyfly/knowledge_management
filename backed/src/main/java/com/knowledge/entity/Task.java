package com.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 任务实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Task extends BaseEntity {
    
    /**
     * 项目ID
     */
    private Long projectId;
    
    /**
     * 任务名称
     */
    private String name;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 任务状态:pending-待执行,in-progress-执行中,completed-已完成,cancelled-已取消,stopped-已停止
     */
    private String status;
    
    /**
     * 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deadline;
    
    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completedTime;
    
    /**
     * 最新进度
     */
    private String newProgress;
    
    /**
     * 执行记录（JSON格式）
     */
    private String executionRecords;
}

