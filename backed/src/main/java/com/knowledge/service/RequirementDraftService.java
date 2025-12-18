package com.knowledge.service;

import com.knowledge.entity.RequirementDraft;
import com.knowledge.mapper.RequirementDraftMapper;
import com.knowledge.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 需求草案服务类
 *
 * @author system
 * @date 2025-11-16
 */
@Service
public class RequirementDraftService {
    
    @Autowired
    private RequirementDraftMapper requirementDraftMapper;
    
    /**
     * 查询所有需求草案（按版本分组）
     *
     * @return 需求草案列表
     */
    public List<RequirementDraft> getAllRequirementDrafts() {
        return requirementDraftMapper.selectAll();
    }
    
    /**
     * 根据ID查询需求草案
     *
     * @param id 需求草案ID
     * @return 需求草案
     */
    public RequirementDraft getRequirementDraftById(Long id) {
        return requirementDraftMapper.selectById(id);
    }
    
    /**
     * 根据版本号查询需求草案
     *
     * @param version 版本号
     * @return 需求草案列表
     */
    public List<RequirementDraft> getRequirementDraftsByVersion(String version) {
        return requirementDraftMapper.selectByVersion(version);
    }
    
    /**
     * 查询所有版本号列表
     *
     * @return 版本号列表
     */
    public List<String> getVersionList() {
        return requirementDraftMapper.selectVersionList();
    }
    
    /**
     * 创建需求草案
     *
     * @param draft 需求草案
     * @return 创建成功的需求草案
     */
    @Transactional(rollbackFor = Exception.class)
    public RequirementDraft createRequirementDraft(RequirementDraft draft) {
        String username = UserContext.getCurrentUser();
        Date now = new Date();
        
        draft.setRecCreator(username);
        draft.setRecCreateTime(now);
        draft.setRecRevisor(username);
        draft.setRecReviseTime(now);
        draft.setArchFlag(0);
        
        // 如果没有设置排序号，默认为0
        if (draft.getSortOrder() == null) {
            draft.setSortOrder(0);
        }
        
        // 如果没有设置状态，默认为草拟中
        if (draft.getStatus() == null || draft.getStatus().trim().isEmpty()) {
            draft.setStatus("drafting");
        }
        
        requirementDraftMapper.insert(draft);
        return draft;
    }
    
    /**
     * 更新需求草案
     *
     * @param draft 需求草案
     * @return 更新后的需求草案
     */
    @Transactional(rollbackFor = Exception.class)
    public RequirementDraft updateRequirementDraft(RequirementDraft draft) {
        String username = UserContext.getCurrentUser();
        Date now = new Date();
        
        draft.setRecRevisor(username);
        draft.setRecReviseTime(now);
        
        requirementDraftMapper.update(draft);
        return requirementDraftMapper.selectById(draft.getId());
    }
    
    /**
     * 删除需求草案
     *
     * @param id 需求草案ID
     * @return 是否删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRequirementDraft(Long id) {
        return requirementDraftMapper.deleteById(id) > 0;
    }
}

