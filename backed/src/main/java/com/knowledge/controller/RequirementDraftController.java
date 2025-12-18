package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.RequirementDraft;
import com.knowledge.service.RequirementDraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 需求草案控制器
 *
 * @author system
 * @date 2025-11-16
 */
@RestController
@RequestMapping("/requirement-draft")
public class RequirementDraftController {
    
    @Autowired
    private RequirementDraftService requirementDraftService;
    
    /**
     * 获取所有需求草案（按版本分组）
     *
     * @return 需求草案列表
     */
    @GetMapping("/list")
    public Result<List<RequirementDraft>> getAllRequirementDrafts() {
        try {
            List<RequirementDraft> drafts = requirementDraftService.getAllRequirementDrafts();
            return Result.success(drafts);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取需求草案列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取需求草案
     *
     * @param id 需求草案ID
     * @return 需求草案
     */
    @GetMapping("/{id}")
    public Result<RequirementDraft> getRequirementDraftById(@PathVariable Long id) {
        try {
            RequirementDraft draft = requirementDraftService.getRequirementDraftById(id);
            if (draft == null) {
                return Result.error("需求草案不存在");
            }
            return Result.success(draft);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取需求草案失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取所有版本号列表
     *
     * @return 版本号列表
     */
    @GetMapping("/versions")
    public Result<List<String>> getVersionList() {
        try {
            List<String> versions = requirementDraftService.getVersionList();
            return Result.success(versions);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取版本列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据版本号获取需求草案
     *
     * @param version 版本号
     * @return 需求草案列表
     */
    @GetMapping("/version/{version}")
    public Result<List<RequirementDraft>> getRequirementDraftsByVersion(@PathVariable String version) {
        try {
            List<RequirementDraft> drafts = requirementDraftService.getRequirementDraftsByVersion(version);
            return Result.success(drafts);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取需求草案失败：" + e.getMessage());
        }
    }
    
    /**
     * 创建需求草案
     *
     * @param draft 需求草案
     * @return 创建成功的需求草案
     */
    @PostMapping
    public Result<RequirementDraft> createRequirementDraft(@RequestBody RequirementDraft draft) {
        try {
            // 验证必填字段
            if (draft.getVersion() == null || draft.getVersion().trim().isEmpty()) {
                return Result.error("版本号不能为空");
            }
            if (draft.getTitle() == null || draft.getTitle().trim().isEmpty()) {
                return Result.error("标题不能为空");
            }
            
            RequirementDraft created = requirementDraftService.createRequirementDraft(draft);
            return Result.success("创建成功", created);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("创建需求草案失败：" + e.getMessage());
        }
    }
    
    /**
     * 更新需求草案
     *
     * @param draft 需求草案
     * @return 更新后的需求草案
     */
    @PutMapping
    public Result<RequirementDraft> updateRequirementDraft(@RequestBody RequirementDraft draft) {
        try {
            if (draft.getId() == null) {
                return Result.error("需求草案ID不能为空");
            }
            
            RequirementDraft updated = requirementDraftService.updateRequirementDraft(draft);
            return Result.success("更新成功", updated);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新需求草案失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除需求草案
     *
     * @param id 需求草案ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteRequirementDraft(@PathVariable Long id) {
        try {
            boolean success = requirementDraftService.deleteRequirementDraft(id);
            if (success) {
                return Result.success("删除成功", null);
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除需求草案失败：" + e.getMessage());
        }
    }
}

