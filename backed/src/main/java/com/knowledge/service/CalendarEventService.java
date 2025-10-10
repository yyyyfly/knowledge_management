package com.knowledge.service;

import com.knowledge.entity.CalendarEvent;
import com.knowledge.mapper.CalendarEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 日历事件服务类
 */
@Service
public class CalendarEventService {

    @Autowired
    private CalendarEventMapper calendarEventMapper;

    /**
     * 创建事件
     */
    public CalendarEvent createEvent(CalendarEvent event, String creator) {
        event.setRecCreator(creator);
        event.setRecRevisor(creator);
        if (event.getEventType() == null || event.getEventType().isEmpty()) {
            event.setEventType("custom");
        }
        if (event.getRepeatType() == null || event.getRepeatType().isEmpty()) {
            event.setRepeatType("once");
        }
        if (event.getColor() == null || event.getColor().isEmpty()) {
            event.setColor("#3b82f6");
        }
        calendarEventMapper.insert(event);
        return event;
    }

    /**
     * 根据ID查询事件
     */
    public CalendarEvent getById(Long id) {
        return calendarEventMapper.selectById(id);
    }

    /**
     * 查询所有事件
     */
    public List<CalendarEvent> getAllEvents(String username) {
        return calendarEventMapper.selectAll(username);
    }

    /**
     * 根据日期范围查询事件
     */
    public List<CalendarEvent> getEventsByDateRange(Date startDate, Date endDate, String username) {
        return calendarEventMapper.selectByDateRange(startDate, endDate, username);
    }

    /**
     * 更新事件
     */
    public CalendarEvent updateEvent(CalendarEvent event, String revisor) {
        event.setRecRevisor(revisor);
        calendarEventMapper.update(event);
        return event;
    }

    /**
     * 删除事件
     */
    public void deleteEvent(Long id, String revisor) {
        calendarEventMapper.delete(id, revisor);
    }
}

