import request from './request'

export interface NoteConfig {
  id?: number
  noteType: 'fragment' | 'study' | 'memorization' | 'exercise' | 'practical' | 'framework'
  configType: string  // category, theme, subject 等
  configName: string
  sortOrder?: number
  recCreator?: string
  recCreateTime?: string
  recRevisor?: string
  recReviseTime?: string
}

/**
 * 获取所有配置
 */
export const getAllConfigs = () => {
  return request.get<NoteConfig[]>('/note-config/list')
}

/**
 * 根据笔记类型获取配置
 */
export const getConfigsByNoteType = (noteType: string) => {
  return request.get<NoteConfig[]>(`/note-config/note-type/${noteType}`)
}

/**
 * 根据笔记类型和配置类型获取配置
 */
export const getConfigsByNoteAndConfigType = (noteType: string, configType: string) => {
  return request.get<NoteConfig[]>(`/note-config/note-type/${noteType}/config-type/${configType}`)
}

/**
 * 创建配置
 */
export const createConfig = (config: Omit<NoteConfig, 'id' | 'recCreator' | 'recCreateTime' | 'recRevisor' | 'recReviseTime'>) => {
  return request.post<NoteConfig>('/note-config', config)
}

/**
 * 更新配置
 */
export const updateConfig = (config: NoteConfig) => {
  return request.put<NoteConfig>('/note-config', config)
}

/**
 * 删除配置
 */
export const deleteConfig = (id: number) => {
  return request.delete<string>(`/note-config/${id}`)
}

