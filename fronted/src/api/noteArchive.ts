import request from './request'

/**
 * 笔记归档API
 */

// 归档类型
export interface NoteArchive {
  id?: number
  archiveName: string
  archiveType?: string
  description?: string
  sourceInfo?: string
  tags?: string
  noteCount?: number
  recCreateTime?: string
  recReviseTime?: string
}

// 归档详情类型
export interface NoteArchiveDetail extends NoteArchive {
  notes?: NoteArchiveNote[]
}

// 归档关联笔记类型
export interface NoteArchiveNote {
  relationId?: number
  noteId: number
  title: string
  type: string
  summary?: string
  tags?: string
  relationNote?: string
  recCreateTime?: string
}

/**
 * 创建归档
 */
export const createArchive = (data: NoteArchive) => {
  return request.post('/api/note-archive/create', data)
}

/**
 * 更新归档
 */
export const updateArchive = (data: NoteArchive) => {
  return request.post('/api/note-archive/update', data)
}

/**
 * 删除归档
 */
export const deleteArchive = (id: number) => {
  return request.delete(`/api/note-archive/delete/${id}`)
}

/**
 * 查询归档详情
 */
export const getArchiveDetail = (id: number) => {
  return request.get<NoteArchiveDetail>(`/api/note-archive/detail/${id}`)
}

/**
 * 查询归档列表
 */
export const getArchiveList = (params?: { archiveName?: string; archiveType?: string }) => {
  return request.get<NoteArchive[]>('/api/note-archive/list', { params })
}

/**
 * 添加笔记到归档
 */
export const addNoteToArchive = (data: { archiveId: number; noteId: number; relationNote?: string }) => {
  return request.post('/api/note-archive/add-note', null, { params: data })
}

/**
 * 从归档中移除笔记
 */
export const removeNoteFromArchive = (relationId: number, archiveId: number) => {
  return request.delete('/api/note-archive/remove-note', { 
    params: { relationId, archiveId } 
  })
}

/**
 * 更新关联备注
 */
export const updateRelationNote = (relationId: number, relationNote: string) => {
  return request.post('/api/note-archive/update-relation-note', null, { 
    params: { relationId, relationNote } 
  })
}

/**
 * 查询笔记关联的所有归档
 */
export const getArchivesByNoteId = (noteId: number) => {
  return request.get<number[]>(`/api/note-archive/by-note/${noteId}`)
}

