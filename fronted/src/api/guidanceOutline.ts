import request from './request'

/**
 * 指导大纲 API
 */

export interface GuidanceOutline {
  id?: number
  subjectName: string
  subjectCategory?: string
  knowledgeTree?: string
  tags?: string
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
  archFlag?: number
}

/**
 * 获取所有指导大纲
 */
export const getAllGuidanceOutlines = () => {
  return request.get<GuidanceOutline[]>('/guidance-outline/list')
}

/**
 * 根据ID获取指导大纲
 */
export const getGuidanceOutlineById = (id: number) => {
  return request.get<GuidanceOutline>(`/guidance-outline/${id}`)
}

/**
 * 根据分类获取指导大纲
 */
export const getGuidanceOutlinesByCategory = (category: string) => {
  return request.get<GuidanceOutline[]>(`/guidance-outline/category/${category}`)
}

/**
 * 搜索指导大纲
 */
export const searchGuidanceOutlines = (keyword: string) => {
  return request.get<GuidanceOutline[]>('/guidance-outline/search', { params: { keyword } })
}

/**
 * 创建指导大纲
 */
export const createGuidanceOutline = (data: GuidanceOutline) => {
  return request.post<GuidanceOutline>('/guidance-outline', data)
}

/**
 * 更新指导大纲
 */
export const updateGuidanceOutline = (data: GuidanceOutline) => {
  return request.put<GuidanceOutline>('/guidance-outline', data)
}

/**
 * 删除指导大纲
 */
export const deleteGuidanceOutline = (id: number) => {
  return request.delete<string>(`/guidance-outline/${id}`)
}

