/**
 * 荣誉战绩相关API
 */
import request from './request'

// 荣誉战绩接口类型定义
export interface Honor {
  id?: number
  title: string
  description?: string
  category: string // defense-战争行动, construction-工程建设, diplomatic-外交活动
  achievedTime: string
  projectId?: number
  icon?: string
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
}

/**
 * 获取所有荣誉
 */
export const getAllHonors = () => {
  return request({
    url: '/honor/list',
    method: 'get'
  })
}

/**
 * 根据ID获取荣誉
 */
export const getHonorById = (id: number) => {
  return request({
    url: `/honor/${id}`,
    method: 'get'
  })
}

/**
 * 根据类别获取荣誉
 */
export const getHonorsByCategory = (category: string) => {
  return request({
    url: `/honor/category/${category}`,
    method: 'get'
  })
}

/**
 * 创建荣誉
 */
export const createHonor = (honor: Honor) => {
  return request({
    url: '/honor',
    method: 'post',
    data: honor
  })
}

/**
 * 更新荣誉
 */
export const updateHonor = (honor: Honor) => {
  return request({
    url: '/honor',
    method: 'put',
    data: honor
  })
}

/**
 * 删除荣誉
 */
export const deleteHonor = (id: number) => {
  return request({
    url: `/honor/${id}`,
    method: 'delete'
  })
}

