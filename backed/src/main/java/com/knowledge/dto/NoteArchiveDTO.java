package com.knowledge.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 笔记归档数据传输对象
 *
 * @author system
 * @date 2025-10-20
 */
@Data
public class NoteArchiveDTO {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 归档名称
     */
    private String archiveName;
    
    /**
     * 归档类型
     */
    private String archiveType;
    
    /**
     * 归档描述
     */
    private String description;
    
    /**
     * 来源信息
     */
    private String sourceInfo;
    
    /**
     * 包含笔记数量
     */
    private Integer noteCount;
    
    /**
     * 创建时间
     */
    private Date recCreateTime;
    
    /**
     * 修改时间
     */
    private Date recReviseTime;
    
    /**
     * 关联的笔记列表（用于详情页）
     */
    private List<NoteArchiveNoteDTO> notes;
}

