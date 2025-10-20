package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.dto.NoteArchiveDTO;
import com.knowledge.entity.NoteArchive;
import com.knowledge.service.NoteArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 笔记归档控制器
 *
 * @author system
 * @date 2025-10-20
 */
@RestController
@RequestMapping("/api/note-archive")
@CrossOrigin(origins = "*")
public class NoteArchiveController {
    
    @Autowired
    private NoteArchiveService archiveService;
    
    /**
     * 创建归档
     */
    @PostMapping("/create")
    public Result<NoteArchive> createArchive(@RequestBody NoteArchive archive) {
        return archiveService.createArchive(archive);
    }
    
    /**
     * 更新归档
     */
    @PostMapping("/update")
    public Result<Void> updateArchive(@RequestBody NoteArchive archive) {
        return archiveService.updateArchive(archive);
    }
    
    /**
     * 删除归档
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteArchive(@PathVariable Long id) {
        return archiveService.deleteArchive(id);
    }
    
    /**
     * 查询归档详情
     */
    @GetMapping("/detail/{id}")
    public Result<NoteArchiveDTO> getArchiveDetail(@PathVariable Long id) {
        return archiveService.getArchiveDetail(id);
    }
    
    /**
     * 查询归档列表
     */
    @GetMapping("/list")
    public Result<List<NoteArchiveDTO>> getArchiveList(
            @RequestParam(required = false) String archiveName,
            @RequestParam(required = false) String archiveType) {
        return archiveService.getArchiveList(archiveName, archiveType);
    }
    
    /**
     * 添加笔记到归档
     */
    @PostMapping("/add-note")
    public Result<Long> addNoteToArchive(
            @RequestParam Long archiveId,
            @RequestParam Long noteId,
            @RequestParam(required = false) String relationNote) {
        return archiveService.addNoteToArchive(archiveId, noteId, relationNote);
    }
    
    /**
     * 从归档中移除笔记
     */
    @DeleteMapping("/remove-note")
    public Result<Void> removeNoteFromArchive(
            @RequestParam Long relationId,
            @RequestParam Long archiveId) {
        return archiveService.removeNoteFromArchive(relationId, archiveId);
    }
    
    /**
     * 更新关联备注
     */
    @PostMapping("/update-relation-note")
    public Result<Void> updateRelationNote(
            @RequestParam Long relationId,
            @RequestParam String relationNote) {
        return archiveService.updateRelationNote(relationId, relationNote);
    }
    
    /**
     * 查询笔记关联的所有归档
     */
    @GetMapping("/by-note/{noteId}")
    public Result<List<Long>> getArchivesByNoteId(@PathVariable Long noteId) {
        return archiveService.getArchivesByNoteId(noteId);
    }
}

