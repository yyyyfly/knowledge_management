/**
 * 任务相关API
 */
import request from './request'

// 任务接口类型定义
export interface Task {
  id?: number
  projectId: number
  name: string
  description?: string
  status: string
  deadline?: string
  completedTime?: string
  newProgress?: string
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
}

/**
 * 获取所有任务
 */
export const getAllTasks = () => {
  return request({
    url: '/task/list',
    method: 'get'
  })
}

/**
 * 根据ID获取任务
 */
export const getTaskById = (id: number) => {
  return request({
    url: `/task/${id}`,
    method: 'get'
  })
}

/**
 * 根据项目ID获取任务
 */
export const getTasksByProjectId = (projectId: number) => {
  return request({
    url: `/task/project/${projectId}`,
    method: 'get'
  })
}

/**
 * 根据状态获取任务
 */
export const getTasksByStatus = (status: string) => {
  return request({
    url: `/task/status/${status}`,
    method: 'get'
  })
}

/**
 * 创建任务
 */
export const createTask = (task: Task) => {
  return request({
    url: '/task',
    method: 'post',
    data: task
  })
}

/**
 * 更新任务
 */
export const updateTask = (task: Task) => {
  return request({
    url: '/task',
    method: 'put',
    data: task
  })
}

/**
 * 删除任务
 */
export const deleteTask = (id: number) => {
  return request({
    url: `/task/${id}`,
    method: 'delete'
  })
}

