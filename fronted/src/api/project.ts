/**
 * 项目相关API
 */
import request from './request'

// 项目接口类型定义
export interface Project {
  id?: number
  name: string
  description: string
  status: string
  category: string
  taskCount?: number
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
}

/**
 * 获取所有项目
 */
export const getAllProjects = () => {
  return request({
    url: '/project/list',
    method: 'get'
  })
}

/**
 * 根据ID获取项目
 */
export const getProjectById = (id: number) => {
  return request({
    url: `/project/${id}`,
    method: 'get'
  })
}

/**
 * 根据类别获取项目
 */
export const getProjectsByCategory = (category: string) => {
  return request({
    url: `/project/category/${category}`,
    method: 'get'
  })
}

/**
 * 根据状态获取项目
 */
export const getProjectsByStatus = (status: string) => {
  return request({
    url: `/project/status/${status}`,
    method: 'get'
  })
}

/**
 * 创建项目
 */
export const createProject = (project: Project) => {
  return request({
    url: '/project',
    method: 'post',
    data: project
  })
}

/**
 * 更新项目
 */
export const updateProject = (project: Project) => {
  return request({
    url: '/project',
    method: 'put',
    data: project
  })
}

/**
 * 删除项目
 */
export const deleteProject = (id: number) => {
  return request({
    url: `/project/${id}`,
    method: 'delete'
  })
}

