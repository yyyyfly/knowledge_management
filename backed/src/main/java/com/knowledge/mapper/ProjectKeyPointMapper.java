package com.knowledge.mapper;

import com.knowledge.entity.ProjectKeyPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目关键备忘 Mapper 接口
 */
@Mapper
public interface ProjectKeyPointMapper {
    
    /**
     * 插入关键备忘
     */
    int insertSelective(ProjectKeyPoint keyPoint);
    
    /**
     * 根据ID删除
     */
    int deleteById(Long id);
    
    /**
     * 根据项目ID删除所有备忘
     */
    int deleteByProjectId(Long projectId);
    
    /**
     * 更新关键备忘
     */
    int updateByIdSelective(ProjectKeyPoint keyPoint);
    
    /**
     * 根据ID查询
     */
    ProjectKeyPoint selectById(Long id);
    
    /**
     * 根据项目ID查询所有备忘
     */
    List<ProjectKeyPoint> selectByProjectId(Long projectId);
    
    /**
     * 根据项目ID和类型查询
     */
    List<ProjectKeyPoint> selectByProjectIdAndType(@Param("projectId") Long projectId, 
                                                    @Param("type") String type);
    
    /**
     * 统计项目的备忘数量
     */
    int countByProjectId(Long projectId);
}



















