package com.knowledge.service;

import com.knowledge.entity.Note;
import com.knowledge.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 笔记服务类
 *
 * @author system
 * @date 2025-10-04
 */
@Service
public class NoteService {
    
    @Autowired
    private NoteMapper noteMapper;
    
    /**
     * 根据ID查询笔记
     */
    public Note getById(Long id, String recCreator) {
        return noteMapper.selectById(id, recCreator);
    }
    
    /**
     * 查询所有笔记
     */
    public List<Note> getAll(String recCreator) {
        return noteMapper.selectAll(recCreator);
    }
    
    /**
     * 根据类型查询笔记
     */
    public List<Note> getByType(String type, String recCreator) {
        return noteMapper.selectByType(type, recCreator);
    }
    
    /**
     * 搜索笔记
     */
    public List<Note> search(String keyword, String recCreator) {
        return noteMapper.search(keyword, recCreator);
    }
    
    /**
     * 创建笔记
     */
    @Transactional(rollbackFor = Exception.class)
    public Note create(Note note) {
        noteMapper.insert(note);
        return note;
    }
    
    /**
     * 更新笔记
     */
    @Transactional(rollbackFor = Exception.class)
    public Note update(Note note, String recCreator) {
        noteMapper.update(note);
        return noteMapper.selectById(note.getId(), recCreator);
    }
    
    /**
     * 删除笔记
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return noteMapper.deleteById(id) > 0;
    }
}

