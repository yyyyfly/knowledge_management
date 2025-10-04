package com.knowledge.service;

import com.knowledge.entity.Honor;
import com.knowledge.mapper.HonorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 荣誉战绩服务类
 *
 * @author system
 * @date 2025-10-04
 */
@Service
public class HonorService {
    
    @Autowired
    private HonorMapper honorMapper;
    
    /**
     * 根据ID查询荣誉
     */
    public Honor getById(Long id, String recCreator) {
        return honorMapper.selectById(id, recCreator);
    }
    
    /**
     * 查询所有荣誉
     */
    public List<Honor> getAll(String recCreator) {
        return honorMapper.selectAll(recCreator);
    }
    
    /**
     * 根据类别查询荣誉
     */
    public List<Honor> getByCategory(String category, String recCreator) {
        return honorMapper.selectByCategory(category, recCreator);
    }
    
    /**
     * 创建荣誉
     */
    @Transactional(rollbackFor = Exception.class)
    public Honor create(Honor honor) {
        honorMapper.insert(honor);
        return honor;
    }
    
    /**
     * 更新荣誉
     */
    @Transactional(rollbackFor = Exception.class)
    public Honor update(Honor honor, String recCreator) {
        honorMapper.update(honor);
        return honorMapper.selectById(honor.getId(), recCreator);
    }
    
    /**
     * 删除荣誉
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return honorMapper.deleteById(id) > 0;
    }
}

