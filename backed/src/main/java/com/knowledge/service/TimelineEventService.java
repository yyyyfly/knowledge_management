package com.knowledge.service;

import com.knowledge.entity.TimelineEvent;
import com.knowledge.entity.Topic;
import com.knowledge.mapper.TimelineEventMapper;
import com.knowledge.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 时光轨迹事件服务类
 */
@Service
public class TimelineEventService {

    @Autowired
    private TimelineEventMapper timelineEventMapper;

    @Autowired
    private TopicMapper topicMapper;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 创建事件
     */
    public TimelineEvent create(TimelineEvent event, String creator) {
        event.setRecCreator(creator);
        timelineEventMapper.insert(event);
        return event;
    }

    /**
     * 获取所有全局历史事件
     */
    public List<TimelineEvent> getGlobalEvents() {
        return timelineEventMapper.selectGlobalEvents();
    }

    /**
     * 获取所有专项时间轴里程碑（按主题分组）
     */
    public Map<String, Object> getMilestonesGroupByTopic() {
        // 获取所有主题
        List<Topic> allTopics = topicMapper.selectAll();
        
        // 获取所有里程碑
        List<TimelineEvent> milestones = timelineEventMapper.selectMilestones();
        
        // 按主题分组里程碑
        Map<String, List<TimelineEvent>> groupedMap = milestones.stream()
            .collect(Collectors.groupingBy(
                event -> event.getTopicId() != null ? event.getTopicId() : "unknown",
                LinkedHashMap::new,
                Collectors.toList()
            ));
        
        // 转换为前端需要的格式
        List<Map<String, Object>> topicList = new ArrayList<>();
        
        // 遍历所有主题（包括没有里程碑的主题）
        for (Topic topicEntity : allTopics) {
            String topicId = topicEntity.getId();
            List<TimelineEvent> events = groupedMap.getOrDefault(topicId, new ArrayList<>());
            
            Map<String, Object> topic = new HashMap<>();
            topic.put("id", topicId);
            topic.put("name", topicEntity.getName());
            
            // 获取最早日期作为开始日期
            Date startDate = new Date();
            if (!events.isEmpty()) {
                startDate = events.stream()
                    .map(TimelineEvent::getEventDate)
                    .min(Date::compareTo)
                    .orElse(new Date());
            }
            topic.put("startDate", DATE_FORMAT.format(startDate));
            
            // 转换timeline
            List<Map<String, Object>> timeline = events.stream()
                .map(event -> {
                    Map<String, Object> milestone = new HashMap<>();
                    milestone.put("id", event.getId()); // 添加ID用于删除
                    milestone.put("date", DATE_FORMAT.format(event.getEventDate()));
                    milestone.put("title", event.getTitle());
                    milestone.put("description", event.getDescription());
                    return milestone;
                })
                .collect(Collectors.toList());
            
            topic.put("timeline", timeline);
            topicList.add(topic);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("topicList", topicList);
        return result;
    }

    /**
     * 根据ID删除事件
     */
    public boolean delete(Long id, String revisor) {
        TimelineEvent event = timelineEventMapper.selectById(id);
        if (event != null) {
            event.setRecRevisor(revisor);
            return timelineEventMapper.deleteById(id) > 0;
        }
        return false;
    }

    /**
     * 更新事件
     */
    public TimelineEvent update(TimelineEvent event, String revisor) {
        event.setRecRevisor(revisor);
        timelineEventMapper.update(event);
        return event;
    }

    /**
     * 删除主题及其所有里程碑（级联删除）
     */
    public int deleteTopicWithMilestones(String topicId, String revisor) {
        return timelineEventMapper.deleteByTopicId(topicId, revisor);
    }
}

