package com.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 任务执行记录实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExecutionRecord extends BaseEntity {
    
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 执行日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    
    /**
     * 执行进度
     */
    private String progress;
}

