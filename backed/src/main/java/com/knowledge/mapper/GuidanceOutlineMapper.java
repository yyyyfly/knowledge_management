package com.knowledge.mapper;

import com.knowledge.entity.GuidanceOutline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 指导大纲 Mapper 接口
 *
 * @author system
 * @date 2025-11-11
 */
@Mapper
public interface GuidanceOutlineMapper {
    
    /**
     * 根据ID查询
     */
    GuidanceOutline selectById(@Param("id") Long id, @Param("recCreator") String recCreator);
    
    /**
     * 查询所有
     */
    List<GuidanceOutline> selectAll(@Param("recCreator") String recCreator);
    
    /**
     * 根据分类查询
     */
    List<GuidanceOutline> selectByCategory(@Param("subjectCategory") String subjectCategory, @Param("recCreator") String recCreator);
    
    /**
     * 搜索（根据学科名称或标签）
     */
    List<GuidanceOutline> search(@Param("keyword") String keyword, @Param("recCreator") String recCreator);
    
    /**
     * 插入
     */
    int insert(GuidanceOutline guidanceOutline);
    
    /**
     * 更新
     */
    int update(GuidanceOutline guidanceOutline);
    
    /**
     * 删除（逻辑删除）
     */
    int delete(@Param("id") Long id);
    
    /**
     * 物理删除
     */
    int deletePhysically(@Param("id") Long id);
}

