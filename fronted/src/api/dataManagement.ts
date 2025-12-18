import request from './request'

// ========== 表结构管理 ==========

/**
 * 获取所有表名列表
 */
export const getAllTables = () => {
  return request.get('/database/tables')
}

/**
 * 获取表结构信息
 */
export const getTableStructure = (tableName: string) => {
  return request.get('/database/structure', { params: { tableName } })
}

/**
 * 获取表详细信息
 */
export const getTableInfo = (tableName: string) => {
  return request.get('/database/table-info', { params: { tableName } })
}

/**
 * 获取建表语句
 */
export const getCreateTableSQL = (tableName: string) => {
  return request.get('/database/create-sql', { params: { tableName } })
}

/**
 * 创建新表
 */
export const createTable = (sql: string) => {
  return request.post('/database/table', { sql })
}

/**
 * 删除表
 */
export const dropTable = (tableName: string) => {
  return request.delete('/database/table', { params: { tableName } })
}

/**
 * 添加列
 */
export const addColumn = (tableName: string, columnDefinition: string) => {
  return request.post('/database/table/add-column', { tableName, columnDefinition })
}

/**
 * 删除列
 */
export const dropColumn = (tableName: string, columnName: string) => {
  return request.delete('/database/table/drop-column', { params: { tableName, columnName } })
}

/**
 * 修改列
 */
export const modifyColumn = (tableName: string, columnDefinition: string) => {
  return request.put('/database/table/modify-column', { tableName, columnDefinition })
}

/**
 * 重命名表
 */
export const renameTable = (oldName: string, newName: string) => {
  return request.put('/database/table/rename', { oldName, newName })
}

// ========== 数据管理 ==========

/**
 * 查询表数据（分页）
 */
export const queryTableData = (tableName: string, page: number = 1, pageSize: number = 20) => {
  return request.get('/database/data', { params: { tableName, page, pageSize } })
}

/**
 * 新增记录
 */
export const insertRecord = (tableName: string, data: Record<string, any>) => {
  return request.post('/database/data', { tableName, data })
}

/**
 * 更新记录
 */
export const updateRecord = (tableName: string, data: Record<string, any>) => {
  return request.put('/database/data', { tableName, data })
}

/**
 * 删除记录
 */
export const deleteRecord = (tableName: string, pkColumn: string, pkValue: string) => {
  return request.delete('/database/data', { params: { tableName, pkColumn, pkValue } })
}











