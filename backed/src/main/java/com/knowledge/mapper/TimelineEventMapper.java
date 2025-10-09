package com.knowledge.mapper;

import com.knowledge.entity.TimelineEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 时光轨迹事件Mapper接口
 */
@Mapper
public interface TimelineEventMapper {
    
    /**
     * 插入事件
     */
    int insert(TimelineEvent event);
    
    /**
     * 根据ID查询事件
     */
    TimelineEvent selectById(Long id);
    
    /**
     * 查询所有全局历史事件
     */
    List<TimelineEvent> selectGlobalEvents();
    
    /**
     * 查询所有专项时间轴里程碑
     */
    List<TimelineEvent> selectMilestones();
    
    /**
     * 根据主题ID查询里程碑
     */
    List<TimelineEvent> selectByTopicId(@Param("topicId") String topicId);
    
    /**
     * 根据ID删除事件
     */
    int deleteById(Long id);
    
    /**
     * 根据主题ID删除所有里程碑（级联删除）
     */
    int deleteByTopicId(@Param("topicId") String topicId, @Param("revisor") String revisor);
    
    /**
     * 更新事件
     */
    int update(TimelineEvent event);
}

