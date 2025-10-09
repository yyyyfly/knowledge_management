package com.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 时间轴主题实体类
 */
@Data
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题ID
     */
    private String id;

    /**
     * 主题名称
     */
    private String name;

    /**
     * 创建人
     */
    private String recCreator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recCreateTime;

    /**
     * 修改人
     */
    private String recRevisor;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recReviseTime;

    /**
     * 归档标志:0-已删除,1-正常
     */
    private Integer archFlag;
}

