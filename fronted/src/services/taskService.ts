/**
 * 任务服务 - 统一Mock和API的接口
 */
import { USE_MOCK_DATA } from '@/config/dataSource'

// Mock导入
import { tasks } from '@/mock/tasks'

// API导入
import * as apiTask from '@/api/task'

// 类型定义
export interface Task {
  id?: number
  title: string
  description?: string
  status: string
  priority?: string
  projectId?: number
  assignee?: string
  startDate?: string
  dueDate?: string
  completedTime?: string
  tags?: string[]
  recCreator?: string
  recCreateTime?: string
  createTime?: string
}

/**
 * 获取所有任务
 */
export const getAllTasks = async (): Promise<Task[]> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve([...tasks])
  } else {
    const response = await apiTask.getAllTasks()
    return response.data || []
  }
}

/**
 * 根据状态获取任务
 */
export const getTasksByStatus = async (status: string): Promise<Task[]> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(tasks.filter(t => t.status === status))
  } else {
    const response = await apiTask.getTasksByStatus(status)
    return response.data || []
  }
}

/**
 * 根据项目ID获取任务
 */
export const getTasksByProject = async (projectId: number): Promise<Task[]> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(tasks.filter(t => t.projectId === projectId))
  } else {
    const response = await apiTask.getTasksByProject(projectId)
    return response.data || []
  }
}

/**
 * 创建任务
 */
export const createTask = async (task: Task): Promise<Task | null> => {
  if (USE_MOCK_DATA) {
    console.warn('Mock模式暂不支持创建任务')
    return Promise.resolve(null)
  } else {
    const response = await apiTask.createTask(task)
    return response.data
  }
}

/**
 * 更新任务
 */
export const updateTask = async (task: Task): Promise<boolean> => {
  if (USE_MOCK_DATA) {
    console.warn('Mock模式暂不支持更新任务')
    return Promise.resolve(false)
  } else {
    const response = await apiTask.updateTask(task)
    return response.code === 200
  }
}

/**
 * 删除任务
 */
export const deleteTask = async (id: number): Promise<boolean> => {
  if (USE_MOCK_DATA) {
    console.warn('Mock模式暂不支持删除任务')
    return Promise.resolve(false)
  } else {
    const response = await apiTask.deleteTask(id)
    return response.code === 200
  }
}





