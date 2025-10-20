package com.knowledge.dto;

import lombok.Data;

import java.util.Date;

/**
 * 归档关联笔记数据传输对象
 *
 * @author system
 * @date 2025-10-20
 */
@Data
public class NoteArchiveNoteDTO {
    
    /**
     * 笔记ID
     */
    private Long noteId;
    
    /**
     * 笔记标题
     */
    private String title;
    
    /**
     * 笔记类型
     */
    private String type;
    
    /**
     * 笔记摘要
     */
    private String summary;
    
    /**
     * 笔记标签
     */
    private String tags;
    
    /**
     * 关联备注
     */
    private String relationNote;
    
    /**
     * 关联ID
     */
    private Long relationId;
    
    /**
     * 创建时间
     */
    private Date recCreateTime;
}

