package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 笔记归档实体类
 *
 * @author system
 * @date 2025-10-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteArchive extends BaseEntity {
    
    /**
     * 归档名称（如：材料名称、书籍名称）
     */
    private String archiveName;
    
    /**
     * 归档类型（如：book-书籍,course-课程,material-材料,video-视频,other-其他）
     */
    private String archiveType;
    
    /**
     * 归档描述
     */
    private String description;
    
    /**
     * 来源信息（如：作者、出版社、链接等）
     */
    private String sourceInfo;
    
    /**
     * 包含笔记数量
     */
    private Integer noteCount;
}

