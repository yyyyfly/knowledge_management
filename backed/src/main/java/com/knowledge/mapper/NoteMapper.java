package com.knowledge.mapper;

import com.knowledge.entity.Note;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 笔记Mapper接口
 *
 * @author system
 * @date 2025-10-04
 */
@Mapper
public interface NoteMapper {
    
    /**
     * 根据ID查询笔记
     */
    Note selectById(@Param("id") Long id, @Param("recCreator") String recCreator);
    
    /**
     * 查询所有笔记
     */
    List<Note> selectAll(@Param("recCreator") String recCreator);
    
    /**
     * 根据类型查询笔记
     */
    List<Note> selectByType(@Param("type") String type, @Param("recCreator") String recCreator);
    
    /**
     * 搜索笔记（根据标题或摘要）
     */
    List<Note> search(@Param("keyword") String keyword, @Param("recCreator") String recCreator);
    
    /**
     * 根据类型查询推荐巩固的笔记（限5条）
     */
    List<Note> selectByTypeRecommended(@Param("type") String type, @Param("recCreator") String recCreator);
    
    /**
     * 插入笔记
     */
    int insert(Note note);
    
    /**
     * 更新笔记
     */
    int update(Note note);
    
    /**
     * 只更新巩固相关信息
     */
    int updateReviewInfo(Note note);
    
    /**
     * 删除笔记
     */
    int deleteById(@Param("id") Long id);
}

