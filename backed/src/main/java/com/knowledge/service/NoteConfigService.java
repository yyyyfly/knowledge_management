package com.knowledge.service;

import com.knowledge.entity.NoteConfig;
import com.knowledge.mapper.NoteConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 笔记配置服务类（通用）
 *
 * @author system
 * @date 2025-10-08
 */
@Service
public class NoteConfigService {
    
    @Autowired
    private NoteConfigMapper noteConfigMapper;
    
    /**
     * 根据ID查询
     */
    public NoteConfig getById(Long id, String recCreator) {
        return noteConfigMapper.selectById(id, recCreator);
    }
    
    /**
     * 查询所有配置
     */
    public List<NoteConfig> getAll(String recCreator) {
        return noteConfigMapper.selectAll(recCreator);
    }
    
    /**
     * 根据笔记类型查询
     */
    public List<NoteConfig> getByNoteType(String noteType, String recCreator) {
        return noteConfigMapper.selectByNoteType(noteType, recCreator);
    }
    
    /**
     * 根据笔记类型和配置类型查询
     */
    public List<NoteConfig> getByNoteAndConfigType(String noteType, String configType, String recCreator) {
        return noteConfigMapper.selectByNoteAndConfigType(noteType, configType, recCreator);
    }
    
    /**
     * 创建配置
     */
    @Transactional(rollbackFor = Exception.class)
    public NoteConfig create(NoteConfig config) {
        // 检查名称是否已存在
        int count = noteConfigMapper.countByName(config.getNoteType(), config.getConfigType(), config.getConfigName(), config.getRecCreator());
        if (count > 0) {
            throw new RuntimeException("配置名称已存在");
        }
        
        // 如果未设置排序序号，设为最大值+1
        if (config.getSortOrder() == null) {
            config.setSortOrder(999);
        }
        
        noteConfigMapper.insert(config);
        return config;
    }
    
    /**
     * 更新配置
     */
    @Transactional(rollbackFor = Exception.class)
    public NoteConfig update(NoteConfig config, String recCreator) {
        config.setRecRevisor(recCreator);
        noteConfigMapper.update(config);
        return noteConfigMapper.selectById(config.getId(), recCreator);
    }
    
    /**
     * 删除配置
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return noteConfigMapper.deleteById(id) > 0;
    }
}

