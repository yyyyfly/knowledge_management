package com.knowledge.entity;

import com.knowledge.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 碎片笔记扩展实体类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteFragment extends BaseEntity {
    
    /**
     * 笔记ID
     */
    private Long noteId;
    
    /**
     * 碎片分类，JSON数组格式
     */
    private String fragmentCategory;
    
    /**
     * 碎片主题，JSON数组格式
     */
    private String fragmentTheme;
    
    /**
     * 重要程度:low-低,medium-中,high-高
     */
    private String importance;
}





