package com.knowledge.service;

import com.knowledge.entity.CheckinItem;
import com.knowledge.mapper.CheckinItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 打卡项目Service
 */
@Service
public class CheckinItemService {

    @Autowired
    private CheckinItemMapper checkinItemMapper;

    /**
     * 创建打卡项目
     */
    @Transactional
    public CheckinItem createCheckinItem(CheckinItem checkinItem) {
        checkinItemMapper.insert(checkinItem);
        return checkinItem;
    }

    /**
     * 根据ID查询
     */
    public CheckinItem getById(Long id) {
        return checkinItemMapper.selectById(id);
    }

    /**
     * 根据用户ID查询所有打卡项目
     */
    public List<CheckinItem> getByUserId(String userId) {
        return checkinItemMapper.selectByUserId(userId);
    }

    /**
     * 根据用户ID和状态查询
     */
    public List<CheckinItem> getByUserIdAndStatus(String userId, String status) {
        return checkinItemMapper.selectByUserIdAndStatus(userId, status);
    }

    /**
     * 更新打卡项目
     */
    @Transactional
    public CheckinItem updateCheckinItem(CheckinItem checkinItem) {
        checkinItemMapper.update(checkinItem);
        return checkinItem;
    }

    /**
     * 删除打卡项目
     */
    @Transactional
    public void deleteById(Long id, String revisor) {
        checkinItemMapper.deleteById(id, revisor);
    }
}

