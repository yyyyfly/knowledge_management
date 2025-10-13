package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目心得实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectInsight extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 关联项目ID
     */
    private Long projectId;

    /**
     * 心得标题
     */
    private String title;

    /**
     * 心得摘要
     */
    private String summary;

    /**
     * 心得内容（富文本HTML）
     */
    private String content;

    /**
     * 标签，多个标签用逗号分隔
     */
    private String tags;
}


