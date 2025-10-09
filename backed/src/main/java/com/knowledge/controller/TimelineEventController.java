package com.knowledge.controller;

import com.knowledge.entity.TimelineEvent;
import com.knowledge.entity.Topic;
import com.knowledge.service.TimelineEventService;
import com.knowledge.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时光轨迹事件控制器
 */
@RestController
@RequestMapping("/timeline")
public class TimelineEventController {

    @Autowired
    private TimelineEventService timelineEventService;

    @Autowired
    private TopicService topicService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 创建全局历史事件
     */
    @PostMapping("/global")
    public Map<String, Object> createGlobalEvent(@RequestBody Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            TimelineEvent event = new TimelineEvent();
            event.setEventType("global");
            event.setTitle((String) params.get("title"));
            event.setDescription((String) params.get("description"));
            
            String dateStr = (String) params.get("date");
            event.setEventDate(DATE_FORMAT.parse(dateStr));
            
            // 从session或token获取当前用户，这里暂时使用admin
            timelineEventService.create(event, "admin");
            
            response.put("code", 200);
            response.put("message", "创建成功");
            response.put("data", event);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "创建失败：" + e.getMessage());
        }
        return response;
    }

    /**
     * 创建专项时间轴里程碑
     */
    @PostMapping("/milestone")
    public Map<String, Object> createMilestone(@RequestBody Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            TimelineEvent event = new TimelineEvent();
            event.setEventType("milestone");
            event.setTitle((String) params.get("title"));
            event.setDescription((String) params.get("description"));
            event.setTopicId((String) params.get("topicId"));
            
            String dateStr = (String) params.get("date");
            event.setEventDate(DATE_FORMAT.parse(dateStr));
            
            // 从session或token获取当前用户，这里暂时使用admin
            timelineEventService.create(event, "admin");
            
            response.put("code", 200);
            response.put("message", "创建成功");
            response.put("data", event);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "创建失败：" + e.getMessage());
        }
        return response;
    }

    /**
     * 获取所有全局历史事件
     */
    @GetMapping("/global")
    public Map<String, Object> getGlobalEvents() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<TimelineEvent> events = timelineEventService.getGlobalEvents();
            response.put("code", 200);
            response.put("data", events);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败：" + e.getMessage());
        }
        return response;
    }

    /**
     * 获取所有专项时间轴里程碑（按主题分组）
     */
    @GetMapping("/milestones")
    public Map<String, Object> getMilestones() {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> data = timelineEventService.getMilestonesGroupByTopic();
            response.put("code", 200);
            response.put("data", data);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败：" + e.getMessage());
        }
        return response;
    }

    /**
     * 删除事件
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteEvent(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 从session或token获取当前用户，这里暂时使用admin
            boolean success = timelineEventService.delete(id, "admin");
            if (success) {
                response.put("code", 200);
                response.put("message", "删除成功");
            } else {
                response.put("code", 404);
                response.put("message", "事件不存在");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "删除失败：" + e.getMessage());
        }
        return response;
    }

    /**
     * 删除主题（级联删除该主题下的所有里程碑）
     */
    @DeleteMapping("/topic/{topicId}")
    public Map<String, Object> deleteTopic(@PathVariable String topicId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 从session或token获取当前用户，这里暂时使用admin
            // 先删除主题下的所有里程碑
            int deletedCount = timelineEventService.deleteTopicWithMilestones(topicId, "admin");
            // 再删除主题本身
            topicService.delete(topicId, "admin");
            response.put("code", 200);
            response.put("message", "删除成功");
            response.put("data", deletedCount);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "删除失败：" + e.getMessage());
        }
        return response;
    }

    /**
     * 创建主题
     */
    @PostMapping("/topic")
    public Map<String, Object> createTopic(@RequestBody Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            Topic topic = new Topic();
            topic.setId((String) params.get("id"));
            topic.setName((String) params.get("name"));
            
            // 从session或token获取当前用户，这里暂时使用admin
            topicService.create(topic, "admin");
            
            response.put("code", 200);
            response.put("message", "创建成功");
            response.put("data", topic);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "创建失败：" + e.getMessage());
        }
        return response;
    }

    /**
     * 获取所有主题
     */
    @GetMapping("/topics")
    public Map<String, Object> getAllTopics() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Topic> topics = topicService.getAll();
            response.put("code", 200);
            response.put("data", topics);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败：" + e.getMessage());
        }
        return response;
    }

    /**
     * 更新主题名称
     */
    @PutMapping("/topic/{topicId}")
    public Map<String, Object> updateTopic(@PathVariable String topicId, @RequestBody Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 检查主题是否存在
            Topic existingTopic = topicService.getById(topicId);
            if (existingTopic == null) {
                response.put("code", 404);
                response.put("message", "主题不存在");
                return response;
            }
            
            // 更新主题名称
            Topic topic = new Topic();
            topic.setId(topicId);
            topic.setName((String) params.get("name"));
            
            // 从session或token获取当前用户，这里暂时使用admin
            topicService.update(topic, "admin");
            
            response.put("code", 200);
            response.put("message", "更新成功");
            response.put("data", topic);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "更新失败：" + e.getMessage());
        }
        return response;
    }
}

