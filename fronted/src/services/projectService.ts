/**
 * 项目服务 - 统一Mock和API的接口
 */
import { USE_MOCK_DATA } from '@/config/dataSource'

// Mock导入
import { projects } from '@/mock/projects'

// API导入
import * as apiProject from '@/api/project'

// 类型定义
export interface Project {
  id?: number
  name: string
  description?: string
  category: string
  status: string
  priority?: string
  startDate?: string
  endDate?: string
  tags?: string[]
  progress?: number
  recCreator?: string
  recCreateTime?: string
  createTime?: string
}

/**
 * 获取所有项目
 */
export const getAllProjects = async (): Promise<Project[]> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve([...projects])
  } else {
    const response = await apiProject.getAllProjects()
    return response.data || []
  }
}

/**
 * 根据类别获取项目
 */
export const getProjectsByCategory = async (category: string): Promise<Project[]> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(projects.filter(p => p.category === category))
  } else {
    const response = await apiProject.getProjectsByCategory(category)
    return response.data || []
  }
}

/**
 * 根据状态获取项目
 */
export const getProjectsByStatus = async (status: string): Promise<Project[]> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(projects.filter(p => p.status === status))
  } else {
    const response = await apiProject.getProjectsByStatus(status)
    return response.data || []
  }
}

/**
 * 创建项目
 */
export const createProject = async (project: Project): Promise<Project | null> => {
  if (USE_MOCK_DATA) {
    console.warn('Mock模式暂不支持创建项目')
    return Promise.resolve(null)
  } else {
    const response = await apiProject.createProject(project)
    return response.data
  }
}

/**
 * 更新项目
 */
export const updateProject = async (project: Project): Promise<boolean> => {
  if (USE_MOCK_DATA) {
    console.warn('Mock模式暂不支持更新项目')
    return Promise.resolve(false)
  } else {
    const response = await apiProject.updateProject(project)
    return response.code === 200
  }
}

/**
 * 删除项目
 */
export const deleteProject = async (id: number): Promise<boolean> => {
  if (USE_MOCK_DATA) {
    console.warn('Mock模式暂不支持删除项目')
    return Promise.resolve(false)
  } else {
    const response = await apiProject.deleteProject(id)
    return response.code === 200
  }
}





