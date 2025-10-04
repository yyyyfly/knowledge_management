/**
 * 荣誉服务 - 统一Mock和API的接口
 */
import { USE_MOCK_DATA } from '@/config/dataSource'

// Mock导入
import * as mockHonors from '@/mock/honors'

// API导入
import * as apiHonor from '@/api/honor'

// 类型定义
export interface Honor {
  id?: number
  title: string
  description?: string
  category: string
  achievedTime: string
  projectId?: number
  icon?: string
  createTime?: string
}

/**
 * 获取所有荣誉（统一返回格式）
 */
export const getAllHonors = async (): Promise<Honor[]> => {
  if (USE_MOCK_DATA) {
    // Mock数据是同步的，包装成Promise
    return Promise.resolve(mockHonors.getAllHonors())
  } else {
    // API数据已经是Promise
    const response = await apiHonor.getAllHonors()
    return response.data || []
  }
}

/**
 * 根据类别获取荣誉（统一返回格式）
 */
export const getHonorsByCategory = async (category: string): Promise<Honor[]> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(mockHonors.getHonorsByCategory(category as any))
  } else {
    const response = await apiHonor.getHonorsByCategory(category)
    return response.data || []
  }
}

/**
 * 删除荣誉（统一返回格式）
 */
export const deleteHonor = async (id: number): Promise<boolean> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(mockHonors.deleteHonor(id))
  } else {
    const response = await apiHonor.deleteHonor(id)
    return response.code === 200
  }
}

/**
 * 创建荣誉
 */
export const createHonor = async (honor: Honor): Promise<Honor | null> => {
  if (USE_MOCK_DATA) {
    // Mock暂不支持，返回null
    console.warn('Mock模式暂不支持创建荣誉')
    return Promise.resolve(null)
  } else {
    const response = await apiHonor.createHonor(honor)
    return response.data
  }
}

/**
 * 更新荣誉
 */
export const updateHonor = async (honor: Honor): Promise<boolean> => {
  if (USE_MOCK_DATA) {
    console.warn('Mock模式暂不支持更新荣誉')
    return Promise.resolve(false)
  } else {
    const response = await apiHonor.updateHonor(honor)
    return response.code === 200
  }
}

/**
 * 从项目添加荣誉
 */
export const addHonorFromProject = async (
  projectId: number,
  projectName: string,
  projectDescription: string,
  category: string
): Promise<Honor | null> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(
      mockHonors.addHonorFromProject(projectId, projectName, projectDescription, category as any)
    )
  } else {
    // 调用创建接口
    const honor: Honor = {
      title: projectName,
      description: projectDescription,
      category,
      achievedTime: new Date().toISOString().slice(0, 19).replace('T', ' '),
      projectId
    }
    return createHonor(honor)
  }
}

/**
 * 检查项目是否已在荣誉中
 */
export const isProjectInHonors = async (projectId: number): Promise<boolean> => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(mockHonors.isProjectInHonors(projectId))
  } else {
    const honors = await getAllHonors()
    return honors.some(h => h.projectId === projectId)
  }
}

/**
 * 获取荣誉统计
 */
export const getHonorsStats = async () => {
  if (USE_MOCK_DATA) {
    return Promise.resolve(mockHonors.getHonorsStats())
  } else {
    const honors = await getAllHonors()
    return {
      total: honors.length,
      defense: honors.filter(h => h.category === 'defense').length,
      construction: honors.filter(h => h.category === 'construction').length,
      diplomatic: honors.filter(h => h.category === 'diplomatic').length
    }
  }
}

