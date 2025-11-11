package com.knowledge.service;

import com.knowledge.entity.ProjectKeyPoint;
import com.knowledge.mapper.ProjectKeyPointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目关键备忘服务类
 */
@Service
public class ProjectKeyPointService {
    
    @Autowired
    private ProjectKeyPointMapper keyPointMapper;
    
    /**
     * 添加关键备忘
     */
    @Transactional
    public ProjectKeyPoint addKeyPoint(ProjectKeyPoint keyPoint) {
        keyPointMapper.insertSelective(keyPoint);
        return keyPoint;
    }
    
    /**
     * 删除关键备忘
     */
    @Transactional
    public boolean deleteKeyPoint(Long id) {
        return keyPointMapper.deleteById(id) > 0;
    }
    
    /**
     * 删除项目的所有备忘
     */
    @Transactional
    public boolean deleteByProjectId(Long projectId) {
        return keyPointMapper.deleteByProjectId(projectId) > 0;
    }
    
    /**
     * 更新关键备忘
     */
    @Transactional
    public boolean updateKeyPoint(ProjectKeyPoint keyPoint) {
        return keyPointMapper.updateByIdSelective(keyPoint) > 0;
    }
    
    /**
     * 根据ID查询
     */
    public ProjectKeyPoint getKeyPointById(Long id) {
        return keyPointMapper.selectById(id);
    }
    
    /**
     * 获取项目的所有备忘
     */
    public List<ProjectKeyPoint> getKeyPointsByProjectId(Long projectId) {
        return keyPointMapper.selectByProjectId(projectId);
    }
    
    /**
     * 根据项目ID和类型查询
     */
    public List<ProjectKeyPoint> getKeyPointsByProjectIdAndType(Long projectId, String type) {
        return keyPointMapper.selectByProjectIdAndType(projectId, type);
    }
    
    /**
     * 统计项目的备忘数量
     */
    public int countByProjectId(Long projectId) {
        return keyPointMapper.countByProjectId(projectId);
    }
}



















