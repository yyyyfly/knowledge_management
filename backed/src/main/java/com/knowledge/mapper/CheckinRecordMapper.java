package com.knowledge.mapper;

import com.knowledge.entity.CheckinRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 打卡记录Mapper接口
 */
@Mapper
public interface CheckinRecordMapper {
    
    /**
     * 插入打卡记录
     */
    int insert(CheckinRecord checkinRecord);
    
    /**
     * 根据ID查询
     */
    CheckinRecord selectById(@Param("id") Long id);
    
    /**
     * 根据用户ID查询所有打卡记录
     */
    List<CheckinRecord> selectByUserId(@Param("userId") String userId);
    
    /**
     * 根据打卡项目ID和周期标识查询
     */
    CheckinRecord selectByItemIdAndCycleKey(@Param("checkinItemId") Long checkinItemId, 
                                            @Param("userId") String userId,
                                            @Param("cycleKey") String cycleKey);
    
    /**
     * 根据打卡项目ID查询所有记录
     */
    List<CheckinRecord> selectByCheckinItemId(@Param("checkinItemId") Long checkinItemId);
    
    /**
     * 删除打卡记录
     */
    int deleteById(@Param("id") Long id, @Param("revisor") String revisor);
    
    /**
     * 根据打卡项目ID和周期标识删除（物理删除）
     */
    int deleteByCycleKey(@Param("checkinItemId") Long checkinItemId, 
                         @Param("userId") String userId,
                         @Param("cycleKey") String cycleKey);
}

