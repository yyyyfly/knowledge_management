package com.knowledge.mapper;

import com.knowledge.entity.Summary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统总结Mapper接口
 *
 * @author system
 * @date 2025-10-04
 */
@Mapper
public interface SummaryMapper {
    
    /**
     * 根据ID查询总结
     */
    Summary selectById(@Param("id") Long id, @Param("recCreator") String recCreator);
    
    /**
     * 查询所有总结
     */
    List<Summary> selectAll(@Param("recCreator") String recCreator);
    
    /**
     * 根据类型查询总结
     */
    List<Summary> selectByType(@Param("type") String type, @Param("recCreator") String recCreator);
    
    /**
     * 插入总结
     */
    int insert(Summary summary);
    
    /**
     * 更新总结
     */
    int update(Summary summary);
    
    /**
     * 删除总结
     */
    int deleteById(@Param("id") Long id);
}

