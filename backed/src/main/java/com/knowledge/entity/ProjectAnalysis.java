package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目分析实体类
 * 用于存储项目的底层逻辑分析内容
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectAnalysis extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 关联项目ID
     */
    private Long projectId;

    /**
     * 底层逻辑描述（富文本HTML）
     */
    private String logicDescription;
}

