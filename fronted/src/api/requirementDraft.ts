/**
 * 需求草案相关API
 */
import request from './request'

// 需求草案接口类型定义
export interface RequirementDraft {
  id?: number
  version: string
  title: string
  description?: string
  status?: string // drafting-草拟中, approved-已通过, abandoned-已废弃
  sortOrder?: number
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
  archFlag?: number
}

/**
 * 获取所有需求草案（按版本分组）
 */
export const getAllRequirementDrafts = () => {
  return request({
    url: '/requirement-draft/list',
    method: 'get'
  })
}

/**
 * 根据ID获取需求草案
 */
export const getRequirementDraftById = (id: number) => {
  return request({
    url: `/requirement-draft/${id}`,
    method: 'get'
  })
}

/**
 * 获取所有版本号列表
 */
export const getVersionList = () => {
  return request({
    url: '/requirement-draft/versions',
    method: 'get'
  })
}

/**
 * 根据版本号获取需求草案
 */
export const getRequirementDraftsByVersion = (version: string) => {
  return request({
    url: `/requirement-draft/version/${version}`,
    method: 'get'
  })
}

/**
 * 创建需求草案
 */
export const createRequirementDraft = (draft: RequirementDraft) => {
  return request({
    url: '/requirement-draft',
    method: 'post',
    data: draft
  })
}

/**
 * 更新需求草案
 */
export const updateRequirementDraft = (draft: RequirementDraft) => {
  return request({
    url: '/requirement-draft',
    method: 'put',
    data: draft
  })
}

/**
 * 删除需求草案
 */
export const deleteRequirementDraft = (id: number) => {
  return request({
    url: `/requirement-draft/${id}`,
    method: 'delete'
  })
}

