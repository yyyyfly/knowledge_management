package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 笔记归档关联实体类
 *
 * @author system
 * @date 2025-10-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteArchiveRelation extends BaseEntity {
    
    /**
     * 归档ID
     */
    private Long archiveId;
    
    /**
     * 笔记ID
     */
    private Long noteId;
    
    /**
     * 关联备注（如：章节、页码等）
     */
    private String relationNote;
}

