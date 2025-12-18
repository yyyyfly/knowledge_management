package com.knowledge.mapper;

import com.knowledge.entity.CustomPractice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定制模拟实战Mapper接口
 *
 * @author system
 * @date 2025-01-15
 */
@Mapper
public interface CustomPracticeMapper {
    
    /**
     * 根据ID查询定制模拟实战
     */
    CustomPractice selectById(@Param("id") Long id, @Param("recCreator") String recCreator);
    
    /**
     * 查询所有定制模拟实战
     */
    List<CustomPractice> selectAll(@Param("recCreator") String recCreator);
    
    /**
     * 根据场景类型查询定制模拟实战
     */
    List<CustomPractice> selectByScenarioType(@Param("scenarioType") String scenarioType, @Param("recCreator") String recCreator);
    
    /**
     * 插入定制模拟实战
     */
    int insert(CustomPractice customPractice);
    
    /**
     * 更新定制模拟实战
     */
    int update(CustomPractice customPractice);
    
    /**
     * 删除定制模拟实战（软删除）
     */
    int deleteById(@Param("id") Long id);
}

