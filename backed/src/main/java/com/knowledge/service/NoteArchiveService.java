package com.knowledge.service;

import com.knowledge.common.Result;
import com.knowledge.dto.NoteArchiveDTO;
import com.knowledge.entity.NoteArchive;
import com.knowledge.entity.NoteArchiveRelation;
import com.knowledge.mapper.NoteArchiveMapper;
import com.knowledge.mapper.NoteArchiveRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 笔记归档服务类
 *
 * @author system
 * @date 2025-10-20
 */
@Service
public class NoteArchiveService {
    
    @Autowired
    private NoteArchiveMapper archiveMapper;
    
    @Autowired
    private NoteArchiveRelationMapper relationMapper;
    
    /**
     * 创建归档
     */
    @Transactional
    public Result<NoteArchive> createArchive(NoteArchive archive) {
        try {
            archive.setNoteCount(0);
            archiveMapper.insert(archive);
            return Result.success(archive);
        } catch (Exception e) {
            return Result.error("创建归档失败：" + e.getMessage());
        }
    }
    
    /**
     * 更新归档
     */
    @Transactional
    public Result<Void> updateArchive(NoteArchive archive) {
        try {
            archiveMapper.update(archive);
            return Result.success();
        } catch (Exception e) {
            return Result.error("更新归档失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除归档（同时删除关联关系）
     */
    @Transactional
    public Result<Void> deleteArchive(Long id) {
        try {
            // 删除关联关系
            relationMapper.deleteByArchiveId(id);
            // 删除归档
            archiveMapper.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error("删除归档失败：" + e.getMessage());
        }
    }
    
    /**
     * 查询归档详情
     */
    public Result<NoteArchiveDTO> getArchiveDetail(Long id) {
        try {
            NoteArchive archive = archiveMapper.selectById(id);
            if (archive == null) {
                return Result.error("归档不存在");
            }
            
            NoteArchiveDTO dto = new NoteArchiveDTO();
            dto.setId(archive.getId());
            dto.setArchiveName(archive.getArchiveName());
            dto.setArchiveType(archive.getArchiveType());
            dto.setDescription(archive.getDescription());
            dto.setSourceInfo(archive.getSourceInfo());
            dto.setNoteCount(archive.getNoteCount());
            dto.setRecCreateTime(archive.getRecCreateTime());
            dto.setRecReviseTime(archive.getRecReviseTime());
            
            // 查询关联的笔记
            dto.setNotes(relationMapper.selectNotesByArchiveId(id));
            
            return Result.success(dto);
        } catch (Exception e) {
            return Result.error("查询归档详情失败：" + e.getMessage());
        }
    }
    
    /**
     * 查询归档列表
     */
    public Result<List<NoteArchiveDTO>> getArchiveList(String archiveName, String archiveType) {
        try {
            List<NoteArchiveDTO> list = archiveMapper.selectList(archiveName, archiveType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error("查询归档列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 添加笔记到归档
     */
    @Transactional
    public Result<Long> addNoteToArchive(Long archiveId, Long noteId, String relationNote) {
        try {
            // 检查关联是否已存在
            if (relationMapper.checkExists(archiveId, noteId) > 0) {
                return Result.error("该笔记已在此归档中");
            }
            
            // 创建关联
            NoteArchiveRelation relation = new NoteArchiveRelation();
            relation.setArchiveId(archiveId);
            relation.setNoteId(noteId);
            relation.setRelationNote(relationNote);
            relationMapper.insert(relation);
            
            // 更新归档的笔记数量
            int count = relationMapper.countByArchiveId(archiveId);
            archiveMapper.updateNoteCount(archiveId, count);
            
            // 返回创建的关联ID
            return Result.success(relation.getId());
        } catch (Exception e) {
            return Result.error("添加笔记到归档失败：" + e.getMessage());
        }
    }
    
    /**
     * 从归档中移除笔记
     */
    @Transactional
    public Result<Void> removeNoteFromArchive(Long relationId, Long archiveId) {
        try {
            relationMapper.deleteById(relationId);
            
            // 更新归档的笔记数量
            int count = relationMapper.countByArchiveId(archiveId);
            archiveMapper.updateNoteCount(archiveId, count);
            
            return Result.success();
        } catch (Exception e) {
            return Result.error("移除笔记失败：" + e.getMessage());
        }
    }
    
    /**
     * 更新关联备注
     */
    public Result<Void> updateRelationNote(Long relationId, String relationNote) {
        try {
            relationMapper.updateRelationNote(relationId, relationNote);
            return Result.success();
        } catch (Exception e) {
            return Result.error("更新备注失败：" + e.getMessage());
        }
    }
    
    /**
     * 查询笔记关联的所有归档
     */
    public Result<List<Long>> getArchivesByNoteId(Long noteId) {
        try {
            List<Long> archiveIds = relationMapper.selectArchiveIdsByNoteId(noteId);
            return Result.success(archiveIds);
        } catch (Exception e) {
            return Result.error("查询笔记归档失败：" + e.getMessage());
        }
    }
    
    /**
     * 查询归档中的所有笔记ID
     */
    public Result<List<Long>> getArchiveNoteIds(Long archiveId) {
        try {
            List<Long> noteIds = relationMapper.selectNoteIdsByArchiveId(archiveId);
            return Result.success(noteIds);
        } catch (Exception e) {
            return Result.error("查询归档笔记失败：" + e.getMessage());
        }
    }
}

