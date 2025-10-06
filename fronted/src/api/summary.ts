/**
 * 系统总结相关API
 */
import request from './request'

// 系统总结接口类型定义
export interface Summary {
  id?: number
  title: string
  type: string // daily-日报, monthly-月报, quarterly-季报, yearly-年报
  period: string
  description?: string
  content?: string // JSON格式的内容
  satisfaction?: number
  tags?: string
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
}

/**
 * 获取所有总结
 */
export const getAllSummaries = () => {
  return request({
    url: '/summary/list',
    method: 'get'
  })
}

/**
 * 根据ID获取总结
 */
export const getSummaryById = (id: number) => {
  return request({
    url: `/summary/${id}`,
    method: 'get'
  })
}

/**
 * 根据类型获取总结
 */
export const getSummariesByType = (type: string) => {
  return request({
    url: `/summary/type/${type}`,
    method: 'get'
  })
}

/**
 * 创建总结
 */
export const createSummary = (summary: Summary) => {
  return request({
    url: '/summary',
    method: 'post',
    data: summary
  })
}

/**
 * 更新总结
 */
export const updateSummary = (summary: Summary) => {
  return request({
    url: '/summary',
    method: 'put',
    data: summary
  })
}

/**
 * 删除总结
 */
export const deleteSummary = (id: number) => {
  return request({
    url: `/summary/${id}`,
    method: 'delete'
  })
}





