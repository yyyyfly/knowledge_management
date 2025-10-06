/**
 * 统一的API响应类型定义
 */

// 统一响应格式
export interface ApiResponse<T = any> {
  code: number
  message?: string
  data: T
}

// 分页响应
export interface PageResponse<T> {
  code: number
  message?: string
  data: {
    list: T[]
    total: number
    pageNum: number
    pageSize: number
  }
}

// 通用实体基类
export interface BaseEntity {
  id?: number
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
  archFlag?: number
}





