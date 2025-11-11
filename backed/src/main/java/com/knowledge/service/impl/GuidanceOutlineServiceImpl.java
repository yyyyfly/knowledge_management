package com.knowledge.service.impl;

import com.knowledge.entity.GuidanceOutline;
import com.knowledge.mapper.GuidanceOutlineMapper;
import com.knowledge.service.GuidanceOutlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 指导大纲服务实现类
 *
 * @author system
 * @date 2025-11-11
 */
@Service
public class GuidanceOutlineServiceImpl implements GuidanceOutlineService {
    
    @Autowired
    private GuidanceOutlineMapper guidanceOutlineMapper;
    
    @Override
    public GuidanceOutline getById(Long id, String currentUser) {
        return guidanceOutlineMapper.selectById(id, currentUser);
    }
    
    @Override
    public List<GuidanceOutline> getAll(String currentUser) {
        return guidanceOutlineMapper.selectAll(currentUser);
    }
    
    @Override
    public List<GuidanceOutline> getByCategory(String subjectCategory, String currentUser) {
        return guidanceOutlineMapper.selectByCategory(subjectCategory, currentUser);
    }
    
    @Override
    public List<GuidanceOutline> search(String keyword, String currentUser) {
        return guidanceOutlineMapper.search(keyword, currentUser);
    }
    
    @Override
    public GuidanceOutline create(GuidanceOutline guidanceOutline) {
        guidanceOutlineMapper.insert(guidanceOutline);
        return guidanceOutline;
    }
    
    @Override
    public GuidanceOutline update(GuidanceOutline guidanceOutline, String currentUser) {
        guidanceOutline.setRecRevisor(currentUser);
        guidanceOutlineMapper.update(guidanceOutline);
        return guidanceOutlineMapper.selectById(guidanceOutline.getId(), currentUser);
    }
    
    @Override
    public boolean delete(Long id) {
        return guidanceOutlineMapper.delete(id) > 0;
    }
}

