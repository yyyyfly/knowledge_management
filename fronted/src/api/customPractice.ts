import request from './request'

export interface CustomPractice {
  id?: number
  practiceName: string
  scenarioType: string  // 场景类型（考试/项目/竞赛）
  scenarioDetail?: string  // 场景详情
  difficulty?: string
  background?: string  // 背景说明
  description: string
  targetSkills: string  // JSON string
  techStack?: string  // JSON string - 指定技术栈
  constraints?: string  // JSON string - 限制条件
  practiceContent: string  // JSON string
  evaluationCriteria: string  // JSON string
  referenceMaterials?: string  // JSON string - 参考资料
  timeLimit?: number
  compatibilityNotes?: string  // 模板兼容性说明
  tags?: string
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
}

/**
 * 获取所有定制模拟实战
 */
export const getAllCustomPractices = () => {
  return request.get('/api/custom-practice/list')
}

/**
 * 根据ID获取定制模拟实战
 */
export const getCustomPracticeById = (id: number) => {
  return request.get(`/api/custom-practice/${id}`)
}

/**
 * 创建定制模拟实战
 */
export const createCustomPractice = (data: CustomPractice) => {
  return request.post('/api/custom-practice', data)
}

/**
 * 更新定制模拟实战
 */
export const updateCustomPractice = (data: CustomPractice) => {
  return request.put('/api/custom-practice', data)
}

/**
 * 删除定制模拟实战
 */
export const deleteCustomPractice = (id: number) => {
  return request.delete(`/api/custom-practice/${id}`)
}

