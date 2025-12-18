package com.knowledge.service;

import com.knowledge.common.Result;
import com.knowledge.entity.CustomPractice;
import com.knowledge.mapper.CustomPracticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定制模拟实战服务类
 *
 * @author system
 * @date 2025-01-15
 */
@Service
public class CustomPracticeService {
    
    @Autowired
    private CustomPracticeMapper customPracticeMapper;
    
    /**
     * 查询所有定制模拟实战
     */
    public Result<List<CustomPractice>> getAllCustomPractices(String username) {
        try {
            List<CustomPractice> practices = customPracticeMapper.selectAll(username);
            return Result.success(practices);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询定制模拟实战失败");
        }
    }
    
    /**
     * 根据ID查询定制模拟实战
     */
    public Result<CustomPractice> getCustomPracticeById(Long id, String username) {
        try {
            CustomPractice practice = customPracticeMapper.selectById(id, username);
            if (practice == null) {
                return Result.error("定制模拟实战不存在");
            }
            return Result.success(practice);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询定制模拟实战失败");
        }
    }
    
    /**
     * 创建定制模拟实战
     */
    public Result<Long> createCustomPractice(CustomPractice customPractice) {
        try {
            customPracticeMapper.insert(customPractice);
            return Result.success(customPractice.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("创建定制模拟实战失败");
        }
    }
    
    /**
     * 更新定制模拟实战
     */
    public Result<String> updateCustomPractice(CustomPractice customPractice) {
        try {
            int rows = customPracticeMapper.update(customPractice);
            if (rows > 0) {
                return Result.success("更新成功");
            }
            return Result.error("更新失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新定制模拟实战失败");
        }
    }
    
    /**
     * 删除定制模拟实战
     */
    public Result<String> deleteCustomPractice(Long id) {
        try {
            int rows = customPracticeMapper.deleteById(id);
            if (rows > 0) {
                return Result.success("删除成功");
            }
            return Result.error("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除定制模拟实战失败");
        }
    }
}








