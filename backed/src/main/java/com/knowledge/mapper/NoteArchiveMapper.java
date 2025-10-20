package com.knowledge.mapper;

import com.knowledge.entity.NoteArchive;
import com.knowledge.dto.NoteArchiveDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 笔记归档Mapper接口
 *
 * @author system
 * @date 2025-10-20
 */
@Mapper
public interface NoteArchiveMapper {
    
    /**
     * 插入归档
     */
    int insert(NoteArchive archive);
    
    /**
     * 根据ID删除归档
     */
    int deleteById(Long id);
    
    /**
     * 更新归档
     */
    int update(NoteArchive archive);
    
    /**
     * 根据ID查询归档
     */
    NoteArchive selectById(Long id);
    
    /**
     * 查询归档列表
     */
    List<NoteArchiveDTO> selectList(@Param("archiveName") String archiveName,
                                     @Param("archiveType") String archiveType);
    
    /**
     * 更新归档的笔记数量
     */
    int updateNoteCount(@Param("archiveId") Long archiveId, @Param("count") Integer count);
}

