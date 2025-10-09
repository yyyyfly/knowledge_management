package com.knowledge.mapper;

import com.knowledge.entity.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 主题Mapper接口
 */
@Mapper
public interface TopicMapper {
    
    /**
     * 插入主题
     */
    int insert(Topic topic);
    
    /**
     * 根据ID查询主题
     */
    Topic selectById(@Param("id") String id);
    
    /**
     * 查询所有主题
     */
    List<Topic> selectAll();
    
    /**
     * 根据ID删除主题
     */
    int deleteById(@Param("id") String id, @Param("revisor") String revisor);
    
    /**
     * 更新主题
     */
    int update(Topic topic);
}

