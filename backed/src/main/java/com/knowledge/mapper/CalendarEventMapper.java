package com.knowledge.mapper;

import com.knowledge.entity.CalendarEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 日历事件Mapper接口
 */
@Mapper
public interface CalendarEventMapper {

    /**
     * 插入事件
     */
    int insert(CalendarEvent event);

    /**
     * 根据ID查询事件
     */
    CalendarEvent selectById(@Param("id") Long id);

    /**
     * 查询所有事件（按用户过滤）
     */
    List<CalendarEvent> selectAll(@Param("username") String username);

    /**
     * 根据日期范围查询事件
     */
    List<CalendarEvent> selectByDateRange(@Param("startDate") Date startDate, 
                                           @Param("endDate") Date endDate, 
                                           @Param("username") String username);

    /**
     * 更新事件
     */
    int update(CalendarEvent event);

    /**
     * 逻辑删除事件
     */
    int delete(@Param("id") Long id, @Param("revisor") String revisor);
}

