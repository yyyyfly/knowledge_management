package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.CalendarEvent;
import com.knowledge.service.CalendarEventService;
import com.knowledge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 日历事件Controller
 */
@RestController
@RequestMapping("/calendar")
@CrossOrigin
public class CalendarEventController {

    @Autowired
    private CalendarEventService calendarEventService;

    /**
     * 创建事件
     */
    @PostMapping
    public Result<CalendarEvent> createEvent(@RequestBody CalendarEvent event, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            CalendarEvent created = calendarEventService.createEvent(event, username);
            return Result.success(created);
        } catch (Exception e) {
            return Result.error("创建事件失败：" + e.getMessage());
        }
    }

    /**
     * 获取所有事件
     */
    @GetMapping("/list")
    public Result<List<CalendarEvent>> getAllEvents(@RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            List<CalendarEvent> events = calendarEventService.getAllEvents(username);
            return Result.success(events);
        } catch (Exception e) {
            return Result.error("获取事件列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据日期范围获取事件
     */
    @GetMapping("/range")
    public Result<List<CalendarEvent>> getEventsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            List<CalendarEvent> events = calendarEventService.getEventsByDateRange(startDate, endDate, username);
            return Result.success(events);
        } catch (Exception e) {
            return Result.error("获取事件列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID获取事件
     */
    @GetMapping("/{id}")
    public Result<CalendarEvent> getEventById(@PathVariable Long id) {
        try {
            CalendarEvent event = calendarEventService.getById(id);
            return Result.success(event);
        } catch (Exception e) {
            return Result.error("获取事件详情失败：" + e.getMessage());
        }
    }

    /**
     * 更新事件
     */
    @PutMapping("/{id}")
    public Result<CalendarEvent> updateEvent(@PathVariable Long id, @RequestBody CalendarEvent event, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            event.setId(id);
            CalendarEvent updated = calendarEventService.updateEvent(event, username);
            return Result.success(updated);
        } catch (Exception e) {
            return Result.error("更新事件失败：" + e.getMessage());
        }
    }

    /**
     * 删除事件
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteEvent(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = JwtUtil.getUsernameFromToken(token);
            calendarEventService.deleteEvent(id, username);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除事件失败：" + e.getMessage());
        }
    }
}

