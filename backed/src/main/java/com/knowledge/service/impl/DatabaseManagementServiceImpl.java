package com.knowledge.service.impl;

import com.knowledge.mapper.DatabaseManagementMapper;
import com.knowledge.service.DatabaseManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 数据库管理服务实现
 * 
 * @author system
 * @date 2025-11-11
 */
@Service
public class DatabaseManagementServiceImpl implements DatabaseManagementService {
    
    @Autowired
    private DatabaseManagementMapper databaseManagementMapper;
    
    // 系统保护表列表，不允许删除
    private static final Set<String> PROTECTED_TABLES = new HashSet<>(Arrays.asList(
        "sys_user"
    ));
    
    // 危险SQL关键字
    private static final List<String> DANGEROUS_KEYWORDS = Arrays.asList(
        "DROP DATABASE", "TRUNCATE DATABASE", "DROP SCHEMA", "GRANT", "REVOKE"
    );
    
    @Override
    public List<String> getAllTables() {
        return databaseManagementMapper.getAllTables();
    }
    
    @Override
    public List<Map<String, Object>> getTableStructure(String tableName) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        return databaseManagementMapper.getTableStructure(tableName);
    }
    
    @Override
    public String getCreateTableSQL(String tableName) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        Map<String, Object> result = databaseManagementMapper.getCreateTableSQL(tableName);
        // SHOW CREATE TABLE 返回两列：Table 和 Create Table
        // MySQL 5.7和8.0的列名略有不同，需要兼容处理
        // 可能的列名：Create Table, CREATE TABLE, Create View
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            String key = entry.getKey();
            // 跳过表名列，查找建表语句列
            if (!key.equalsIgnoreCase("Table") && !key.equalsIgnoreCase("View")) {
                return (String) entry.getValue();
            }
        }
        return result.toString();
    }
    
    @Override
    public Map<String, Object> getTableInfo(String tableName) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        return databaseManagementMapper.getTableInfo(tableName);
    }
    
    @Override
    public void createTable(String createSQL) {
        validateSQL(createSQL);
        if (!createSQL.trim().toUpperCase().startsWith("CREATE TABLE")) {
            throw new RuntimeException("只允许执行CREATE TABLE语句");
        }
        databaseManagementMapper.executeSQL(createSQL);
    }
    
    @Override
    public void dropTable(String tableName) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        if (PROTECTED_TABLES.contains(tableName.toLowerCase())) {
            throw new RuntimeException("不允许删除系统保护表：" + tableName);
        }
        String sql = "DROP TABLE `" + tableName + "`";
        databaseManagementMapper.executeSQL(sql);
    }
    
    @Override
    public void addColumn(String tableName, String columnDefinition) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        validateColumnDefinition(columnDefinition);
        String sql = "ALTER TABLE `" + tableName + "` ADD COLUMN " + columnDefinition;
        databaseManagementMapper.executeSQL(sql);
    }
    
    @Override
    public void dropColumn(String tableName, String columnName) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        validateColumnName(columnName);
        String sql = "ALTER TABLE `" + tableName + "` DROP COLUMN `" + columnName + "`";
        databaseManagementMapper.executeSQL(sql);
    }
    
    @Override
    public void modifyColumn(String tableName, String columnDefinition) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        validateColumnDefinition(columnDefinition);
        String sql = "ALTER TABLE `" + tableName + "` MODIFY COLUMN " + columnDefinition;
        databaseManagementMapper.executeSQL(sql);
    }
    
    @Override
    public void renameTable(String oldName, String newName) {
        validateTableNameFormat(oldName);
        validateTableNameFormat(newName);
        // 验证旧表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(oldName)) {
            throw new RuntimeException("表不存在：" + oldName);
        }
        if (PROTECTED_TABLES.contains(oldName.toLowerCase())) {
            throw new RuntimeException("不允许重命名系统保护表：" + oldName);
        }
        String sql = "RENAME TABLE `" + oldName + "` TO `" + newName + "`";
        databaseManagementMapper.executeSQL(sql);
    }
    
    @Override
    public Map<String, Object> queryTableData(String tableName, Integer page, Integer pageSize) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        
        // 计算偏移量
        int offset = (page - 1) * pageSize;
        
        // 查询数据
        List<Map<String, Object>> data = databaseManagementMapper.queryTableData(tableName, offset, pageSize);
        
        // 统计总数
        Integer total = databaseManagementMapper.countTableData(tableName);
        
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("total", total);
        result.put("page", page);
        result.put("pageSize", pageSize);
        
        return result;
    }
    
    @Override
    public void insertRecord(String tableName, Map<String, Object> data) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        if (data == null || data.isEmpty()) {
            throw new RuntimeException("数据不能为空");
        }
        
        // 构建INSERT SQL
        StringBuilder sql = new StringBuilder("INSERT INTO `").append(tableName).append("` (");
        StringBuilder values = new StringBuilder(" VALUES (");
        
        int index = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (index > 0) {
                sql.append(", ");
                values.append(", ");
            }
            sql.append("`").append(entry.getKey()).append("`");
            values.append(formatValue(entry.getValue()));
            index++;
        }
        
        sql.append(")").append(values).append(")");
        
        databaseManagementMapper.insertRecord(sql.toString());
    }
    
    @Override
    public void updateRecord(String tableName, Map<String, Object> data) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        if (data == null || data.isEmpty()) {
            throw new RuntimeException("数据不能为空");
        }
        
        // 获取主键列名
        String pkColumn = databaseManagementMapper.getPrimaryKeyColumn(tableName);
        if (pkColumn == null || pkColumn.isEmpty()) {
            throw new RuntimeException("表 " + tableName + " 没有主键，无法更新");
        }
        
        // 获取主键值
        Object pkValue = data.get(pkColumn);
        if (pkValue == null) {
            throw new RuntimeException("缺少主键值");
        }
        
        // 构建UPDATE SQL
        StringBuilder sql = new StringBuilder("UPDATE `").append(tableName).append("` SET ");
        
        int index = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getKey().equals(pkColumn)) {
                continue; // 跳过主键
            }
            if (index > 0) {
                sql.append(", ");
            }
            sql.append("`").append(entry.getKey()).append("` = ").append(formatValue(entry.getValue()));
            index++;
        }
        
        sql.append(" WHERE `").append(pkColumn).append("` = ").append(formatValue(pkValue));
        
        databaseManagementMapper.updateRecord(sql.toString());
    }
    
    @Override
    public void deleteRecord(String tableName, String pkColumn, Object pkValue) {
        validateTableNameFormat(tableName);
        // 验证表是否存在
        List<String> tables = databaseManagementMapper.getAllTables();
        if (!tables.contains(tableName)) {
            throw new RuntimeException("表不存在：" + tableName);
        }
        validateColumnName(pkColumn);
        if (pkValue == null) {
            throw new RuntimeException("主键值不能为空");
        }
        databaseManagementMapper.deleteRecord(tableName, pkColumn, pkValue);
    }
    
    // ========== 私有验证方法 ==========
    
    /**
     * 验证表名格式
     */
    private void validateTableNameFormat(String tableName) {
        if (tableName == null || tableName.trim().isEmpty()) {
            throw new RuntimeException("表名不能为空");
        }
        
        // 检查表名格式（只允许字母、数字、下划线）
        if (!Pattern.matches("^[a-zA-Z0-9_]+$", tableName)) {
            throw new RuntimeException("表名格式不合法");
        }
    }
    
    /**
     * 验证列名
     */
    private void validateColumnName(String columnName) {
        if (columnName == null || columnName.trim().isEmpty()) {
            throw new RuntimeException("列名不能为空");
        }
        if (!Pattern.matches("^[a-zA-Z0-9_]+$", columnName)) {
            throw new RuntimeException("列名格式不合法");
        }
    }
    
    /**
     * 验证列定义
     */
    private void validateColumnDefinition(String columnDefinition) {
        if (columnDefinition == null || columnDefinition.trim().isEmpty()) {
            throw new RuntimeException("列定义不能为空");
        }
    }
    
    /**
     * 验证SQL语句
     */
    private void validateSQL(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            throw new RuntimeException("SQL语句不能为空");
        }
        
        String upperSQL = sql.toUpperCase();
        
        // 检查危险关键字
        for (String keyword : DANGEROUS_KEYWORDS) {
            if (upperSQL.contains(keyword)) {
                throw new RuntimeException("不允许执行包含危险关键字的SQL: " + keyword);
            }
        }
        
        // 禁止多条SQL语句
        if (sql.split(";").length > 1) {
            throw new RuntimeException("不允许执行多条SQL语句");
        }
    }
    
    /**
     * 格式化值为SQL字符串
     */
    private String formatValue(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof Number) {
            return value.toString();
        }
        // 字符串类型，需要转义单引号
        String strValue = value.toString().replace("'", "''");
        return "'" + strValue + "'";
    }
}

