package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.entity.CheckinItem;
import com.knowledge.service.CheckinItemService;
import com.knowledge.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 打卡项目Controller
 */
@RestController
@RequestMapping("/checkin/item")
public class CheckinItemController {

    @Autowired
    private CheckinItemService checkinItemService;

    /**
     * 创建打卡项目
     */
    @PostMapping
    public Result<CheckinItem> create(@RequestBody CheckinItem checkinItem) {
        try {
            String username = UserContext.getCurrentUser();
            checkinItem.setUsername(username);
            checkinItem.setRecCreator(username);
            checkinItem.setRecRevisor(username);
            checkinItem.setStatus("active"); // 默认启用
            
            CheckinItem created = checkinItemService.createCheckinItem(checkinItem);
            return Result.success(created);
        } catch (Exception e) {
            return Result.error("创建打卡项目失败：" + e.getMessage());
        }
    }

    /**
     * 获取当前用户的所有打卡项目
     */
    @GetMapping("/list")
    public Result<List<CheckinItem>> list() {
        try {
            String username = UserContext.getCurrentUser();
            List<CheckinItem> items = checkinItemService.getByUserId(username);
            return Result.success(items);
        } catch (Exception e) {
            return Result.error("获取打卡项目列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据状态获取打卡项目
     */
    @GetMapping("/list/{status}")
    public Result<List<CheckinItem>> listByStatus(@PathVariable String status) {
        try {
            String username = UserContext.getCurrentUser();
            List<CheckinItem> items = checkinItemService.getByUserIdAndStatus(username, status);
            return Result.success(items);
        } catch (Exception e) {
            return Result.error("获取打卡项目列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID获取打卡项目
     */
    @GetMapping("/{id}")
    public Result<CheckinItem> getById(@PathVariable Long id) {
        try {
            CheckinItem item = checkinItemService.getById(id);
            if (item == null) {
                return Result.error("打卡项目不存在");
            }
            return Result.success(item);
        } catch (Exception e) {
            return Result.error("获取打卡项目失败：" + e.getMessage());
        }
    }

    /**
     * 更新打卡项目
     */
    @PutMapping("/{id}")
    public Result<CheckinItem> update(@PathVariable Long id, @RequestBody CheckinItem checkinItem) {
        try {
            String username = UserContext.getCurrentUser();
            checkinItem.setId(id);
            checkinItem.setRecRevisor(username);
            
            CheckinItem updated = checkinItemService.updateCheckinItem(checkinItem);
            return Result.success(updated);
        } catch (Exception e) {
            return Result.error("更新打卡项目失败：" + e.getMessage());
        }
    }

    /**
     * 删除打卡项目
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            String username = UserContext.getCurrentUser();
            checkinItemService.deleteById(id, username);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除打卡项目失败：" + e.getMessage());
        }
    }
}

