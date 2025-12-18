package com.knowledge.mapper;

import com.knowledge.entity.RequirementDraft;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 需求草案Mapper接口
 *
 * @author system
 * @date 2025-11-16
 */
@Mapper
public interface RequirementDraftMapper {
    
    /**
     * 查询所有需求草案（按version分组，组内按sort_order排序）
     *
     * @return 需求草案列表
     */
    List<RequirementDraft> selectAll();
    
    /**
     * 根据ID查询需求草案
     *
     * @param id 需求草案ID
     * @return 需求草案
     */
    RequirementDraft selectById(@Param("id") Long id);
    
    /**
     * 根据版本号查询需求草案
     *
     * @param version 版本号
     * @return 需求草案列表
     */
    List<RequirementDraft> selectByVersion(@Param("version") String version);
    
    /**
     * 查询所有版本号列表（去重）
     *
     * @return 版本号列表
     */
    List<String> selectVersionList();
    
    /**
     * 新增需求草案
     *
     * @param draft 需求草案
     * @return 影响行数
     */
    int insert(RequirementDraft draft);
    
    /**
     * 更新需求草案
     *
     * @param draft 需求草案
     * @return 影响行数
     */
    int update(RequirementDraft draft);
    
    /**
     * 根据ID删除需求草案
     *
     * @param id 需求草案ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
}


