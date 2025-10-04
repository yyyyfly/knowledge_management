package com.knowledge.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体基类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
public class BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    private Long id;
    
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
     * 归档标志:0-未归档,1-已归档
     */
    private Integer archFlag;
}

