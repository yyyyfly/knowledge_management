package com.knowledge.service;

import com.knowledge.entity.CheckinItem;
import com.knowledge.entity.CheckinRecord;
import com.knowledge.mapper.CheckinItemMapper;
import com.knowledge.mapper.CheckinRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

/**
 * 打卡记录Service
 */
@Service
public class CheckinRecordService {

    @Autowired
    private CheckinRecordMapper checkinRecordMapper;
    
    @Autowired
    private CheckinItemMapper checkinItemMapper;

    /**
     * 打卡
     */
    @Transactional
    public CheckinRecord checkin(Long checkinItemId, String username) {
        // 查询打卡项目
        CheckinItem checkinItem = checkinItemMapper.selectById(checkinItemId);
        if (checkinItem == null) {
            throw new RuntimeException("打卡项目不存在");
        }
        
        // 计算当前周期标识
        String cycleKey = calculateCycleKey(checkinItem.getFrequency());
        
        // 检查是否已打卡
        CheckinRecord existingRecord = checkinRecordMapper.selectByItemIdAndCycleKey(checkinItemId, username, cycleKey);
        if (existingRecord != null) {
            throw new RuntimeException("本周期已打卡");
        }
        
        // 创建打卡记录
        CheckinRecord record = new CheckinRecord();
        record.setCheckinItemId(checkinItemId);
        record.setUsername(username);
        record.setCheckinTime(LocalDateTime.now());
        record.setCycleKey(cycleKey);
        record.setRecCreator(username);
        record.setRecRevisor(username);
        
        checkinRecordMapper.insert(record);
        return record;
    }

    /**
     * 取消打卡
     */
    @Transactional
    public void cancelCheckin(Long checkinItemId, String username) {
        // 查询打卡项目
        CheckinItem checkinItem = checkinItemMapper.selectById(checkinItemId);
        if (checkinItem == null) {
            throw new RuntimeException("打卡项目不存在");
        }
        
        // 计算当前周期标识
        String cycleKey = calculateCycleKey(checkinItem.getFrequency());
        
        // 删除本周期的打卡记录（物理删除）
        checkinRecordMapper.deleteByCycleKey(checkinItemId, username, cycleKey);
    }

    /**
     * 检查是否已打卡
     */
    public boolean isCheckedIn(Long checkinItemId, String username, String frequency) {
        String cycleKey = calculateCycleKey(frequency);
        CheckinRecord record = checkinRecordMapper.selectByItemIdAndCycleKey(checkinItemId, username, cycleKey);
        return record != null;
    }

    /**
     * 获取打卡项目的所有记录
     */
    public List<CheckinRecord> getRecordsByItemId(Long checkinItemId) {
        return checkinRecordMapper.selectByCheckinItemId(checkinItemId);
    }

    /**
     * 计算周期标识
     */
    private String calculateCycleKey(String frequency) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter;
        
        switch (frequency) {
            case "daily":
                // 格式：2025-10-11
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return now.format(formatter);
                
            case "weekly":
                // 格式：2025-W41
                int weekOfYear = now.get(WeekFields.of(Locale.CHINA).weekOfWeekBasedYear());
                int year = now.get(WeekFields.of(Locale.CHINA).weekBasedYear());
                return String.format("%d-W%02d", year, weekOfYear);
                
            case "monthly":
                // 格式：2025-10
                formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                return now.format(formatter);
                
            case "quarterly":
                // 格式：2025-Q4
                int quarter = (now.getMonthValue() - 1) / 3 + 1;
                return String.format("%d-Q%d", now.getYear(), quarter);
                
            case "yearly":
                // 格式：2025
                return String.valueOf(now.getYear());
                
            default:
                throw new RuntimeException("不支持的频率类型: " + frequency);
        }
    }
}

