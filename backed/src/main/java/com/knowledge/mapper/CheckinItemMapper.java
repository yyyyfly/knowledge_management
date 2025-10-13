package com.knowledge.mapper;

import com.knowledge.entity.CheckinItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 打卡项目Mapper接口
 */
@Mapper
public interface CheckinItemMapper {
    
    /**
     * 插入打卡项目
     */
    int insert(CheckinItem checkinItem);
    
    /**
     * 根据ID查询
     */
    CheckinItem selectById(@Param("id") Long id);
    
    /**
     * 根据用户ID查询所有打卡项目
     */
    List<CheckinItem> selectByUserId(@Param("userId") String userId);
    
    /**
     * 根据用户ID和状态查询
     */
    List<CheckinItem> selectByUserIdAndStatus(@Param("userId") String userId, @Param("status") String status);
    
    /**
     * 更新打卡项目
     */
    int update(CheckinItem checkinItem);
    
    /**
     * 根据ID删除（逻辑删除）
     */
    int deleteById(@Param("id") Long id, @Param("revisor") String revisor);
}

