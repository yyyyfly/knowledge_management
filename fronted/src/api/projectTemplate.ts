import request from './request'

export interface ProjectRequirement {
  name: string
  desc: string
  children?: ProjectRequirement[]
}

export interface ProjectTemplate {
  id?: number
  domain: string
  name: string
  description: string
  requirements: string  // JSON string
  commonFeatures: string  // JSON string
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
  archFlag?: number
}

/**
 * 获取所有项目模板
 */
export const getAllProjectTemplates = () => {
  return request.get('/projecttemplate/list')
}

/**
 * 根据ID获取项目模板
 */
export const getProjectTemplateById = (id: number) => {
  return request.get(`/projecttemplate/${id}`)
}

/**
 * 根据领域获取项目模板
 */
export const getProjectTemplatesByDomain = (domain: string) => {
  return request.get(`/projecttemplate/domain/${domain}`)
}

/**
 * 创建项目模板
 */
export const createProjectTemplate = (projectTemplate: ProjectTemplate) => {
  return request.post('/projecttemplate', projectTemplate)
}

/**
 * 更新项目模板
 */
export const updateProjectTemplate = (projectTemplate: ProjectTemplate) => {
  return request.put('/projecttemplate', projectTemplate)
}

/**
 * 删除项目模板
 */
export const deleteProjectTemplate = (id: number) => {
  return request.delete(`/projecttemplate/${id}`)
}

