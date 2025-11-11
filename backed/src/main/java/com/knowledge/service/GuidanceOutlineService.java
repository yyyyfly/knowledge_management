package com.knowledge.service;

import com.knowledge.entity.GuidanceOutline;

import java.util.List;

/**
 * 指导大纲服务接口
 *
 * @author system
 * @date 2025-11-11
 */
public interface GuidanceOutlineService {
    
    /**
     * 根据ID查询
     */
    GuidanceOutline getById(Long id, String currentUser);
    
    /**
     * 查询所有
     */
    List<GuidanceOutline> getAll(String currentUser);
    
    /**
     * 根据分类查询
     */
    List<GuidanceOutline> getByCategory(String subjectCategory, String currentUser);
    
    /**
     * 搜索
     */
    List<GuidanceOutline> search(String keyword, String currentUser);
    
    /**
     * 创建
     */
    GuidanceOutline create(GuidanceOutline guidanceOutline);
    
    /**
     * 更新
     */
    GuidanceOutline update(GuidanceOutline guidanceOutline, String currentUser);
    
    /**
     * 删除
     */
    boolean delete(Long id);
}

