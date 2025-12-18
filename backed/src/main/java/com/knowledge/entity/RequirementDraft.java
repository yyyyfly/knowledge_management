package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 需求草案实体类
 *
 * @author system
 * @date 2025-11-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RequirementDraft extends BaseEntity {
    
    /**
     * 版本号，如v3.0、v4.0等
     */
    private String version;
    
    /**
     * 标题，简短描述功能想法
     */
    private String title;
    
    /**
     * 详细描述，详细的功能说明和实现思路
     */
    private String description;
    
    /**
     * 版本状态：drafting-草拟中，approved-已通过，abandoned-已废弃
     */
    private String status;
    
    /**
     * 排序号，同一版本内的排序
     */
    private Integer sortOrder;
}

