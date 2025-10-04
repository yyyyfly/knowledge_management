package com.knowledge.service;

import com.knowledge.entity.Summary;
import com.knowledge.mapper.SummaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统总结服务类
 *
 * @author system
 * @date 2025-10-04
 */
@Service
public class SummaryService {
    
    @Autowired
    private SummaryMapper summaryMapper;
    
    /**
     * 根据ID查询总结
     */
    public Summary getById(Long id, String recCreator) {
        return summaryMapper.selectById(id, recCreator);
    }
    
    /**
     * 查询所有总结
     */
    public List<Summary> getAll(String recCreator) {
        return summaryMapper.selectAll(recCreator);
    }
    
    /**
     * 根据类型查询总结
     */
    public List<Summary> getByType(String type, String recCreator) {
        return summaryMapper.selectByType(type, recCreator);
    }
    
    /**
     * 创建总结
     */
    @Transactional(rollbackFor = Exception.class)
    public Summary create(Summary summary) {
        summaryMapper.insert(summary);
        return summary;
    }
    
    /**
     * 更新总结
     */
    @Transactional(rollbackFor = Exception.class)
    public Summary update(Summary summary, String recCreator) {
        summaryMapper.update(summary);
        return summaryMapper.selectById(summary.getId(), recCreator);
    }
    
    /**
     * 删除总结
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return summaryMapper.deleteById(id) > 0;
    }
}

