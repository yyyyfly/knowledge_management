package com.knowledge.controller;

import com.knowledge.common.Result;
import com.knowledge.service.DatabaseManagementService;
import com.knowledge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据库管理控制器
 * 仅admin用户可访问
 * 
 * @author system
 * @date 2025-11-11
 */
@RestController
@RequestMapping("/database")
@CrossOrigin
public class DatabaseManagementController {
    
    @Autowired
    private DatabaseManagementService databaseManagementService;
    
    // ========== 表结构管理 ==========
    
    /**
     * 获取所有表名列表
     */
    @GetMapping("/tables")
    public Result<List<String>> getAllTables(@RequestHeader("Authorization") String token) {
        try {
            // 验证admin权限
            validateAdmin(token);
            
            List<String> tables = databaseManagementService.getAllTables();
            return Result.success(tables);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取表结构信息
     */
    @GetMapping("/structure")
    public Result<List<Map<String, Object>>> getTableStructure(
            @RequestParam String tableName,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            List<Map<String, Object>> structure = databaseManagementService.getTableStructure(tableName);
            return Result.success(structure);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取表详细信息
     */
    @GetMapping("/table-info")
    public Result<Map<String, Object>> getTableInfo(
            @RequestParam String tableName,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            Map<String, Object> info = databaseManagementService.getTableInfo(tableName);
            return Result.success(info);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取建表语句
     */
    @GetMapping("/create-sql")
    public Result<String> getCreateTableSQL(
            @RequestParam String tableName,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            String sql = databaseManagementService.getCreateTableSQL(tableName);
            return Result.success(sql);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 创建新表
     */
    @PostMapping("/table")
    public Result<String> createTable(
            @RequestBody Map<String, String> request,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            String createSQL = request.get("sql");
            databaseManagementService.createTable(createSQL);
            return Result.success("表创建成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 删除表
     */
    @DeleteMapping("/table")
    public Result<String> dropTable(
            @RequestParam String tableName,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            databaseManagementService.dropTable(tableName);
            return Result.success("表删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 添加列
     */
    @PostMapping("/table/add-column")
    public Result<String> addColumn(
            @RequestBody Map<String, String> request,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            String tableName = request.get("tableName");
            String columnDefinition = request.get("columnDefinition");
            
            databaseManagementService.addColumn(tableName, columnDefinition);
            return Result.success("列添加成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 删除列
     */
    @DeleteMapping("/table/drop-column")
    public Result<String> dropColumn(
            @RequestParam String tableName,
            @RequestParam String columnName,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            databaseManagementService.dropColumn(tableName, columnName);
            return Result.success("列删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 修改列
     */
    @PutMapping("/table/modify-column")
    public Result<String> modifyColumn(
            @RequestBody Map<String, String> request,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            String tableName = request.get("tableName");
            String columnDefinition = request.get("columnDefinition");
            
            databaseManagementService.modifyColumn(tableName, columnDefinition);
            return Result.success("列修改成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 重命名表
     */
    @PutMapping("/table/rename")
    public Result<String> renameTable(
            @RequestBody Map<String, String> request,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            String oldName = request.get("oldName");
            String newName = request.get("newName");
            
            databaseManagementService.renameTable(oldName, newName);
            return Result.success("表重命名成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    // ========== 数据管理 ==========
    
    /**
     * 查询表数据（分页）
     */
    @GetMapping("/data")
    public Result<Map<String, Object>> queryTableData(
            @RequestParam String tableName,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            Map<String, Object> result = databaseManagementService.queryTableData(tableName, page, pageSize);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 新增记录
     */
    @PostMapping("/data")
    public Result<String> insertRecord(
            @RequestBody Map<String, Object> request,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            String tableName = (String) request.get("tableName");
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) request.get("data");
            
            databaseManagementService.insertRecord(tableName, data);
            return Result.success("记录添加成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 更新记录
     */
    @PutMapping("/data")
    public Result<String> updateRecord(
            @RequestBody Map<String, Object> request,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            String tableName = (String) request.get("tableName");
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) request.get("data");
            
            databaseManagementService.updateRecord(tableName, data);
            return Result.success("记录更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 删除记录
     */
    @DeleteMapping("/data")
    public Result<String> deleteRecord(
            @RequestParam String tableName,
            @RequestParam String pkColumn,
            @RequestParam String pkValue,
            @RequestHeader("Authorization") String token) {
        try {
            validateAdmin(token);
            
            databaseManagementService.deleteRecord(tableName, pkColumn, pkValue);
            return Result.success("记录删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    // ========== 私有方法 ==========
    
    /**
     * 验证admin权限
     */
    private void validateAdmin(String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("未登录或登录已过期");
        }
        
        // 去掉 "Bearer " 前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        // 验证Token并获取用户名
        if (!JwtUtil.validateToken(token)) {
            throw new RuntimeException("Token无效或已过期");
        }
        
        String username = JwtUtil.getUsernameFromToken(token);
        
        // 验证是否为admin用户
        if (!"admin".equals(username)) {
            throw new RuntimeException("权限不足，只有管理员可以访问");
        }
    }
}


