package com.knowledge.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据库管理Mapper
 *
 * @author system
 * @date 2025-11-11
 */
@Mapper
public interface DatabaseManagementMapper {
    
    /**
     * 获取所有表名
     */
    List<String> getAllTables();
    
    /**
     * 获取表结构信息
     */
    List<Map<String, Object>> getTableStructure(@Param("tableName") String tableName);
    
    /**
     * 获取表信息
     */
    Map<String, Object> getTableInfo(@Param("tableName") String tableName);
    
    /**
     * 获取建表SQL
     */
    Map<String, Object> getCreateTableSQL(@Param("tableName") String tableName);
    
    /**
     * 执行DDL语句（CREATE、DROP、ALTER等）
     */
    void executeSQL(@Param("sql") String sql);
    
    /**
     * 查询表数据
     */
    List<Map<String, Object>> queryTableData(
            @Param("tableName") String tableName,
            @Param("offset") int offset,
            @Param("limit") int limit
    );
    
    /**
     * 统计表数据总数
     */
    int countTableData(@Param("tableName") String tableName);
    
    /**
     * 获取表的主键列名
     */
    String getPrimaryKeyColumn(@Param("tableName") String tableName);
    
    /**
     * 插入记录（使用构建好的SQL）
     */
    void insertRecord(@Param("insertSQL") String insertSQL);
    
    /**
     * 更新记录（使用构建好的SQL）
     */
    void updateRecord(@Param("updateSQL") String updateSQL);
    
    /**
     * 删除记录
     */
    void deleteRecord(
            @Param("tableName") String tableName,
            @Param("pkColumn") String pkColumn,
            @Param("pkValue") Object pkValue
    );
}


