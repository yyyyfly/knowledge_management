/**
 * 时间节点相关API
 */
import request from './request'

// 时间节点接口类型定义
export interface TimeNode {
  id?: number
  title: string
  description?: string
  time: string
  type: string // war-战争行动, diplomatic-外交活动
  status: string // not-started-未开始, upcoming-即将开始, ongoing-进行中, completed-已完成, overdue-已逾期, closed-已关闭
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
}

/**
 * 获取所有时间节点
 */
export const getAllTimeNodes = () => {
  return request({
    url: '/timenode/list',
    method: 'get'
  })
}

/**
 * 根据ID获取时间节点
 */
export const getTimeNodeById = (id: number) => {
  return request({
    url: `/timenode/${id}`,
    method: 'get'
  })
}

/**
 * 根据类型获取时间节点
 */
export const getTimeNodesByType = (type: string) => {
  return request({
    url: `/timenode/type/${type}`,
    method: 'get'
  })
}

/**
 * 根据状态获取时间节点
 */
export const getTimeNodesByStatus = (status: string) => {
  return request({
    url: `/timenode/status/${status}`,
    method: 'get'
  })
}

/**
 * 创建时间节点
 */
export const createTimeNode = (timeNode: TimeNode) => {
  return request({
    url: '/timenode',
    method: 'post',
    data: timeNode
  })
}

/**
 * 更新时间节点
 */
export const updateTimeNode = (timeNode: TimeNode) => {
  return request({
    url: '/timenode',
    method: 'put',
    data: timeNode
  })
}

/**
 * 删除时间节点
 */
export const deleteTimeNode = (id: number) => {
  return request({
    url: `/timenode/${id}`,
    method: 'delete'
  })
}





