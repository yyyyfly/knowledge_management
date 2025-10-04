package com.knowledge.service;

import com.knowledge.entity.TimeNode;
import com.knowledge.mapper.TimeNodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 时间节点服务类
 *
 * @author system
 * @date 2025-10-04
 */
@Service
public class TimeNodeService {
    
    @Autowired
    private TimeNodeMapper timeNodeMapper;
    
    /**
     * 根据ID查询时间节点
     */
    public TimeNode getById(Long id, String recCreator) {
        return timeNodeMapper.selectById(id, recCreator);
    }
    
    /**
     * 查询所有时间节点
     */
    public List<TimeNode> getAll(String recCreator) {
        return timeNodeMapper.selectAll(recCreator);
    }
    
    /**
     * 根据类型查询时间节点
     */
    public List<TimeNode> getByType(String type, String recCreator) {
        return timeNodeMapper.selectByType(type, recCreator);
    }
    
    /**
     * 根据状态查询时间节点
     */
    public List<TimeNode> getByStatus(String status, String recCreator) {
        return timeNodeMapper.selectByStatus(status, recCreator);
    }
    
    /**
     * 创建时间节点
     */
    @Transactional(rollbackFor = Exception.class)
    public TimeNode create(TimeNode timeNode) {
        timeNodeMapper.insert(timeNode);
        return timeNode;
    }
    
    /**
     * 更新时间节点
     */
    @Transactional(rollbackFor = Exception.class)
    public TimeNode update(TimeNode timeNode, String recCreator) {
        timeNodeMapper.update(timeNode);
        return timeNodeMapper.selectById(timeNode.getId(), recCreator);
    }
    
    /**
     * 删除时间节点
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return timeNodeMapper.deleteById(id) > 0;
    }
}

