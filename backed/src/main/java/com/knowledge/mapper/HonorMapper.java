package com.knowledge.mapper;

import com.knowledge.entity.Honor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 荣誉战绩Mapper接口
 *
 * @author system
 * @date 2025-10-04
 */
@Mapper
public interface HonorMapper {
    
    /**
     * 根据ID查询荣誉
     */
    Honor selectById(@Param("id") Long id, @Param("recCreator") String recCreator);
    
    /**
     * 查询所有荣誉
     */
    List<Honor> selectAll(@Param("recCreator") String recCreator);
    
    /**
     * 根据类别查询荣誉
     */
    List<Honor> selectByCategory(@Param("category") String category, @Param("recCreator") String recCreator);
    
    /**
     * 插入荣誉
     */
    int insert(Honor honor);
    
    /**
     * 更新荣誉
     */
    int update(Honor honor);
    
    /**
     * 删除荣誉
     */
    int deleteById(@Param("id") Long id);
}

