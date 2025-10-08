package com.knowledge.mapper;

import com.knowledge.entity.NoteConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 笔记配置Mapper（通用）
 *
 * @author system
 * @date 2025-10-08
 */
@Mapper
public interface NoteConfigMapper {
    
    /**
     * 根据ID查询
     */
    NoteConfig selectById(@Param("id") Long id, @Param("recCreator") String recCreator);
    
    /**
     * 查询所有配置
     */
    List<NoteConfig> selectAll(@Param("recCreator") String recCreator);
    
    /**
     * 根据笔记类型查询
     */
    List<NoteConfig> selectByNoteType(@Param("noteType") String noteType, @Param("recCreator") String recCreator);
    
    /**
     * 根据笔记类型和配置类型查询
     */
    List<NoteConfig> selectByNoteAndConfigType(@Param("noteType") String noteType, @Param("configType") String configType, @Param("recCreator") String recCreator);
    
    /**
     * 插入配置
     */
    int insert(NoteConfig config);
    
    /**
     * 更新配置
     */
    int update(NoteConfig config);
    
    /**
     * 删除配置
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 检查配置名称是否已存在
     */
    int countByName(@Param("noteType") String noteType, @Param("configType") String configType, @Param("configName") String configName, @Param("recCreator") String recCreator);
}


