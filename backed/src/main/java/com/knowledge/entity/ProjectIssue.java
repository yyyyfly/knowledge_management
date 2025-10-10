package com.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 项目问题实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectIssue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关联项目ID
     */
    private Long projectId;

    /**
     * 问题标题
     */
    private String issueTitle;

    /**
     * 问题描述
     */
    private String issueDescription;

    /**
     * 问题类型：technical-技术问题, requirement-需求问题, design-设计问题, other-其他问题
     */
    private String issueType;

    /**
     * 优先级：low-低, medium-中, high-高, urgent-紧急
     */
    private String priority;

    /**
     * 问题状态：open-待处理, in_progress-处理中, resolved-已解决, closed-已关闭
     */
    private String status;

    /**
     * 解决方案
     */
    private String solution;

    /**
     * 解决时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date resolveTime;
}

