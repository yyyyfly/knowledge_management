package com.knowledge.mapper;

import com.knowledge.entity.TimeNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 时间节点Mapper接口
 *
 * @author system
 * @date 2025-10-04
 */
@Mapper
public interface TimeNodeMapper {
    
    /**
     * 根据ID查询时间节点
     */
    TimeNode selectById(@Param("id") Long id, @Param("recCreator") String recCreator);
    
    /**
     * 查询所有时间节点
     */
    List<TimeNode> selectAll(@Param("recCreator") String recCreator);
    
    /**
     * 根据类型查询时间节点
     */
    List<TimeNode> selectByType(@Param("type") String type, @Param("recCreator") String recCreator);
    
    /**
     * 根据状态查询时间节点
     */
    List<TimeNode> selectByStatus(@Param("status") String status, @Param("recCreator") String recCreator);
    
    /**
     * 插入时间节点
     */
    int insert(TimeNode timeNode);
    
    /**
     * 更新时间节点
     */
    int update(TimeNode timeNode);
    
    /**
     * 删除时间节点
     */
    int deleteById(@Param("id") Long id);
}

