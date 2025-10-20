package com.knowledge.mapper;

import com.knowledge.entity.NoteArchiveRelation;
import com.knowledge.dto.NoteArchiveNoteDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 笔记归档关联Mapper接口
 *
 * @author system
 * @date 2025-10-20
 */
@Mapper
public interface NoteArchiveRelationMapper {
    
    /**
     * 插入关联
     */
    int insert(NoteArchiveRelation relation);
    
    /**
     * 根据ID删除关联
     */
    int deleteById(Long id);
    
    /**
     * 根据归档ID删除所有关联
     */
    int deleteByArchiveId(Long archiveId);
    
    /**
     * 根据笔记ID删除所有关联
     */
    int deleteByNoteId(Long noteId);
    
    /**
     * 更新关联备注
     */
    int updateRelationNote(@Param("id") Long id, @Param("relationNote") String relationNote);
    
    /**
     * 查询归档下的所有笔记
     */
    List<NoteArchiveNoteDTO> selectNotesByArchiveId(@Param("archiveId") Long archiveId);
    
    /**
     * 查询笔记关联的所有归档ID
     */
    List<Long> selectArchiveIdsByNoteId(@Param("noteId") Long noteId);
    
    /**
     * 检查关联是否存在
     */
    int checkExists(@Param("archiveId") Long archiveId, @Param("noteId") Long noteId);
    
    /**
     * 统计归档的笔记数量
     */
    int countByArchiveId(@Param("archiveId") Long archiveId);
}

