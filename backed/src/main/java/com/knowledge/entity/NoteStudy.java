package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 求学笔记扩展实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteStudy extends BaseEntity {
    
    /**
     * 笔记ID
     */
    private Long noteId;
    
    /**
     * 课程，JSON数组格式
     */
    private String course;
    
    /**
     * 学科，JSON数组格式
     */
    private String studySubject;
    
    /**
     * 书名
     */
    private String bookName;
    
    /**
     * 书籍学科，JSON数组格式
     */
    private String bookSubject;
}





