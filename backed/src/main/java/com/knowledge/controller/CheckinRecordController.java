package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.CheckinRecord;
import com.knowledge.service.CheckinRecordService;
import com.knowledge.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 打卡记录Controller
 */
@RestController
@RequestMapping("/checkin/record")
public class CheckinRecordController {

    @Autowired
    private CheckinRecordService checkinRecordService;

    /**
     * 打卡
     */
    @PostMapping("/checkin/{itemId}")
    public Result<CheckinRecord> checkin(@PathVariable Long itemId) {
        try {
            String username = UserContext.getCurrentUser();
            CheckinRecord record = checkinRecordService.checkin(itemId, username);
            return Result.success(record);
        } catch (Exception e) {
            return Result.error("打卡失败：" + e.getMessage());
        }
    }

    /**
     * 取消打卡
     */
    @DeleteMapping("/cancel/{itemId}")
    public Result<Void> cancelCheckin(@PathVariable Long itemId) {
        try {
            String username = UserContext.getCurrentUser();
            checkinRecordService.cancelCheckin(itemId, username);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("取消打卡失败：" + e.getMessage());
        }
    }

    /**
     * 检查是否已打卡
     */
    @GetMapping("/status/{itemId}")
    public Result<Map<String, Boolean>> checkStatus(@PathVariable Long itemId, 
                                                     @RequestParam String frequency) {
        try {
            String username = UserContext.getCurrentUser();
            boolean isCheckedIn = checkinRecordService.isCheckedIn(itemId, username, frequency);
            
            Map<String, Boolean> result = new HashMap<>();
            result.put("isCheckedIn", isCheckedIn);
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("检查打卡状态失败：" + e.getMessage());
        }
    }

    /**
     * 获取打卡项目的所有记录
     */
    @GetMapping("/list/{itemId}")
    public Result<List<CheckinRecord>> getRecords(@PathVariable Long itemId) {
        try {
            List<CheckinRecord> records = checkinRecordService.getRecordsByItemId(itemId);
            return Result.success(records);
        } catch (Exception e) {
            return Result.error("获取打卡记录失败：" + e.getMessage());
        }
    }
}

