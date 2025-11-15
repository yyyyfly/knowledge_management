package com.knowledge.service;

import java.util.List;
import java.util.Map;

/**
 * 数据库管理服务接口
 * 
 * @author system
 * @date 2025-11-11
 */
public interface DatabaseManagementService {
    
    // ========== 表结构操作 ==========
    
    /**
     * 获取所有表名
     */
    List<String> getAllTables();
    
    /**
     * 获取表结构信息
     */
    List<Map<String, Object>> getTableStructure(String tableName);
    
    /**
     * 获取建表语句
     */
    String getCreateTableSQL(String tableName);
    
    /**
     * 获取表详细信息
     */
    Map<String, Object> getTableInfo(String tableName);
    
    /**
     * 创建新表
     */
    void createTable(String createSQL);
    
    /**
     * 删除表
     */
    void dropTable(String tableName);
    
    /**
     * 添加列
     */
    void addColumn(String tableName, String columnDefinition);
    
    /**
     * 删除列
     */
    void dropColumn(String tableName, String columnName);
    
    /**
     * 修改列
     */
    void modifyColumn(String tableName, String columnDefinition);
    
    /**
     * 重命名表
     */
    void renameTable(String oldName, String newName);
    
    // ========== 数据操作 ==========
    
    /**
     * 分页查询表数据
     */
    Map<String, Object> queryTableData(String tableName, Integer page, Integer pageSize);
    
    /**
     * 新增记录
     */
    void insertRecord(String tableName, Map<String, Object> data);
    
    /**
     * 更新记录
     */
    void updateRecord(String tableName, Map<String, Object> data);
    
    /**
     * 删除记录
     */
    void deleteRecord(String tableName, String pkColumn, Object pkValue);
}



