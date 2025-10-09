package com.knowledge.service;

import com.knowledge.entity.Topic;
import com.knowledge.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 主题服务类
 */
@Service
public class TopicService {

    @Autowired
    private TopicMapper topicMapper;

    /**
     * 创建主题
     */
    public Topic create(Topic topic, String creator) {
        topic.setRecCreator(creator);
        topicMapper.insert(topic);
        return topic;
    }

    /**
     * 根据ID查询主题
     */
    public Topic getById(String id) {
        return topicMapper.selectById(id);
    }

    /**
     * 查询所有主题
     */
    public List<Topic> getAll() {
        return topicMapper.selectAll();
    }

    /**
     * 删除主题
     */
    public boolean delete(String id, String revisor) {
        return topicMapper.deleteById(id, revisor) > 0;
    }

    /**
     * 更新主题
     */
    public Topic update(Topic topic, String revisor) {
        topic.setRecRevisor(revisor);
        topicMapper.update(topic);
        return topic;
    }
}

