package com.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 荣誉战绩实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Honor extends BaseEntity {
    
    /**
     * 荣誉标题
     */
    private String title;
    
    /**
     * 荣誉描述
     */
    private String description;
    
    /**
     * 荣誉类别:defense-战争行动,construction-工程建设,diplomatic-外交活动
     */
    private String category;
    
    /**
     * 获得时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date achievedTime;
    
    /**
     * 关联项目ID
     */
    private Long projectId;
    
    /**
     * 图标类型
     */
    private String icon;
}





